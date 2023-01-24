/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.wns.WNSAudioData;

import java.io.IOException;

public class WNSAudioDeserializer extends JsonDeserializer<WNSAudioData> {

    private static final FieldParserRegistry<WNSAudioData, WNSAudioReader> FIELD_PARSERS = new MapFieldParserRegistry<WNSAudioData, WNSAudioReader>(
            ImmutableMap.<String, FieldParser<WNSAudioReader>>builder()
            .put("sound", (reader, json, context) -> reader.readSound(json, context))
            .put("loop", (reader, json, context) -> reader.readLoop(json))
            .build()
            );

    private final StandardObjectDeserializer<WNSAudioData, ?> deserializer;

    public WNSAudioDeserializer() {
        deserializer = new StandardObjectDeserializer<WNSAudioData, WNSAudioReader>(
            FIELD_PARSERS,
                () -> new WNSAudioReader()
        );
    }

    @Override
    public WNSAudioData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
