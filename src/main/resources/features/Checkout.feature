Feature: Complete the order process
  I want to test a complete process to send an order

  Background:
    Given I go to the Website
    And I specify my standard user credential and click Login
    And I am into the home page
    And I add a product to the cart
    And The product should be added to the cart

    Scenario: Testing the checkout process
      When I am in the cart page
      And Click the checkout button and go to the checkout information page
      Then Enter the personal info and go to the checkout overview page
      Then I am in the Checkout overview page and send the order
      Then I am in the Checkout complete page and click the back home button
      Then I am into the home page
      And The cart is empty


