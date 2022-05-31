@TrackExpense
Feature: Track Expense
	Track expense in user account
	
	@TrackExpense1
	Scenario: Track Expense
		Given the User is in homepage
		And the First User 'testingExample' exists
		And the User logs in successfully
		And the User had previously created one account
		And the User had expense transfer to "Checking" account
		When the User clicks on My Account
		And the User clicks on "Checking" account
		Then the application displays the expense to that account
	
