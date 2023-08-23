package io.github.dft.nimbuspost;

import io.github.dft.nimbuspost.model.order.OrderWrapper;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashMap;

public class NimbusOrderAPI extends NimbusSDK {

    private final String ORDER_ENDPOINT = "/orders";

    public NimbusOrderAPI(String apiKey) {
        super(apiKey);
    }

    public OrderWrapper getOrders(HashMap<String, String> params) {
        URI uri = baseUrl(ORDER_ENDPOINT);
        uri = addParameters(uri, params);
        HttpRequest request = get(uri);

        return getRequestWrapped(request, OrderWrapper.class);
    }
}