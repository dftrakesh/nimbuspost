package io.github.dft.nimbuspost.model.shipment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShipmentRequest {
    private String orderNumber;
    private Integer shippingCharges;
    private Integer discount;
    private Integer codCharges;
    private String paymentType;
    private Integer orderAmount;
    private Integer packageWeight;
    private Integer packageLength;
    private Integer packageBreadth;
    private Integer packageHeight;
    private String requestAutoPickup;
    private Consignee consignee;
    private Pickup pickup;
    private List<OrderItem> orderItems;
    private String courierId;
    private String isInsurance;
    private String tags;

    public void setTags(List<String> tags) {
        this.tags = String.join(", ", tags);
    }
}