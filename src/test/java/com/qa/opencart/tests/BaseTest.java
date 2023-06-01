package com.qa.opencart.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;
import com.qa.opencart.pages.ShoppingCartPage;

public class BaseTest {
	
	DriverFactory df;
	WebDriver driver;
	LoginPage loginPage;
	Properties prop;
	AccountsPage accPage;
	RegisterPage regPage;
	ResultsPage resultsPage;
	ProductInfoPage productInfoPage;
	HomePage homePage;
	ShoppingCartPage shoppingCartPage;
	
	SoftAssert softAssert;
	
	int overallProdQuantity;
	
	@BeforeTest
	public void setUp() {
		df = new DriverFactory();
		prop = df.init_prop();
		driver = df.init_driver(prop);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
		overallProdQuantity = 0;
		loginPage.adminLogin(prop.getProperty("adminUsername"), prop.getProperty("adminPassword"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.navigate().to(prop.getProperty("url"));
	}
	
	@AfterTest
	public void tearDown() {
		//driver.quit();
	}

}
