package com.revature.pages;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
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


	public boolean atLeastNAccountExisted(int n) {
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
		if(accountCards.size() >= n) {
			return true;
		}
		return false;
	}

	
	public boolean accountExist(String accountName) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(5))
				.pollingEvery(Duration.ofMillis(250))
				.ignoring(NoSuchElementException.class);
		List<WebElement> accountCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));
		for(int i = 0; i < accountCards.size(); i++) {
			WebElement ele = accountCards.get(i);
			try {
				List<WebElement> span = ele.findElements(By.tagName("span"));
				if(span.size()> 0) {
					if(ele.findElement(By.tagName("span")).getAttribute("innerHTML").equals(accountName) ){
						return true;
					}
				}
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	
	public Double getAccountBalance(String accountName) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> accountCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));

		for(int i = 0; i < accountCards.size(); i++) {
			WebElement ele = accountCards.get(i);
			System.out.println(ele.findElement(By.tagName("span")).getAttribute("innerHTML"));
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
	
	public void clickOnAccount(String accountName) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> accountCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));
		for(int i = 0; i < accountCards.size(); i++) {
			WebElement ele = accountCards.get(i);
			try {
				List<WebElement> span = ele.findElements(By.tagName("span"));
				if(span.size()> 0) {
					if(ele.findElement(By.tagName("span")).getAttribute("innerHTML").equals(accountName) ){
						ele.click();
					}
				}
			} catch(Exception e) {
			}
		}
	}

	public boolean viewSuccess() {
		List<WebElement> accountCards = this.driver.findElements(By.className("card"));

		if(accountCards.size() > 0) {
			return true;
		}
		return false;
	}


	public boolean trackIncomeTransactionType() {
		List<WebElement> accountCards = this.driver.findElements(By.tagName("td"));
		
		for(int i = 0; i < accountCards.size();i++) {
			if(accountCards.get(i).getText().equals("Income")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean trackExpenseTransactionType() {
		List<WebElement> accountCards = this.driver.findElements(By.tagName("td"));
		
		for(int i = 0; i < accountCards.size();i++) {
			if(accountCards.get(i).getText().equals("Expense")) {
				return true;
			}
		}
		return false;
	}
}
