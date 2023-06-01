package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtils;

import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;


public class LoginPage {
	
	private WebDriver driver; //private - to avoid direct access of this page's driver. Direct access will give null
	private ElementUtils eleUtil;
	
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//button[@type='submit']");
	private By forgotPwdLink = By.linkText("Forgotten Password1");
	private By adminEmailId = By.id("input-username");
	private By adminPassword = By.id("input-password");
	private By registerLink = By.linkText("Register");
	
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	@Step("getting login page title...")
	public String getLoginPageTitle() {
		return eleUtil.doGetPageTitleIs(Constants.LOGIN_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}
	
	@Step("getting login page url...")
	public String getLoginPageUrl() {
		return eleUtil.waitForUrlContains(Constants.LOGIN_PAGE_URL, Constants.DEFAULT_TIME_OUT);
	}
	
	@Step("check forgot password link is displayed...")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.doIsDisplayed(forgotPwdLink);
	}
	
	@Step("admin login with username : {0} and password {1}")
	public void adminLogin(String userName, String password) {
		eleUtil.doSendKeys(adminEmailId, userName);
		eleUtil.doSendKeys(adminPassword, password);
		eleUtil.doClick(loginBtn);
	}
	
	@Step("user login with username : {0} and password {1}")
	public AccountsPage doLogin(String userName, String pwd) {
		eleUtil.doSendKeys(emailId, userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	@Step("navigate to register page")
	public RegisterPage goToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
