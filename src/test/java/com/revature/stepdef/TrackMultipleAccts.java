package com.revature.stepdef;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TrackMultipleAccts {

	public static SetUp setUp;

	public TrackMultipleAccts() {

	}

	public TrackMultipleAccts(SetUp setUp) {
		this.setUp = setUp;
	}

	@When("the User clicks on create a bank account in Navigation Bar")
	public void the_user_clicks_on_create_a_bank_account_in_Navigation_Bar() {
		this.setUp.pageController.homePage.clickCreateBankAccountNav();
	}

	@When("the User selects checking account")
	public void the_user_selects_checking_account() {
		this.setUp.pageController.createBankAccountPage.selectAccountTypeForm();
		this.setUp.pageController.createBankAccountPage.selectAccountTypeChecking();
	}
	@When("the User inputs {string} into Name form")
	public void the_user_inputs_into_name_form(String string) {
		this.setUp.pageController.createBankAccountPage.sendInputToNameForm(string);

	}

	@When("the User inputs {string} into Description Form")
	public void the_user_inputs_into_description_form(String string) {
		this.setUp.pageController.createBankAccountPage.sendInputToDescriptionForm(string);

	}
	@When("the User clicks create account")
	public void the_user_clicks_create_account() {
		this.setUp.pageController.createBankAccountPage.clickCreateAccount();
	}

	@Then("the application shows a new checking account is created")
	public void the_application_shows_a_new_checking_account_is_created() {
		this.setUp.pageController.myAccountPage.accountExist("Checking");
	}

	@When("the User selects savings account")
	public void the_user_selects_savings_account() {
		this.setUp.pageController.createBankAccountPage.selectAccountTypeForm();
		this.setUp.pageController.createBankAccountPage.selectAccountTypeSaving();
	}
	@When("the User clicks on My Account")
	public void the_user_clicks_on_my_account() {
		this.setUp.pageController.homePage.clickMyAccount();
	}
	@Then("the application shows a new savings account is created")
	public void the_application_shows_a_new_savings_account_is_created() {
		this.setUp.pageController.myAccountPage.accountExist("Saving");
	}

	@Then("the application notifies the user that they need to fill in the missing form")
	public void the_application_notifies_the_user_that_they_need_to_fill_in_the_missing_form() {
		this.setUp.pageController.createBankAccountPage.ValidationNotice();
	}

	@When("the User clicks on create my accounts")
	public void the_user_clicks_on_create_my_accounts() {
		this.setUp.pageController.createBankAccountPage.clickCreateAccount();
	}

	@When("the User clicks between accounts to access")
	public void the_user_clicks_between_accounts_to_access() {
		this.setUp.pageController.homePage.clickMyAccount();

	}

	@Then("the application displays all accounts")
	public void the_application_displays_all_accounts() {
		assertTrue(this.setUp.pageController.myAccountPage.viewSuccess());
	}

}
