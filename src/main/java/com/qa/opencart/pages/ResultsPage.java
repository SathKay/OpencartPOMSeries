package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtils;

public class ResultsPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	private By productResults = By.cssSelector("div.caption a");
	
	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public int getProductCount() {
		int productCount = eleUtil.waitForElementsVisible(10, productResults).size();
		System.out.println("Count of the products : "+productCount);
		return productCount;
	}
	
	public ProductInfoPage selectProduct(String mainProductName) {
		List<WebElement> productList = eleUtil.waitForElementsVisible(10, productResults);
		for(WebElement e: productList) {
			String text = e.getText();
			if(text.equals(mainProductName)) {
				e.click();
			}
		}
		
		return new ProductInfoPage(driver);
	}

}
