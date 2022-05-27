package com.revature.pages;

import org.openqa.selenium.WebDriver;

public class PageController {
	public static HomePage homePage;
	public static RegisterPage registerPage;
	public static CreateBankAccountPage createBankAccountPage;
	public static LogInPage logInPage;
	public static MyAccountPage myAccountPage;
	public static ManageAccountBalancePage manageAccountBalancePage;
	public static TransferMoneyBetweenAccountPage transferMoneyBetweenAccountPage;
	public static EmailNotificationPage emailNotificationPage;
	public static TransferMoneyWithOtherUsersPage transferMoneyWithOtherUsersPage;
	
	public PageController(WebDriver driver){
		homePage = new HomePage(driver);
		registerPage = new RegisterPage(driver);
		createBankAccountPage = new CreateBankAccountPage(driver);
		logInPage = new LogInPage(driver);	
		myAccountPage = new MyAccountPage(driver);
		manageAccountBalancePage = new ManageAccountBalancePage(driver);
		transferMoneyBetweenAccountPage = new TransferMoneyBetweenAccountPage(driver);
		myAccountPage = new MyAccountPage(driver);
		emailNotificationPage = new EmailNotificationPage(driver);
		manageAccountBalancePage = new ManageAccountBalancePage(driver);
		transferMoneyWithOtherUsersPage  = new TransferMoneyWithOtherUsersPage(driver); 
	}
}
