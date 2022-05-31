package com.revature.stepdef;

import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DarkModeTest {
	
	public static SetUp setUp;
	
	public DarkModeTest() {
		
	}
	
	public DarkModeTest(SetUp setUp) {
		this.setUp = setUp;
	}

	@When("User clicks on dark mode")
	public void user_clicks_on_dark_mode() {
	   this.setUp.pageController.logInPage.clickDarkModeButton();
	}
	
	@Then("application changes to dark mode")
	public void application_changes_to_dark_mode() {
	
		String exptheme = "dark-theme";
		String acttheme = this.setUp.pageController.logInPage.gettheme();
		Assert.assertEquals(exptheme,acttheme);
		
	}
}
