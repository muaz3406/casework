package com.muaz.demo.service;

import com.muaz.demo.model.Campaign;
import com.muaz.demo.model.Product;
import com.muaz.demo.model.ShoppingCart;
import com.muaz.demo.utils.BigDecimalUtils;

import java.math.BigDecimal;
import java.util.*;

public class CampaignApplyingService {
    private static final int BIGGER_THAN = 1;

    public void campaignApplyTo(ShoppingCart shoppingCart, List<Campaign> campaigns) {

        Map<BigDecimal, Campaign> campaignMap = new HashMap<>();

        for (Campaign campaign : campaigns) {

            List<Product> categorizedProducts = shoppingCart.getProductsByCategory(campaign.getCategoryEnum());
            BigDecimal totalPriceByCategory = categorizedProducts.stream()
                    .map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (categorizedProducts == null) {
                continue;
            }

            BigDecimal totalQuantity = categorizedProducts.stream()
                    .map(Product::getQuantity)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (isNotMinApplicableAmount(campaign, totalQuantity)) {
                continue;
            }

            campaignMap.putAll(findApplicableDiscount(totalPriceByCategory, campaign, campaignMap));
        }

        updateShoppingCartAndCampaing(shoppingCart, findBestCampaign(campaignMap));
    }

    private void updateShoppingCartAndCampaing(ShoppingCart shoppingCart, Map<BigDecimal, Campaign> campaignMap1) {
        if (campaignMap1 == null) {
            return;
        }

        for (Map.Entry<BigDecimal, Campaign> campaignEntry : campaignMap1.entrySet()) {
            BigDecimal discount = campaignEntry.getKey();
            Campaign campaign = campaignEntry.getValue();
            shoppingCart.addCampaingDiscount(discount,campaign);
        }
    }

    private Map<BigDecimal, Campaign> findBestCampaign(Map<BigDecimal, Campaign> campaignMap) {
        if (campaignMap.isEmpty()) {
            return null;
        }
        Optional<Map.Entry<BigDecimal, Campaign>> maxEntry = campaignMap.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getKey));

        Map<BigDecimal, Campaign> campaignMap1 = new HashMap<>();
        campaignMap1.put(maxEntry.get().getKey(), maxEntry.get().getValue());
        return campaignMap1;
    }

    private Map<BigDecimal, Campaign> findApplicableDiscount(BigDecimal result, Campaign campaign, Map<BigDecimal, Campaign> campaignMap) {

        if (isApplicableAmount(campaign, result)) {
            campaignMap.put(campaign.getDiscountCost(), campaign);
            return campaignMap;
        }

        if (campaign.isRateDiscount()) {

            BigDecimal discount = calculateRateDiscount(result, campaign.getDiscountRate());
            campaignMap.put(discount, campaign);
            return campaignMap;
        }
        return campaignMap;
    }

    private boolean isApplicableAmount(Campaign campaign, BigDecimal result) {

        if (campaign.isRateDiscount()) {
            return false;
        }

        if (result.compareTo(campaign.getDiscountCost()) == BIGGER_THAN) {
            return true;
        }
        return false;
    }

    private BigDecimal calculateRateDiscount(BigDecimal amount, BigDecimal rate) {
        return BigDecimalUtils.getRateDiscount(amount, rate);

    }

    private boolean isNotMinApplicableAmount(Campaign campaign, BigDecimal quantity) {
        return campaign.getMinProductAmount().compareTo(quantity) == BIGGER_THAN;
    }
}
