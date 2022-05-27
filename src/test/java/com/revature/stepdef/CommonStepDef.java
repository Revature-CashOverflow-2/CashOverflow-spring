package com.revature.stepdef;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;

public class CommonStepDef {
	public static SetUp setUp;
	public static RegisterTest rt;
	public static TrackMultipleAccts tma;
	public static TransferMoneyFromAccountsTest TMFAt;
	public static LoginTest lt;
	public static EmailNotifications EMN;
	public static RequestMoneyTest reqMoney;
	
	@BeforeAll
	public static void beforeAll() {
		setUp = new SetUp();
		rt = new RegisterTest(setUp);
		tma = new TrackMultipleAccts(setUp);
		TMFAt = new TransferMoneyFromAccountsTest(setUp);
		lt = new LoginTest(setUp);
		EMN = new EmailNotifications(setUp);
		reqMoney = new RequestMoneyTest(setUp);
	}
	
	@Given("the User is in homepage")
	public void the_user_is_in_homepage() {
		setUp.driver.get("http://localhost:4200/");
		setUp.js.executeScript("return sessionStorage.removeItem(\"username\");");
	}
	@Given("the User is not logged in")
	public void the_user_is_not_logged_in() {
		assertFalse(checkLogin());
	}
		
	@Given("the User had previously created two accounts")
	public void the_user_had_previously_created_two_accounts() {
		if(this.setUp.pageController.myAccountPage.atLeastTwoAccountExisted() == false) {
			this.createTwoAccount();
		}
	}
	
	@Given("the User logs in successfully as {string}")
	public void the_user_logs_in_successfully_as(String string) {
		if(checkLogin() == false) {
			LogIn("testingExample","Password!1");
		}
	}

	@Given("the User had some fund in the {string} account")
	public void the_user_had_some_fund_in_the_account(String string) {
		if(this.setUp.pageController.myAccountPage.getAccountBalance(string) == 0.0) {
			addFundsToAccount(string);
		}
	}
	@Given("the User logs in successfully")
	public void the_user_logs_in_successfully() {
		if(checkLogin() == false) {
			LogIn("testingExample","Password!1");
		}
	}
	
<<<<<<< HEAD
	@Given("the User has a bank account")
	public void the_user_has_a_bank_account() {
	   if(this.setUp.pageController.myAccountPage.accountExist("Checking")) {
		   this.setUp.pageController.myAccountPage.clickMyAccount();
	   }
=======
	@Given("the Second User {string} exists")
	public void the_second_user_exists(String string) {
	    Register(string,"secondUser","Testov","second@gmail.com","pass!!12AS","pass!!12AS");
	}

	@Given("the User had previously created one account")
	public void the_user_had_previously_created_one_account() {
		this.setUp.pageController.myAccountPage.atLeastOneAccountExist();
	}
	
	@Given("the Second User had some fund in the {string} account")
	public void the_second_user_had_some_fund_in_the_account(String string) {
		if(checkLogin() == false) {
		LogIn("secondUser","pass!!12AS");
		//createTwoAccount();
		//addFundsToAccount(string);
		}
>>>>>>> cfcae3afd14d193a6d7f855b4b5db94938a32d5a
	}
	
	public boolean checkLogin() {
		return setUp.js.executeScript("return sessionStorage.getItem(\"username\");") != null;
	}
	
	
	public void addFundsToAccount(String accountName) {
		this.setUp.pageController.homePage.clickManageAccountBalanceNav();
		this.setUp.pageController.manageAccountBalancePage.clickAccountsDropDown();
		this.setUp.pageController.manageAccountBalancePage.selectAccount("Checking");
		this.setUp.pageController.manageAccountBalancePage.clickAccountTypeDropDown();
		this.setUp.pageController.manageAccountBalancePage.clickAccountTypeIncome();
		this.setUp.pageController.manageAccountBalancePage.inputIntoAmountForm("500");
		this.setUp.pageController.manageAccountBalancePage.inputIntoDescriptionForm("testing");
		this.setUp.pageController.manageAccountBalancePage.clickCreateTransactionButton();
	}
	
	public void createTwoAccount() {
		this.setUp.pageController.homePage.clickCreateBankAccountNav();
		this.setUp.pageController.createBankAccountPage.sendInputToNameForm("Checking");
		this.setUp.pageController.createBankAccountPage.sendInputToDescriptionForm("Checking");
		this.setUp.pageController.createBankAccountPage.selectAccountTypeForm();
		this.setUp.pageController.createBankAccountPage.selectAccountTypeChecking();
		this.setUp.pageController.createBankAccountPage.clickCreateAccount();

		this.setUp.pageController.homePage.clickCreateBankAccountNav();
		this.setUp.pageController.createBankAccountPage.sendInputToNameForm("Saving");
		this.setUp.pageController.createBankAccountPage.sendInputToDescriptionForm("Saving");
		this.setUp.pageController.createBankAccountPage.selectAccountTypeForm();
		this.setUp.pageController.createBankAccountPage.selectAccountTypeChecking();
		this.setUp.pageController.createBankAccountPage.clickCreateAccount();

	}
	
	public void LogIn(String Username, String Password) {
		this.setUp.pageController.homePage.checkIfAtLoginPage();
		this.setUp.pageController.logInPage.inputIntoUsername(Username);
		this.setUp.pageController.logInPage.inputIntoPassword(Password);
		this.setUp.pageController.logInPage.clickLogInButton();
	}
	
	public void Register(String username,String firstName,String lastName,String email,String password,String confirmPassword) {
		this.setUp.pageController.homePage.clickRegisterOnNavBar();
		this.setUp.pageController.registerPage.inputIntoUsername(username);
		this.setUp.pageController.registerPage.inputIntoFirstName(firstName);
		this.setUp.pageController.registerPage.inputIntoLastName(lastName);
		this.setUp.pageController.registerPage.inputIntoPassword(password);
		this.setUp.pageController.registerPage.inputIntoConfirmPassword(confirmPassword);
		this.setUp.pageController.registerPage.inputIntoEmail(email);
		this.setUp.pageController.registerPage.clickRegisterButton();
	}
}
