package com.muaz.demo.model;

import com.muaz.demo.enums.CategoryEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private String productName;
    private BigDecimal price;
    private CategoryEnum categoryEnum;
    private BigDecimal quantity = BigDecimal.ONE;
}
