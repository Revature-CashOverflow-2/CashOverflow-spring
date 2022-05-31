package com.revature.stepdef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ResetPasswordTest {

	public static SetUp setUp;
	
	public ResetPasswordTest() {
		
	}
	
	public ResetPasswordTest(SetUp setUp) {
		this.setUp = setUp;
	}
	
	
	@When("the User inputs {string} to current password form")
	public void the_user_inputs_to_current_password_form(String string) {
		this.setUp.pageController.settingsPage.inputCurrentPassword(string);
	}

	@When("the User inputs {string} to new password form")
	public void the_user_inputs_to_new_password_form(String string) {
		this.setUp.pageController.settingsPage.inputNewPassword(string);
	}

	@When("the User inputs {string} to retype new password form")
	public void the_user_inputs_to_retype_new_password_form(String string) {
		this.setUp.pageController.settingsPage.inputReTypePassword(string);
	}

	@When("the User clicks change Password buttons")
	public void the_user_clicks_change_password_buttons() {
		this.setUp.pageController.settingsPage.clickChangePassword();
	}
	
	@Then("the application shows a notification on password requirements")
	public void the_application_shows_a_notification_on_password_requirements() {
		this.setUp.pageController.settingsPage.viewFailMessage();
	}

	@Then("the application shows a notification on password changed successfuly")
	public void the_application_shows_a_notification_on_password_changed_successfuly() {
		this.setUp.pageController.settingsPage.viewPasswordChangedSuccess();
	}
}
