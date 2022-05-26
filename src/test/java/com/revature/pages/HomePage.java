package com.revature.pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 * 
 * Create Bank Account:
 * shorten
 * /html/body/app-root/app-user-page/app-navbar-general/nav/div/div/ul/li[3]/a
 * /html/body/app-root/app-user-page/app-navbar-general/nav/div/div/ul/li[3]/a
 * /html/body/app-root/app-user-page/app-navbar-general/nav/div/div/ul/li[3]/a
 * 
 */


public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickRegisterOnNavBar() {
		clickLogInMenuBar();
		clickAfterWaitXpath(5, "/html/body/app-root"
				+ "/app-login-page/app-navbar-login/nav/div/div/ul/li[3]/a");
	}
	
	public boolean checkIfAtLoginPage() {
		WebDriverWait wait=new WebDriverWait(driver, 2);		        
		WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//*[@id=\"exampleInputEmail1\"]")));
		return this.driver.getCurrentUrl().equals("http://localhost:4200/login");
	}
	public void clickLogInOnNavBar() {
		clickLogInMenuBar();
		clickAfterWaitXpath(5, "/html/body/app-root/"
				+ "app-login-page/app-navbar-login/"
				+ "nav/div/div/ul/li[3]/a");

	}
	
	public void clickMyAccount() {
		clickUserMenuBar();
		clickAfterWaitXpath(5, "/html/body/app-root/"
					+ "app-user-page/app-navbar-general/nav/div/div/ul/li[2]/a");			
	}
	
	public void clickCreateBankAccountNav() {
		if(clickAccountRegisterMenuBar()) {
			clickAfterWaitXpath(5, "/html/body/app-root"
					+ "/app-bank-account-register-page/app-navbar-general/nav/div/div/ul/li[3]/a");
		} else {
			clickAfterWaitXpath(5, "/html/body/app-root"
					+ "/app-user-page/app-navbar-general/nav/div/div/ul/li[3]/a");
		}

	}
	public void clickLogOutButton(){
		clickUserMenuBar();
		clickAfterWaitXpath(5, "//*[@id=\"navbarSupportedContent\"]/ul/li[7]/a");	
	}
	public void clickLogInMenuBar() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(1))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/app-root/app-login-page/app-navbar-login/nav/div/button")));
	    if(ele.isDisplayed()) {
	    	ele.click();
	    }
	}
	public void clickUserMenuBar() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(1000))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/app-root/app-user-page/app-navbar-general/nav/div/button")));
	    if(ele.isDisplayed()) {
	    	ele.click();
	    }
	}
	public boolean clickAccountRegisterMenuBar() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(1000))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> ele = wait.until(new Function<WebDriver, List<WebElement>>() {
			  public List<WebElement> apply(WebDriver driver) {
				    return driver.findElements(By.xpath("/html/body/app-root/"
				    		+ "app-bank-account-register-page/app-navbar-general/nav/div/button"));
				  }
		});
	    if(ele.size() > 0) {
	    	if(ele.get(0).isDisplayed()) {
	    		ele.get(0).click();
	    	}
	    	return true;
	    }
	    return false;
	}
	public void clickAfterWaitXpath(int time, String xpath) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(time))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath(xpath)));
		ele.click();
	}
}
