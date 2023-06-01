package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtils;

public class HomePage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	private By productLink = By.xpath("//div[@class='row']//h4/a[text()='MacBook']");
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public ProductInfoPage selectProduct(String productName) {
		JavascriptExecutor js =(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", eleUtil.getElement(By.xpath("//div[@class='row']//h4/a[text()='"+productName+"']")));
		System.out.println("The product Selected is "+productName);
		return new ProductInfoPage(driver);
	}

}
