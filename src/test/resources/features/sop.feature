Feature: SOP Profile Creation

  As an authenticated IPACS user
  I want to be able to select accessibility and certification options
  So that I can successfully create a new SOP profile

  @run
  Scenario: Create SOP with Accessibility Option 1 by Admin
    Given the user is logged into the IPACS application with environment "UAT"
    And the user navigates to the Add New SOP page
    When the user fills in mandatory SOP profile details from "UAT" and selects accessibility option 1
    And the user submits the SOP form
    Then the SOP should be created successfully


  Scenario: Create SOP with Accessibility Option 2 by Admin
    Given the user is logged into the IPACS application with environment "UAT"
    And the user navigates to the Add New SOP page
    When the user fills in mandatory SOP profile details from "UAT" and selects accessibility option 2
    And the user submits the SOP form
    Then the SOP should be created successfully


  Scenario: Create SOP with Accessibility Option 3 by Admin
    Given the user is logged into the IPACS application with environment "UAT"
    And the user navigates to the Add New SOP page
    When the user fills in mandatory SOP profile details from "UAT" and selects accessibility option 3
    And the user submits the SOP form
    Then the SOP should be created successfully

  @run
  Scenario: Verify that the created SOP is visible to the Process Expert
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials for WTCProcessExpert from "UAT"
    And the user clicks the login button
    Then the user should be redirected to the dashboardPage
    And the user clicks the Work Transaction Counter button
    And the WTC page should be displayed
    When the user searches for the created SOP in the search field from "UAT"
    Then the SOP should be visible in the grid from "UAT"

