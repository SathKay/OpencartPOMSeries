package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtils;

public class ShoppingCartPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	//private By cartTable = By.xpath("//div[@id='shopping-cart']//td/a[text()='iPhone']/parent::td/following-sibling::td");
	private By cartProductTable = By.xpath("//div[@id='shopping-cart']//td/a/img[@title='iPhone']/ancestor::td/following-sibling::td");
	private By cartProductQuantity = By.xpath("//div[@id='shopping-cart']//td/a/img[@title='iPhone']/ancestor::td/following-sibling::td//input[@name='quantity']");
	
	private By shippingCollapsible = By.xpath("//div[@id='accordion']//button[text()='Estimate Shipping & Taxes']");
	private By shippingCountry = By.id("input-country");
	private By shippingRegion = By.xpath("//select[@id='input-zone']/option[text()='Karnataka']");
	private By shippingPostCode = By.id("input-postcode");
	private By getQuotesBtn = By.id("button-quote");
	private By shippingFlatRateRadioBtn = By.xpath("//form[@id='form-shipping']//input[@type='radio']");
	private By shippingRate = By.xpath("//form[@id='form-shipping']//label");
	private By applyShippingBtn = By.xpath("//form[@id='form-shipping']//button[@id='button-shipping']");
	
	// Success: Your shipping estimate has been applied! 
	private By shippingRateAlert = By.cssSelector("div#alert div");
	private By shippingRateAlertBtn = By.cssSelector("div#alert button");
	
	private By flatShippingRate = By.xpath("((//tfoot[@id='checkout-total']/tr)[position()=2]/td)[2]");
	
	private By couponCodeCollapsible = By.xpath("//div[@id='accordion']//button[text()='Use Coupon Code']");
	
	private By checkoutBtn = By.xpath("//div[@class='float-end']/a[text()='Checkout']");
	
	private Map<String, String> detailsMap;
	
	public ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public Map<String, String> getCartDetails() {
		detailsMap = new LinkedHashMap<String, String>();
		List<WebElement> detailsList = eleUtil.waitForElementsVisible(Constants.DEFAULT_TIME_OUT, cartProductTable);
		List<String> textList = new ArrayList<String>();
		for(WebElement e:detailsList) {
			String text = e.getText();
			if(!text.isEmpty()) {
			textList.add(text);
			}
		}
		for(int i=0;i<textList.size();i++) {
			detailsMap.put(Constants.SHOPPING_CART_TABLE_KEYS.get(i), textList.get(i));
		}
		detailsMap.put("Quantity", cartProductQuantity());
		System.out.println("The product cart details :");
		detailsMap.forEach((k,v) -> System.out.println(k+":"+v));
		return detailsMap;
	}
	
	private String cartProductQuantity() {
		return eleUtil.doGetAttribute(cartProductQuantity, "value");
	}
	
	public String applyShipping() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", eleUtil.getElement(shippingCollapsible));
		//eleUtil.doActionsClick(shippingCollapsible);
		eleUtil.clickWhenReady(Constants.DEFAULT_TIME_OUT, shippingCountry);
		eleUtil.doSelectDropDownByVisibleText(shippingCountry, "India");
		//eleUtil.doSelectDropDownByVisibleText(shippingRegion, "Karnataka");
		//eleUtil.doClick(By.xpath("//select[@id='input-zone']"));
		js.executeScript("arguments[0].click()", eleUtil.getElement(By.xpath("//select[@id='input-zone']")));
		eleUtil.clickWhenReady(Constants.DEFAULT_TIME_OUT, shippingRegion ).click();
		eleUtil.doSendKeys(shippingPostCode, "560100");
		eleUtil.doClick(getQuotesBtn);
		String actShippingRate = eleUtil.doGetText(shippingRate, Constants.DEFAULT_TIME_OUT);
		System.out.println(actShippingRate);
		eleUtil.doClick(shippingFlatRateRadioBtn);
		eleUtil.doClick(applyShippingBtn);
		
		System.out.println(eleUtil.doGetText(shippingRateAlert, Constants.DEFAULT_TIME_OUT ));
		eleUtil.doClick(shippingRateAlertBtn);
		
		return actShippingRate;
	}
	
	public String getAppliedShippingRate() {
		return eleUtil.doGetText(flatShippingRate, Constants.DEFAULT_TIME_OUT);
	}
	
	public CheckoutPage productCheckout() {
		eleUtil.doClick(checkoutBtn);
		return new CheckoutPage(driver);
	}

}
