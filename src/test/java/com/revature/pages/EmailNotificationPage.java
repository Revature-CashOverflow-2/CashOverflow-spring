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

public class EmailNotificationPage {
	WebDriver driver;
	JavascriptExecutor jvs;
	Actions action;
	public EmailNotificationPage(WebDriver driver) {
		this.driver = driver;
		jvs = (JavascriptExecutor)driver; 
		action = new Actions(driver);
}
	public void ClickSetting() {
		driver.findElement(By.xpath("/html/body/app-root/app-user-page/app-navbar-general/nav/div/div/ul/li[6]/a")).click();
	}
	public void TurnonNotification() {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(1))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.name("checkbox")));

	jvs.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();},100)",ele);
	}
	public void EnterAmountThreshold() {
		driver.findElement(By.xpath("/html/body/app-root/app-settings/app-email-toggle/div/div/div/div/div/div/form/div[2]/input")).sendKeys("2000");
	}
	public void ClickSaveSettings() {
		driver.findElement(By.xpath("/html/body/app-root/app-settings/app-email-toggle/div/div/div/div/div/div/form/div[3]/input")).sendKeys(Keys.ENTER);
	}
}