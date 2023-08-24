package io.github.dft.nimbuspost;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dft.nimbuspost.auth.AuthCredentials;
import io.github.dft.nimbuspost.auth.NimbusCredentials;
import lombok.SneakyThrows;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import static io.github.dft.nimbuspost.constantcodes.ConstantCode.API_BASE_URL;

public class NimbusSDK {

    private final int MAX_ATTEMPTS = 50;
    private final int TIME_OUT_DURATION = 2000;
    private final String USERS_LOGIN_ENDPOINT = "/users/login";

    private String accessToken;
    private String legacyApiKey;
    private String authorization;
    private AuthCredentials authCredentials;

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public NimbusSDK(String apiKey) {
        this.legacyApiKey = apiKey;
        objectMapper = new ObjectMapper();
        this.client = HttpClient.newHttpClient();
    }

    public NimbusSDK(AuthCredentials authCredentials) {
        this.authCredentials = authCredentials;
        objectMapper = new ObjectMapper();
        this.client = HttpClient.newHttpClient();
    }

    @SneakyThrows
    protected URI baseUrl(String apiBaseUrl, String endpoint) {
        return URI.create(apiBaseUrl + endpoint);
    }

    protected HttpRequest get(URI uri) {
        String token = setAuthorizationHeaderAndGetToken(uri);
        return HttpRequest.newBuilder(uri)
                          .header(authorization, token)
                          .GET()
                          .build();
    }

    private String setAuthorizationHeaderAndGetToken(URI uri) {
        String token;
        if (API_BASE_URL.contains(uri.getHost())) {
            authorization = "Authorization";
            refreshAccessToken();
            token = this.accessToken;
        } else {
            authorization = "NP-API-KEY";
            token = this.legacyApiKey;
        }
        return token;
    }

    private void refreshAccessToken() {
        if (this.accessToken == null) {
            HttpRequest request = HttpRequest.newBuilder(URI.create(API_BASE_URL + USERS_LOGIN_ENDPOINT))
                                             .POST(HttpRequest.BodyPublishers.ofString(convertToJson(authCredentials)))
                                             .build();

            NimbusCredentials credentials = getRequestWrapped(request, NimbusCredentials.class);
            this.accessToken = "Bearer " + credentials.getData();
        }
    }

    @SneakyThrows
    protected URI addParameters(URI uri, HashMap<String, String> params) {

        StringBuilder builder = new StringBuilder();
        params.forEach((key, value) -> {
            if (!builder.toString().isEmpty()) builder.append("&");
            builder.append(key.concat("=").concat(value));
        });

        return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), builder.toString(), uri.getFragment());
    }

    @SneakyThrows
    protected <T> T getRequestWrapped(HttpRequest request, Class<T> tClass) {

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                     .thenComposeAsync(response -> tryResend(client, request, HttpResponse.BodyHandlers.ofString(), response, 1))
                     .thenApplyAsync(res -> convertBody(res.body(), tClass))
                     .get();
    }

    @SneakyThrows
    private <T> T convertBody(String body, Class<T> tClass) {
        return objectMapper.readValue(body, tClass);
    }

    @SneakyThrows
    private String convertToJson(Object body) {
        return objectMapper.writeValueAsString(body);
    }

    @SneakyThrows
    private <T> CompletableFuture<HttpResponse<T>> tryResend(HttpClient client,
                                                            HttpRequest request,
                                                            HttpResponse.BodyHandler<T> handler,
                                                            HttpResponse<T> resp,
                                                            int count) {
        int statusCode = resp.statusCode();
        if (statusCode == 401 && count < MAX_ATTEMPTS) {
            Thread.sleep(TIME_OUT_DURATION);
            return client.sendAsync(request, handler)
                         .thenComposeAsync(response -> tryResend(client, request, handler, response, count + 1));
        }
        return CompletableFuture.completedFuture(resp);
    }
}
