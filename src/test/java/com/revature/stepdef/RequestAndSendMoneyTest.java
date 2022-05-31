package com.revature.stepdef;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RequestAndSendMoneyTest {

	public static SetUp setUp;
	
	public RequestAndSendMoneyTest() {
	}

	public RequestAndSendMoneyTest(SetUp setUp) {
		this.setUp = setUp;
	}
	
	
	@When("the User selects {string} in Request\\/Send form")
	public void the_user_selects_in_request_send_form(String string) {
		this.setUp.pageController.transferMoneyWithOtherUsersPage.selectRequestSendForm(string);
	}

	@When("the User inputs {string} in Username field")
	public void the_user_inputs_in_username_field(String string) {
		this.setUp.pageController.transferMoneyWithOtherUsersPage.inputUsername(string);
	}

	@When("the User selects {string} in From\\/To field")
	public void the_user_selects_in_from_to_field(String string) {
		this.setUp.pageController.transferMoneyWithOtherUsersPage.selectFromTo(string);
	}

	@When("the User inputs {string} in Amount field")
	public void the_user_inputs_in_amount_field(String string) {
		this.setUp.pageController.transferMoneyWithOtherUsersPage.inputAmount(string);
	}

	@When("the User clicks Submit button")
	public void the_user_clicks_submit_button() {
		this.setUp.pageController.transferMoneyWithOtherUsersPage.clickSubmit();
	}

	@Then("green notification appears as a transfer success")
	public void green_notification_appears_as_a_transfer_success() {
		assertTrue(this.setUp.pageController.transferMoneyWithOtherUsersPage.transferSuccessNotification());
	}

	@Then("red notification appears as a transfer fail")
	public void red_notification_appears_as_a_transfer_fail() {
		assertTrue(this.setUp.pageController.transferMoneyWithOtherUsersPage.transferErrorNotification());
	}
	
	@When("the User finds the request from {string}")
	public void the_user_finds_the_request_from(String string) {
		this.setUp.pageController.transferMoneyWithOtherUsersPage.findRequest(string,"request");
	}
	
	@When("the User selects {string} account from To Withdraw Form from {string} request")
	public void the_user_selects_account_from_to_withdraw_form_from_request(String string, String string2) {
		this.setUp.pageController.transferMoneyWithOtherUsersPage.selectAccountOnRequest(string, string2,"request");
	}
	
	@When("the User clicks Accept button from {string} {string} Request")
	public void the_user_clicks_accept_button_from_request(String string, String string2) {
		this.setUp.pageController.transferMoneyWithOtherUsersPage.clickAcceptOnRequest(string,string2);
	}

	@When("the User clicks Deny button from {string} {string} Request")
	public void the_user_clicks_deny_button_from_request(String string, String string2) {
		this.setUp.pageController.transferMoneyWithOtherUsersPage.clickRejectOnRequest(string,string2);
	}

	@When("the User selects {string} account from To deposit Form from {string} request")
	public void the_user_selects_account_from_to_deposit_form_from_request(String string, String string2) {
		this.setUp.pageController.transferMoneyWithOtherUsersPage.selectAccountOnRequest(string, string2,"sent");
	}
	
	
	@Then("green notification appears as a transfer denied succesfully")
	public void green_notification_appears_as_a_transfer_denied_succesfully() {
		assertTrue(this.setUp.pageController.transferMoneyWithOtherUsersPage.transferDenyNotification());
	}
	
	@When("the User clicks on With Other Users")
	public void the_user_clicks_on_with_other_users() {
		this.setUp.pageController.homePage.clickWithOtherUsers();
	}	

}
