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
import org.openqa.selenium.interactions.Actions;
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
	Actions action;
	String UserMenuBar = "/html/body/app-root/app-user-page/app-navbar-general/nav/div/button";
	String AccountRegisterMenuBar = "/html/body/app-root/"
    		+ "app-bank-account-register-page/app-navbar-general/nav/div/button";
	String LogsInMenuBar = "/html/body/app-root/app-login-page/app-navbar-login/nav/div/button";
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.action = new Actions(driver);

	}
	
	public void clickRegisterOnNavBar() {
		if(clickShrunkMenuBar(LogsInMenuBar)) {
			findByXpathWithWait(5, "//*[@id=\"navbarSupportedContent\"]"
					+ "/ul/li[3]/a").click();	

		} else {
			WebElement ele = this.driver.findElement(By.linkText("Register"));
			action.moveToElement(ele);
			action.click().build().perform();
	}
	
	public boolean checkIfAtLoginPage() {
		WebDriverWait wait=new WebDriverWait(driver,10 );		        
		WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//*[@id=\"exampleInputEmail1\"]")));
		return this.driver.getCurrentUrl().equals("http://localhost:4200/login");
	}
	public void clickLogInOnNavBar() {
		if(clickShrunkMenuBar(LogsInMenuBar)) {
			findByXpathWithWait(5, "/html/body/app-root"
					+ "/app-login-page/app-navbar-login"
					+ "/nav/div/div/ul/li[2]/a").click();

		} else {
			findByXpathWithWait(5, "/html/body/app-root"
					+ "/app-login-page/app-navbar-login"
					+ "/nav/div/div/ul/li[2]/a").click();		
		}	
	}
	
	public void clickMyAccount() {
		if(clickShrunkMenuBar(UserMenuBar)) {
			findByXpathWithWait(5, "//*[@id=\"navbarSupportedContent\"]/ul/li[2]/a").click();	

		} else {
			findByXpathWithWait(5, "/html/body/app-root/"
					+ "app-user-page/app-navbar-general"
					+ "/nav/div/div/ul/li[2]/a").click();
		}		
	}
	
	public void clickCreateBankAccountNav() {
		if(clickShrunkMenuBar(AccountRegisterMenuBar)) {
			findByXpathWithWait(5, "/html/body/app-root"
					+ "/app-bank-account-register-page"
					+ "/app-navbar-general/nav/div/div/ul/li[3]/a").click();
		} else {
			WebElement ele = this.driver.findElement(By.linkText("Create Bank Account"));
			action.moveToElement(ele);
			action.click().build().perform();
		}
	}
	
	public void clickManageAccountBalanceNav() {
		if(clickShrunkMenuBar(UserMenuBar)) {
			findByXpathWithWait(5, "//*[@id=\"navbarSupportedContent\"]/ul/li[5]/a").click();

		} else {
			findByXpathWithWait(5, "/html/body/app-root/"
					+ "app-user-page/app-navbar-general/nav/div/div/ul/li[5]/a").click();			
		}	
	}
	
	public void hoverTransferMoney() {
		if(clickShrunkMenuBar(UserMenuBar)) {
			WebElement ele = findByXpathWithWait(5, "//*[@id=\"navbarSupportedContent\"]/ul/li[4]");
			action.moveToElement(ele).perform();

		} else {
			WebElement ele = findByXpathWithWait(5, "/html/body/app-root/"
					+ "app-user-page/app-navbar-general/nav/div/div/ul/li[4]");
			action.moveToElement(ele).perform();
		}	
	}
	
	public void clickBetweenAccount() {
		if(clickShrunkMenuBar(UserMenuBar)) {
			WebElement ele = this.driver.findElement(By.linkText("Between Accounts"));
			action.moveToElement(ele);
			action.click().build().perform();
		} else {
			WebElement ele = this.driver.findElement(By.linkText("Between Accounts"));
			action.moveToElement(ele);
			action.click().build().perform();
		}

	}
	
	public void clickWithOtherUsers() {
		if(clickShrunkMenuBar(UserMenuBar)) {
			WebElement ele = this.driver.findElement(By.linkText("With Other Users"));
			action.moveToElement(ele);
			action.click().build().perform();
		} else {
			WebElement ele = this.driver.findElement(By.linkText("With Other Users"));
			action.moveToElement(ele);
			action.click().build().perform();
		}

	}
	
	
	public void clickLogOutButton(){
		if(clickShrunkMenuBar(UserMenuBar)) {
			findByXpathWithWait(5, "//*[@id=\"navbarSupportedContent\"]/ul/li[7]/a").click();	

		} else {
			findByXpathWithWait(5, "/html/body/app-root/"
					+ "app-user-page/app-navbar-general/nav/div/div/ul/li[7]/a").click();			
		}	
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
	
	public boolean clickShrunkMenuBar(String xpath) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(1000))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> ele = wait.until(new Function<WebDriver, List<WebElement>>() {
			  public List<WebElement> apply(WebDriver driver) {
				    return driver.findElements(By.xpath(xpath));
				  }
		});
	    if(ele.size() > 0) {
	    	if(ele.get(0).isDisplayed()) {
	    		ele.get(0).click();
		    	return true;
	    	}
	    }
	    return false;
	}
	
	public WebElement findByXpathWithWait(int time, String xpath) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(time))
				  .pollingEvery(Duration.ofMillis(100))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath(xpath)));
		return ele;
	}
}
