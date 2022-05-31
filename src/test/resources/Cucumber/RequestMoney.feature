

Feature: Request Money  
  
  Scenario: Request Money From Another User
    Given the User is in homepage
    And the Second User 'secondUser' exists
    And the First User 'testingExample' exists
    And the User logs in successfully
    And the User had previously created one account
    When the User hovers on Transfer Money
    And the User clicks on With Other Users
    And the User selects 'Request' in Request/Send form
    And the User inputs 'secondUser' in Username field
    And the User selects 'Checking' in From/To field
    And the User inputs '100' in Amount field
    And the User clicks Submit button
    Then green notification appears as a transfer success
  
  Scenario: Request Money From Incorrect Account
    Given the User is in homepage
    And the First User 'testingExample' exists
    And the User logs in successfully
    And the User had previously created one account
    When the User hovers on Transfer Money
    And the User clicks on With Other Users
    And the User selects 'Request' in Request/Send form
    And the User inputs 'nonExistingUsername' in Username field
    And the User selects 'Checking' in From/To field
    And the User inputs '100' in Amount field
    And the User clicks Submit button
    Then red notification appears as a transfer fail
    
  
  Scenario: User Accepts a request
    Given the User is in homepage
    And the User logs in successfully as 'secondUser' 'pass!!12AS'
    And the User had previously created one account
    And the User had some fund in the "Checking" account
    When the User hovers on Transfer Money
    And the User clicks on With Other Users
    And the User finds the request from "Checking"
    And the User selects "Checking" account from To Withdraw Form from "testingExample" request
    And the User clicks Accept button from "testingExample" "request" Request
    Then green notification appears as a transfer success
  
  
  Scenario: User Rejects A Request
    Given the User is in homepage
    And the User logs in successfully as 'secondUser' 'pass!!12AS'
    And the User had previously created one account
    And the User had some fund in the "Checking" account
    When the User hovers on Transfer Money
    And the User clicks on With Other Users
    And the User finds the request from "Checking"
    And the User selects "Checking" account from To Withdraw Form from "testingExample" request
    And the User clicks Deny button from "testingExample" "request" Request
    Then green notification appears as a transfer denied succesfully
