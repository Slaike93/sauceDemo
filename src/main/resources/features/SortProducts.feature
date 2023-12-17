Feature: Sort the products in the home page

  Background:
    Given I go to the Website
    Then I specify my standard user credential and click Login
    Then I am into the home page

  Scenario: Sorting the products in name ascending order
    When I select the name ascending order sorting
    Then I see the products in name ascending order

  Scenario: Sorting the products in name descending order
    When I select the name descending order sorting
    Then I see the products in name descending order
