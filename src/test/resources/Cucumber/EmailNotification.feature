@EmailNotification
Feature: EmailNotification
  User turns on Email notifications

  @EmailNotification
  Scenario: Turning on Email Notification
    Given the User is in homepage
		When the User logs in successfully
    And the user click the settings button 
    And the user turns on notification
    And user enters Amount threshold
    Then the user clicks save settings
    And the message displays user has turned on notifications

  