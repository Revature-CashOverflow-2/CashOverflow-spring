package com.revature.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TransferMoneyWithOtherUsersPage {
	
	WebDriver driver;
	JavascriptExecutor jvs;
	Actions action;

	public TransferMoneyWithOtherUsersPage(WebDriver driver) {
		this.driver = driver;
		jvs = (JavascriptExecutor) driver;
		action = new Actions(driver);
	}
	
	public void selectRequestSendForm(String transactionType) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(3))
				  .pollingEvery(Duration.ofMillis(100))
				  .ignoring(NoSuchElementException.class);
		WebElement eles = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.id("sendreceive")));
		Select s = new Select(eles);
		List<WebElement> options = s.getOptions();
		
		for(int i = 0; i < options.size(); i++) {
			WebElement ele = options.get(i);
			String[] acc = ele.getText().split(" ",2);
			if(acc[0].equals(transactionType)) {
				System.out.println(acc[0]);
				ele.click();
				break;
			}
		}	
	}
	
	public void inputUsername(String username) {
		this.driver.findElement(By.xpath("/html/body/app-root/app-bank-account-user-transfer/"
				+ "app-transfer-money-between-users/div/div/div/div/div/div/form/input[1]")).sendKeys(username);
	}
	
	
	public void selectFromTo(String accountName) {

		Select s = new Select(this.driver.findElement(By.id("account2")));
		List<WebElement> options = s.getOptions();
		
		for(int i = 0; i < options.size(); i++) {
			WebElement ele = options.get(i);
			String[] acc = ele.getText().split(" ",2);
			if(acc[0].equals(accountName)) {
				System.out.println(acc[0]);
				ele.click();
				break;
			}
		}	
	}
	public void inputAmount(String amount) {
		this.driver.findElement(By.xpath("/html/body/app-root/app-bank-account-user-transfer/"
				+ "app-transfer-money-between-users/"
				+ "div/div/div/div/div/div/form/input[2]")).sendKeys(amount);
	}
	
	public void clickSubmit() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/app-root/app-bank-account-user-transfer/"
				+ "app-transfer-money-between-users/div/div/div/div/div/div/form/div/button")));
		ele.click();
	}
	
	public boolean transferSuccessNotification() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div")));
		action.moveToElement(ele).perform();
		String text = ele.getText().toString();
		return text.contains("Please verify your accounts have updated");
	}
	
	public boolean transferDenyNotification() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(10))
				  .pollingEvery(Duration.ofMillis(100))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div")));
		action.moveToElement(ele).perform();
		String text = ele.getText().toString();
		return text.contains("Transfer denied");
	}
	
	public boolean transferErrorNotification() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(2))
				  .pollingEvery(Duration.ofMillis(100))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div")));
		action.moveToElement(ele).perform();
		String text = ele.getAttribute("innerHTML");
		return text.contains("Something went wrong with the transfer, please try again");
	}
	
	public boolean findRequest(String string, String requestType) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> accountCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));
		for(int i = 0; i < accountCards.size(); i++) {
			WebElement ele = accountCards.get(i);
			try {
				List<WebElement> h3 = ele.findElements(By.tagName("h3"));
				if(h3.size()> 0) {
					if(h3.get(0).getAttribute("innerHTML").contains(string)
							&& h3.get(0).getAttribute("innerHTML").contains(requestType)){
						return true;
					}
				}
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	
	public boolean selectAccountOnRequest(String accountName, String requester,String requestType) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> accountCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));
		for(int i = 0; i < accountCards.size(); i++) {
			WebElement ele = accountCards.get(i);
			List<WebElement> h3 = ele.findElements(By.tagName("h3"));
			try {
				if(h3.size()> 0) {
					if(h3.get(0).getAttribute("innerHTML").contains(requester)
							&& h3.get(0).getAttribute("innerHTML").contains(requestType)){
						Select s = new Select(ele.findElement(By.id("account")));
						List<WebElement> options = s.getOptions();
						
						for(int j = 0; j < options.size(); j++) {
							WebElement op = options.get(j);
							String[] acc = op.getText().split(" ",2);
							if(acc[0].equals(accountName)) {
								op.click();
								return true;
							}
						}
					}
				}
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	
	public void clickAcceptOnRequest(String requester, String requestType) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> accountCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));
		for(int i = 0; i < accountCards.size(); i++) {
			WebElement ele = accountCards.get(i);
			try {
				List<WebElement> h3 = ele.findElements(By.tagName("h3"));
				if(h3.size()> 0) {
					if(h3.get(0).getAttribute("innerHTML").contains(requester)
							&& h3.get(0).getAttribute("innerHTML").contains(requestType)){
						List<WebElement> btn = ele.findElements(By.tagName("button"));
						jvs.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();},100)",btn.get(0));
						break;
					}
				}
			} catch(Exception e) {
			}
		}
	}
	public void clickRejectOnRequest(String requester,String requestType) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		List<WebElement> accountCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card")));
		for(int i = 0; i < accountCards.size(); i++) {
			WebElement ele = accountCards.get(i);
			try {
				List<WebElement> h3 = ele.findElements(By.tagName("h3"));
				if(h3.size()> 0) {
					if(h3.get(0).getAttribute("innerHTML").contains(requester)
							&& h3.get(0).getAttribute("innerHTML").contains(requestType)){
						List<WebElement> btn = ele.findElements(By.tagName("button"));
						jvs.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();},100)",btn.get(1));
						break;
					}
				}
			} catch(Exception e) {
			}
		}
	}
}


