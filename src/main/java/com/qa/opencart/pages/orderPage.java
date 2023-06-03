package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class orderPage {
	
	private static String orderId = "12345";
	
	By locator = By.name("order");

	public static void main(String[] args) {
		
		System.out.println("The order id is "+ orderId);

	}

}
