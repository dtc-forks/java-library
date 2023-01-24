/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.android.BigTextStyle;

import java.io.IOException;

public class BigTextStyleDeserializer extends JsonDeserializer<BigTextStyle> {
    private static final FieldParserRegistry<BigTextStyle, BigTextStyleReader> FIELD_PARSERS = new MapFieldParserRegistry<BigTextStyle, BigTextStyleReader>(
            ImmutableMap.<String, FieldParser<BigTextStyleReader>>builder()
                    .put("title", (reader, json, context) -> reader.readTitle(json))
                    .put("summary", (reader, json, context) -> reader.readSummary(json))
                    .put("big_text", (reader, json, context) -> reader.readContent(json))
                    .build()
    );

    private final StandardObjectDeserializer<BigTextStyle, ?> deserializer;

    public BigTextStyleDeserializer() {
        deserializer = new StandardObjectDeserializer<BigTextStyle, BigTextStyleReader>(
                FIELD_PARSERS,
                () -> new BigTextStyleReader()
        );
    }

    @Override
    public BigTextStyle deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
