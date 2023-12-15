Feature: Add item to the cart and then remove it
  I want to add an item to the cart and then remove it

  Background:
    Given I go to the Website
    And I specify my standard user credential and click Login

  Scenario: Test the add and remove cart functionality
    When I add a product to the cart
    Then The product should be added to the cart
    And Remove the cart item