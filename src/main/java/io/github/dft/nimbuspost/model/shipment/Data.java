package io.github.dft.nimbuspost.model.shipment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@lombok.Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Data {
    private int orderId;
    private int shipmentId;
    private String awbNumber;
    private String courierId;
    private String courierName;
    private String status;
    private String additionalInfo;
    private String paymentType;
    private String label;
    private String manifest;
}