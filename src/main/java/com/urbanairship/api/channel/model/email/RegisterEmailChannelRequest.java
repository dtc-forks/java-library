package com.urbanairship.api.channel.model.email;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the Request to be used for registering or updating an email channel.
 */
public class RegisterEmailChannelRequest implements Request<EmailChannelResponse> {

    private final static String API_REGISTER_EMAIL_CHANNEL = "/api/channels/email/";
    private final RegisterEmailChannel payload;

    private RegisterEmailChannelRequest(RegisterEmailChannel payload) {
        Preconditions.checkNotNull(payload, "Payload must not be null to create an RegisterEmail channel request");
        this.payload = payload;
    }

    public static RegisterEmailChannelRequest newRequest(RegisterEmailChannel payload) {
        return new RegisterEmailChannelRequest(payload);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        return payload.toJSON();
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, API_REGISTER_EMAIL_CHANNEL);
    }

    @Override
    public ResponseParser<EmailChannelResponse> getResponseParser() {
        return response -> ChannelObjectMapper.getInstance().readValue(response, EmailChannelResponse.class);
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return true;
    }
}