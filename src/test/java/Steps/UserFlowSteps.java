package Steps;

import apiController.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.UserDetailsBookStoreData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import org.testng.Assert;
import Server.ExtentReportUtil;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertNotNull;


public class UserFlowSteps {


    public UserDetailsBookStoreData userDetailsBookStoreData;

    public UserFlowSteps() {
        this.userDetailsBookStoreData = new UserDetailsBookStoreData();
    }

    List<HashMap<String, Object>> allBooksList = new ArrayList<>();

    HashMap<String, Object> bookRecord = new HashMap<>();

    @Given("Adding the new book into the store after successful login of user into the system Book Name {string} and {string}")
    public void adding_the_new_book_into_the_store_after_successful_login_of_user_into_the_system_book_name_and(String bookName, String author) {
        String uniqueIdentifier = "A lone soldier stood guard through the storm, eyes fixed on the silent horizon.\n" +
                "He fought not for glory, but so others could sleep in peace.";
        String currentYear = String.valueOf(Year.now().getValue());
        bookRecord.put("bookName", "Book Title " + bookName);
        bookRecord.put("author", "Book Author " + author);
        bookRecord.put("published_year", currentYear);
        bookRecord.put("book_summary", "Book summary for the book " + uniqueIdentifier);
        allBooksList.add(new HashMap<>(bookRecord));
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("add New book");
        ExtentReportUtil.logInfo("Prepared book Record payload: " + allBooksList);
        System.out.println(allBooksList);
    }

    @When("I hit the  create a new book in the book store using the valid token of the user and endpoint {string} with {string} Method")
    public void i_hit_the_create_a_new_book_in_the_book_store_using_the_valid_token_of_the_user_and_endpoint_with_method(String resource, String method) throws IOException {
        userDetailsBookStoreData.setAddBookResponse(bookManagementServiceController.book(resource, method, bookRecord, userDetailsBookStoreData.getAccessToken(), userDetailsBookStoreData));
    }

    @When("I hit the Book endpoint {string} with {string} Method")
    public void i_hit_the_book_endpoint_with_method(String resource, String method) throws IOException {
        if (resource.equalsIgnoreCase("EditBookId")) {
            userDetailsBookStoreData.setEditBookResponse(bookManagementServiceController.book(resource, method, bookRecord, userDetailsBookStoreData.getAccessToken(), userDetailsBookStoreData));
        } else if (resource.equalsIgnoreCase("GetBookId") || resource.equalsIgnoreCase("EditBook") || resource.equalsIgnoreCase("InvalidBookRecord")) {
            userDetailsBookStoreData.setGetbookRecordByIdResponse(bookManagementServiceController.book(resource, method, bookRecord, userDetailsBookStoreData.getAccessToken(), userDetailsBookStoreData));
        } else if (resource.equalsIgnoreCase("DeleteBookId") || resource.equalsIgnoreCase("DeleteBookIdNotFound")) {
            userDetailsBookStoreData.setDeleteBookResponse(bookManagementServiceController.book(resource, method, bookRecord, userDetailsBookStoreData.getAccessToken(), userDetailsBookStoreData));
        }
    }

