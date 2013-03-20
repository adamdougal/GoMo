Feature: Default values in Settings Activity

  Scenario: Should contain Jenkins jobs from default Jenkins value
    Given I wait for progress
    Then I should see "ipython"
    Then I should see "ipython-windows #"

  Scenario: Should contain Refresh rate from default value
    Given I press the menu key
    Then I wait for the "SettingsActivity" screen to appear
    Then I should see "60"
    Then I should see "https://jenkins.shiningpanda.com/ipython/"


