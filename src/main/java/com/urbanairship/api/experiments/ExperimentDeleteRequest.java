package com.urbanairship.api.experiments;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The ExperimentRequest class builds delete experiment requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class ExperimentDeleteRequest implements Request<GenericResponse> {

    private final static String DELETE_EXPERIMENT_PATH = "/api/experiments/scheduled/";

    private final String experimentId;

    private ExperimentDeleteRequest(String experimentId) {
        this.experimentId = experimentId;
    }

    /**
     * Create the scheduled experiment delete request.
     *
     * @param experimentId String
     * @return DeleteScheduleRequest
     */
    public static ExperimentDeleteRequest newRequest(String experimentId) {
        Preconditions.checkNotNull(experimentId, "Experiment Id may not be null");
        return new ExperimentDeleteRequest(experimentId);
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.DELETE;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        String path  = DELETE_EXPERIMENT_PATH + experimentId;
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<GenericResponse> getResponseParser() {
        return RequestUtils.GENERIC_RESPONSE_PARSER;
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return false;
    }
}
