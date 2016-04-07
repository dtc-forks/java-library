/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import org.apache.http.entity.ContentType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * The base interface for UrbanAirshipClient API requests
 */

public interface Request<T> {

    public final static String CONTENT_TYPE_JSON = "application/json";
    public final static String UA_VERSION = "application/vnd.urbanairship+json; version=3";

    public static enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE,
    }

    HttpMethod getHttpMethod();

    String getRequestBody();

    ContentType getContentType();

    Map<String, String> getRequestHeaders();

    URI getUri(URI baseUri) throws URISyntaxException;

    ResponseParser<T> getResponseParser();
}