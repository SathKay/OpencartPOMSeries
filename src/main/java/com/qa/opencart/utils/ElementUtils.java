package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtils {
	
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	public ElementUtils(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	public By getBy(String locatorType, String locatorValue) {
		
		By locator=null;
		
		switch (locatorType.toLowerCase()) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "classname":
			locator = By.className(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		case "cssselector":
			locator = By.cssSelector(locatorValue);
			break;
		case "linktext":
			locator = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			locator = By.partialLinkText(locatorValue);
			break;
		case "tagname":
			locator = By.tagName(locatorValue);
			break;
		default:
			break;
		}
		
		return locator;
	}
	
	public WebElement getElement(By locator) {
		WebElement ele = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(ele);
		}
		return ele;
	}
	
	public WebElement getElementWithWait(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void doSendKeys(By locator, String value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}
	
	public void doSendKeys(String locatorType, String locatorValue, String value) {
		getElement(getBy(locatorType, locatorValue)).sendKeys(value);
	}
	
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public void doClick(String locatorType, String locatorValue) {
		getElement(getBy(locatorType, locatorValue)).click();
	}
	
	public String doGetText(By locator) {
		return getElement(locator).getText();
	}
	
	public String doGetText(By locator, int timeOut) {
		return waitForElementVisible(locator, timeOut).getText();
	}
	
	public String doGetAttribute(By locator,String attName) {
		return getElement(locator).getAttribute(attName);
	}
	
	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();	
	}
	
	public boolean doIsDisplayedWithWait(By locator, int timeOut) {
		return getElementWithWait(locator, timeOut).isDisplayed();	
	}
	
	/**
	 * This method return the list of the given element in the page
	 * @param locator
	 * @return List<WebElement>
	 */
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	/**
	 * This method return the count of the given element in the page
	 * @param locator
	 * @return int
	 */
	public int getElementCount(By locator) {
		return getElements(locator).size();
	}
	
	/**
	 * This method prints the texts of the elements
	 * @param locator
	 */
	public void printElementsText(By locator) {
		List<WebElement> eleList = getElements(locator);
		for(WebElement e:eleList) {
			String text = e.getText();
			System.out.println(text);
			}
	}
	
	/**
	 * This method return the element's text list
	 * @param locator
	 * @return List<String>
	 */
	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();
		for(WebElement e:eleList) {
			String text = e.getText();
			if(!text.isEmpty()) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}
	
	/**
	 * To get the list of given attribute value of the elements
	 * @param locator
	 * @param attribute
	 * @return List<String>
	 */
	public List<String> getElementsAttributeList(By locator, String attribute) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttList = new ArrayList<String>();
		for(WebElement e: eleList) {
			String attVal = e.getAttribute(attribute);
			eleAttList.add(attVal);
		}
		return eleAttList;
		}
	
	/**
	 * This method is to click on the links's text
	 * @param locator
	 * @param text
	 */
	public void clickOnText(By locator, String text) {
		List<WebElement> langList = getElements(locator);
		System.out.println(langList.size());
		for(WebElement e: langList) {
			String textval = e.getText();
			System.out.println(textval);
			if(textval.contains(text)) {
				e.click();
				break;
			}
		}
	}
	
	/**
	 * This method is to select a particular suggestion from the Google search box
	 * @param locator
	 * @param suggestion
	 */
	public void selectSuggestion(By locator, String suggestion) {
		List<WebElement> suggList = driver.findElements(locator);
		System.out.println(suggList.size());
		for(WebElement e : suggList) {
			String text = e.getText();
			if(text.equals(suggestion)) {
				e.click();
			}
		}
		
	}
	
	/************************************DropDownUtils********************************/
	
	/**
	 * This method selects drop down option by its index
	 * @param locator
	 * @param index
	 */
	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}
	
	/**
	 * This method selects drop down option by its visible text
	 * @param locator
	 * @param visibleText
	 */
	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}
	
	/**
	 * This method selects drop down option by its value attribute
	 * @param locator
	 * @param value
	 */
	public void doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	
	/**
	 * This method return the count of the drop down options
	 * @param locator
	 * @return int
	 */
	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}
	
	/**
	 * This method returns the drop down options text as a list
	 * @param locator
	 * @return List<String> 
	 */
	public List<String> getDropDownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsTextList = new ArrayList<String>();
		
		for(WebElement e: optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
		
	}
	
	/**
	 * This method selects the drop down option using its text
	 * @param locator
	 * @param value
	 */
	public void selectValueFromDropDown(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		for(WebElement e : optionsList) {
			String text = e.getText();
			if(text.equals(value)) {
				e.click();
				break;
			}
		}
	}
	
	/***********************************Actions Methods**************************************/
	
	/**
	 * This method uses sendKeys method from actions class to perform the function
	 * @param locator
	 * @param value
	 */
	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}
	
	/**
	 * This method uses click method from Actions class to perform the function
	 * @param locator
	 */
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}
	
	/************************************Waits***************************/
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param timeOut
	 * @param locator
	 * @return
	 */
	public WebElement clickWhenReady(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	
	/**
	 * This is the explicit WebDriverWait method.
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeout
	 * @return WebElement
	 */
	public WebElement waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	/**
	 * This is a explicit WebDriverWait method
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * Default polling time = 500 ms
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/**
	 * This is a explicit WebDriverWait method
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * Default polling time = customized time
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeOut, int interval) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(interval));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param timeOut
	 * @param locator
	 * @return List<WebElement>
	 */
	public List<WebElement> waitForElementsPresence(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * This is a wait method for WebElements
	 * An expectation for checking that all elements present on the web page that match the locator are visible. 
	 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
	 * @param timeOut
	 * @param locator
	 * @return List<WebElement>
	 */
	public List<WebElement> waitForElementsVisible(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	/****************Waits-Non WebElement************************/
	
	public boolean waitForPageTitle(String titleVal, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.titleContains(titleVal));
	}
	
	public boolean waitForPageActTitle(String actTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.titleIs(actTitle));
	}
	
	public String doGetPageTitleContains(String titleVal, int timeOut) {
		if(waitForPageTitle(titleVal, timeOut)) {
			return driver.getTitle();
		}
		return null;
	}
	
	public String doGetPageTitleIs(String actTitle, int timeOut) {
		if(waitForPageActTitle(actTitle,timeOut)) {
			return driver.getTitle();
		}
		return null;
	}
	
	/**
	 * WebDriverWait method for url, with a fraction of url as input
	 * @param urlfraction
	 * @param timeOut
	 * @return String
	 */
	public String waitForUrlContains(String urlfraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try{if(wait.until(ExpectedConditions.urlContains(urlfraction))) {
			return driver.getCurrentUrl();
		}
		}catch(TimeoutException e) {
		return null;
		}
		return null;
	}
	
	/**
	 * WebDriverWait method for url, with full url as input
	 * @param url
	 * @param timeOut
	 * @return
	 */
	public String waitForUrlToBe(String url, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try{if(wait.until(ExpectedConditions.urlToBe(url))) {
			return driver.getCurrentUrl();
		}
		}catch(TimeoutException e){
			return null;
		}
		return null;
	}

	public Alert waitForAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public String getAlertText(int timeOut) {
		return waitForAlert(timeOut).getText();
	}
	
	public void acceptAlert(int timeOut) {
		waitForAlert(timeOut).accept();
	}
	
	public void dismissAlert(int timeOut) {
		waitForAlert(timeOut).dismiss();
	}
	
	public WebDriver waitForFrameByIndex(int timeOut, int index) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
	}
	
	public WebDriver waitForFrameByLocator(int timeOut, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}
	
	public WebDriver waitForFrameByString(int timeOut, String nameOrId) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
	}
	
	public WebDriver waitForFrameByWebElement(int timeOut, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
	}
	
	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
									.withTimeout(Duration.ofSeconds(timeOut))
										.pollingEvery(Duration.ofSeconds(pollingTime))
											.ignoring(NoSuchElementException.class)
												.ignoring(StaleElementReferenceException.class)
													.withMessage(locator +" is not found within given time......");
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
									
	}
	
	public WebElement waitForElementVisibleWithWait(By locator,int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.pollingEvery(Duration.ofSeconds(pollingTime))
					.ignoring(NoSuchElementException.class)
						.ignoring(StaleElementReferenceException.class)
							.withMessage(locator+" is not found ");	
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}
}
