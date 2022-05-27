package com.revature.stepdef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EmailNotifications {
	
	public static SetUp setUp;

	public EmailNotifications() {

	}
	public EmailNotifications(SetUp setUp) {
		this.setUp = setUp;
	}
	
	@When("the user click the settings button")
	public void the_user_click_the_settings_button() {
		this.setUp.pageController.emailNotificationPage.ClickSetting();
	}

	@When("the user turns on notification")
	public void the_user_turns_on_notification() {
		this.setUp.pageController.emailNotificationPage.TurnonNotification();
	}

	@When("user enters Amount threshold")
	public void user_enters_amount_threshold() {
		this.setUp.pageController.emailNotificationPage.EnterAmountThreshold();
	}

	@Then("the user clicks save settings")
	public void the_user_clicks_save_settings() {
		this.setUp.pageController.emailNotificationPage.ClickSaveSettings();
	}

	@Then("the message displays user has turned on notifications")
	public void the_message_displays_user_has_turned_on_notifications() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
}
