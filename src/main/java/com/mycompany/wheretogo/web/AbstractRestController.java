package com.mycompany.wheretogo.web;

public class AbstractRestController {
    protected static final String REST_BASE_URL = "/rest/api-v1";

    public static String getRestBaseUrl() {
        return REST_BASE_URL;
    }
}
