package com.urbanairship.api.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.model.PushListingResponse;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class PushListingRequestTest {
    ObjectMapper mapper = ReportsObjectMapper.getInstance();

    DateTime start = new DateTime(2014, 10, 1, 12, 0, 0, 0, DateTimeZone.UTC);
    DateTime end = start.plus(Period.hours(48));
    
    PushListingRequest listingRequest;
    PushListingRequest listingNextPageRequest;
    
    @Before
    public void setupCreate() {
        listingRequest = PushListingRequest.newRequest()
                .setStart(start)
                .setEnd(end)
                .setLimit(2)
                .setPushIdStart("start");
        listingNextPageRequest = PushListingRequest.newRequest(URI.create("https://go.urbanairship.com/api/reports/responses/list/?start=2015-01-08T12:00:00.000Z&end=2015-05-01T00:00:00.000Z&precision=DAILY"));
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(listingRequest.getContentType(), ContentType.APPLICATION_JSON);
        assertEquals(listingNextPageRequest.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(listingRequest.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(listingNextPageRequest.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(listingRequest.getRequestBody());
        assertNull(listingNextPageRequest.getRequestBody());
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(listingRequest.getRequestHeaders(), headers);
        assertEquals(listingNextPageRequest.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedURI = URI.create("https://go.urbanairship.com/api/reports/responses/list/?start=2014-10-01T12%3A00%3A00&end=2014-10-03T12%3A00%3A00&limit=2&push_id_start=start");
        assertEquals(listingRequest.getUri(baseURI), expectedURI);

        expectedURI = URI.create("https://go.urbanairship.com/api/reports/responses/list/?start=2015-01-08T12:00:00.000Z&end=2015-05-01T00:00:00.000Z&precision=DAILY");
        assertEquals(listingNextPageRequest.getUri(baseURI), expectedURI);
    }

    @Test
    public void testPushParser() throws Exception {
        ResponseParser<PushListingResponse> responseParser = response -> mapper.readValue(response, PushListingResponse.class);

        String response = "{  \n" +
                "  \"next_page\":\"Value for Next Page\",\n" +
                "  \"pushes\":[  \n" +
                "    {  \n" +
                "      \"push_uuid\":\"df31cae0-fa3c-11e2-97ce-14feb5d317b8\",\n" +
                "      \"push_time\":\"2013-07-31 23:56:52\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"3043779a-fa3c-11e2-a22b-d4bed9a887d4\",\n" +
                "      \"push_time\":\"2013-07-31 23:51:58\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"1c06d01a-fa3c-11e2-aa2d-d4bed9a88699\",\n" +
                "      \"push_time\":\"2013-07-31 23:51:24\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"a50eb7de-fa3b-11e2-912f-90e2ba025998\",\n" +
                "      \"push_time\":\"2013-07-31 23:48:05\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_uuid\":\"90483c8a-fa3b-11e2-92d0-90e2ba0253a0\",\n" +
                "      \"push_time\":\"2013-07-31 23:47:30\",\n" +
                "      \"push_type\":\"BROADCAST_PUSH\",\n" +
                "      \"direct_responses\":0,\n" +
                "      \"sends\":1\n" +
                "    },\n" +
                "   {\n" +
                "       \"push_uuid\": \"f133a7c8-d750-11e1-a6cf-e06995b6c872\",\n" +
                "       \"direct_responses\": \"45\",\n" +
                "       \"sends\": 123,\n" +
                "       \"push_type\": \"UNICAST_PUSH\",\n" +
                "       \"push_time\": \"2012-07-31 12:34:56\",\n" +
                "       \"open_channels_sends\": {\n" +
                "           \"platforms\": [\n" +
                "               {\n" +
                "                   \"id\": \"PLATFORM_NAME_1\",\n" +
                "                   \"sends\": 13\n" +
                "               },\n" +
                "               {\n" +
                "                   \"id\": \"PLATFORM_NAME_2\",\n" +
                "                   \"sends\": 31\n" +
                "               },\n" +
                "               {\n" +
                "                   \"id\": \"PLATFORM_NAME_3\",\n" +
                "                   \"sends\": 26\n" +
                "               }\n" +
                "           ]\n" +
                "       }\n" +
                "   }\n" +
                "  ]\n" +
                "}";


        assertEquals(listingRequest.getResponseParser().parse(response), responseParser.parse(response));
        assertEquals(listingNextPageRequest.getResponseParser().parse(response), responseParser.parse(response));
    }
}
