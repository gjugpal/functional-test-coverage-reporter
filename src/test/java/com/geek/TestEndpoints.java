package com.geek;

import com.geek.http.RequestHandler;
import org.testng.annotations.Test;

public class TestEndpoints {

    @Test(invocationCount = 54)
    public void createCustomer() {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.createCustomer();
    }

    @Test(invocationCount = 30)
    public void deleteCustomer() {
        new RequestHandler().deleteCustomer();
    }

    @Test(invocationCount = 25)
    public void modifyAddress() {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.modifyCustomerAddress();
    }

    @Test(invocationCount = 8)
    public void modifyEmail() {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.modifyCustomerEmailAddress();
    }

    @Test(invocationCount = 49)
    public void getFirstname() {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.getCustomerFirstName();
    }

    @Test(invocationCount = 39)
    public void getLastname() {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.getCustomerLastname();
    }

    @Test(invocationCount = 23)
    public void getDob() {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.getCustomerDOB();
    }

    @Test(invocationCount = 10)
    public void getEmailAddress() {
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.getCustomerEmailAddress();
    }
}
