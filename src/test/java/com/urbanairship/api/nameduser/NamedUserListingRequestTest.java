package com.urbanairship.api.nameduser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NamedUserListingRequestTest {

    private final ObjectMapper mapper = NamedUserObjectMapper.getInstance();

    private final NamedUserListingRequest listAllRequest = NamedUserListingRequest.newRequest();
    private final NamedUserListingRequest listRequest = NamedUserListingRequest.newRequest("named_user_id");
    private final NamedUserListingRequest listNextPageRequest = NamedUserListingRequest.newRequest(URI.create("https://go.urbanairship.com/api/named_users/?limit=10&start=mfd"));

    @Test
    public void testContentType() throws Exception {
        assertEquals(listAllRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(listRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(listNextPageRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(listAllRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(listRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(listNextPageRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(listAllRequest.getRequestBody());
        assertNull(listRequest.getRequestBody());
        assertNull(listNextPageRequest.getRequestBody());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(listAllRequest.getRequestHeaders(), headers);
        assertEquals(listRequest.getRequestHeaders(), headers);
        assertEquals(listNextPageRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/named_users/");
        assertEquals(listAllRequest.getUri(baseURI), expectedURI);

        expectedURI = URI.create("https://go.urbanairship.com/api/named_users/?id=named_user_id");
        assertEquals(listRequest.getUri(baseURI), expectedURI);

        expectedURI = URI.create("https://go.urbanairship.com/api/named_users/?limit=10&start=mfd");
        assertEquals(listNextPageRequest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testListingParser() throws Exception {
        ResponseParser<NamedUserListingResponse> responseParser = response -> mapper.readValue(response, NamedUserListingResponse.class);

        String response = "{" +
            "\"ok\": true," +
            "\"next_page\": \"https://go.urbanairship.com/api/named_users?start=user-1234\"," +
            "\"named_users\": [" +
            "{" +
            "\"named_user_id\": \"user-id-1234\"," +
            "\"tags\": {" +
            "\"my_fav_tag_group\": [\"tag1\", \"tag2\"]" +
            "}," +
            "\"channels\": [" +
            "{" +
            "\"channel_id\": \"ABCD\"," +
            "\"device_type\": \"ios\"," +
            "\"installed\": true," +
            "\"opt_in\": true," +
            "\"push_address\": \"FFFF\"," +
            "\"created\": \"2013-08-08T20:41:06\"," +
            "\"last_registration\": \"2014-05-01T18:00:27\"," +
            "\"alias\": \"xxxx\"," +
            "\"tags\": [\"asdf\"]," +
            "\"ios\": {" +
            "\"badge\": 0," +
            "\"quiettime\": {" +
            "\"start\": \"22:00\"," +
            "\"end\": \"06:00\"" +
            "}," +
            "\"tz\": \"America/Los_Angeles\"" +
            "}" +
            "}" +
            "]" +
            "}," +
            "{" +
            "\"named_user_id\": \"user-id-5678\"," +
            "\"tags\": {}," +
            "\"channels\": []" +
            "}" +
            "]" +
            "}";

        assertEquals(listAllRequest.getResponseParser().parse(response), responseParser.parse(response));
        assertEquals(listNextPageRequest.getResponseParser().parse(response), responseParser.parse(response));
    }

    @Test
    public void testLookupParser() throws Exception {
        ResponseParser<NamedUserListingResponse> responseParser = response -> mapper.readValue(response, NamedUserListingResponse.class);

        String response = "{" +
            "\"ok\": true," +
            "\"named_user\": {" +
            "\"named_user_id\": \"user-id-1234\"," +
            "\"tags\": {" +
            "\"my_fav_tag_group\": [\"tag1\", \"tag2\"]" +
            "}," +
            "\"channels\": [" +
            "{" +
            "\"channel_id\": \"ABCD\"," +
            "\"device_type\": \"ios\"," +
            "\"installed\": true," +
            "\"opt_in\": true," +
            "\"push_address\": \"FFFF\"," +
            "\"created\": \"2013-08-08T20:41:06\"," +
            "\"last_registration\": \"2014-05-01T18:00:27\"," +
            "\"alias\": \"xxxx\"," +
            "\"tags\": [\"asdf\"]," +
            "\"ios\": {" +
            "\"badge\": 0," +
            "\"quiettime\": {" +
            "\"start\": \"22:00\"," +
            "\"end\": \"06:00\"" +
            "}," +
            "\"tz\": \"America/Los_Angeles\"" +
            "}" +
            "}" +
            "]" +
            "}" +
            "}";

        assertEquals(listRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
