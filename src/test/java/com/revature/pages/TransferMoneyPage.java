package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class TransferMoneyPage {
	WebDriver driver;

	public TransferMoneyPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void hoverTransferMoney() {
		this.driver.findElement(By.xpath("/html/body/app-root"
				+ "/app-bank-account-money-transfer/app-navbar-general"
				+ "/nav/div/div/ul/li[4]/div/div/a[1]")).click();
	}
	
	public void selectFromForm(String accountName) {
		Select ele = new Select(this.driver.findElement(By.id("account1")));
		ele.selectByVisibleText(accountName);
	}
	public void selectToForm(String accountName) {
		Select ele = new Select(this.driver.findElement(By.id("account2")));
		ele.selectByVisibleText(accountName);
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
}
