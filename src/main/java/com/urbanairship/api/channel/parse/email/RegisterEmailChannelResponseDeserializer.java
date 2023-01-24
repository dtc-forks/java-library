package com.urbanairship.api.channel.parse.email;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.email.EmailChannelResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public class RegisterEmailChannelResponseDeserializer extends JsonDeserializer<EmailChannelResponse> {

    private static final FieldParserRegistry<EmailChannelResponse, RegisterEmailChannelResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<>(
            ImmutableMap.<String, FieldParser<RegisterEmailChannelResponseReader>>builder()
                    .put("ok", (reader, parser, context) -> reader.readOk(parser))
                    .put("channel_id", (reader, parser, context) -> reader.readChannelId(parser))
                    .put("error", (reader, parser, context) -> reader.readError(parser))
                    .put("details", (reader, parser, context) -> reader.readErrorDetails(parser))
                    .build()
    );

    private final StandardObjectDeserializer<EmailChannelResponse, ?> deserializer;

    public RegisterEmailChannelResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<EmailChannelResponse, RegisterEmailChannelResponseReader>(
                FIELD_PARSERS,
                () -> new RegisterEmailChannelResponseReader()
        );
    }

    @Override
    public EmailChannelResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
