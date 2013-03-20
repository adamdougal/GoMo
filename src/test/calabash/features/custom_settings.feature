Feature: Custom settings

  Background: Navigate to settings page
    Given I press the menu key
    Then I wait for the "SettingsActivity" screen to appear

  Scenario: Monitor should reflect custom settings
    Given I clear input field number 2
    Then I enter "10.65.87.90:8080" into input field number 2
    Then I press the "Save" button
    Then I wait for the "MonitorActivity" screen to appear
    Then I wait for progress
    Then I should see "umv #"
    Then I should not see "ipython"

