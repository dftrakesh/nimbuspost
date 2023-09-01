package io.github.dft.nimbuspost.model.shipment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderItem {
    private String name;
    private Integer qty;
    private Integer price;
    private String sku;
}