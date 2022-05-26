package com.revature.stepdef;

import java.io.File;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.pages.PageController;


public class SetUp {
	WebDriver driver;
	JavascriptExecutor js;	
	public PageController pageController;
	public SetUp() {
		File file = new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());//Environmental variable
		driver = new ChromeDriver(); //default to find environmental variable 
		js = (JavascriptExecutor)driver;
		pageController = new PageController(driver);
	}
}
