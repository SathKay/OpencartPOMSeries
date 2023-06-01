package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtils;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By subscribeYes = By.xpath("//label[@for='input-newsletter-yes']");
	private By subscribeNo = By.xpath("//label[@for='input-newsletter-no']");
	private By privacyPolicy = By.xpath("//input[@name='agree']");
	private By continueBtn = By.xpath("//button[@type='submit']");
	
	private By successMsg = By.cssSelector("div#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public boolean accountRegisteration(String firstName, String lastName, String emailId, 
											String password, String subscribe) {
		
		eleUtil.doSendKeys(this.firstName, firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.emailId, emailId);
		eleUtil.doSendKeys(this.password, password);
		
		
		//js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		if(subscribe.equalsIgnoreCase("yes")) {
			//eleUtil.doClick(subscribeYes);
			js.executeScript("arguments[0].click()", eleUtil.getElement(subscribeYes));
		}else {
			//eleUtil.doClick(subscribeNo);
			js.executeScript("arguments[0].click()", eleUtil.getElement(subscribeNo));
		}
		
		//eleUtil.doClick(privacyPolicy);
		js.executeScript("arguments[0].click()", eleUtil.getElement(privacyPolicy));
		//eleUtil.doClick(continueBtn);
		js.executeScript("arguments[0].click()", eleUtil.getElement(continueBtn));
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String succesMsg = eleUtil.doGetText(successMsg);
		System.out.println(succesMsg);
		
		if(succesMsg.contains("Your Account Has Been Created!")) {
			js.executeScript("arguments[0].click()", eleUtil.getElement(logoutLink));
			//eleUtil.doClick(logoutLink);
			js.executeScript("arguments[0].click()", eleUtil.getElement(registerLink));
			eleUtil.doClick(registerLink);
			return true;
		}
		return false;
	}
	
	

}
