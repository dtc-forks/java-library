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
import com.urbanairship.api.push.model.notification.android.BigPictureStyle;

import java.io.IOException;

public class BigPictureStyleDeserializer extends JsonDeserializer<BigPictureStyle> {
    private static final FieldParserRegistry<BigPictureStyle, BigPictureStyleReader> FIELD_PARSERS = new MapFieldParserRegistry<BigPictureStyle, BigPictureStyleReader>(
            ImmutableMap.<String, FieldParser<BigPictureStyleReader>>builder()
                    .put("title", (reader, json, context) -> reader.readTitle(json))
                    .put("summary", (reader, json, context) -> reader.readSummary(json))
                    .put("big_picture", (reader, json, context) -> reader.readContent(json))
                    .build()
    );

    private final StandardObjectDeserializer<BigPictureStyle, ?> deserializer;

    public BigPictureStyleDeserializer() {
        deserializer = new StandardObjectDeserializer<BigPictureStyle, BigPictureStyleReader>(
                FIELD_PARSERS,
                () -> new BigPictureStyleReader()
        );
    }

    @Override
    public BigPictureStyle deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
