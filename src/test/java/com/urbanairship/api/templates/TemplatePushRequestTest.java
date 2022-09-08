package com.urbanairship.api.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.templates.model.TemplatePushPayload;
import com.urbanairship.api.templates.model.TemplateResponse;
import com.urbanairship.api.templates.model.TemplateSelector;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class TemplatePushRequestTest {

    private final static ObjectMapper mapper = TemplatesObjectMapper.getInstance();
    private final static String PATH_NAME = "/api/templates/push/";

    TemplatePushRequest request;
    TemplatePushRequest requestValidateOnly;

    TemplatePushPayload payload = TemplatePushPayload.newBuilder()
            .setAudience(Selectors.namedUser("named_user"))
            .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID))
            .setMergeData(TemplateSelector.newBuilder()
                    .setTemplateId("template-id")
                    .addSubstitution("FIRST_NAME", "Firsty")
                    .addSubstitution("LAST_NAME", "Lasty")
                    .addSubstitution("TITLE", "Dr.")
                    .build())
            .build();


    @Before
    public void setup() {
        request = TemplatePushRequest.newRequest()
                .addTemplatePushPayload(payload);
        requestValidateOnly = TemplatePushRequest.newRequest()
                .addTemplatePushPayload(payload)
                .setValidateOnly(true);
    }

    @Test
    public void testContentType() throws Exception {
        Assert.assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
        Assert.assertEquals(requestValidateOnly.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        Assert.assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
        Assert.assertEquals(requestValidateOnly.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testBody() throws Exception {
        Assert.assertEquals(request.getRequestBody(), mapper.writeValueAsString(payload));
        Assert.assertEquals(requestValidateOnly.getRequestBody(), mapper.writeValueAsString(payload));
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        Assert.assertEquals(request.getRequestHeaders(), headers);
        Assert.assertEquals(requestValidateOnly.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedUri = URI.create("https://go.urbanairship.com" + PATH_NAME);
        Assert.assertEquals(request.getUri(baseURI), expectedUri);

        expectedUri = URI.create("https://go.urbanairship.com" + PATH_NAME + "validate/");
        Assert.assertEquals(requestValidateOnly.getUri(baseURI), expectedUri);
    }

    @Test
    public void testResponseParser() throws Exception {

        String responseJson =
                "{" +
                        "\"ok\": true," +
                        "\"operation_id\": \"df6a6b50-9843-0304-d5a5-743f246a4946\"," +
                        "\"push_ids\": [" +
                            "\"9d78a53b-b16a-c58f-b78d-181d5e242078\"," +
                            "\"1cbfbfa2-08d1-92c2-7119-f8f7f670f5f6\"" +
                        "]" +
                 "}";


        final ResponseParser<TemplateResponse> responseParser = response -> mapper.readValue(response, TemplateResponse.class);

        Assert.assertEquals(request.getResponseParser().parse(responseJson), responseParser.parse(responseJson));
        Assert.assertEquals(requestValidateOnly.getResponseParser().parse(responseJson), responseParser.parse(responseJson));
    }

}