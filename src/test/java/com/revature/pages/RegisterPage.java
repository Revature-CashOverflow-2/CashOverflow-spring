package com.revature.pages;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
	WebDriver driver;
	Actions action;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
	}

	public void inputIntoUsername(String str) {
		WebDriverWait wait=new WebDriverWait(driver, 5);		        
		WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id=\"username\"]")));
		ele.sendKeys(str);
	}
	public void inputIntoFirstName(String str) {
		driver.findElement(By.xpath("//*[@id=\"firstname\"]")).sendKeys(str);

	}
	public void inputIntoLastName(String str) {
		driver.findElement(By.xpath("//*[@id=\"lastname\"]")).sendKeys(str);

	}
	public void inputIntoEmail(String str) {
		driver.findElement(By.xpath("//*[@id=\"email1\"]")).sendKeys(str);

	}
	public void inputIntoPassword(String str) {
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(str);

	}
	public void inputIntoConfirmPassword(String str) {
		driver.findElement(By.xpath("//*[@id=\"password2\"]")).sendKeys(str);
	}
	
	public void clickRegisterButton(){
		WebDriverWait wait=new WebDriverWait(driver, 2);		        
		WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("/html/body/app-root/app-register-page/app-register-user/div/div/div/div/div/div/form/div[7]/button")));
		ele.sendKeys(Keys.ENTER);
	}
	
	public boolean registerSuccessPopUp() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(5))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div")));
		action.moveToElement(ele).perform();
		String text = ele.getText().toString();
		return text.contains("You have been successfully registere");
	}
	
	public boolean ValidationNotice() {
		String Message = driver.findElement(By.id("username")).getAttribute("validationMessage");
		return Message.contains("Please fill out this field.");
	}
	
	public boolean registerFailPopUp() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(2))
				  .pollingEvery(Duration.ofMillis(250))
				  .ignoring(NoSuchElementException.class);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div")));
		action.moveToElement(ele).perform();
		String text = ele.getText().toString();
		System.out.println(text);
		return text.contains("There was a problem registering your account. Please try again.");
	}
	
}