    @Then("verify whether the book is created and the response is success")
    public void verifyWhetherTheBookIsCreatedAndTheResponseIsSuccess() {
        assertNotNull(userDetailsBookStoreData.getAddBookResponse().getBody().jsonPath().get("id"), "Unique id is not generated");
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("verify whether the book is created");
        ExtentReportUtil.logPass("Id found in response: " + userDetailsBookStoreData.getAddBookResponse().jsonPath().get("id"));
        bookRecord.put("createdBookId", userDetailsBookStoreData.getAddBookResponse().getBody().jsonPath().get("id"));
        Assert.assertEquals(userDetailsBookStoreData.getAddBookResponse().getBody().jsonPath().get("name"), bookRecord.get("bookName"), "Book name  mismatch");
        ExtentReportUtil.logPass("Actual book Name: " + userDetailsBookStoreData.getAddBookResponse().getBody().jsonPath().get("name") + " Expected Book Name : "+ bookRecord.get("bookName"));
        Assert.assertEquals(userDetailsBookStoreData.getAddBookResponse().getBody().jsonPath().get("author"), bookRecord.get("author"), "Author name mismatch");
        ExtentReportUtil.logPass("Actual author Name: " + userDetailsBookStoreData.getAddBookResponse().getBody().jsonPath().get("author") + " Expected author Name : "+ bookRecord.get("author"));
        Assert.assertEquals(userDetailsBookStoreData.getAddBookResponse().getBody().jsonPath().get("published_year"), bookRecord.get("published_year"), "Published year mismatch");
        ExtentReportUtil.logPass("Actual published_year Name: " + userDetailsBookStoreData.getAddBookResponse().getBody().jsonPath().get("published_year") + " Expected published_year Name : "+ bookRecord.get("published_year"));
        Assert.assertEquals(userDetailsBookStoreData.getAddBookResponse().getBody().jsonPath().get("book_summary"), bookRecord.get("book_summary"), "Book summary  mismatch");
        ExtentReportUtil.logPass("Actual book_summary Name: " + userDetailsBookStoreData.getAddBookResponse().getBody().jsonPath().get("book_summary") + " Expected book_summary Name : "+ bookRecord.get("book_summary"));

    }

    @When("user tries to edit the book with the (.*)$")
    public void userTriesTiEditTheBookWithThe(String editAction) {
        if (editAction.equalsIgnoreCase("name")) {
            bookRecord.put("bookName", "Book name is Modern Theory");
        } else if (editAction.equalsIgnoreCase("author")) {
            bookRecord.put("author", "Book author name is Mark");
        } else if (editAction.equalsIgnoreCase("bookSummary")) {
            bookRecord.put("book_summary", "Book summary is edited now via update");
        } else if (editAction.equalsIgnoreCase("published_year")) {
            bookRecord.put("published_year", "2023");
        }
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("Edit the book");
        ExtentReportUtil.logInfo("Prepared Edit book Record payload: " + bookRecord);
    }

    @Then("verifies whether the response is {int}")
    public void verifiesWhetherTheResponseIs(int statusCode) {
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("verifies whether the response");
        Assert.assertEquals(userDetailsBookStoreData.getLoginResponse().getStatusCode(), statusCode, "The response code is not " + statusCode);
        ExtentReportUtil.logPass("Actual Status code: " + userDetailsBookStoreData.getLoginResponse().getStatusCode() + " Expected Status code : "+ statusCode);
        if (statusCode == 200) {
            Assert.assertEquals(userDetailsBookStoreData.getEditBookResponse().getStatusLine(), "HTTP/1.1 200 OK", "Response line is not as expected for 200");
            ExtentReportUtil.logPass("Actual Status Line: " + userDetailsBookStoreData.getEditBookResponse().getStatusLine() + " Expected Status Line : "+ "HTTP/1.1 200 OK");
        } else if (statusCode == 400) {
            Assert.assertEquals(userDetailsBookStoreData.getEditBookResponse().getStatusLine(), "HTTP/1.1 400 Bad Request", "Response line is not as expected for 400");
            ExtentReportUtil.logPass("Actual Status Line: " + userDetailsBookStoreData.getEditBookResponse().getStatusLine() + " Expected Status Line : "+ "HTTP/1.1 200 OK");
        } else if (statusCode == 403) {
            Assert.assertEquals(userDetailsBookStoreData.getEditBookResponse().getStatusLine(), "HTTP/1.1 403 Forbidden", "Response line is not as expected for 403");
            ExtentReportUtil.logPass("Actual Status Line: " + userDetailsBookStoreData.getEditBookResponse().getStatusLine() + " Expected Status Line : "+ "HTTP/1.1 200 OK");
            Assert.assertEquals(userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("detail"), "Not authenticated", "No error message for 403");
            ExtentReportUtil.logPass("Actual error message: " + userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("detail") + " Expected error message: "+ "Not authenticated");
        }
    }

