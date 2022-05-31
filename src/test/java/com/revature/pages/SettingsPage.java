package com.revature.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class SettingsPage {
	WebDriver driver;
	JavascriptExecutor jvs;
	Actions action;
	public SettingsPage(WebDriver driver) {
		this.driver = driver;
		jvs = (JavascriptExecutor)driver; 
		action = new Actions(driver);
	}

	public void turnOnNotification() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.name("checkbox")));
		jvs.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();},100)",ele);
	}
	public void enterAmountThreshold() {
		WebElement ele = driver.findElement(By.xpath("/html/body/app-root/app-settings/app-email-toggle/div/div/div/div/div/div/form/div[2]/input"));
		jvs.executeScript("var elem=arguments[0]; setTimeout(function() {elem.value=2000;},100)",ele);
	}
	public void clickSaveSettings() {
		driver.findElement(By.xpath("/html/body/app-root/app-settings/app-email-toggle/div/div/div/div/div/div/form/div[3]/input")).sendKeys(Keys.ENTER);
	}
	
	public void inputCurrentPassword(String string) {
		this.driver.findElement(By.id("current-password")).sendKeys(string);
	}
	
	public void inputNewPassword(String string) {
		this.driver.findElement(By.id("new-password")).sendKeys(string);
	}
	
	public void inputReTypePassword(String string) {
		this.driver.findElement(By.id("retype-password")).sendKeys(string);
	}
	
	public void clickChangePassword() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/app-root/app-settings"
						+ "/app-change-password/div/div/div/div/div/div/form/div[4]/input")));
		jvs.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();},1500)",ele);
		ele.click();
	}
	
	public boolean viewPasswordChangedSuccess() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div")));
		action.moveToElement(ele).perform();
		String text = ele.getText().toString();
		return text.contains("Password Changed");
	}
	
	public boolean viewFailMessage() {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		  .withTimeout(Duration.ofSeconds(5))
		  .pollingEvery(Duration.ofMillis(250))
		  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/app-root/app-settings/app-change-password/div/div/div/div/div/div/form/p")));
		action.moveToElement(ele).perform();
		String text = ele.getText().toString();
		return text.contains("The current password doesnt match 5 attempts remaining");
	}
	
	public boolean viewNotificationSuccess() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div")));
		action.moveToElement(ele).perform();
		String text = ele.getText().toString();
		return text.contains("Email Settings Changed");
	}
}