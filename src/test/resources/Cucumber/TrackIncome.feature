	@TrackIncome
	Feature: Income credited to account
	Track income created by
	
	@TrackIncome1
	Scenario: Track Income
	Given the User logs in successfully
	And the User has a bank account
	When the User clicks My Account
	And the User clicks an account
	Then the application displays the income to account
	