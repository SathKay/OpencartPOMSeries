package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.utils.Constants;

public class ShoppingCartPageTest extends BaseTest{
	
	@BeforeTest
	public void shoppingCartSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		homePage = accPage.goToHomePage();
		productInfoPage = homePage.selectProduct("iPhone");
		overallProdQuantity = overallProdQuantity + Integer.valueOf(productInfoPage.getProductQuantity());
		shoppingCartPage = productInfoPage.addToCart();
	}
	
	@DataProvider
	public Object[][] cartProductDetailsTestData() {
		return new Object[][] {
			{"iPhone", "product 11", "$123.20"}
		};
	}
	
	@Test(dataProvider="cartProductDetailsTestData")
	public void addToCartTest(String productName, String productModel, String unitPrice)
	{
		Map<String, String> detailsMap = shoppingCartPage.getCartDetails();
		softAssert.assertEquals(detailsMap.get("Product Name"), productName);
		softAssert.assertEquals(detailsMap.get("Product Model"), productModel);
		softAssert.assertEquals(detailsMap.get("Unit Price"), unitPrice);
		softAssert.assertEquals(detailsMap.get("Quantity"), String.valueOf(overallProdQuantity));
		softAssert.assertAll();
	}
	
	@Test
	public void shippingTest() {
		String shippingRate = shoppingCartPage.applyShipping();
		String appliedShippingRate = shoppingCartPage.getAppliedShippingRate();
		System.out.println(appliedShippingRate);
		//Assert.assertTrue(shippingRate.contains(appliedShippingRate));
	}
}
