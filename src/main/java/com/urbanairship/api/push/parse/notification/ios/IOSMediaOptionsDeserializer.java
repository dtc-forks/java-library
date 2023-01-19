/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.IOSMediaOptions;

import java.io.IOException;


public class IOSMediaOptionsDeserializer extends JsonDeserializer<IOSMediaOptions> {

    private static final FieldParserRegistry<IOSMediaOptions, IOSMediaOptionsPayloadReader> FIELD_PARSER = new MapFieldParserRegistry<IOSMediaOptions, IOSMediaOptionsPayloadReader>(
            ImmutableMap.<String, FieldParser<IOSMediaOptionsPayloadReader>>builder()
            .put("time", (reader, parser, context) -> reader.readTime(parser)).put("crop", (reader, parser, context) -> reader.readCrop(parser))
            .put("hidden", (reader, parser, context) -> reader.readHidden(parser))
            .build()
    );

    private final StandardObjectDeserializer<IOSMediaOptions, ?> deserializer;

    public IOSMediaOptionsDeserializer() {
        deserializer = new StandardObjectDeserializer<IOSMediaOptions, IOSMediaOptionsPayloadReader>(
                FIELD_PARSER,
                () -> new IOSMediaOptionsPayloadReader()
        );
    }

    @Override
    public IOSMediaOptions deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
