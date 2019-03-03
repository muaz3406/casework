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
    private Coupon coupon;
    private Campaign campaign;

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

    public void addAmountCouponDiscount(Coupon coupon) {
        BigDecimal discount = coupon.getDiscountCost();
        this.coupon = coupon;
        this.couponDiscount = discount;
        this.totalPrice = totalPrice.subtract(discount);
    }


    public void addCouponRateDiscount(Coupon coupon) {
        BigDecimal discount = BigDecimalUtils.getRateDiscount(getTotalPrice(), coupon.getDiscountRate());
        this.coupon = coupon;
        this.totalPrice = totalPrice.subtract(discount);
        this.couponDiscount = discount;
    }

    public void addCampaingDiscount(BigDecimal discount, Campaign campaign) {
        this.campaign = campaign;
        this.campaignDiscount = campaignDiscount.add(discount);
        this.totalPrice = totalPrice.subtract(discount);
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
