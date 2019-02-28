package com.geek.http;

import com.geek.elasticsearch.dto.Endpoint;
import com.google.gson.Gson;
import org.testng.Assert;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

public class RequestHandler extends Request {


    public RequestHandler() {
        setHostNameAndPort();
    }

    public void createCustomer(){
        sendDummyRequest(HttpRequest.newBuilder()
                .uri(buildURI("createCustomer"))
                .POST(HttpRequest.BodyPublishers.ofString("{a:b}"))
                .build());
    }

    public void deleteCustomer(){
        sendDummyRequest(HttpRequest.newBuilder()
                .uri(buildURI( "deleteCustomer"))
                .DELETE()
                .build());
    }

    public void modifyCustomerEmailAddress(){
        sendDummyRequest(HttpRequest.newBuilder()
                .uri(buildURI( "modifyEmailAddress"))
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build());
    }

    public void modifyCustomerAddress(){
        sendDummyRequest(HttpRequest.newBuilder()
                .uri(buildURI( "modifyAddress"))
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build());
    }

    public void getCustomerEmailAddress(){
        sendDummyRequest(HttpRequest.newBuilder()
                .GET()
                .uri(buildURI( "getEmailAddress"))
                .build());
    }

    public void getCustomerDOB(){
        sendDummyRequest(HttpRequest.newBuilder()
                .GET()
                .uri(buildURI( "getDob"))
                .build());
    }

    public void getCustomerFirstName(){
        sendDummyRequest(HttpRequest.newBuilder()
                .GET()
                .uri(buildURI( "getFirstname"))
                .build());
    }

    public void getCustomerLastname(){
        sendDummyRequest(HttpRequest.newBuilder()
                .GET()
                .uri(buildURI( "getLastname"))
                .build());
    }


    /**
     * For demo purposes, this won't actually attempt to send a request. Instead it will
     * build up the request and then do nothing.
     */
    private void sendDummyRequest(HttpRequest httpRequest) {

        // Before sending the main request, let's extract the relevant data from the request and send it to elastic search
        Endpoint endpoint = new Endpoint();
        endpoint
                .setTimestamp(Instant.now().toEpochMilli())
                .setMethod(httpRequest.method())
                .setPath(httpRequest.uri().getPath());

        sendDataToElasticSearch(endpoint);
    }

    private void sendDataToElasticSearch(Endpoint endpoint) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildURI("endpoint/test"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(endpoint)))
                    .build();

            HttpResponse<String> response = send(request);
            Assert.assertEquals(response.statusCode(), 201);
    }

    @Override
    public void setHostNameAndPort() {
        hostname = "127.0.0.1";
        port = 9200;
    }
}
