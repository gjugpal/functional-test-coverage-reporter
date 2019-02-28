package com.geek.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class Request {

    protected static HttpClient httpClient;
    protected String hostname;
    protected int port;

    static {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    protected abstract void setHostNameAndPort();

    protected URI buildURI(String path) {
        URI uri = null;
        try {
            uri = new URI("http", null, hostname, port, "/" + path, null, null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return uri;
    }

    protected HttpResponse send(HttpRequest request) {
        HttpResponse response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }
}
