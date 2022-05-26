package com.revature.pages;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateBankAccountPage {

	WebDriver driver;

	public CreateBankAccountPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void sendInputToNameForm(String str) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.id("nameId")));
		ele.sendKeys(str);
	}
	public void sendInputToDescriptionForm(String str) {
		this.driver.findElement(By.id("descriptionId")).sendKeys(str);
	}
	public void selectAccountTypeForm() {
		this.driver.findElement(By.name("accountType")).click();
	}
	public void selectAccountTypeChecking() {
		this.driver.findElement(By.xpath("/html/body/app-root/app-bank-account-register-page/app-create-bank-account-form/div/div/div/div/div/div/form/div[3]/select/option[1]")).click();
	}
	public void selectAccountTypeSaving() {
		this.driver.findElement(By.xpath("/html/body/app-root/app-bank-account-register-page/app-create-bank-account-form/div/div/div/div/div/div/form/div[3]/select/option[2]")).click();
	}
	
	public boolean ValidationNotice() {
		String Message = driver.findElement(By.id("nameId")).getAttribute("validationMessage");
		return Message.contains("Please fill out this field.");
	}
	
	public void clickCreateAccount() {
		this.driver.findElement(By.xpath("/html/body/app-root/"
				+ "app-bank-account-register-page/app-create-bank-account-form/"
				+ "div/div/div/div/div/div/form/div[4]/button")).click();
        this.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}
	
}
