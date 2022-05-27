@Register
Feature: Register
	Register a user account to CashOverflow

	@Register1
  Scenario: Register Successfully
    Given the User is in homepage
    And the User is not logged in
    When the User clicks on Register on the top
    And the User inputs "testingExample" into Username form
    And the User inputs "Yu" into First Name form
    And the User inputs "Wang" into Last Name form
    And the User inputs "example@hotmail.com" into Email form
    And the User inputs "Password!1" into Password form
    And the User inputs "Password!1" into Confirm Password form
    And the User clicks Register after the form
    Then the app shows a register successfully notification
    And the app moves to login

	@Register2
  Scenario: Register Missing form
    Given the User is in homepage
    And the User is not logged in
    When the User clicks on Register on the top
    And the User clicks Register after the form
    Then the app shows notification on what form needs to be fill


	@Register3
  Scenario: Register Unsuccessfully User Exists
    Given the User is in homepage
    And the User is not logged in
    When the User clicks on Register on the top
    And the User inputs "testingExample" into Username form
    And the User inputs "Yu" into First Name form
    And the User inputs "Wang" into Last Name form
    And the User inputs "example@hotmail.com" into Email form
    And the User inputs "Password!1" into Password form
    And the User inputs "Password!1" into Confirm Password form
    And the User clicks Register after the form
    Then the app shows a there is a problem to registeration notification
