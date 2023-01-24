package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.OpenChannelResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class OpenChannelResponseDeserializer extends JsonDeserializer<OpenChannelResponse> {
    private static final FieldParserRegistry<OpenChannelResponse, OpenChannelResponseReader> FIELD_PARSERS = new MapFieldParserRegistry<OpenChannelResponse, OpenChannelResponseReader>(
            ImmutableMap.<String, FieldParser<OpenChannelResponseReader>>builder()
            .put("ok", (reader, parser, context) -> reader.readOk(parser))
            .put(Constants.CHANNEL_ID, (reader, parser, context) -> reader.readChannelId(parser))
            .put("error", (reader, parser, context) -> reader.readError(parser))
            .put("details", (reader, parser, context) -> reader.readErrorDetails(parser))
            .build()
    );

    private final StandardObjectDeserializer<OpenChannelResponse, ?> deserializer;

    public OpenChannelResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<OpenChannelResponse, OpenChannelResponseReader>(
                FIELD_PARSERS,
                () -> new OpenChannelResponseReader()
        );
    }

    @Override
    public OpenChannelResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}
