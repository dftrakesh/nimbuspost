package io.github.dft.nimbuspost;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class NimbusSDK {

    private final int MAX_ATTEMPTS = 50;
    private final int TIME_OUT_DURATION = 2000;
    private final String AUTHORIZATION = "NP-API-KEY";
    private final String API_BASE_URL = "https://ship.nimbuspost.com/api";

    private final String apiKey;
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public NimbusSDK(String apiKey) {
        this.apiKey = apiKey;
        objectMapper = new ObjectMapper();
        this.client = HttpClient.newHttpClient();
    }

    @SneakyThrows
    protected URI baseUrl(String endpoint) {
        return URI.create(API_BASE_URL + endpoint);
    }

    @SneakyThrows
    protected HttpRequest get(URI uri) {
        return HttpRequest.newBuilder(uri)
                          .header(AUTHORIZATION, this.apiKey)
                          .GET()
                          .build();
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
    public <T> CompletableFuture<HttpResponse<T>> tryResend(HttpClient client,
                                                            HttpRequest request,
                                                            HttpResponse.BodyHandler<T> handler,
                                                            HttpResponse<T> resp,
                                                            int count) {
        int statusCode = resp.statusCode();
        if ((statusCode == 401 || statusCode == 429) && count < MAX_ATTEMPTS) {
            Thread.sleep(TIME_OUT_DURATION);
            return client.sendAsync(request, handler)
                         .thenComposeAsync(response -> tryResend(client, request, handler, response, count + 1));
        }
        return CompletableFuture.completedFuture(resp);
    }
}
