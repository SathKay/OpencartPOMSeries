package com.qa.opencart.utils;

import java.util.Arrays;
import java.util.List;

public class Constants {
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_URL ="account/login";
	public static final int DEFAULT_TIME_OUT = 5;
	public static final String ACCOUNT_PAGE_TITLE = "My Account";
	public static final String ACCOUNT_PAGE_URL = "account/account";
	public static final List<String> ACCOUNT_PAGE_SECTION_LIST = Arrays.asList("My Account", "My Orders", 
																		"My Affiliate Account", "Newsletter");
	public static final String REGISTER_PAGE_TEST_SHEET_NAME = "register";
	public static final String LOGIN_PAGE_TEST_SHEET_NAME = "login";
	
	public static final int MACBOOK_IMAGES_COUNT = 4;
	public static final int IMAC_IMAGES_COUNT = 3;
	
	public static final List<String> SHOPPING_CART_TABLE_KEYS = Arrays.asList("Product Name", "Product Model", "Unit Price", "Total");
	//public static final List<String> SHOPPING_CART_IPHONE_DETAILS = Arrays.asList("iPhone", "product 11", "$123.20");

}
