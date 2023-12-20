Feature: Add item to the cart and then remove it
  I want to add an item to the cart and then remove it

  Background:
    Given I am in the first page of the website
    And I specify "standard_user" and "secret_sauce" as credentials
    Then I should see the "home page"

  Scenario: Test the add and remove cart functionality
    When I add a product to the cart
    Then The product should be added to the cart
    #And Remove the cart item