package com.muaz.demo.service;

import com.muaz.demo.enums.CategoryEnum;
import com.muaz.demo.enums.DiscountType;
import com.muaz.demo.model.Campaign;
import com.muaz.demo.model.Product;
import com.muaz.demo.model.ShoppingCart;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class CampaignApplyingServiceTest {

    @InjectMocks
    private CampaignApplyingService campaignApplyingService;

    @Test
    public void shouldNotApplyCampaignWhenNotApplicableAmount() {

        Campaign campaign1 = new Campaign();
        campaign1.setTitle("campaign1");
        campaign1.setCategoryEnum(CategoryEnum.ELECTRONIC);
        campaign1.setDiscountType(DiscountType.AMOUNT);
        campaign1.setMinProductAmount(BigDecimal.valueOf(2));
        campaign1.setDiscountCost(BigDecimal.valueOf(10d));

        Product product1 = new Product();
        product1.setProductName("samsung");
        product1.setCategoryEnum(CategoryEnum.ELECTRONIC);
        product1.setPrice(BigDecimal.valueOf(100.0d));

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(product1, 1);

        campaignApplyingService.campaignApplyTo(shoppingCart, new ArrayList<>(Arrays.asList(campaign1)));

        assertThat(BigDecimal.valueOf(100d), Matchers.comparesEqualTo(shoppingCart.getTotalPrice()));
        assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(shoppingCart.getCampaignDiscount()));
    }

    @Test
    public void shouldApplyRateTypeCampaignWhenApplicableAmount() {

        Campaign campaign1 = new Campaign();
        campaign1.setTitle("campaign1");
        campaign1.setCategoryEnum(CategoryEnum.ELECTRONIC);
        campaign1.setDiscountType(DiscountType.RATE);
        campaign1.setMinProductAmount(BigDecimal.valueOf(1));
        campaign1.setDiscountRate(BigDecimal.TEN);

        Product product1 = new Product();
        product1.setProductName("samsung");
        product1.setCategoryEnum(CategoryEnum.ELECTRONIC);
        product1.setPrice(BigDecimal.valueOf(100d));

        Product product2 = new Product();
        product2.setProductName("ayfon");
        product2.setCategoryEnum(CategoryEnum.ELECTRONIC);
        product2.setPrice(BigDecimal.valueOf(200d));

        Product product3 = new Product();
        product3.setProductName("nayk");
        product3.setCategoryEnum(CategoryEnum.CLOTHES);
        product3.setPrice(BigDecimal.valueOf(300d));

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(product1, 1);
        shoppingCart.addProduct(product2, 1);
        shoppingCart.addProduct(product3, 1);

        campaignApplyingService.campaignApplyTo(shoppingCart, new ArrayList<>(Arrays.asList(campaign1)));

        assertThat(BigDecimal.valueOf(570d), Matchers.comparesEqualTo(shoppingCart.getTotalPrice()));
        assertThat(BigDecimal.valueOf(30d), Matchers.comparesEqualTo(shoppingCart.getCampaignDiscount()));
    }

    @Test
    public void shouldApplyAmountTypeCampaignWhenApplicableAmount() {
        Campaign campaign1 = new Campaign();
        campaign1.setTitle("campaign1");
        campaign1.setCategoryEnum(CategoryEnum.ELECTRONIC);
        campaign1.setDiscountType(DiscountType.AMOUNT);
        campaign1.setMinProductAmount(BigDecimal.valueOf(1));
        campaign1.setDiscountCost(BigDecimal.TEN);

        Product product1 = new Product();
        product1.setProductName("samsung");
        product1.setCategoryEnum(CategoryEnum.ELECTRONIC);
        product1.setPrice(BigDecimal.valueOf(100d));

        Product product2 = new Product();
        product2.setProductName("ayfon");
        product2.setCategoryEnum(CategoryEnum.ELECTRONIC);
        product2.setPrice(BigDecimal.valueOf(200d));

        Product product3 = new Product();
        product3.setProductName("nayk");
        product3.setCategoryEnum(CategoryEnum.CLOTHES);
        product3.setPrice(BigDecimal.valueOf(300d));

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(product1, 1);
        shoppingCart.addProduct(product2, 1);
        shoppingCart.addProduct(product3, 1);

        campaignApplyingService.campaignApplyTo(shoppingCart, new ArrayList<>(Arrays.asList(campaign1)));

        assertThat(BigDecimal.valueOf(590d), Matchers.comparesEqualTo(shoppingCart.getTotalPrice()));
        assertThat(BigDecimal.valueOf(10d), Matchers.comparesEqualTo(shoppingCart.getCampaignDiscount()));
    }

    @Test
    public void shouldApplyBestCampaign() {

        Campaign campaign1 = new Campaign();
        campaign1.setTitle("campaign1");
        campaign1.setCategoryEnum(CategoryEnum.ELECTRONIC);
        campaign1.setDiscountType(DiscountType.AMOUNT);
        campaign1.setMinProductAmount(BigDecimal.valueOf(1));
        campaign1.setDiscountCost(BigDecimal.valueOf(10));

        Campaign campaign2 = new Campaign();
        campaign2.setTitle("campaign2");
        campaign2.setCategoryEnum(CategoryEnum.ELECTRONIC);
        campaign2.setDiscountType(DiscountType.AMOUNT);
        campaign2.setMinProductAmount(BigDecimal.valueOf(1));
        campaign2.setDiscountCost(BigDecimal.valueOf(20));

        Campaign campaign3 = new Campaign();
        campaign3.setTitle("campaign3");
        campaign3.setCategoryEnum(CategoryEnum.CLOTHES);
        campaign3.setDiscountType(DiscountType.RATE);
        campaign3.setMinProductAmount(BigDecimal.valueOf(1));
        campaign3.setDiscountRate(BigDecimal.valueOf(50));

        Product product1 = new Product();
        product1.setProductName("samsung");
        product1.setCategoryEnum(CategoryEnum.ELECTRONIC);
        product1.setPrice(BigDecimal.valueOf(100));

        Product product2 = new Product();
        product2.setProductName("ayfon");
        product2.setCategoryEnum(CategoryEnum.CLOTHES);
        product2.setPrice(BigDecimal.valueOf(200));

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(product1, 1);
        shoppingCart.addProduct(product2, 1);

        campaignApplyingService.campaignApplyTo(shoppingCart, new ArrayList<>(Arrays.asList(campaign1, campaign2, campaign3)));

        assertThat(BigDecimal.valueOf(200d), Matchers.comparesEqualTo(shoppingCart.getTotalPrice()));
        assertThat(BigDecimal.valueOf(100d), Matchers.comparesEqualTo(shoppingCart.getCampaignDiscount()));
    }
}