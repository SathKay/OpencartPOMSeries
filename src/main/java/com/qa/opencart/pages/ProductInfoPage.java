package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtils;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("div#content img");
	private By productMetadata = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
	private By productPricedata = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");
	private By productQuantity = By.id("input-quantity");
	private By addToCart = By.id("button-cart");
	
	private By homeBtn = By.cssSelector("div#product-info ul.breadcrumb li:nth-of-type(1)");
	
	private By alert = By.xpath("//div[@id='alert']/div[@class='alert alert-success alert-dismissible']//button");
	
	private By cartBtn = By.xpath("//div[@class='dropdown d-grid']/button");
	private By viewCartLink = By.xpath("(//ul[@class='dropdown-menu dropdown-menu-right show']//p/a)[position()=1]");
	
	
	private Map<String, String> productMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public String getProductHeader() {
		return eleUtil.doGetText(productHeader);
	}
	
	public int getProductImageCount() {
		return eleUtil.waitForElementsVisible(10, productImages).size();
	}
	
	/*
	 * This method gives the all the info of the product in key value pair
	 */
	public Map<String, String> getproductInfo() {
		productMap = new TreeMap<String, String>();
		productMap.put("name", getProductHeader());
		productMap.put("totalimages", String.valueOf(getProductImageCount()));
		getProductMetadata();
		getproductPricedata();
		return productMap;
	}
	
	/*
	 * private method so test will not able to call it directly but via getproductInfo()
	 */
	private void getProductMetadata() {
		List<WebElement> metadataList = eleUtil.getElements(productMetadata);
		for(WebElement e: metadataList) {
			String text = e.getText();
			String meta[] = text.split(":");
			String key = meta[0].trim();
			String value = meta[1].trim();
			productMap.put(key, value);
 		}
	}
	
	/*
	 * private method so test will not able to call it directly but via getproductInfo()
	 */
	private void getproductPricedata() {
		List<WebElement> pricedataList = eleUtil.getElements(productPricedata);
		productMap.put("price", pricedataList.get(0).getText().trim());
		productMap.put("Extaxprice",pricedataList.get(1).getText().trim());
	}
	
	//this method is associated with home page product select
	public HomePage goToHome() {
		eleUtil.clickWhenReady(Constants.DEFAULT_TIME_OUT, homeBtn).click();
		return new HomePage(driver);
	}
	
	public void changeProductQuantity(String quantity) {
		eleUtil.doSendKeys(productQuantity, quantity);
	}
	
	public String getProductQuantity() {
		return eleUtil.doGetAttribute(productQuantity, "value");	
	}
	
	public ShoppingCartPage addToCart() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", eleUtil.getElement(addToCart));
		System.out.println("The product is added to the cart");
		//eleUtil.doClick(cartBtn);
		eleUtil.clickWhenReady(Constants.DEFAULT_TIME_OUT, alert).click();
		eleUtil.clickWhenReady(15, cartBtn).click();
		eleUtil.clickWhenReady(10, viewCartLink).click();
		return new ShoppingCartPage(driver);
				
	}
	
	
	
}
