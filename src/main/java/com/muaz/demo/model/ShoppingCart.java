package com.muaz.demo.model;

import com.muaz.demo.enums.CategoryEnum;
import com.muaz.demo.utils.BigDecimalUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ShoppingCart {
    private List<Product> products = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal campaignDiscount = BigDecimal.ZERO;
    private BigDecimal couponDiscount = BigDecimal.ZERO;
    private BigDecimal deliveryCost = BigDecimal.ZERO;

    public void setProductList(List<Product> products) {

        this.products = products;

        this.totalPrice = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addProduct(Product product, int quantityInCart) {
        BigDecimal multiply = product.getPrice().multiply(BigDecimal.valueOf(quantityInCart));
        product.setPrice(multiply);

        if (products != null) {
            this.products.add(product);
            this.totalPrice = totalPrice.add(multiply);
        }
        if (products == null) {
            setProductList(new ArrayList<>(Arrays.asList(product)));
        }
    }

    public void addAmountCouponDiscount(BigDecimal discountCost) {
        this.couponDiscount = discountCost;
        this.totalPrice = totalPrice.subtract(discountCost);
    }


    public void addCouponRateDiscount(BigDecimal discountRate) {
        BigDecimal discount = BigDecimalUtils.getRateDiscount(getTotalPrice(), discountRate);
        this.totalPrice = totalPrice.subtract(discount);
        this.couponDiscount = discount;
    }

    public void addCampaingDiscount(BigDecimal discountCost) {
        this.campaignDiscount = campaignDiscount.add(discountCost);
        this.totalPrice = totalPrice.subtract(discountCost);
    }

    public void addDelivery(BigDecimal cost) {
        this.totalPrice = totalPrice.add(cost);
        this.deliveryCost = deliveryCost.add(cost);
    }

    public BigDecimal getNumberOfProduct() {
        return BigDecimal.valueOf(products.size());
    }

    public List<Product> getProductsByCategory(CategoryEnum categoryEnum) {
        return products.stream()
                .filter(p -> p.getCategoryEnum() == categoryEnum)
                .collect(Collectors.toList());
    }

    public BigDecimal getNumberOfDelivery() {
        return BigDecimal.valueOf(products.stream()
                .collect(Collectors
                        .groupingBy(Product::getCategoryEnum, Collectors.counting()))
                .size());
    }
}
