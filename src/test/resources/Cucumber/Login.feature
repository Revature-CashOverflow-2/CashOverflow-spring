@Login
Feature: Login	
User logs into CashOverflow account

@Login1
Scenario: Successfully Login
Given the User is in homepage
And the User is not logged in
When the User inputs "testingExample" into Username login form
And the User inputs "Password!1" into Password login form
And the User clicks the log in button
Then the app displays Login Successfully

@Login2
Scenario: User Successfully Logs out
Given the User is in homepage
And the User logs in successfully
When the User clicks the log out button
Then the app displays log out successfully

@Login3
Scenario: Login Unsuccessfully Incorrect Password
Given the User is in homepage
And the User is not logged in
When the User inputs "testingExample" into Username login form
And the User inputs "1234225" into Password login form
And the User clicks the log in button
Then the app displays incorrect credentials

@Login4
Scenario: Login Unsuccessfully Incorrect Username
 Given the User is in homepage
 And the User is not logged in
 When the User inputs "askjsh jkashkhjkd" into Username login form
 And the User inputs "Password!1" into Password login form
 And the User clicks the log in button
 Then the app displays incorrect credentials



