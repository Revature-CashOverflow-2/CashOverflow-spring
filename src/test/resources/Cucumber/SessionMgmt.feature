
@SessionMgmt
Feature: Session maintained until user logs out
Manage session created by

@SessionMgmt1
Scenario: Session Management
Given  the User is in homepage
And  the User logs in successfully
When the User clicks on different features
Then the User stays logged in while exploring features without being logged out