package com.muaz.demo.service;

import com.muaz.demo.enums.DiscountType;
import com.muaz.demo.model.Coupon;
import com.muaz.demo.model.ShoppingCart;

public class CouponApplyingService {
    private static final int BIGGER_THAN = 1;

    public void couponApplyingTo(ShoppingCart shoppingCart ,Coupon coupon) {

            if (coupon == null ) {
                return;
            }
            updateShoppingCart(coupon, shoppingCart);
    }

    private void updateShoppingCart(Coupon coupon, ShoppingCart shoppingCart) {

        if (isApplicableAmounDiscount(coupon, shoppingCart)) {
            shoppingCart.addAmountCouponDiscount(coupon.getDiscountCost());
        }
        if (isApplicabbleRateDiscount(coupon, shoppingCart)) {
            shoppingCart.addCouponRateDiscount(coupon.getDiscountRate());
        }
    }

    private boolean isApplicabbleRateDiscount(Coupon coupon, ShoppingCart shoppingCart) {

        if (isNotRateDiscount(coupon)) {
            return false;
        }

        if (isNotMinApplicableAmount(coupon, shoppingCart)) {
            return false;
        }
        return true;
    }

    private boolean isApplicableAmounDiscount(Coupon coupon, ShoppingCart shoppingCart) {

        if (isNotAmountDiscount(coupon)) {
            return false;
        }

        if (isNotMinApplicableAmount(coupon, shoppingCart)) {
            return false;
        }
        return true;
    }

    private boolean isNotRateDiscount(Coupon coupon) {
        return !isNotAmountDiscount(coupon);
    }

    private boolean isNotMinApplicableAmount(Coupon coupon, ShoppingCart shoppingCart) {
        return coupon.getMinApplicableAmount().compareTo(shoppingCart.getTotalPrice()) == BIGGER_THAN;
    }

    private boolean isNotAmountDiscount(Coupon coupon) {
        return coupon.getDiscountType() != DiscountType.AMOUNT;
    }
}
