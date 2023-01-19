/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.reports.model.PlatformStats;

import java.io.IOException;

public final class PlatformStatsDeserializer extends JsonDeserializer<PlatformStats> {

    private static final FieldParserRegistry<PlatformStats, PlatformStatsReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PlatformStats, PlatformStatsReader>(ImmutableMap.<String, FieldParser<PlatformStatsReader>>builder()
                    .put("android", (reader, jsonParser, deserializationContext) -> reader.readAndroid(jsonParser))
                    .put("date", (reader, jsonParser, deserializationContext) -> reader.readDate(jsonParser))
                    .put("ios", (reader, jsonParser, deserializationContext) -> reader.readIOS(jsonParser))
                    .build()
            );

    private final StandardObjectDeserializer<PlatformStats, ?> deserializer;

    public PlatformStatsDeserializer() {
        deserializer = new StandardObjectDeserializer<PlatformStats, PlatformStatsReader>(
                FIELD_PARSERS,
                () -> new PlatformStatsReader()
        );
    }

    @Override
    public PlatformStats deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
