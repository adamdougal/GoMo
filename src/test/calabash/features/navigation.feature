Feature: Navigation

  Scenario: Monitor Activity should navigate to the Setting Activity via Menu button
    Given I press the menu key
    Then I wait for the "SettingsActivity" screen to appear

  Scenario: Given I am in the Settings Activity, clicking back button should navigate to Home Activity
    Given I press the menu key
    Then I wait for the "SettingsActivity" screen to appear
    Given I press the "Back" button
    Then I wait for the "MonitorActivity" screen to appear

  Scenario: Given I am in the Settings Activity, clicking Save button should navigate to Home Activity
    Given I press the menu key
    Then I wait for the "SettingsActivity" screen to appear
    Given I press the "Save" button
    Then I wait for the "MonitorActivity" screen to appear


