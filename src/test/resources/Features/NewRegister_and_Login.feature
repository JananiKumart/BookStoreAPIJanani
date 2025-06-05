@LoginAndRegister @regression
Feature: To validate the book store management login flow and Register flow

  @NewRegister @Sanity
  Scenario Outline:To Register to the bookstore by creating account with new MobileNumber and password and email
    Given Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with newUser
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 200 and response message User created successfully after Register
    Examples:
      | mobileNumber | email        |
      | "6123456789" | "janani237@" |

  @RegisterWithAlreadyExistingMobileNumber @regression
  Scenario Outline:To Register to the bookstore by creating account with new MobileNumber and password and email
    Given Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with existingUser
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 400 and response message Already registered existingUser after Register
    Examples:
      | mobileNumber | email        |
      | "6123456789" | "janani237@" |

  @RegisterWithOnlyPassword @regression
  Scenario Outline:To Register to the bookstore by creating account with new MobileNumber and password and email
    Given Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with existingPassword
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 400 and response message Already registered existingPassword after Register
    Examples:
      | mobileNumber | email        |
      | "6123456789" | "janani237@" |

  @RegisterWithOnlyEmailID @regression
  Scenario Outline:To Register to the bookstore by creating account with new MobileNumber and password and email
    Given Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with email credentials
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 200 and response message User created successfully after Register
    Examples:
      | mobileNumber | email        |
      | "6123456789" | "janani237@" |

  @RegisterAndLoginUser @sanity
  Scenario Outline:To Register to the bookstore by creating account with new MobileNumber and password and email and login to that user
    Given Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with newUser
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 200 and response message User created successfully after Register
    When user tries to login to the bookStore using the  newUser
    When I hit the login endpoint "Login" with "POST" Method
    Then validate whether the user has logged in successfully with the response code 200 and response message success
    Examples:
      | mobileNumber | email        |
      | "6123456789" | "janani237@" |

  @LoginBeforeRegister @regression
  Scenario: Verify by logging with the credentials which is not yet signed into bookstore system
    When user tries to login to the bookStore using the  noRegisterUser
    When I hit the login endpoint "Login" with "POST" Method
    Then validate whether the user has logged in successfully with the response code 400 and response message incorrectCredentials

  @LoginAPIValidationWithInvalidParam @regression
  Scenario: Verify by logging with the credentials which is not yet signed into bookstore system
    When user tries to login to the bookStore using the  missingParam
    When I hit the login endpoint "Login" with "POST" Method
    Then validate whether the user has logged in successfully with the response code 422 and response message missingParam
