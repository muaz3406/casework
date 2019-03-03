package com.muaz.demo.model;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeliveryCostModel {
    public static final double FIXED_COST = 2.99;
    private BigDecimal costPerDelivery;
    private BigDecimal costPerProduct;
    private BigDecimal totalCostDelivery;
}
