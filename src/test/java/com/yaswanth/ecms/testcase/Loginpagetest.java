 package com.yaswanth.ecms.testcase;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yaswanth.ecms.configuration.Baseclass;
import com.yaswanth.ecms.configuration.Browser;
import com.yaswanth.ecms.loginpage.Login;

public class Loginpagetest{
	
	 Login loginpage;
	 WebDriver driver;
	 private static Properties properties =Baseclass.getProperties();
	 public final  String URL = properties.getProperty("url");
	 public final  String BROWSER = properties.getProperty("browser");
	 Logger logger = Logger.getLogger("Loginpagetest.class");
	 /**
    * This test shows whether it chrome is opened or not.
    */
	@Test
	public void browserpage() {
		Browser browser=new Browser();
		driver=browser.connectBrowsers(BROWSER,URL);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		logger.info("Chrome browser was opened");
		driver.manage().window().maximize();
		logger.info("Chrome browser was maximised");
		logger.info("Login page of application is opened");
		String CurrentUrl=driver.getCurrentUrl();
		logger.info("Login url of the application");
		System.out.println(CurrentUrl);
		Assert.assertEquals(CurrentUrl,"https://apollo-ecms.firebaseapp.com/");
		logger.info("login page of application is opened");
		
	}
	    /**
	    * This test shows whether the loginpage is opened or not.
	    */
	@Test(dependsOnMethods="forgotpassword")
	public void validateLogin() {
		driver.navigate().refresh();
	    loginpage=PageFactory.initElements(driver,Login.class);
		try {
			loginpage.login("admin@gmail.com","admin");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Email and password was entered");
		driver.manage().timeouts().implicitlyWait(1200, TimeUnit.SECONDS);
		String CurrentUrl=driver.getCurrentUrl();
		System.out.println(CurrentUrl);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(CurrentUrl,"https://apollo-ecms.firebaseapp.com/admin-dashboard");
	}
	    /**
	    * This test shows whether the loginpage and giving invalid Credentials is opened or not.
	    */
	@Test(dependsOnMethods="browserpage")
	public void invalidCredentials() {
		loginpage=PageFactory.initElements(driver,Login.class);
		try {
		
			loginpage.login("admin@gmail123.com","admin123");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		logger.info("invalidEmail and invalidpassword was entered ");
		String CurrentUrl=driver.getCurrentUrl();
		System.out.println(CurrentUrl);
		Assert.assertEquals(CurrentUrl,"https://apollo-ecms.firebaseapp.com/");
	}
	/**
	    * This test shows whether the forgotpassword is opened or not.
	    */
	@Test(dependsOnMethods="invalidCredentials")
	public void forgotpassword() {
		loginpage=PageFactory.initElements(driver,Login.class);
		try {
			loginpage.forgotpassword();
			logger.info("Forgot password page is opened");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String CurrentUrl=driver.getCurrentUrl();
		System.out.println(CurrentUrl);
		Assert.assertEquals(CurrentUrl,"https://apollo-ecms.firebaseapp.com/login");
	}
	 
}
