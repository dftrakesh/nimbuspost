package io.github.dft.nimbuspost.model.order;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Product {
    private String productName;
    private String productQty;
    private String productSku;
    private String productWeight;
    private String productPrice;
}