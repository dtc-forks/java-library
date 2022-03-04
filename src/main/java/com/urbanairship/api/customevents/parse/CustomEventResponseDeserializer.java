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
            .put("ok", new FieldParser<CustomEventResponseReader>() {
                @Override
                public void parse(CustomEventResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readOk(jsonParser);
                }
            })
            .put("operationId", new FieldParser<CustomEventResponseReader>() {
                @Override
                public void parse(CustomEventResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readOperationId(jsonParser);
                }
            })
            .put("error", new FieldParser<CustomEventResponseReader>() {
                @Override
                public void parse(CustomEventResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readError(jsonParser);
                }
            })
            .put("details", new FieldParser<CustomEventResponseReader>() {
                @Override
                public void parse(CustomEventResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readErrorDetails(jsonParser);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<CustomEventResponse, ?> deserializer;

    public CustomEventResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<CustomEventResponse, CustomEventResponseReader>(
                FIELD_PARSERS,
                new Supplier<CustomEventResponseReader>(){
                    @Override
                    public CustomEventResponseReader get() {
                        return new CustomEventResponseReader();
                    }
                }
        );
    }

    @Override
    public CustomEventResponse deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}
