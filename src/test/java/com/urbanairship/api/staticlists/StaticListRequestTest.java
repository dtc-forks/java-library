package com.urbanairship.api.staticlists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.staticlists.parse.StaticListsObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StaticListRequestTest {
    private final static ObjectMapper MAPPER = StaticListsObjectMapper.getInstance();
    private final static String STATIC_LIST_CREATE_PATH = "/api/lists/";
    private final static String STATIC_LIST_UPDATE_PATH = "/api/lists/bleep";

    StaticListRequest createRequest;
    StaticListRequest updateRequest;

    @Before
    public void setupCreate() {
        createRequest = StaticListRequest.newRequest("abc123")
                .setDescription("create description")
                .addExtra("key1", "val1")
                .addExtra("key2", "val2");
    }

    @Before
    public void setupUpdate() {
        updateRequest = StaticListRequest.newUpdateRequest("bleep")
                .setDescription("update description")
                .addExtra("key3", "val3")
                .addExtra("key4", "val4");
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(createRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(updateRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(createRequest.getHttpMethod(), Request.HttpMethod.POST);
        assertEquals(updateRequest.getHttpMethod(), Request.HttpMethod.PUT);
    }

    @Test
    public void testBody() throws Exception {
        String NAME_KEY = "name";
        String DESCRIPTION_KEY = "description";
        String EXTRAS_KEY = "extra";


        String createString = "{" +
                "\"extra\":{" +
                "\"key2\":\"val2\"," +
                "\"key1\":\"val1\"" +
                "}," +
                "\"description\":\"create description\"," +
                "\"name\":\"abc123\"" +
                "}";

        String updateString = "{" +
                "\"extra\":{" +
                "\"key4\":\"val4\"," +
                "\"key3\":\"val3\"" +
                "}," +
                "\"description\":\"update description\"," +
                "\"name\":\"bleep\"" +
                "}";

        Map<String, Object> createPayload = new HashMap<>();
        Map<String, String> createExtras = new HashMap<>();
        createPayload.put(NAME_KEY, "abc123");
        createPayload.put(DESCRIPTION_KEY, "create description");
        createExtras.put("key1", "val1");
        createExtras.put("key2", "val2");
        createPayload.put(EXTRAS_KEY, createExtras);

        Map<String, Object> updatePayload = new HashMap<>();
        Map<String, String> updateExtras = new HashMap<>();
        updatePayload.put(NAME_KEY, "bleep");
        updatePayload.put(DESCRIPTION_KEY, "update description");
        updateExtras.put("key3", "val3");
        updateExtras.put("key4", "val4");
        updatePayload.put(EXTRAS_KEY, updateExtras);

        String createPayloadString = MAPPER.writeValueAsString(createPayload);
        String updatePayloadString = MAPPER.writeValueAsString(updatePayload);

        assertEquals(MAPPER.readTree(createPayloadString), MAPPER.readTree(createString));
        assertEquals(MAPPER.readTree(updatePayloadString), MAPPER.readTree(updateString));
        assertEquals(MAPPER.readTree(createRequest.getRequestBody()), MAPPER.readTree(createPayloadString));
        assertEquals(MAPPER.readTree(updateRequest.getRequestBody()), MAPPER.readTree(updatePayloadString));
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);

        assertEquals(updateRequest.getRequestHeaders(), headers);
        assertEquals(createRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedCreateUri = URI.create("https://go.urbanairship.com" + STATIC_LIST_CREATE_PATH);
        assertEquals(createRequest.getUri(baseURI), expectedCreateUri);
        URI expectedUpdateUri = URI.create("https://go.urbanairship.com" + STATIC_LIST_UPDATE_PATH);
        assertEquals(updateRequest.getUri(baseURI), expectedUpdateUri);
    }

    @Test
    public void testParser() throws Exception {
        GenericResponse genericResponse = new GenericResponse(true, null, null, null, null, null);

        String responseJson = "{" +
                "\"ok\": true" +
                "}";

        assertEquals(createRequest.getResponseParser().parse(responseJson), genericResponse);
        assertEquals(updateRequest.getResponseParser().parse(responseJson), genericResponse);
    }
}
