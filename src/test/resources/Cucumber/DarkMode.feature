@DarkMode
Feature: Dark Mode
  The user can select a darker style template

	@DarkMode1
	Scenario: Application change to dark mode
		Given the User is in homepage
		When User clicks on dark mode
		Then application changes to dark mode
