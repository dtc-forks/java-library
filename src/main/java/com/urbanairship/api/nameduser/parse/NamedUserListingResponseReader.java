/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import com.urbanairship.api.nameduser.model.NamedUserView;

import java.io.IOException;
import java.util.List;

public class NamedUserListingResponseReader implements JsonObjectReader<NamedUserListingResponse> {

    private final NamedUserListingResponse.Builder builder;

    public NamedUserListingResponseReader() {
        this.builder = NamedUserListingResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.getBooleanValue());
    }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readNamedUser(JsonParser jsonParser) throws IOException {
        builder.setNamedUserView(jsonParser.readValueAs(NamedUserView.class));
    }

    public void readNamedUsers(JsonParser jsonParser) throws IOException {
        builder.setNamedUserViews((List<NamedUserView>) jsonParser.readValueAs(new TypeReference<List<NamedUserView>>() {
        }));
    }

    public void readError(JsonParser jsonParser) throws IOException {
        builder.setError(jsonParser.readValueAs(String.class));
    }

    public void readErrorDetails(JsonParser jsonParser) throws IOException {
        builder.setErrorDetails(jsonParser.readValueAs(ErrorDetails.class));
    }

    @Override
    public NamedUserListingResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
