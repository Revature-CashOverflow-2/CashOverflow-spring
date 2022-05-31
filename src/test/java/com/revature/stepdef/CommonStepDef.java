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
	public static TrackMultipleAcctsTest tma;
	public static TransferMoneyFromAccountsTest TMFAt;
	public static LoginTest lt;
	public static EmailNotificationsTest EMN;
	public static RequestAndSendMoneyTest reqMoney;
	public static TrackIncomeAndExpenseTest TI;
	public static SessionManagmentTest SMT;
	public static DarkModeTest DMT;
	public static ResetPasswordTest RPT;

	@BeforeAll
	public static void beforeAll() {
		setUp = new SetUp();
		rt = new RegisterTest(setUp);
		tma = new TrackMultipleAcctsTest(setUp);
		TMFAt = new TransferMoneyFromAccountsTest(setUp);
		lt = new LoginTest(setUp);
		EMN = new EmailNotificationsTest(setUp);
		reqMoney = new RequestAndSendMoneyTest(setUp);
		TI = new TrackIncomeAndExpenseTest(setUp);
		SMT = new SessionManagmentTest(setUp);
		DMT = new DarkModeTest(setUp);
		RPT = new ResetPasswordTest(setUp);
	}

	@Given("the User is in homepage")
	public void the_user_is_in_homepage() {
		setUp.driver.get("http://localhost:4200/");
		setUp.js.executeScript("return sessionStorage.removeItem(\"username\");");
	}

	@Given("the User is not logged in")
	public void the_user_is_not_logged_in() {
		setUp.pageController.homePage.clickLogInOnNavBar();
		assertFalse(checkLogin());
	}

	@Given("the User had previously created two accounts")
	public void the_user_had_previously_created_two_accounts() {
		if (this.setUp.pageController.myAccountPage.atLeastNAccountExisted(2) == false) {
			this.createTwoAccount();
		}
	}

	@Given("the User logs in successfully as {string} {string}")
	public void the_user_logs_in_successfully_as(String string, String string2) {
		if (checkLogin() == false) {
			LogIn(string, string2);
		}
	}

	@Given("the User had some fund in the {string} account")
	public void the_user_had_some_fund_in_the_account(String string) {
		this.setUp.pageController.homePage.clickMyAccount();
		if (this.setUp.pageController.myAccountPage.getAccountBalance(string) == 0.0) {
			addIncomeToAccount(string);
		}
	}

	@Given("the User logs in successfully")
	public void the_user_logs_in_successfully() {
		if (checkLogin() == false) {
			LogIn("testingExample", "Password!1");
		} else {
			this.setUp.pageController.homePage.clickLogOutButton();
			this.setUp.pageController.homePage.clickLogInOnNavBar();
			LogIn("testingExample", "Password!1");
		}
	}

	@Given("the User has a bank account")
	public void the_user_has_a_bank_account() {
		this.setUp.pageController.homePage.clickMyAccount();
		if (this.setUp.pageController.myAccountPage.accountExist("Checking")) {
			this.setUp.pageController.myAccountPage.clickMyAccount();
		}
	}

	@Given("the First User {string} exists")
	public void the_First_User_exists(String string) {
		Register(string, "Yu", "Wang", "example@hotmail.com", "Password!1", "Password!1");
	}

	@Given("the Second User {string} exists")
	public void the_second_user_exists(String string) {
		Register(string, "secondUser", "Testov", "second@gmail.com", "pass!!12AS", "pass!!12AS");
	}

	@Given("the User had previously created one account")
	public void the_user_had_previously_created_one_account() {
		this.createTwoAccount();
	}

	@Given("the User had income transfer to {string} account")
	public void the_user_had_income_transfer_to_account(String string) {
		this.addIncomeToAccount(string);
	}

	@Given("the User had expense transfer to {string} account")
	public void the_user_had_expense_transfer_to_account(String string) {
		this.addExpenseToAccount(string);
	}

	public boolean checkLogin() {
		return setUp.js.executeScript("return sessionStorage.getItem(\"username\");") != null;
	}

	public void addIncomeToAccount(String accountName) {
		this.setUp.pageController.homePage.clickManageAccountBalanceNav();
		this.setUp.pageController.manageAccountBalancePage.clickAccountsDropDown();
		this.setUp.pageController.manageAccountBalancePage.selectAccount("Checking");
		this.setUp.pageController.manageAccountBalancePage.clickAccountTypeDropDown();
		this.setUp.pageController.manageAccountBalancePage.clickAccountTypeIncome();
		this.setUp.pageController.manageAccountBalancePage.inputIntoAmountForm("500");
		this.setUp.pageController.manageAccountBalancePage.inputIntoDescriptionForm("testing");
		this.setUp.pageController.manageAccountBalancePage.clickCreateTransactionButton();
	}


	public void addExpenseToAccount(String accountName) {
		this.setUp.pageController.homePage.clickManageAccountBalanceNav();
		this.setUp.pageController.manageAccountBalancePage.clickAccountsDropDown();
		this.setUp.pageController.manageAccountBalancePage.selectAccount("Checking");
		this.setUp.pageController.manageAccountBalancePage.clickAccountTypeDropDown();
		this.setUp.pageController.manageAccountBalancePage.clickAccountTypeExpense();
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
		this.setUp.pageController.homePage.clickLogInOnNavBar();
		this.setUp.pageController.logInPage.inputIntoUsername(Username);
		this.setUp.pageController.logInPage.inputIntoPassword(Password);
		this.setUp.pageController.logInPage.clickLogInButton();
	}


	public void Register(String username, String firstName, String lastName, String email, String password, String confirmPassword) {
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
