Feature: Testing the menu functionality from the cart page
  Test the functionality of the menu into the cart page

  Background:
    Given I am in the first page of the website
    Then I specify "standard_user" and "secret_sauce" as credentials
    Then I should see the "home page"
    And I add a product to the cart
    And The product should be added to the cart
    And I am in the cart page
    And I click on the menu icon and see the options

  Scenario: Going back to the products page
    When I click on All Items option
    Then I should see the "home page"

  Scenario: Navigate to the About page
    When I click on About option
    Then I am redirected to Sauce Labs page

  Scenario: Logout
    When I click on Logout option
    Then I am in the first page of the website

  Scenario: Reset the app state, cart and items
    When I click on Reset App State option
    Then The cart is empty
    And Cart page is empty




