package com.muaz.demo.service;

import com.muaz.demo.enums.CategoryEnum;
import com.muaz.demo.model.DeliveryCostModel;
import com.muaz.demo.model.Product;
import com.muaz.demo.model.ShoppingCart;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryServiceTest {

    @InjectMocks
    private DeliveryService deliveryService;

    @Test
    public void shouldCalculateDelivery() {

        Product product1 = new Product();
        product1.setProductName("samsung");
        product1.setCategoryEnum(CategoryEnum.ELECTRONIC);
        product1.setPrice(BigDecimal.valueOf(10d));

        Product product2 = new Product();
        product2.setProductName("ayfon");
        product2.setCategoryEnum(CategoryEnum.ELECTRONIC);
        product2.setPrice(BigDecimal.valueOf(20d));

        Product product3 = new Product();
        product3.setProductName("nayk");
        product3.setCategoryEnum(CategoryEnum.CLOTHES);
        product3.setPrice(BigDecimal.valueOf(30d));

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(product1, 2);
        shoppingCart.addProduct(product2, 1);
        shoppingCart.addProduct(product3, 1);

        DeliveryCostModel deliveryCostModel = new DeliveryCostModel();
        deliveryCostModel.setCostPerDelivery(BigDecimal.TEN);
        deliveryCostModel.setCostPerProduct(BigDecimal.ONE);

        deliveryService.deliveryApplyTo(deliveryCostModel, shoppingCart);

        assertThat(shoppingCart.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(95.99d)));
        assertThat(shoppingCart.getDeliveryCost(), Matchers.comparesEqualTo(BigDecimal.valueOf(25.99d)));
        assertThat(DeliveryCostModel.FIXED_COST, equalTo(2.99));
    }
}