    @And("verify the edited book details values in response for editing (.*)$")
    public void verifyTheEditedbookRecordValuesInResponseForEditingName(String condition) {
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("verify the edited book details values in response");
        Assert.assertEquals(userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("name"), bookRecord.get("bookName"), "Book name  mismatch");
        ExtentReportUtil.logPass("Actual book Name: " + userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("name") + " Expected Book Name : "+ bookRecord.get("bookName"));
        Assert.assertEquals(userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("author"), bookRecord.get("author"), "Author name mismatch");
        ExtentReportUtil.logPass("Actual author Name: " + userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("author") + " Expected author Name : "+ bookRecord.get("author"));
        Assert.assertEquals(userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("published_year"), bookRecord.get("published_year"), "Published year mismatch");
        ExtentReportUtil.logPass("Actual published_year Name: " + userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("published_year") + " Expected published_year Name : "+ bookRecord.get("published_year"));
        Assert.assertEquals(userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("book_summary"), bookRecord.get("book_summary"), "Book summary  mismatch");
        ExtentReportUtil.logPass("Actual book_summary Name: " + userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("book_summary") + " Expected book_summary Name : "+ bookRecord.get("book_summary"));
        Assert.assertEquals(userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("id"), bookRecord.get("createdBookId"), "Book id is different to the request");
        ExtentReportUtil.logPass("Actual id Name: " + userDetailsBookStoreData.getEditBookResponse().getBody().jsonPath().get("id") + " Expected id Name : "+ bookRecord.get("createdBookId"));

    }

    @Then("verify whether the response is success")
    public void verifyWhetherTheResponseIsSuccess() {
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("Verify the response success");
        Assert.assertEquals(userDetailsBookStoreData.getGetbookRecordByIdResponse().getBody().jsonPath().get("name"), bookRecord.get("bookName"), "Book name  mismatch");
        ExtentReportUtil.logPass("Actual book Name: " + userDetailsBookStoreData.getGetbookRecordByIdResponse().getBody().jsonPath().get("name") + " Expected Book Name : "+ bookRecord.get("bookName"));
        Assert.assertEquals(userDetailsBookStoreData.getGetbookRecordByIdResponse().getBody().jsonPath().get("author"), bookRecord.get("author"), "Author name mismatch");
        ExtentReportUtil.logPass("Actual author Name: " + userDetailsBookStoreData.getGetbookRecordByIdResponse().getBody().jsonPath().get("author") + " Expected author Name : "+ bookRecord.get("author"));
        Assert.assertEquals(userDetailsBookStoreData.getGetbookRecordByIdResponse().getBody().jsonPath().get("published_year"), bookRecord.get("published_year"), "Published year mismatch");
        ExtentReportUtil.logPass("Actual published_year Name: " + userDetailsBookStoreData.getGetbookRecordByIdResponse().getBody().jsonPath().get("published_year") + " Expected published_year Name : "+ bookRecord.get("published_year"));
        Assert.assertEquals(userDetailsBookStoreData.getGetbookRecordByIdResponse().getBody().jsonPath().get("book_summary"), bookRecord.get("book_summary"), "Book summary  mismatch");
        ExtentReportUtil.logPass("Actual book_summary Name: " + userDetailsBookStoreData.getGetbookRecordByIdResponse().getBody().jsonPath().get("book_summary") + " Expected book_summary Name : "+ bookRecord.get("book_summary"));
        Assert.assertEquals(userDetailsBookStoreData.getGetbookRecordByIdResponse().getBody().jsonPath().get("id"), bookRecord.get("createdBookId"), "Book id is different to the request");
        ExtentReportUtil.logPass("Actual id Name: " + userDetailsBookStoreData.getGetbookRecordByIdResponse().getBody().jsonPath().get("id") + " Expected id Name : "+ bookRecord.get("createdBookId"));

    }

