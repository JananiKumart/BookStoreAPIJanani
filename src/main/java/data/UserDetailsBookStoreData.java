package data;

import io.restassured.response.Response;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailsBookStoreData {
    private String email;
    private String password;
    private String Username;
    private String MobileNumber;
    private Response RegisterResponse;
    private Response loginResponse;
    private String accessToken;
    private Response addBookResponse;
    private Response editBookResponse;
    private Response getbookRecordByIdResponse;
    private Response deleteBookResponse;
    private List<Response> fetchAllBooks;

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Response> getFetchAllBooks() {
        return fetchAllBooks;
    }

    public void setFetchAllBooks(List<Response> fetchAllBooks) {
        this.fetchAllBooks = fetchAllBooks;
    }

    public Response getDeleteBookResponse() {
        return deleteBookResponse;
    }

    public void setDeleteBookResponse(Response deleteBookResponse) {
        this.deleteBookResponse = deleteBookResponse;
    }

    public Response getGetbookRecordByIdResponse() {
        return getbookRecordByIdResponse;
    }

    public void setGetbookRecordByIdResponse(Response getbookRecordByIdResponse) {
        this.getbookRecordByIdResponse = getbookRecordByIdResponse;
    }

    public Response getEditBookResponse() {
        return editBookResponse;
    }

    public void setEditBookResponse(Response editBookResponse) {
        this.editBookResponse = editBookResponse;
    }

    public Response getAddBookResponse() {
        return addBookResponse;
    }

    public void setAddBookResponse(Response addBookResponse) {
        this.addBookResponse = addBookResponse;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Response getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(Response loginResponse) {
        this.loginResponse = loginResponse;
    }

    public Response getRegisterResponse() {
        return RegisterResponse;
    }

    public void setRegisterResponse(Response RegisterResponse) {
        this.RegisterResponse = RegisterResponse;
    }
}
