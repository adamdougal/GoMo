Feature: Refresh Rate Validation

  Background: Navigate to settings page
    Given I press the menu key
    When I wait for the "SettingsActivity" screen to appear

  Scenario: Refresh rate less than 5 should see message
    Given I clear input field number 1
    Then I enter "4" into input field number 1
    Then I press the "Save" button
    Then I should see "Refresh rate cannot be lower than 5 seconds"

  Scenario: Refresh rate with empty value should see message
    Given I clear input field number 1
    Then I press the "Save" button
    Then I should see "Refresh rate cannot be empty"

  Scenario: Refresh rate with non-numerical value should see message
    Given I clear input field number 1
    Then I enter "abc" into input field number 1
    Then I press the "Save" button
    Then I should see "Refresh rate must be a number"

  Scenario:Refresh rate should be saved
    When I clear input field number 1
    Then I enter "5" into input field number 1
    Then I press the "Save" button
    Given I press the menu key
    When I wait for the "SettingsActivity" screen to appear
    Then I should see "5"






