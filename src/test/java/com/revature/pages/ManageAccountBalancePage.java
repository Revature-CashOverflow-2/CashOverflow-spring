package com.revature.pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

public class ManageAccountBalancePage {
	WebDriver driver;

	public ManageAccountBalancePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickAccountsDropDown() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(100))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.id("accounts")));
		ele.click();
	}
	
	public void selectAccount(String accountName) {
		Select s = new Select(driver.findElement(By.id("accounts")));
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
	
	public void clickAccountTypeDropDown() {
		this.driver.findElement(By.id("type")).click();

	}
	
	public void clickAccountTypeIncome() {
		this.driver.findElement(By.xpath("/html/body"
				+ "/app-root/app-manage-account-balance/app-income-expense"
				+ "/div/div/div/div/div/form/div/div[2]/select/option[2]")).click();
	}
	
	public void clickAccountTypeExpense() {
		this.driver.findElement(By.xpath("/html/body"
				+ "/app-root/app-manage-account-balance/app-income-expense"
				+ "/div/div/div/div/div/form/div/div[2]/select/option[3]")).click();
	}
	
	public void inputIntoAmountForm(String inputs) {
		this.driver.findElement(By.id("amount")).sendKeys(inputs);;

	}
	
	public void inputIntoDescriptionForm(String input) {
		this.driver.findElement(By.id("exampleFormControlTextarea1")).sendKeys(input);;

	}
	
	public void clickCreateTransactionButton() {
		this.driver.findElement(By.xpath("/html/body/app-root/"
				+ "app-manage-account-balance/app-income-expense"
				+ "/div/div/div/div/div/form/div/div[5]/button")).click();
        this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

	}
}
