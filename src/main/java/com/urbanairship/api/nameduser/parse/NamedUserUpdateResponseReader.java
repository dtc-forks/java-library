package com.urbanairship.api.nameduser.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.nameduser.model.NamedUserUpdateResponse;

import java.io.IOException;
import java.util.Optional;

public class NamedUserUpdateResponseReader implements JsonObjectReader<NamedUserUpdateResponse> {
    private final NamedUserUpdateResponse.Builder builder;

    public NamedUserUpdateResponseReader() {
        this.builder = NamedUserUpdateResponse.newBuilder();
    }

    public void readOk(JsonParser parser) throws IOException {
        builder.setOk(parser.getBooleanValue());
    }

    public void readError(JsonParser parser) throws IOException {
        builder.setError(Optional.ofNullable(parser.getText()));
    }

    public void readAttributeWarnings(JsonParser parser) throws IOException {
        builder.setAttributeWarnings(Optional.ofNullable(parser.readValueAs(new TypeReference<ImmutableList<String>>() {})));
    }

    public void readTagWarnings(JsonParser parser) throws IOException {
        builder.setTagWarnings(Optional.ofNullable(parser.readValueAs(new TypeReference<ImmutableList<String>>() {})));
    }

    public void readErrorDetails(JsonParser parser) throws IOException {
        builder.setErrorDetails(Optional.ofNullable(parser.readValueAs(ErrorDetails.class)));
    }

    @Override
    public NamedUserUpdateResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
