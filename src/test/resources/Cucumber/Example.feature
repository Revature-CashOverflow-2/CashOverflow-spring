@Example
Feature: Example
	
	@Example
  Scenario: Register Successfully
    Given the User is in homepage
    And the User is not logged in
    When the User do something
    Then the app shows something
    And happy face


	Scenario: Login Successfully
		Given the User is in homepage
		When the User clicks on "Login"
		And The User inputs Username
		And The User Inputs Password
		And The User clicks on LogIn button
		Then the application will have a green pop up
	