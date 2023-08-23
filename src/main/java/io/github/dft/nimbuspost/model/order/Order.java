package io.github.dft.nimbuspost.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Order {

    private String id;
    private String channelId;
    private String orderNumber;
    private String orderDate;
    private String orderAmount;
    private String paymentMethod;
    private String shippingFname;
    private String shippingLname;
    private String shippingAddress;
    @JsonProperty("shipping_address_2")
    private String shippingAddress2;
    private String shippingPhone;
    private String shippingCity;
    private String shippingState;
    private String shippingCountry;
    private String shippingZip;
    private String packageWeight;
    private String packageLength;
    private String packageHeight;
    private String packageBreadth;
    private String whatsappTags;
    private String tags;
    private String status;
    private List<Product> products;
}