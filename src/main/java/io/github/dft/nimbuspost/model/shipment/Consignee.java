package io.github.dft.nimbuspost.model.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Consignee {
    private String name;
    private String address;
    @JsonProperty(value = "address_2")
    private String address2;
    private String city;
    private String state;
    private String pincode;
    private String phone;
}