package com.revature.stepdef;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SessionManagmentTest {

	public static SetUp setUp;

	public SessionManagmentTest() {

	}

	public SessionManagmentTest(SetUp setUp) {
		this.setUp = setUp;
	}
	
	@When("the User clicks on different features")
	public void the_user_clicks_on_different_features() {
		this.setUp.pageController.homePage.clickRegisterOnNavBar();
	    this.setUp.pageController.homePage.clickMyAccount();
	    this.setUp.pageController.homePage.clickManageAccountBalanceNav();
	    this.setUp.pageController.homePage.clickCreateBankAccountNav();
	    this.setUp.pageController.homePage.clickAccountRegisterMenuBar();
	}
	
	@Then("the User stays logged in while exploring features without being logged out")
	public void the_user_stays_logged_in_while_exploring_features_without_being_logged_out() {
	    assertTrue(this.setUp.pageController.homePage.clickAccountRegisterMenuBar());
		}
	}
