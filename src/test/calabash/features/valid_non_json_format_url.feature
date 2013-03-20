Feature: Jenkins Url Validation

  Background: Navigate to settings page
    Given I press the menu key
    When I wait for the "SettingsActivity" screen to appear

  Scenario:  Given a not JSON URL should show message
    Given I clear input field number 2
    Then I enter "http://sky.com" into input field number 2
    Then I press the "Save" button
    Then I should see "Not a JSON file"
