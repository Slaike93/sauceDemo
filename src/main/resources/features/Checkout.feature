Feature: Complete the order process
  I want to test a complete process to send an order

  Background:
    Given I am in the first page of the website
    And I specify "standard_user" and "secret_sauce" as credentials
    And I should see the "home page"
    And I add a product to the cart
    And The product should be added to the cart

    Scenario: Testing the checkout process
      When I am in the cart page
      And Click the checkout button and go to the checkout information page
      Then Enter the personal info and go to the checkout overview page
      Then I am in the Checkout overview page and send the order
      Then I am in the Checkout complete page and click the back home button
      Then I should see the "home page"
      And The cart is empty


