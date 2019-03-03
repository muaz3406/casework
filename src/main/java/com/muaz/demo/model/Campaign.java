package com.muaz.demo.model;

import com.muaz.demo.enums.CategoryEnum;
import com.muaz.demo.enums.DiscountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Campaign {

    private String title;
    private BigDecimal minProductAmount;
    private DiscountType discountType;
    private BigDecimal discountRate;
    private BigDecimal discountCost;
    private CategoryEnum categoryEnum;

    public boolean isRateDiscount() {
        return discountType == DiscountType.RATE ;
    }

}
