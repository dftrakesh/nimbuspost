package io.github.dft.nimbuspost.model.shipment;

import lombok.Data;

@Data
public class CancelShipmentRequest {
    private String awb;
}