    @And("verify the response after deleting the book should be (.*)$")
    public void verifyTheResponseAfterDeletingTheBookShouldBeSuccess(String condition) {
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("verify the response after deleting the book");
        if (condition.equalsIgnoreCase("Success")) {
            Assert.assertEquals(userDetailsBookStoreData.getDeleteBookResponse().getStatusCode(), 200, "The response code is not 200");
            ExtentReportUtil.logPass("Actual Status code: " + userDetailsBookStoreData.getDeleteBookResponse().getStatusCode() + " Expected Status code : "+ 200);

            Assert.assertEquals(userDetailsBookStoreData.getDeleteBookResponse().getStatusLine(), "HTTP/1.1 200 OK", "Response line is not as expected");
            ExtentReportUtil.logPass("Actual Status Line: " + userDetailsBookStoreData.getDeleteBookResponse().getStatusLine() + " Expected Status Line : "+ "HTTP/1.1 200 OK");

            Assert.assertEquals(userDetailsBookStoreData.getDeleteBookResponse().getBody().jsonPath().get("message"), "Book deleted successfully", "Book not deleted yet");
            ExtentReportUtil.logPass("Actual error message: " + userDetailsBookStoreData.getDeleteBookResponse().getBody().jsonPath().get("message") + " Expected error message: "+ "Book deleted successfully");

        } else if (condition.equalsIgnoreCase("notFound")) {
            Assert.assertEquals(userDetailsBookStoreData.getDeleteBookResponse().getStatusCode(), 404, "The response code is not 404");
            ExtentReportUtil.logFail("Actual Status code: " + userDetailsBookStoreData.getDeleteBookResponse().getStatusCode() + " Expected Status code : "+ 404);

            Assert.assertEquals(userDetailsBookStoreData.getDeleteBookResponse().getStatusLine(), "HTTP/1.1 404 Not Found", "Response line is not as expected");
            ExtentReportUtil.logFail("Actual Status Line: " + userDetailsBookStoreData.getDeleteBookResponse().getStatusLine() + " Expected Status Line : "+ "HTTP/1.1 404 Not Found");

            Assert.assertEquals(userDetailsBookStoreData.getDeleteBookResponse().getBody().jsonPath().get("message"), "Book not found", "Book should not be deleted");
            ExtentReportUtil.logFail("Actual error message: " + userDetailsBookStoreData.getDeleteBookResponse().getBody().jsonPath().get("message") + " Expected error message: "+ "Book not found");

        }

    }

    @When("fetch all the books that added to the book store hit the login endpoint {string} with {string} Method")
    public void fetch_all_the_books_that_added_to_the_book_store_hit_the_login_endpoint_with_method(String resource, String method) throws IOException {
        userDetailsBookStoreData.setGetbookRecordByIdResponse(bookManagementServiceController.book(resource, method, bookRecord, userDetailsBookStoreData.getAccessToken(), userDetailsBookStoreData));

    }

    @Then("verify the details of books that listed")
    public void verifyTheDetailsOfBooksThatListed() throws IOException {
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("verify the details of books that listed");

        Response response = userDetailsBookStoreData.getGetbookRecordByIdResponse();

        File jsonFile = new File("src/main/java/data/BookRecord.json");

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode expectedJson = objectMapper.readTree(jsonFile);
        JsonNode actualJson = objectMapper.readTree(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200, "The response code is not 200");
        ExtentReportUtil.logPass("Actual Status code: " + response.getStatusCode() + " Expected Status code : "+ 200);
        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK", "Response line is not as expected");
        ExtentReportUtil.logPass("Actual Status line: " + response.getStatusLine() + " Expected Status line : "+ "HTTP/1.1 200 OK");
        Assert.assertEquals(actualJson, expectedJson, "Book All Record JSON content does not match!");
        ExtentReportUtil.logPass("Actual Json"+actualJson + " Expected json "+expectedJson);

    }

