Feature: IPACS Login Feature


  Scenario: ✅ Verify user login to IPACS as WTCProcessExpert using Excel-configured credentials
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials for WTCProcessExpert from "UAT"
    And the user clicks the login button
    Then the user should be redirected to the dashboardPage


  Scenario: ✅ Verify user login to IPACS as WTCProcessOwner using Excel-configured credentials
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials for WTCProcessOwner from "UAT"
    And the user clicks the login button
    Then the user should be redirected to the dashboardPage

  Scenario: ❌ Verify login fails with invalid password
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials from "UAT" using InvalidPassword
    And the user clicks the login button
    Then the user should see an invalid login message

  Scenario: ❌ Verify login fails with invalid username
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials from "UAT" using InvalidUserName
    And the user clicks the login button
    Then the user should see an invalid login message

  Scenario: ❌ Verify login fails with invalid partner code
    Given the user is on the IPACS login page from "UAT"
    When the user enters credentials from "UAT" using InvalidPartnerCode
    And the user clicks the login button
    Then the user should see an invalid partner code message