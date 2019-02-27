package com.geek.http;

import com.geek.elasticsearch.dto.Endpoint;
import com.google.gson.Gson;
import org.testng.Assert;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

public class RequestHandler {

    private static final String HOSTNAME = "myapplication.geek.com";
    private static HttpClient httpClient;

    static {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    private URI buildURI(String path) {
        URI uri = null;
        try {
            uri = new URI("http", HOSTNAME, "/" + path, null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return uri;
    }

    public void createCustomer(){
        send(HttpRequest.newBuilder()
                .uri(buildURI("createCustomer"))
                .POST(HttpRequest.BodyPublishers.ofString("{a:b}"))
                .build());
    }

    public void deleteCustomer(){
        send(HttpRequest.newBuilder()
                .uri(buildURI("deleteCustomer"))
                .DELETE()
                .build());
    }

    public void modifyCustomerEmailAddress(){
        send(HttpRequest.newBuilder()
                .uri(buildURI("modifyEmailAddress"))
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build());
    }

    public void modifyCustomerAddress(){
        send(HttpRequest.newBuilder()
                .uri(buildURI("modifyAddress"))
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build());
    }

    public void getCustomerEmailAddress(){
        send(HttpRequest.newBuilder()
                .GET()
                .uri(buildURI("getEmailAddress"))
                .build());
    }

    public void getCustomerDOB(){
        send(HttpRequest.newBuilder()
                .GET()
                .uri(buildURI("getDob"))
                .build());
    }

    public void getCustomerFirstName(){
        send(HttpRequest.newBuilder()
                .GET()
                .uri(buildURI("getFirstname"))
                .build());
    }

    public void getCustomerLastname(){
        send(HttpRequest.newBuilder()
                .GET()
                .uri(buildURI("getLastname"))
                .build());
    }


    /**
     * For demo purposes, this won't actually attempt to send a request. Instead it will
     * build up the request and then do nothing.
     */
    private void send(HttpRequest httpRequest) {

        // Before sending the main request, let's extract the relevant data from the request and send it to elastic search
        Endpoint endpoint = new Endpoint();
        endpoint
                .setTimestamp(Instant.now().toEpochMilli())
                .setMethod(httpRequest.method())
                .setPath(httpRequest.uri().getPath());

        sendDataToElasticSearch(endpoint);
    }

    private void sendDataToElasticSearch(Endpoint endpoint) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http", null, "127.0.0.1", 9200, "/endpoint/test", null, null))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(endpoint)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(response.statusCode(), 201);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
