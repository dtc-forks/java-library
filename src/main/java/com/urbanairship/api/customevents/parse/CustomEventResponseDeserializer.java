package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.customevents.model.CustomEventResponse;

import java.io.IOException;

public class CustomEventResponseDeserializer extends JsonDeserializer<CustomEventResponse> {
    private static final FieldParserRegistry<CustomEventResponse, CustomEventResponseReader> FIELD_PARSERS = new MapFieldParserRegistry<CustomEventResponse, CustomEventResponseReader>(
            ImmutableMap.<String, FieldParser<CustomEventResponseReader>>builder()
            .put("ok", (reader, jsonParser, deserializationContext) -> reader.readOk(jsonParser))
            .put("operationId", (reader, jsonParser, deserializationContext) -> reader.readOperationId(jsonParser))
            .put("error", (reader, jsonParser, deserializationContext) -> reader.readError(jsonParser))
            .put("details", (reader, jsonParser, deserializationContext) -> reader.readErrorDetails(jsonParser))
            .build()
    );

    private final StandardObjectDeserializer<CustomEventResponse, ?> deserializer;

    public CustomEventResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<CustomEventResponse, CustomEventResponseReader>(
                FIELD_PARSERS,
                () -> new CustomEventResponseReader()
        );
    }

    @Override
    public CustomEventResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}
