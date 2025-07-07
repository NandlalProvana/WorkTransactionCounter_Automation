Feature: WTC Redirection and login


  Scenario: Verify that WTCProcessExpert can log in to IPACS and is redirected to the Work Transaction Counter page
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials for WTCProcessExpert from "UAT"
    And the user clicks the login button
    Then the user should be redirected to the dashboardPage
    And the user clicks the Work Transaction Counter button
    And the WTC page should be displayed


  Scenario: Verify successful login to IPACS as WTCProcessExpert, navigation to WTC page, and logout
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials for WTCProcessExpert from "UAT"
    And the user clicks the login button
    Then the user should be redirected to the dashboardPage
    And the user clicks the Work Transaction Counter button
    And the WTC page should be displayed
    And the user logs out of the application on WTC Page

  @RunThis
  Scenario: Verify that WTCProcessExpert can log in to IPACS, navigate to the Work Transaction Counter page, return to the IPACS dashboard, and log out successfully
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials for WTCProcessExpert from "UAT"
    And the user clicks the login button
    Then the user should be redirected to the dashboardPage
    When the user clicks the Work Transaction Counter button
    Then the user should be on the WTC page
    When the user clicks the back button on the Work Transaction Counter page
    Then the user should be redirected back to the IPACS dashboard
    And the user logs out of the application on Ipacs Page


  Scenario: Verify that WTCProcessOwner can log in to IPACS and is redirected to the Work Transaction Counter page
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials for WTCProcessOwner from "UAT"
    And the user clicks the login button
    Then the user should be redirected to the dashboardPage
    And the user clicks the Work Transaction Counter button
    And the WTC page should be displayed

  
  Scenario: Verify successful login to IPACS as WTCProcessOwner, navigation to WTC page, and logout
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials for WTCProcessOwner from "UAT"
    And the user clicks the login button
    Then the user should be redirected to the dashboardPage
    And the user clicks the Work Transaction Counter button
    And the WTC page should be displayed
    And the user logs out of the application on WTC Page

  @RunThis
  Scenario: Verify that WTCProcessOwner can log in to IPACS, navigate to the Work Transaction Counter page, return to the IPACS dashboard, and log out successfully
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials for WTCProcessOwner from "UAT"
    And the user clicks the login button
    Then the user should be redirected to the dashboardPage
    When the user clicks the Work Transaction Counter button
    Then the user should be on the WTC page
    When the user clicks the back button on the Work Transaction Counter page
    Then the user should be redirected back to the IPACS dashboard
    And the user logs out of the application on Ipacs Page