package com.positive.chaka.challenge.test;

import java.util.HashMap;
import java.util.Map;

public class TestCase {

    public HashMap<String, Object> request;

    public Response response;

    public HashMap<String, Object> getRequest() {
        return request;
    }

    public void setRequest(HashMap<String, Object> request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
