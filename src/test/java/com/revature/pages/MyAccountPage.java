package com.revature.pages;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class MyAccountPage {
	WebDriver driver;
	
	public MyAccountPage(WebDriver driver) {
		this.driver = driver;
	}

	public void getToViewPage() {
		this.driver.get("http://localhost:4200/feed");
	}
	
	public boolean atLeastTwoAccountExisted() {
		//Method called too early
		Wait<WebDriver> waitForLogin = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		waitForLogin.until(ExpectedConditions.presenceOfElementLocated(By.xpath(""
				+ "/html/body/app-root/app-user-page/app-navbar-general/nav/div/div/ul/li[2]/a")));
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> accountCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));
		if(accountCards.size() >= 2) {
			return true;
		}
		return false;
	}
	
	
	public boolean accountExist(String accountName) {
		getToViewPage();
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> accountCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));
		for(int i = 0; i < accountCards.size(); i++) {
			WebElement ele = accountCards.get(i);
			if(ele.findElement(By.tagName("span")).getAttribute("innerHTML") == accountName ){
				return true;
			}
		}
		return false;
	}
	
	public Double getAccountBalance(String accountName) {
		getToViewPage();
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> accountCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));

		for(int i = 0; i < accountCards.size(); i++) {
			WebElement ele = accountCards.get(i);
			if(ele.findElement(By.tagName("span")).getAttribute("innerHTML").equals(accountName) ){
				String balance = ele.findElements(By.tagName("span")).get(2).getAttribute("innerHTML");
				String withoutDollarSign = balance.replace("$", "");
				return Double.parseDouble(withoutDollarSign);
			}
		}
		return 0.0;
	}
	public void clickMyAccount() {
		this.driver.findElement(By.xpath("/html/body/app-root/app-user-page/app-navbar-general/nav/div/div/ul/li[2]/a")).click();
	}

	public boolean viewSuccess() {
		List<WebElement> accountCards = this.driver.findElements(By.className("card"));
		
		if(accountCards.size() > 0) {
			return true;
		}
		return false;
	}
}
