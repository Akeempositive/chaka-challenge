package com.positive.chaka.challenge.test;

import com.positive.chaka.challenge.Statistics;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private int status_code;

    private Statistics  body;

    private Map<String, Object> headers;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public Statistics getBody() {
        return body;
    }

    public void setBody(Statistics body) {
        this.body = body;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public boolean equals(Object o){
        Response test = (Response) o;
        return  test.status_code == status_code &&
                test.headers.equals(headers) &&
                test.body.equals(body);
    }
}
