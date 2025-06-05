@BookStoreManagement @Regression
Feature: To validate the bookstore by adding , updating, fetching all books and deleting them from the store with the help of user authentication

  @CRUDOperations @Sanity
  Scenario Outline: Create, fetch, update, and delete a book in the book store using registered user credentials
    Given Adding the new book into the store after successful login of user into the system Book Name <bookName> and <author>
    And Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with newUser
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 200 and response message User created successfully after Register
    When user tries to login to the bookStore using the  newUser
    When I hit the login endpoint "Login" with "POST" Method
    Then validate whether the user has logged in successfully with the response code 200 and response message success
    When I hit the  create a new book in the book store using the valid token of the user and endpoint "Books" with "POST" Method
    Then verify whether the book is created and the response is success

    When user tries to edit the book with the name
    And I hit the Book endpoint "EditBookId" with "PUT" Method

    Then verifies whether the response is 200
    And verify the edited book details values in response for editing name

    When I hit the Book endpoint "GetBookId" with "GET" Method
    Then verify whether the response is success

    When I hit the Book endpoint "DeleteBookId" with "DELETE" Method
    And verify the response after deleting the book should be success
    When I hit the Book endpoint "DeleteBookIdNotFound" with "DELETE" Method
    And verify the response after deleting the book should be notfound
    Examples:
      | mobileNumber | email        | bookName         | author   |
      | "6123456789" | "janani237@" | "Soldiers Story" | "Norman" |

  @EditTheBooksScenario @Regression
  Scenario Outline: Edit all fields of a book and validate each update
    Given Adding the new book into the store after successful login of user into the system Book Name <bookName> and <author>
    And Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with newUser
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 200 and response message User created successfully after Register
    When user tries to login to the bookStore using the  newUser
    When I hit the login endpoint "Login" with "POST" Method
    Then validate whether the user has logged in successfully with the response code 200 and response message success
    When I hit the  create a new book in the book store using the valid token of the user and endpoint "Books" with "POST" Method
    Then verify whether the book is created and the response is success

    When user tries to edit the book with the name
    And I hit the Book endpoint "EditBookId" with "PUT" Method
    Then verifies whether the response is 200
    And verify the edited book details values in response for editing name


    When user tries to edit the book with the author
    And I hit the Book endpoint "EditBookId" with "PUT" Method
    Then verifies whether the response is 200
    And verify the edited book details values in response for editing author

    When user tries to edit the book with the published_year
    And I hit the Book endpoint "EditBookId" with "PUT" Method
    Then verifies whether the response is 200
    And verify the edited book details values in response for editing published_year

    When user tries to edit the book with the bookSummary
    And I hit the Book endpoint "EditBookId" with "PUT" Method
    Then verifies whether the response is 200
    And verify the edited book details values in response for editing bookSummary


    When I hit the Book endpoint "EditBook" with "GET" Method
    Then verify whether the response is success

    Examples:
      | mobileNumber | email        | bookName         | author   |
      | "6123456789" | "janani237@" | "Soldiers Story" | "Norman" |

  @MultipleBooks @Regression
  Scenario Outline: Add multiple books and validate that they are fetched successfully
    Given Adding the new book into the store after successful login of user into the system Book Name <bookName> and <author>
    And Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with newUser
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 200 and response message User created successfully after Register
    When user tries to login to the bookStore using the  newUser
    When I hit the login endpoint "Login" with "POST" Method
    Then validate whether the user has logged in successfully with the response code 200 and response message success
    When I hit the  create a new book in the book store using the valid token of the user and endpoint "Books" with "POST" Method

    Examples:
      | mobileNumber | email        | bookName         | author   |
      | "6123456789" | "janani237@" | "Soldiers Story" | "Norman" |
      | "7123456789" | "Kumar237@"  | "Great Leader"   | "Kumar"  |
      | "9123456789" | "john237@"   | "Modern Theory"  | "John"   |

  @FetchAllBook @Regression
  Scenario: Fetch all books from the book store
    When fetch all the books that added to the book store hit the login endpoint "GetAllBooks" with "Get" Method
    Then verify the details of books that listed

  @FetchBookWithInvalidId @Regression
  Scenario Outline: Attempt to fetch a book with an invalid book ID
    Given Adding the new book into the store after successful login of user into the system Book Name <bookName> and <author>
    And Register book store with new MobileNumber <mobileNumber> and email <email> and password
    When user tries to Register the store with newUser
    When I hit the endpoint "Register" with "POST" Method
    Then validate whether a new user is created with response code 200 and response message User created successfully after Register

    When user tries to login to the bookStore using the  newUser
    When I hit the login endpoint "Login" with "POST" Method
    Then validate whether the user has logged in successfully with the response code 200 and response message success
    When I hit the  create a new book in the book store using the valid token of the user and endpoint "Books" with "POST" Method
    Then verify whether the book is created and the response is success

    When I hit the Book endpoint "InvalidBookRecord" with "GET" Method
    Then verify whether we do not get a correct response
    Examples:
      | mobileNumber | email        | bookName         | author   |
      | "6123456789" | "janani237@" | "Soldiers Story" | "Norman" |
