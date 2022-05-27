package com.revature.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

public class TransferMoneyWithOtherUsersPage {
	
	WebDriver driver;

	public TransferMoneyWithOtherUsersPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void selectRequestSendForm(String transactionType) {
		Select s = new Select(this.driver.findElement(By.id("sendreceive")));
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
		this.driver.findElement(By.xpath("/html/body/app-root/app-bank-account-user-transfer/"
				+ "app-transfer-money-between-users/div/div/div/div/div/div/form/div/button")).click();
	}
	
	public boolean transferSuccessNotification() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(10))
				  .pollingEvery(Duration.ofMillis(100))
				  .ignoring(NoSuchElementException.class);

        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div")));
		String text = ele.getText().toString();
		return text.contains("Please verify your accounts have updated");
	}
	
	public boolean transferErrorNotification() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(2))
				  .pollingEvery(Duration.ofMillis(100))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//*[@id=\"toast-container\"]")));
		String text = ele.getAttribute("innerHTML");
		return text.contains("Something went wrong with the transfer, please try again");
	}
}


