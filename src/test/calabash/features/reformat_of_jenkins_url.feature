Feature: Jenkins Url Validation

  Background: Navigate to settings page
    Given I press the menu key
    When I wait for the "SettingsActivity" screen to appear

  Scenario: Given Jenkins URL without "http://" and "/", should prepend "http://" and append "/" onto provided Jenkins URL
    Given I clear input field number 2
    Then I enter "10.65.87.90:8080" into input field number 2
    Then I press the "Save" button
    Given I press the menu key
    When I wait for the "SettingsActivity" screen to appear
    Then I should see "http://10.65.87.90:8080/"















