package io.github.dft.nimbuspost.model.order;

import lombok.Data;
import java.util.List;

@Data
public class OrderWrapper {
    private String status;
    private List<Order> data;
}