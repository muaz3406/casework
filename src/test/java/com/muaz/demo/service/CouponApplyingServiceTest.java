package com.muaz.demo.service;

import com.muaz.demo.enums.CategoryEnum;
import com.muaz.demo.enums.DiscountType;
import com.muaz.demo.model.Coupon;
import com.muaz.demo.model.Product;
import com.muaz.demo.model.ShoppingCart;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CouponApplyingServiceTest {

    @InjectMocks
    private CouponApplyingService couponApplyingService;

    private Coupon amountCoupon;

    private Coupon rateCoupon;

    private ShoppingCart shoppingCart;

    private Product product;

    @Before
    public void init() {
        amountCoupon = new Coupon();
        amountCoupon.setDiscountType(DiscountType.AMOUNT);
        amountCoupon.setMinApplicableAmount(BigDecimal.valueOf(500));
        amountCoupon.setDiscountCost(BigDecimal.valueOf(300));

        rateCoupon = new Coupon();
        rateCoupon.setDiscountType(DiscountType.RATE);
        rateCoupon.setDiscountRate(BigDecimal.TEN);
        rateCoupon.setMinApplicableAmount(BigDecimal.valueOf(500));

        product = new Product();
        product.setProductName("samsung");
        product.setCategoryEnum(CategoryEnum.ELECTRONIC);
        product.setPrice(BigDecimal.valueOf(30));

        shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(product, 5);

    }

    @Test
    public void shouldNotApplyWhenCouponNotExist() {

        couponApplyingService.couponApplyingTo(shoppingCart, null);

        assertThat(shoppingCart.getCouponDiscount(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertThat(shoppingCart.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(150)));
        assertNull(shoppingCart.getCoupon());
    }

    @Test
    public void shouldNotApplyCouponWhenNotApplicableAmount() {

        couponApplyingService.couponApplyingTo(shoppingCart, amountCoupon);

        assertThat(shoppingCart.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(150)));
        assertThat(shoppingCart.getCouponDiscount(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertNull(shoppingCart.getCoupon());
    }

    @Test
    public void shouldApplyAmountCouponWhenApplicableMinAmount() {

        Product product1 = new Product();
        product1.setPrice(BigDecimal.valueOf(1000));

        shoppingCart.addProduct(product1, 1);

        couponApplyingService.couponApplyingTo(shoppingCart,amountCoupon);

        assertThat(shoppingCart.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(850)));
        assertThat(shoppingCart.getCouponDiscount(), Matchers.comparesEqualTo(BigDecimal.valueOf(300)));
        assertEquals(shoppingCart.getCoupon(), amountCoupon);
    }

    @Test
    public void shouldNotApplyRateCouponWhenApplicableMinAmount() {
        couponApplyingService.couponApplyingTo(shoppingCart, rateCoupon);

        assertThat(shoppingCart.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(150)));
        assertThat(shoppingCart.getCouponDiscount(),  Matchers.comparesEqualTo(BigDecimal.ZERO));
        assertNull(shoppingCart.getCoupon());
    }

    @Test
    public void shouldApplyRateCouponWhenApplicableMinAmount() {

        Product product1 = new Product();
        product1.setPrice(BigDecimal.valueOf(1000));

        shoppingCart.addProduct(product1, 1);

        couponApplyingService.couponApplyingTo(shoppingCart, rateCoupon);

        assertThat(shoppingCart.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(1035)));
        assertEquals(shoppingCart.getCoupon(),rateCoupon);
    }
}