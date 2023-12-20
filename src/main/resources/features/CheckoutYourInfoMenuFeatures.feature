Feature: Testing the menu functionality in the Checkout Your Information page
  Test the functionality of the burger menu in the Checkout Your Information page

  Background:
    Given I am in the first page of the website
    When I specify my standard user credential and click Login
    Then I am into the home page
    And I add a product to the cart
    Then Cl