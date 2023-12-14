Feature: Add element to cart
  As a logged user, I want to add items to cart

  Background:
    Given I go to the Website
    When I specify my standard user credential and click Login
    Then I can log into the home page

  Scenario: Adding an item to the shopping cart
    When I add a product to the cart
    Then the product should be added to the cart