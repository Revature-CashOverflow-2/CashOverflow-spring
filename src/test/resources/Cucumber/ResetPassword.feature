@ResetPassword
Feature: Reset password
  The User wants to change his/her password
  @ResetPassword1
  Scenario: Reset password unsuccessfully
    Given the User is in homepage
		And the First User 'testingExample' exists
		And the User logs in successfully
    When the User clicks the settings button 
    And the User inputs "Password!1" to current password form
    And the User inputs "111111111" to new password form
    And the User inputs "111111111" to retype new password form
    When the User clicks change Password buttons
    Then the application shows a notification on password requirements
  @ResetPassword2
  Scenario: Reset password successfully
    Given the User is in homepage
		And the First User 'testingExample' exists
		And the User logs in successfully
    When the User clicks the settings button 
    And the User inputs "Password!1" to current password form
    And the User inputs "PPassword!!22" to new password form
    And the User inputs "PPassword!!22" to retype new password form
    When the User clicks change Password buttons
    Then the application shows a notification on password changed successfuly


