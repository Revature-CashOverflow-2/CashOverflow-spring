package com.revature.stepdef;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegisterTest {
	
	public static SetUp setUp;
	
	public RegisterTest() {
		
	}
	
	public RegisterTest(SetUp setUp) {
		this.setUp = setUp;
	}
	

	@When("the User clicks on Register on the top")
	public void the_user_clicks_on_register_on_the_top() {
		this.setUp.pageController.homePage.clickRegisterOnNavBar();
	}

	@When("the User inputs {string} into Username form")
	public void the_user_inputs_into_username_form(String string) {
		this.setUp.pageController.registerPage.inputIntoUsername(string);
	}

	@When("the User inputs {string} into First Name form")
	public void the_user_inputs_into_first_name_form(String string) {
		this.setUp.pageController.registerPage.inputIntoFirstName(string);
	}

	@When("the User inputs {string} into Last Name form")
	public void the_user_inputs_into_last_name_form(String string) {
		this.setUp.pageController.registerPage.inputIntoLastName(string);
	}

	@When("the User inputs {string} into Email form")
	public void the_user_inputs_into_email_form(String string) {
		this.setUp.pageController.registerPage.inputIntoEmail(string);
	}

	@When("the User inputs {string} into Password form")
	public void the_user_inputs_into_password_form(String string) {
		this.setUp.pageController.registerPage.inputIntoPassword(string);

	}

	@When("the User inputs {string} into Confirm Password form")
	public void the_user_inputs_into_confirm_password_form(String string) {
		this.setUp.pageController.registerPage.inputIntoConfirmPassword(string);
	}

	@When("the User clicks Register after the form")
	public void the_user_clicks_register_after_the_form() {
		this.setUp.pageController.registerPage.clickRegisterButton();
	}

	@Then("the app shows a register successfully notification")
	public void the_app_shows_a_register_successfully_notification() {
		assertTrue(this.setUp.pageController.registerPage.registerSuccessPopUp());
	}

	@Then("the app moves to login")
	public void the_app_moves_to_login() {
		assertTrue(this.setUp.pageController.homePage.checkIfAtLoginPage());
	}

	@Then("the app shows notification on what form needs to be fill")
	public void the_app_shows_notification_on_what_form_needs_to_be_fill() {
		assertTrue(this.setUp.pageController.registerPage.ValidationNotice());
	}

	@Then("the app shows a there is a problem to registeration notification")
	public void the_app_shows_a_there_is_a_problem_to_registeration_notification() {
		assertTrue(this.setUp.pageController.registerPage.registerFailPopUp());
	}
}
