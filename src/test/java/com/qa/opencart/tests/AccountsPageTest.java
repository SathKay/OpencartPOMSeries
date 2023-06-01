package com.qa.opencart.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.utils.Constants;

public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accountsPageSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void accountPageTitleTest() {
		String accTitle = accPage.getAccountPageTitle();
		System.out.println("Account Page Title : " +accTitle);
		Assert.assertEquals(accTitle, Constants.ACCOUNT_PAGE_TITLE);
	}
	
	@Test
	public void accountPageUrlTest() {
		String accUrl = accPage.getAccountPageUrl();
		System.out.println("Account Page Url : "+accUrl);
		Assert.assertTrue(accUrl.contains(Constants.ACCOUNT_PAGE_URL));
	}
	
	@Test
	public void accountPageLogoTest() {
		Assert.assertTrue(accPage.getAccountPageLogo());
	}
	
	@Test
	public void logoutExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void searchExistTest() {
		Assert.assertTrue(accPage.isSearchExist());
	}
	
	@Test
	public void accountPageSectionsTest() {
		List<String> accSectionList = accPage.getAccountPageSections();
		System.out.println(accSectionList);
		Assert.assertEquals(accSectionList, Constants.ACCOUNT_PAGE_SECTION_LIST);
	}
	
	@DataProvider
	public Object[][] searchTestData() {
		return new Object[][] {
			{"macbook"},
			{"imac"},
			{"apple"}
		};
	}
	
	//method will run continuously for three products
	@Test(dataProvider="searchTestData",enabled=false)
	public void searchProductTest() {
		resultsPage = accPage.doSearch("macbook");
		Assert.assertTrue(resultsPage.getProductCount()>0);
	}
	
	@DataProvider
	public Object[][] selecProductTestData() {
		return new Object[][] {
			{"Macbook", "Macbook Air"},
			{"macbook", "Macbook Pro"},
			{"iMac", "iMac"},
			{"Apple", "Apple Cinema 30\""}
		};
	}
	
	@Test(dataProvider= "selecProductTestData",enabled=false)
	public void selectProductTest(String productName, String mainProduct) {
		resultsPage = accPage.doSearch(productName);
		productInfoPage = resultsPage.selectProduct(mainProduct);
		Assert.assertEquals(productInfoPage.getProductHeader(), mainProduct);
	}


	
}