    @Then("verify whether we do not get a correct response")
    public void verifyWhetherWeDoNotGetACorrectResponse() {
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("verify whether we do not get a correct response");
        Assert.assertEquals(userDetailsBookStoreData.getGetbookRecordByIdResponse().getStatusCode(), 422);
        ExtentReportUtil.logFail("Actual Status code: " + userDetailsBookStoreData.getGetbookRecordByIdResponse().getStatusCode() + " Expected Status code : "+ 422);
        Assert.assertEquals(userDetailsBookStoreData.getGetbookRecordByIdResponse().getStatusLine(), "HTTP/1.1 422 Unprocessable Entity");
        ExtentReportUtil.logFail("Actual Status line: " + userDetailsBookStoreData.getGetbookRecordByIdResponse().getStatusLine() + " Expected Status line : "+ "HTTP/1.1 422 Unprocessable Entity");

    }

    @Given("Register book store with new MobileNumber {string} and email {string} and password")
    public void register_book_store_with_new_mobile_number_and_email_and_password(String mobileNumber, String email) {
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("Register book store with new MobileNumber and email");
        userDetailsBookStoreData.setEmail(email + "yahoo.com");
        userDetailsBookStoreData.setPassword(loginAndRegisterServiceContoller.generateEmailAndPassword(15));
        userDetailsBookStoreData.setMobileNumber(mobileNumber);
        ExtentReportUtil.logInfo("Prepared Register user payload: " + userDetailsBookStoreData);
    }

    @When("^user tries to Register the store with (.*)$")
    public void userTriesToRegisterTheStoreWith(String credentials) {
        userDetailsBookStoreData.setUsername(credentials);
    }

    @When("I hit the endpoint {string} with {string} Method")
    public void iHitTheEndpointWithMethod(String resource, String method) throws IOException {
        Response response = loginAndRegisterServiceContoller.signUp(method, resource, userDetailsBookStoreData.getUsername(), userDetailsBookStoreData.getMobileNumber(), userDetailsBookStoreData.getAccessToken(), userDetailsBookStoreData.getEmail(), userDetailsBookStoreData.getPassword(), userDetailsBookStoreData);
        userDetailsBookStoreData.setRegisterResponse(response);
    }

    @Then("^validate whether a new user is created with response code (.*) and response message (.*) after Register$")
    public void validateWhetherANewUserIsCreatedWithResponseCodeAndResponseMessageAfterRegister(int statusCode, String message) {
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("validate whether a new user is created with response code and response message");

        Assert.assertEquals(userDetailsBookStoreData.getRegisterResponse().getStatusCode(), statusCode, "Sign up expected status code mismatch");
        if (statusCode == 200) {
            Assert.assertEquals(message, userDetailsBookStoreData.getRegisterResponse().getBody().jsonPath().get("message").toString(), "User created successfully");
            ExtentReportUtil.logPass("Actual Status code: " + userDetailsBookStoreData.getRegisterResponse().getStatusCode() + " Expected Status code : "+ statusCode);
            ExtentReportUtil.logPass("Actual Message: " + userDetailsBookStoreData.getRegisterResponse().getBody().jsonPath().get("message").toString() + " Expected Message : "+ "User created successfully");

        } else if (statusCode == 400) {
            Assert.assertEquals(message, userDetailsBookStoreData.getRegisterResponse().getBody().jsonPath().get("message").toString(), "Email already registered");
            ExtentReportUtil.logFail("Actual Status code: " + userDetailsBookStoreData.getRegisterResponse().getStatusCode() + " Expected Status code : "+ statusCode);
            ExtentReportUtil.logFail("Actual Message: " + userDetailsBookStoreData.getRegisterResponse().getBody().jsonPath().get("message").toString() + " Expected Message : "+ "Email already registered");
        }
    }

    @When("^user tries to login to the bookStore using the  (.*)$")
    public void userTriesToLoginToTheBookStoreUsingTheNewCredentials(String credentials) {
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("user tries to login to the bookStore");
        if (credentials.equalsIgnoreCase("noRegisterUser")) {
            userDetailsBookStoreData.setUsername(credentials);
            userDetailsBookStoreData.setEmail(loginAndRegisterServiceContoller.generateEmailAndPassword(10) + "@yahoo.com");
            userDetailsBookStoreData.setPassword(loginAndRegisterServiceContoller.generateEmailAndPassword(8));
            ExtentReportUtil.logInfo("Prepared Register user payload: " + userDetailsBookStoreData);
        } else if (credentials.equalsIgnoreCase("missingParam")) {
            userDetailsBookStoreData.setUsername(credentials);
            userDetailsBookStoreData.setEmail(null);
            userDetailsBookStoreData.setPassword(null);
            ExtentReportUtil.logInfo("Prepared Register user payload: " + userDetailsBookStoreData);
        }

    }

