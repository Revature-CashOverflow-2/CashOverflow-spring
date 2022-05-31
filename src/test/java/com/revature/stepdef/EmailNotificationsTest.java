package com.revature.stepdef;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EmailNotificationsTest {
	
	public static SetUp setUp;

	public EmailNotificationsTest() {

	}
	public EmailNotificationsTest(SetUp setUp) {
		this.setUp = setUp;
	}
	
	@When("the User clicks the settings button")
	public void the_User_clicks_the_settings_button() {
		this.setUp.pageController.homePage.clickSettings();
	}

	@When("the User turns on notification")
	public void the_user_turns_on_notification() {
		this.setUp.pageController.settingsPage.turnOnNotification();
	}

	@When("the User enters Amount threshold")
	public void User_enters_amount_threshold() {
		this.setUp.pageController.settingsPage.enterAmountThreshold();
	}

	@Then("the User clicks save settings")
	public void the_User_clicks_save_settings() {
		this.setUp.pageController.settingsPage.clickSaveSettings();
	}

	@Then("the message displays User has turned on notifications")
	public void the_message_displays_User_has_turned_on_notifications() {
		assertTrue(this.setUp.pageController.settingsPage.viewNotificationSuccess());
	}
	
}
