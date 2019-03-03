package com.muaz.demo.service;

import com.muaz.demo.model.DeliveryCostModel;
import com.muaz.demo.model.ShoppingCart;

import java.math.BigDecimal;

public class DeliveryService {

    public void deliveryApplyTo(DeliveryCostModel deliveryCostModel, ShoppingCart shoppingCart) {
        BigDecimal calculatedPerDelivery = deliveryCostModel.getCostPerDelivery().multiply(shoppingCart.getNumberOfDelivery());
        BigDecimal calculatedPerProduct = deliveryCostModel.getCostPerProduct().multiply(shoppingCart.getNumberOfProduct());

        BigDecimal add = calculatedPerDelivery.add(calculatedPerProduct).add(BigDecimal.valueOf(DeliveryCostModel.FIXED_COST));

        shoppingCart.addDelivery(add);
    }
}
