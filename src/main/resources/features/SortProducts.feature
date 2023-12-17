Feature: Sort the products in the home page

  Background:
    Given I go to the Website
    Then I specify my standard user credential and click Login
    Then I am into the home page

  Scenario: Sorting the products in name ascending order
    When I select ascending sorting by name
    Then I see the products sorted by name in ascending order

  Scenario: Sorting the products in name descending order
    When I select descending sorting by name
    Then I see the products sorted by name in descending order

  Scenario: Sorting the products in price ascending order
    When I select ascending sorting by price
    Then I see the products sorted by price in ascending order

  Scenario: Sorting the products in price descending order
    When I select descending sorting by price
    Then I see the products sorted by price in descending order
