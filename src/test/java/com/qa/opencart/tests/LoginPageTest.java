package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.listeners.AnnotationTransformer;
import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC - 1: Designing the opencart login page")  //epic's id and description from the software management tool
@Story("STORY - 1: User story for login page") //user story's id and description from the software management tool
@Listeners({AnnotationTransformer.class, TestAllureListener.class})
public class LoginPageTest extends BaseTest{
	
	@Description("Login Page Title Test")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String title = loginPage.getLoginPageTitle();
		System.out.println("Login Page title : "+title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Description("Login Page Url Test")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageUrlTest() {
		String url = loginPage.getLoginPageUrl();
		System.out.println("Login page url : "+url);
		Assert.assertTrue(url.contains(Constants.LOGIN_PAGE_URL));
	}
	
	@Description("this is testing the forgot password link's existence")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void ForgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@DataProvider
	public Object[][] getloginTestData() {
		return ExcelUtil.getTestData(Constants.LOGIN_PAGE_TEST_SHEET_NAME);
	}
	
	@Description("positive test case for login")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority=1,dataProvider ="getloginTestData",description="Login test with correct user name and password")
	public void LoginTest(String username, String password) {
		accPage = loginPage.doLogin(username, password);
		//accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
}
