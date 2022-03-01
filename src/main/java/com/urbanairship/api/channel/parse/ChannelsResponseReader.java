/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.common.model.ErrorDetails;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;
import java.util.List;

public final class ChannelsResponseReader implements JsonObjectReader<ChannelResponse> {

    private final ChannelResponse.Builder builder;

    public ChannelsResponseReader() {
        this.builder = ChannelResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.getBooleanValue());
    }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readChannelObject(JsonParser jsonParser) throws IOException {
        builder.setChannelObject(jsonParser.readValueAs(ChannelView.class));
    }

    public void readChannelObjects(JsonParser jsonParser) throws IOException {
        builder.addAllChannels((List<ChannelView>) jsonParser.readValueAs(new TypeReference<List<ChannelView>>() {
        }));
    }

    public void readError(JsonParser jsonParser) throws IOException {
        builder.setError(jsonParser.readValueAs(String.class));
    }

    public void readErrorDetails(JsonParser jsonParser) throws IOException {
        builder.setErrorDetails(jsonParser.readValueAs(ErrorDetails.class));
    }

    @Override
    public ChannelResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
