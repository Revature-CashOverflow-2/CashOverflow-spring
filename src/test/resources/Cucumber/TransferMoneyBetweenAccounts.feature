@TMBA
Feature: Transfer Money Between Accounts
  I want to use this template for my feature file

  @TMBA1
  Scenario: Transfer money between accounts with Success
    Given the User is in homepage
    And the User logs in successfully
    And the User had previously created two accounts
    And the User had some fund in the "Checking" account
    When the User hovers on Transfer Money
    And the User clicks on Between Account
    And the User selects account one in the send funds from 
    And the User select account two to receive funds
    And the User inputs the "100" in the amount
    And the User clicks on Submit
    Then The application shows that the money is transferred from account one to account two

  @TMBA2
  Scenario: Transfer money between accounts with insufficient funds
    Given the User is in homepage
    And the User logs in successfully
    And the User had previously created two accounts
    And the User had some fund in the "Checking" account
    When the User hovers on Transfer Money
    And the User clicks on Between Account
    And the User selects account one in the send funds from 
    And the User select account two to receive funds
    And the User inputs the "600" in the amount
    And the User clicks on Submit
    Then the app shows a red application that there is insufficient fund
