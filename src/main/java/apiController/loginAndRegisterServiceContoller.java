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
import java.util.Random;

import static io.restassured.RestAssured.given;

public class loginAndRegisterServiceContoller {
    static Utils d = new Utils();
    public static ResponseSpecification resspec;
    public static Response responsespec;
    public static String generateEmailAndPassword(int length)
    {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder stringGenerated = new StringBuilder ();
        Random random = new Random();
        for (int i = 0; i < length; i ++) {
            stringGenerated.append (candidateChars.charAt (random.nextInt (candidateChars
                    .length ())));
        }
        return stringGenerated.toString ();
    }
    public static Response signUp(String method, String resource,String username,String mobile,String accessToken,String email, String password, UserDetailsBookStoreData userDetailsBookStoreData) throws IOException {

        RequestSpecification request = given().relaxedHTTPSValidation().spec(d.requestSpecification()).contentType("application/json;charset=utf-8");

        if (accessToken!=null) {
            request.header("Authorization", accessToken);
        }
        String body = "{\n" +
                "    \"username\":\""+username+"\",\n" +
                "    \"email\": \""+email+"\",\n" +
                "    \"password\": \""+password+"\",\n" +
                "    \"mobile\": \""+mobile+"\"\n" +
                "}";
        request.body(body);
        Resources resourceAPI = Resources.valueOf(resource);
        System.out.println(RestAssured.baseURI+resourceAPI.getResource());
        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.URLENC).build();
        if (method.equalsIgnoreCase("POST"))
            responsespec = request.when().post( RestAssured.baseURI+resourceAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            responsespec = request.when().get(RestAssured.baseURI+resourceAPI.getResource());
        else if (method.equalsIgnoreCase("PUT"))
            responsespec = request.when().get(RestAssured.baseURI+resourceAPI.getResource());
        else if (method.equalsIgnoreCase("DELETE"))
            responsespec = request.when().get(RestAssured.baseURI+resourceAPI.getResource());
        int statusCode = responsespec.getStatusCode();
        System.out.println("Print response code: " + statusCode);

        ResponseBody ResBody = responsespec.getBody();
        System.out.println("Response is: " + ResBody.asString());

        return responsespec;
    }

    public static Response login(String method, String resource,String username,String mobile,String accessToken,String email, String password, UserDetailsBookStoreData userDetailsBookStoreData) throws IOException {

        RequestSpecification request = given().relaxedHTTPSValidation().spec(d.requestSpecification()).contentType("application/json;charset=utf-8");

        if (accessToken!=null) {
            request.header("Authorization", accessToken);
        }
        String body = "{\n" +
                "    \"username\":\""+username+"\",\n" +
                "    \"email\": \""+email+"\",\n" +
                "    \"password\": \""+password+"\",\n" +
                "    \"mobile\": \""+mobile+"\"\n" +
                "}";
        request.body(body);
        Resources resourceAPI = Resources.valueOf(resource);
        System.out.println(RestAssured.baseURI+resourceAPI.getResource());
        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.URLENC).build();
        if (method.equalsIgnoreCase("POST"))
            responsespec = request.when().post( RestAssured.baseURI+resourceAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            responsespec = request.when().get(RestAssured.baseURI+resourceAPI.getResource());
        else if (method.equalsIgnoreCase("PUT"))
            responsespec = request.when().get(RestAssured.baseURI+resourceAPI.getResource());
        else if (method.equalsIgnoreCase("DELETE"))
            responsespec = request.when().get(RestAssured.baseURI+resourceAPI.getResource());
        int statusCode = responsespec.getStatusCode();
        System.out.println("Print response code: " + statusCode);

        ResponseBody ResBody = responsespec.getBody();
        System.out.println("Response is: " + ResBody.asString());

        return responsespec;
    }

}