    @When("I hit the login endpoint {string} with {string} Method")
    public void i_hit_the_login_endpoint_with_method(String resource, String method) throws IOException {
        Response response = loginAndRegisterServiceContoller.login(method, resource, userDetailsBookStoreData.getUsername(), userDetailsBookStoreData.getMobileNumber(), userDetailsBookStoreData.getAccessToken(), userDetailsBookStoreData.getEmail(), userDetailsBookStoreData.getPassword(), userDetailsBookStoreData);
        userDetailsBookStoreData.setLoginResponse(response);

    }

    @Then("validate whether the user has logged in successfully with the response code (.*) and response message (.*)$")
    public void validateWhetherTheUserHasLoggedInSuccessfullyWithTheResponseCodeAndResponseMessage(int statusCode, String condition) {
        ExtentReportUtil.test = ExtentReportUtil.extent.createTest("validate whether the user has logged in successfully with the response code");

        Assert.assertEquals(userDetailsBookStoreData.getLoginResponse().getStatusCode(), statusCode, "The response code is not " + statusCode);
        switch (condition) {
            case "success":
                System.out.println("Successfully Sign In");
                userDetailsBookStoreData.setAccessToken("Bearer " + userDetailsBookStoreData.getLoginResponse().jsonPath().get("access_token"));
                System.out.println(userDetailsBookStoreData.getAccessToken());
                assertNotNull(userDetailsBookStoreData.getLoginResponse().jsonPath().get("access_token"), "Token is not generated after login");
                ExtentReportUtil.logPass("Token found in response: " + userDetailsBookStoreData.getLoginResponse().jsonPath().get("access_token"));
                Assert.assertEquals(userDetailsBookStoreData.getLoginResponse().jsonPath().get("token_type"), "Cloud", "Token generated type is not bearer");
                ExtentReportUtil.logPass("Token type found in response: " + userDetailsBookStoreData.getLoginResponse().jsonPath().get("token_type"));
                break;

            case "incorrectCredentials":
                Assert.assertEquals(userDetailsBookStoreData.getLoginResponse().getStatusLine(), "HTTP/1.1 400 Bad Request", "Response line is not as expected");
                Assert.assertEquals(userDetailsBookStoreData.getLoginResponse().jsonPath().get("message"), "Incorrect email or password", "Incorrect error message in detail mismatch");
                ExtentReportUtil.logFail("Actual Status Line: " + userDetailsBookStoreData.getLoginResponse().getStatusLine() + " Expected Status Line : "+ "HTTP/1.1 400 Bad Request");
                ExtentReportUtil.logFail("Actual Message: " + userDetailsBookStoreData.getLoginResponse().jsonPath().get("message") + " Expected Message : "+ "Incorrect error message in detail mismatch");
                break;

            case "missingParam":
                Assert.assertEquals(userDetailsBookStoreData.getLoginResponse().getStatusLine(), "HTTP/1.1 422 Unprocessable Entity", "Response line is not as expected");
                Assert.assertEquals(userDetailsBookStoreData.getLoginResponse().jsonPath().get("message"), "Required field missingParam", "Incorrect error message in detail mismatch");
                ExtentReportUtil.logFail("Actual Status Line: " + userDetailsBookStoreData.getLoginResponse().getStatusLine() + " Expected Status Line : "+ "HTTP/1.1 422 Unprocessable Entity");
                ExtentReportUtil.logFail("Actual Message: " + userDetailsBookStoreData.getLoginResponse().jsonPath().get("message") + " Expected Message : "+ "Required field missingParam");
                break;

        }
    }

}
