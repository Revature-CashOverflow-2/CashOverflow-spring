@EmailNotification
Feature: EmailNotification
  User turns on Email notifications

  @EmailNotification
  Scenario: Turning on Email Notification
    Given the User is in homepage
		And the First User 'testingExample' exists
		And the User logs in successfully
    When the User clicks the settings button 
    And the User turns on notification
    And the User enters Amount threshold
    Then the User clicks save settings
    And the message displays User has turned on notifications

  