package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
	
	/**
	 * This method intiates the webdriver with browsername
	 * @param browserName
	 * @return this method returns the driver
	 */
	public WebDriver init_driver(Properties prop) {
		String browserName = prop.getProperty("browser").trim();
		highlight = prop.getProperty("highlight").trim();
		
		System.out.println("The given browser is "+ browserName);
		optionsManager = new OptionsManager(prop);
		
		if(browserName.equalsIgnoreCase("chrome")) {
			//ChromeOptions option  = new ChromeOptions();
			//option.addArguments("--remote-allow-origins=*");
			WebDriverManager.chromedriver().setup();
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}
		else if(browserName.equalsIgnoreCase("safari")) {
			//driver = new SafariDriver();
			tldriver.set(new SafariDriver());
		}
		else {
			System.out.println("Given browser is not available");
		}
		
		//driver.manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("adminUrl").trim());
		return getDriver();
	}
	
	/**
	 * This method is used to initialize the properties
	 * @return this method return the Properties class reference
	 */
	public Properties init_prop() {
		prop = new Properties();
		try {
			FileInputStream input = new FileInputStream("./src/test/resources/config/config.properties"); //. for current project directory
			prop.load(input);	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
		
	/*
	 * This method returns the thread local copy of the driver 
	 */
	public static WebDriver getDriver() {
		return tldriver.get();
	}
	
	
	// ThreadLocal -- JDK 8 --> create a local copy of driver
	// set driver with TL
	// getdriver() -- driver
	// dribver null problem
	// u can take ur driver copy anywhere in ur framework
	// better thread management
	// to avoid the dead local conditon -- TL driver copy
	// large test cases count -- 200, 300 TCS --> proper test results
	
	
	/**
	 * take screenshot
	 */
	public String getScreenshot() {
		//scrFile is the screenshot stored in memory
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE); //TakesScreenshot is an interface which has
		//getScreenshotAs method , to access that we type casting driver to that interface.
		//OutputType can be base64,binary , FILE and others. FILE is the standard we mostly use
		
		//path is the actual path where we want to store the screenshot. user.dir - current project path, screenshot- folder 
		//will get created , with current time as name and as png file
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		
		//converting the above String path to File path
		File destination = new File(path);
		try {
			//copy the scrFile to destination path
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}
