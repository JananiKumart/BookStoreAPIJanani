package apiController;

import constants.Resources;
import data.UserDetailsBookStoreData;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import Server.Utils;

import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class bookManagementServiceController {
    static Utils d = new Utils();
    public static ResponseSpecification resspec;
    public static Response responsespec;
    public static Response book(String resource, String method,HashMap<String,Object> bookRecord, String accessToken, UserDetailsBookStoreData userDetailsBookStoreData) throws IOException {

        RequestSpecification request = given().relaxedHTTPSValidation().spec(d.requestSpecification()).contentType("application/json;charset=utf-8");

        if (accessToken!=null) {
            request.header("Authorization", accessToken);
        }
        String body = "{\"name\":\""+bookRecord.get("bookName")+"\",\"author\":\""+bookRecord.get("author")+"\",\"published_year\":\""+bookRecord.get("published_year")+"\",\"book_summary\":\""+bookRecord.get("book_summary")+"\"}";

        Resources resourceAPI = Resources.valueOf(resource);
        System.out.println(RestAssured.baseURI+resourceAPI.getResource());
        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.URLENC).build();
        if (method.equalsIgnoreCase("POST"))
            responsespec = request.body(body).when().post( RestAssured.baseURI+resourceAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            responsespec = request.body("").when().get(RestAssured.baseURI+resourceAPI.getResource());
        else if (method.equalsIgnoreCase("PUT"))
            responsespec = request.body(body).when().put(RestAssured.baseURI+resourceAPI.getResource());
        else if (method.equalsIgnoreCase("DELETE"))
            responsespec = request.body("").when().delete(RestAssured.baseURI+resourceAPI.getResource());
        int statusCode = responsespec.getStatusCode();
        System.out.println("Print response code: " + statusCode);

        ResponseBody ResBody = responsespec.getBody();
        System.out.println("Response is: " + ResBody.asString());

        return responsespec;
    }


}
