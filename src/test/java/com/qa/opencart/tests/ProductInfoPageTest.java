package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.utils.Constants;

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void productInfoSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	@Test(enabled = false)
	public void productHeaderTest() {
		resultsPage = accPage.doSearch("macbook");
		productInfoPage = resultsPage.selectProduct("Macbook Air");
		Assert.assertEquals(productInfoPage.getProductHeader(), "Macbook Air");
	}
	
	@DataProvider
	public Object[][] productImagesTestData() {
		return new Object[][] {
			{"Macbook", "Macbook Air", Constants.MACBOOK_IMAGES_COUNT},
			{"Macbook", "Macbook pro", Constants.MACBOOK_IMAGES_COUNT},
			{"iMac", "iMac", Constants.IMAC_IMAGES_COUNT}
		};
	}
	
	@Test(dataProvider="productImagesTestData", enabled = false)
	public void productImageCountTest(String productName, String mainProduct, int totalCount) {
		resultsPage = accPage.doSearch(productName);
		productInfoPage = resultsPage.selectProduct(mainProduct);
		int actualCount = productInfoPage.getProductImageCount();
		System.out.println("Total Images of "+mainProduct+" : "+actualCount);
		Assert.assertEquals(actualCount, totalCount);
	}
	
	
	//should complete this for search and select , productInfo
	@Test(enabled=false)
	public void productInfoTest() {
		resultsPage = accPage.doSearch("macbook");
		productInfoPage = resultsPage.selectProduct("Macbook Air");
		Map<String, String> actProductInfo = productInfoPage.getproductInfo();
	}
	
	//LinkedHashMap
//	name:MacBook
//	totalimages:5
//	Brand:Apple
//	Product Code:Product 16
//	Reward Points:600
//	Availability:In Stock
//	price:$602.00
//	Extaxprice:Ex Tax: $500.00
	
	//TreeMap
//	Availability:In Stock
//	Brand:Apple
//	Extaxprice:Ex Tax: $500.00
//	Product Code:Product 16
//	Reward Points:600
//	name:MacBook
//	price:$602.00
//	totalimages:5
	
	@DataProvider
	public Object[][] homeProductTestData() {
		return new Object[][] {
			{"iPhone", "product 11", "In Stock", "$123.20" , "$101.00"}
		};
		
	}
	
	//should modify for generic performance, including the dataProvider
	@Test(dataProvider="homeProductTestData")
	public void selectproductFromHomeTest(String productName, String productCode, String availability, String price, String exTax) {
		homePage = accPage.goToHomePage();
		productInfoPage = homePage.selectProduct(productName);
		Map<String, String> actProductMap = productInfoPage.getproductInfo();
		actProductMap.forEach((k,v) -> System.out.println(k+":"+v));
		softAssert.assertEquals(actProductMap.get("name"), productName);
		softAssert.assertEquals(actProductMap.get("Availability"), availability);
		softAssert.assertEquals(actProductMap.get("price"), price);
		softAssert.assertEquals(actProductMap.get("Product Code"), productCode);
		softAssert.assertAll();
	}
	
	@DataProvider
	public Object[][] productQuantityTestData() {
		return new Object[][] {
			{"iPhone", "3"}
		};
	}
	
	//same test should be written for search,select and productInfo flow
	@Test(dataProvider="productQuantityTestData")
	public void changeQuantityTest(String productName, String quantity ) {
		homePage = accPage.goToHomePage();
		productInfoPage = homePage.selectProduct(productName);
		productInfoPage.changeProductQuantity(quantity);
		String actQuantity = productInfoPage.getProductQuantity();
		System.out.println("The new product quantity is "+actQuantity);
		Assert.assertEquals(quantity, actQuantity);
	}
	
	@Test
	public void addToCartTest() {
		homePage = accPage.goToHomePage();
		productInfoPage = homePage.selectProduct("iPhone");
		productInfoPage.addToCart();
	}

}
