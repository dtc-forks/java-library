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
import com.urbanairship.api.push.model.notification.wns.WNSTileData;

import java.io.IOException;

public class WNSTileDeserializer extends JsonDeserializer<WNSTileData> {

    private static final FieldParserRegistry<WNSTileData, WNSTileReader> FIELD_PARSERS = new MapFieldParserRegistry<WNSTileData, WNSTileReader>(
            ImmutableMap.<String, FieldParser<WNSTileReader>>builder()
            .put("binding", (reader, json, context) -> reader.readBinding(json, context))
            .build()
            );

    private final StandardObjectDeserializer<WNSTileData, ?> deserializer;

    public WNSTileDeserializer(final WNSBindingDeserializer bindingDS) {
        deserializer = new StandardObjectDeserializer<WNSTileData, WNSTileReader>(
            FIELD_PARSERS,
                () -> new WNSTileReader(bindingDS)
        );
    }

    @Override
    public WNSTileData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
