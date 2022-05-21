package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
	WebDriver driver;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
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
		WebDriverWait wait=new WebDriverWait(driver, 5);		        
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div")));
		String text = ele.getText().toString();
		System.out.println(text);
		return text.contains("You have been successfully registered");
	}
	
	public boolean ValidationNotice() {
		String Message = driver.findElement(By.id("username")).getAttribute("validationMessage");
		return Message.contains("Please fill out this field.");
	}
	
	public boolean registerFailPopUp() {
		WebDriverWait wait=new WebDriverWait(driver, 5);		        
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("/html/body/div/div/div")));
		String text = ele.getText().toString();
		System.out.println(text);
		return text.contains("There was a problem registering your account. Please try again.");

	}
}