package com.muaz.demo.model;

import com.muaz.demo.enums.DiscountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Coupon {

    private BigDecimal discountCost;
    private BigDecimal minApplicableAmount;
    private DiscountType discountType;
    private BigDecimal discountRate;
}
