Feature: Jenkins Url Validation

  Background: Navigate to settings page
    Given I press the menu key
    When I wait for the "SettingsActivity" screen to appear

  Scenario:  Given an invalid URL should show message
    Given I clear input field number 2
    Then I enter "10.65.87.9s" into input field number 2
    Then I press the "Save" button
    Then I should see "Invalid Jenkins JSON file"




