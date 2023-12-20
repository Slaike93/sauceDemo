Feature: Login

  Scenario Outline: Login with different type of users
    Given I am in the first page of the website
    When I specify "<username>" and "secret_sauce" as credentials
    Then I should see the "<expected_result>"
    Examples:
      |username  |expected_result
      |standard_user |home page
      |locked_out_user |locked_out_error message
      |wrong_credential_user |wrong credentials error message
