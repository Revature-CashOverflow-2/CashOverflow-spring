package com.revature.stepdef;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TrackIncomeAndExpenseTest {
	
	public static SetUp setUp;

	public TrackIncomeAndExpenseTest() {

	}

	public TrackIncomeAndExpenseTest(SetUp setUp) {
		this.setUp = setUp;
	}

	@When("the User clicks My Account")
	public void the_user_clicks_my_account() {
		this.setUp.pageController.homePage.clickMyAccount();;
	}
	
	@When("the User clicks on {string} account")
	public void the_user_clicks_on_account(String string) {
		this.setUp.pageController.myAccountPage.clickOnAccount(string);
	}

	@Then("the application displays the income to that account")
	public void the_application_displays_the_income_to_that_account() {
		assertTrue(this.setUp.pageController.myAccountPage.trackIncomeTransactionType());
	}

	@Then("the application displays the expense to that account")
	public void the_application_displays_the_expense_to_that_account() {
		assertTrue(this.setUp.pageController.myAccountPage.trackExpenseTransactionType());
	}
}
