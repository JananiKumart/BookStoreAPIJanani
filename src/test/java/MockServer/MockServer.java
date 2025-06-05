package MockServer;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.time.Year;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServer {
    public static void main(String[] args) {
        String uniqueIdentifier = "Book summary for the book A lone soldier stood guard through the storm, eyes fixed on the silent horizon.\n" +
                "He fought not for glory, but so others could sleep in peace.";
        int currentYear = Year.now().getValue();
        WireMockServer wireMockServer = new WireMockServer(8000);
        wireMockServer.start();
        wireMockServer.stubFor(post(urlEqualTo("/register"))
                .withRequestBody(matchingJsonPath("$.username", equalTo("newUser")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"User created successfully\" }")));

// 2. Register already exists case
        wireMockServer.stubFor(post(urlEqualTo("/register"))
                .withRequestBody(matchingJsonPath("$.username", equalTo("existingUser")))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Already registered existingUser\" }")));

        // 2. Register already exists case
        wireMockServer.stubFor(post(urlEqualTo("/register"))
                .withRequestBody(matchingJsonPath("$.username", equalTo("existingPassword")))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Already registered existingPassword\" }")));
        // 2. Register already exists case
        wireMockServer.stubFor(post(urlEqualTo("/register"))
                .withRequestBody(matchingJsonPath("$.username", equalTo("email credentials")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"User created successfully\" }")));



        // Mock /ADD_NEW_BOOK
        wireMockServer.stubFor(post(urlEqualTo("/books/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"id\": \"1\",\n" +
                                "    \"name\": \"Book Title Soldiers Story\",\n" +
                                "    \"author\":\"Book Author Norman\",\n" +
                                "    \"published_year\":\""+currentYear+"\",\n" +
                                "    \"book_summary\":\""+uniqueIdentifier+"\",\n" +
                                "    \"message\":\"Add book record successfully\"\n" +
                                "}")));
        // Mock /BY_BOOK_ID
        wireMockServer.stubFor(put(urlEqualTo("/books/1"))
                .withRequestBody(matchingJsonPath("$.name", equalTo("Book name is Modern Theory")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"id\": \"1\",\n" +
                                "    \"name\": \"Book name is Modern Theory\",\n" +
                                "    \"author\":\"Book Author Norman\",\n" +
                                "    \"published_year\":\""+currentYear+"\",\n" +
                                "    \"book_summary\":\""+uniqueIdentifier+"\",\n" +
                                "    \"message\":\"Edit book record successfully\"\n" +
                                "}")));
        wireMockServer.stubFor(put(urlEqualTo("/books/1"))
                .withRequestBody(matchingJsonPath("$.author", equalTo("Book author name is Mark")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"id\": \"1\",\n" +
                                "    \"name\": \"Book name is Modern Theory\",\n" +
                                "    \"author\":\"Book author name is Mark\",\n" +
                                "    \"published_year\":\"2025\",\n" +
                                "    \"book_summary\":\""+uniqueIdentifier+"\",\n" +
                                "    \"message\":\"Edit book record successfully\"\n" +
                                "}")));
        wireMockServer.stubFor(put(urlEqualTo("/books/1"))
                .withRequestBody(matchingJsonPath("$.published_year", equalTo("2023")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"id\": \"1\",\n" +
                                "    \"name\": \"Book name is Modern Theory\",\n" +
                                "    \"author\":\"Book author name is Mark\",\n" +
                                "    \"published_year\":\"2023\",\n" +
                                "    \"book_summary\":\""+uniqueIdentifier+"\",\n" +
                                "    \"message\":\"Edit book record successfully\"\n" +
                                "}")));
        wireMockServer.stubFor(put(urlEqualTo("/books/1"))
                .withRequestBody(matchingJsonPath("$.book_summary", equalTo("Book summary is edited now via update")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"id\": \"1\",\n" +
                                "    \"name\": \"Book name is Modern Theory\",\n" +
                                "    \"author\":\"Book author name is Mark\",\n" +
                                "    \"published_year\":\"2023\",\n" +
                                "    \"book_summary\":\"Book summary is edited now via update\",\n" +
                                "    \"message\":\"Edit book record successfully\"\n" +
                                "}")));
        wireMockServer.stubFor(get(urlEqualTo("/editedBooks/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"id\": \"1\",\n" +
                                "    \"name\": \"Book name is Modern Theory\",\n" +
                                "    \"author\":\"Book author name is Mark\",\n" +
                                "    \"published_year\":\"2023\",\n" +
                                "    \"book_summary\":\"Book summary is edited now via update\",\n" +
                                "    \"message\":\"Edit book record successfully\"\n" +
                                "}")));
        wireMockServer.stubFor(get(urlEqualTo("/books/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"id\": \"1\",\n" +
                                "    \"name\": \"Book name is Modern Theory\",\n" +
                                "    \"author\":\"Book Author Norman\",\n" +
                                "    \"published_year\":\""+currentYear+"\",\n" +
                                "    \"book_summary\":\""+uniqueIdentifier+"\",\n" +
                                "    \"message\":\"Get book record successfully\"\n" +
                                "}")));
        wireMockServer.stubFor(get(urlEqualTo("/InvalidBooks"))
                .willReturn(aResponse()
                        .withStatus(422)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Invalid Book Record\" }")));
        wireMockServer.stubFor(get(urlEqualTo("/AllBooks"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"Book details\":[{\n" +
                                "    \"id\": \"1\",\n" +
                                "    \"name\": \"Soldiers Story\",\n" +
                                "    \"author\":\"Norman\",\n" +
                                "    \"published_year\":\""+currentYear+"\",\n" +
                                "    \"book_summary\":\"Book summary\"\n" +
                                "},\n" +
                                "{\n" +
                                "    \"id\": \"2\",\n" +
                                "    \"name\": \"Great Leader\",\n" +
                                "    \"author\":\"Kumar\",\n" +
                                "    \"published_year\":\""+currentYear+"\",\n" +
                                "    \"book_summary\":\"Book summary\"\n" +
                                "},\n" +
                                "{\n" +
                                "    \"id\": \"3\",\n" +
                                "    \"name\": \"Modern Theory\",\n" +
                                "    \"author\":\"John\",\n" +
                                "    \"published_year\":\""+currentYear+"\",\n" +
                                "    \"book_summary\":\"Book summary\"\n" +
                                "}\n" +
                                "]}")));
        wireMockServer.stubFor(delete(urlEqualTo("/books/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Book deleted successfully\" }")));
        wireMockServer.stubFor(delete(urlEqualTo("/books/NotFound"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Book not found\" }")));

        // Mock /login
        wireMockServer.stubFor(post(urlEqualTo("/login"))
                .withRequestBody(matchingJsonPath("$.username", equalTo("newUser")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"access_token\": \"j4h167963517675385\",\n" +
                                "    \"token_type\": \"Cloud\"\n" +
                                "}")));
        wireMockServer.stubFor(post(urlEqualTo("/login"))
                .withRequestBody(matchingJsonPath("$.username", equalTo("noRegisterUser")))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Incorrect email or password\" }")));
        wireMockServer.stubFor(post(urlEqualTo("/login"))
                .withRequestBody(matchingJsonPath("$.username", equalTo("missingParam")))
                .willReturn(aResponse()
                        .withStatus(422)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Required field missingParam\" }")));
    }
}
