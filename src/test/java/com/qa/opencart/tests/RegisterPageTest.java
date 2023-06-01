package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest{
	
	@BeforeClass 
	public void registerSetUp() {
		regPage = loginPage.goToRegisterPage();
	}
	
	public String genEmailId() {
		Random ranNum = new Random();
		String emailId = "testssss"+ranNum.nextInt(1000)+"@gmail.com";
		return emailId;
	}
	
	@DataProvider
	public Object[][] getRegisterTestData() {
		return ExcelUtil.getTestData(Constants.REGISTER_PAGE_TEST_SHEET_NAME);
	}
	
	@Test(dataProvider="getRegisterTestData")
	public void userRegisterTest(String firstName, String lastName, String password, String subscribe) {
		Assert.assertTrue(regPage.accountRegisteration(firstName, lastName, genEmailId(), password, subscribe));
	}

}
