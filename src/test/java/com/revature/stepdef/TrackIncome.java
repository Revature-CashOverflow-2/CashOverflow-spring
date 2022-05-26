package com.revature.stepdef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TrackIncome {
	
	public static SetUp setUp;

	public TrackIncome() {

	}

	public TrackIncome(SetUp setUp) {
		this.setUp = setUp;
	}

	@When("the User clicks My Account")
	public void the_user_clicks_my_account() {
		this.setUp.pageController.homePage.clickMyAccount();;
	}

	@When("the User clicks an account")
	public void the_user_clicks_an_account() {
		this.setUp.pageController.myAccountPage.clickMyAccount();;
	}

	@Then("the application displays the income to account")
	public void the_application_displays_the_income_to_account() {
		this.setUp.pageController.myAccountPage.viewSuccess();
	}

}
