@SendMoney
Feature: Send Money


  @SendMoney1
  Scenario:  User Send Money Successfully
    Given the User is in homepage
    And the Second User 'secondUser' exists
    And the First User 'testingExample' exists
    And the User logs in successfully
    And the User had previously created one account
    When the User hovers on Transfer Money
    And the User clicks on With Other Users
    And the User selects 'Send' in Request/Send form
    And the User inputs 'secondUser' in Username field
    And the User selects 'Checking' in From/To field
    And the User inputs '100' in Amount field
    And the User clicks Submit button
    Then green notification appears as a transfer success
  @SendMoney2
  Scenario: User Accept Sent Money
    Given the User is in homepage
    And the User logs in successfully as 'secondUser' 'pass!!12AS'
    And the User had previously created one account
    When the User hovers on Transfer Money
    And the User clicks on With Other Users
    And the User finds the request from "Checking"
    And the User selects "Checking" account from To deposit Form from "testingExample" request
    And the User clicks Accept button from "testingExample" "sent" Request
    Then green notification appears as a transfer success
  @SendMoney3
  Scenario:  User Send Money Successfully
    Given the User is in homepage
    And the User logs in successfully as 'secondUser' 'pass!!12AS'
    And the User had previously created one account
    When the User hovers on Transfer Money
    And the User clicks on With Other Users
    And the User finds the request from "Checking"
    And the User selects "Checking" account from To deposit Form from "testingExample" request
    And the User clicks Deny button from "testingExample" "sent" Request
    Then green notification appears as a transfer denied succesfully
