package io.github.dft.nimbuspost.model.shipment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@lombok.Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShipmentResponse {
    private Boolean status;
    private Data data;
}