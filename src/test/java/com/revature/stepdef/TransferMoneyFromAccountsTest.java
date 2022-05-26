package com.revature.stepdef;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TransferMoneyFromAccountsTest {
	
	public static SetUp setUp;
	
	double checking;
	double saving;
	
	public TransferMoneyFromAccountsTest() {
		
	}
	
	public TransferMoneyFromAccountsTest(SetUp setUp) {
		this.setUp = setUp;
	}

	@When("the User selects account one in the send funds from")
	public void the_user_selects_account_one_in_the_send_funds_from() {
		this.setUp.pageController.transferMoneyPage.selectFromForm("Checking");
	}


	@When("the User select account two to receive funds")
	public void the_user_select_account_two_to_receive_funds() {
		this.setUp.pageController.transferMoneyPage.selectToForm("Saving");
	}

	@When("the User inputs the {string} in the amount")
	public void the_user_inputs_the_in_the_amount(String string) {
		this.setUp.pageController.transferMoneyPage.inputAmount(string);
	}

	@When("the User clicks on Submit")
	public void the_user_clicks_on_Submit() {
		this.setUp.pageController.transferMoneyPage.clickSubmit();
	}

	@Then("The application shows that the money is transferred from account one to account two")
	public void the_application_shows_that_the_money_is_transferred_from_account_one_to_account_two() {
		assertTrue(this.setUp.pageController.transferMoneyPage.transferSuccessNotification());
	}

	@Then("the app shows a red application that there is insufficient fund")
	public void the_app_shows_a_red_application_that_there_is_insufficient_fund() {
		assertTrue(this.setUp.pageController.transferMoneyPage.transferErrorNotification());

	}
	@When("the User hovers on Transfer Money")
	public void the_user_hovers_on_transfer_money() {
		this.setUp.pageController.homePage.hoverTransferMoney();
	}

	@When("the User clicks on Between Account")
	public void the_user_clicks_on_between_account() {
		this.setUp.pageController.homePage.clickBetweenAccount();
	}
}
