package io.github.dft.nimbuspost;

import io.github.dft.nimbuspost.auth.AuthCredentials;
import io.github.dft.nimbuspost.model.shipment.ShipmentRequest;
import io.github.dft.nimbuspost.model.shipment.ShipmentResponse;
import java.net.URI;
import java.net.http.HttpRequest;
import static io.github.dft.nimbuspost.constantcodes.ConstantCode.API_BASE_URL;

public class NimbusShipmentAPI extends NimbusSDK {

    private final String SHIPMENT_ENDPOINT = "/shipments";

    public NimbusShipmentAPI(AuthCredentials authCredentials) {
        super(authCredentials);
    }

    public ShipmentResponse createShipment(ShipmentRequest shipmentRequest) {
        URI baseUrl = baseUrl(API_BASE_URL, SHIPMENT_ENDPOINT);
        HttpRequest request = post(baseUrl, shipmentRequest);

        return getRequestWrapped(request, ShipmentResponse.class);
    }
}