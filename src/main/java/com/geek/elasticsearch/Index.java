package com.geek.elasticsearch;

import com.geek.http.Request;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Index extends Request {

    @Override
    public void setHostNameAndPort() {
        hostname = "127:0.0.1";
        port = 9200;
    }

    public Index() {
        setHostNameAndPort();
    }

    @BeforeSuite
    public void create() {
        if (doesIndexExist()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(buildURI("/endpoint"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString("{\n" +
                            "  \"mappings\": {\n" +
                            "    \"test\": { \n" +
                            "      \"properties\": {\n" +
                            "      \t\"timestamp\":  { \"type\": \"date\"},\n" +
                            "      \t\"method\":{ \"type\": \"text\", \"fielddata\" : true},\n" +
                            "        \"path\":    { \"type\": \"text\", \"fielddata\" : true}\n" +
                            "        }\n" +
                            "      }\n" +
                            "    }\n" +
                            "}"))
                    .build();

            HttpResponse response = send(request);
            Assert.assertEquals(response.statusCode(), 200);
        }
    }

    private boolean doesIndexExist() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(buildURI("/endpoint"))
                .GET()
                .build();

        HttpResponse response = send(request);
        return response.statusCode() != 404;
    }

}
