	@TrackIncome
	Feature: Track Income
	Track income in user account
	
	@TrackIncome1
	Scenario: Track Income
		Given the User is in homepage
		And the First User 'testingExample' exists
		And the User logs in successfully
		And the User had previously created one account
		And the User had income transfer to "Checking" account
		When the User clicks on My Account
		And the User clicks on "Checking" account
		Then the application displays the income to that account
	