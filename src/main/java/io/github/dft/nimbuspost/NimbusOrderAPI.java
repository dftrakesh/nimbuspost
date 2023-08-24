package io.github.dft.nimbuspost;

import io.github.dft.nimbuspost.model.order.Order;
import io.github.dft.nimbuspost.model.order.OrderWrapper;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static io.github.dft.nimbuspost.constantcodes.ConstantCode.LEGACY_API_BASE_URL;

public class NimbusOrderAPI extends NimbusSDK {

    private final String ORDER_ENDPOINT = "/orders";

    public NimbusOrderAPI(String apiKey) {
        super(apiKey);
    }

    public OrderWrapper getOrders(HashMap<String, String> params) {
        URI uri = baseUrl(LEGACY_API_BASE_URL, ORDER_ENDPOINT);
        uri = addParameters(uri, params);
        HttpRequest request = get(uri);

        return getRequestWrapped(request, OrderWrapper.class);
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();

        int i = 1;
        OrderWrapper orders;
        do {
            params.put("page", String.valueOf(i++));
            orders = getOrders(params);
            orderList.addAll(orders.getData());
        } while (!orders.getData().isEmpty());

        return orderList;
    }
}