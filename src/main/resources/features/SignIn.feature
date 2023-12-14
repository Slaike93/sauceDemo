Feature: Shopping Automation
  As a user, I want to login to the website

  Scenario: Testing the standard user authentication
    Given I go to the Website
    When I specify my standard user credential and click Login
    Then I can log into the home page

  Scenario: Testing login with a locked out user
    Given I go to the Website
    When I specify my locked out user's credential and click Login
    Then I see the locked out error message

  Scenario: Testing login with wrong credentials
    Given I go to the Website
    When I enter wrong credentials and click login
    Then I see the wrong credentials error message