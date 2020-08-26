package com.positive.chaka.challenge.test;

public class Headers {

    private String contentType;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Headers) obj).contentType.equals(contentType);
    }
}
