package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtils;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	private By logo = By.cssSelector("div#logo img");
	private By sections = By.cssSelector("div#content h2");
	private By logoutLink = By.linkText("Logout");
	
	private By searchBox = By.name("search");
	private By searchBtn = By.cssSelector("div#search button");
	
	private By homeBtn = By.cssSelector("div#account-account ul.breadcrumb li:nth-of-type(1)");
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public String getAccountPageTitle() {
		return eleUtil.doGetPageTitleIs(Constants.ACCOUNT_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}
	
	public String getAccountPageUrl() {
		return eleUtil.waitForUrlContains(Constants.ACCOUNT_PAGE_URL, Constants.DEFAULT_TIME_OUT);
	}
	
	public boolean getAccountPageLogo() {
		return eleUtil.doIsDisplayed(logo);
	}
	
	public List<String> getAccountPageSections() {
		List<WebElement> secList = eleUtil.waitForElementsVisible(10, sections);
		List<String> secValList = new ArrayList<String>();
		for(int i=0;i<secList.size();i++) {
			String text = secList.get(i).getText();
			secValList.add(text);
		}
		return secValList;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.doIsDisplayedWithWait(logoutLink, Constants.DEFAULT_TIME_OUT);
	}
	
	//This method is incomplete
	public boolean doLogout() {
		if(isLogoutLinkExist()) {
			eleUtil.doClick(logoutLink);
			return true;
		}
		return false;
	}
	
	public boolean isSearchExist() {
		return eleUtil.doIsDisplayed(searchBox);
	}
	
	
	public ResultsPage doSearch(String productName) {
		if(isSearchExist()) {
			eleUtil.doSendKeys(searchBox, productName);
			eleUtil.doClick(searchBtn);
		}
		return new ResultsPage(driver);
	}
	
	public HomePage goToHomePage() {
		eleUtil.clickWhenReady(Constants.DEFAULT_TIME_OUT, homeBtn).click();
		return new HomePage(driver);
	}
	
}
