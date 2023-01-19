/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSMediaContent;

import java.io.IOException;

public class IOSMediaContentDeserializer extends JsonDeserializer<IOSMediaContent> {

    private static final FieldParserRegistry<IOSMediaContent, IOSMediaContentReader> FIELD_PARSER = new MapFieldParserRegistry<IOSMediaContent, IOSMediaContentReader>(
            ImmutableMap.<String, FieldParser<IOSMediaContentReader>>builder()
            .put("body", (reader, json, context) -> reader.readBody(json, context))
            .put("title", (reader, json, context) -> reader.readTitle(json, context))
            .put("subtitle", (reader, json, context) -> reader.readSubtitle(json, context))
            .build()
    );

    private final StandardObjectDeserializer<IOSMediaContent, ?> deserializer;

    public IOSMediaContentDeserializer() {
        deserializer = new StandardObjectDeserializer<IOSMediaContent, IOSMediaContentReader>(
                FIELD_PARSER,
                () -> new IOSMediaContentReader()
        );
    }

    @Override
    public IOSMediaContent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return deserializer.deserialize(jp, ctxt);
    }
}
