package com.revature.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;


public class TransferMoneyBetweenAccountPage {
	WebDriver driver;
	Actions action;

	public TransferMoneyBetweenAccountPage(WebDriver driver) {
		this.driver = driver;
		this.action = new Actions(driver);

	}
	

	
	public void selectFromForm(String accountName) {
		Select s = new Select(this.driver.findElement(By.id("account1")));
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
	public void selectToForm(String accountName) {
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
		this.driver.findElement(By.xpath("/html/body/"
				+ "app-root/app-bank-account-money-transfer/app-transfer-money-owned/"
				+ "div/div/div/div/div/div/form/input")).sendKeys(amount);
	}
	
	public void clickSubmit() {
		this.driver.findElement(By.xpath("/html/body/app-root"
				+ "/app-bank-account-money-transfer/app-transfer-money-owned"
				+ "/div/div/div/div/div/div/form/div/button")).click();
	}
	
	public boolean transferSuccessNotification() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div")));
		String text = ele.getText().toString();
		action.moveToElement(ele).perform();
		return text.contains("Please verify your accounts have updated");
	}
	
	public boolean transferErrorNotification() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(2))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//*[@id=\"toast-container\"]/div/div[1]")));
		String text = ele.getAttribute("innerHTML");
		action.moveToElement(ele).perform();
		return text.contains("Something went wrong with the transfer, please try again");
	}
}
