@LoginAndRegister @Regression
Feature: To validate the book store management login flow and Register flow

  @NewRegister @Sanity
  Scenario Outline: Register a new user with valid mobile number, email, and password
    Given Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with newUser
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 200 and response message User created successfully after Register
    Examples:
      | mobileNumber | email        |
      | "6123456789" | "janani237@" |

  @RegisterWithAlreadyExistingMobileNumber @Regression
  Scenario Outline: Attempt to register with an already existing mobile number
    Given Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with existingUser
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 400 and response message Already registered existingUser after Register
    Examples:
      | mobileNumber | email        |
      | "6123456789" | "janani237@" |

  @RegisterWithOnlyPassword @Regression
  Scenario Outline: Attempt to register using only a password without proper mobile/email
    Given Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with existingPassword
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 400 and response message Already registered existingPassword after Register
    Examples:
      | mobileNumber | email        |
      | "6123456789" | "janani237@" |

  @RegisterWithOnlyEmailID @Regression
  Scenario Outline: Attempt to register using only email ID and password
    Given Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with email credentials
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 200 and response message User created successfully after Register
    Examples:
      | mobileNumber | email        |
      | "6123456789" | "janani237@" |

  @RegisterAndLoginUser @sanity
  Scenario Outline: Register a new user and log in with the same credentials
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

  @LoginBeforeRegister @Regression
  Scenario: Attempt to log in with credentials not yet registered
    When user tries to login to the bookStore using the  noRegisterUser
    When I hit the login endpoint "Login" with "POST" Method
    Then validate whether the user has logged in successfully with the response code 400 and response message incorrectCredentials

  @LoginAPIValidationWithInvalidParam @Regression
  Scenario: Attempt to log in with missing or invalid parameters
    When user tries to login to the bookStore using the  missingParam
    When I hit the login endpoint "Login" with "POST" Method
    Then validate whether the user has logged in successfully with the response code 422 and response message missingParam
