/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.PushResponse;

import java.io.IOException;

/*
Deserializers create a mapping between Jackson and an object. This abstracts all
the boilerplate necessary for Jackson stream parsing, which is essentially what
 we're doing. This will be a lot cleaner when lambda's come down.
 If you're using Intellij, be sure and toggle open the code that's
 been collapsed.
 */
public final class PushResponseDeserializer extends JsonDeserializer<PushResponse> {

    private static final FieldParserRegistry<PushResponse, PushResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<PushResponse, PushResponseReader>(
                    ImmutableMap.<String, FieldParser<PushResponseReader>>builder()
                            .put("operation_id", new FieldParser<PushResponseReader>() {
                                @Override
                                public void parse(PushResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readOperationId(jsonParser);
                                }
                            })
                            .put("push_ids", new FieldParser<PushResponseReader>() {
                                @Override
                                public void parse(PushResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readPushIds(jsonParser);
                                }
                            })
                            .put("ok", new FieldParser<PushResponseReader>() {
                                @Override
                                public void parse(PushResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("message_ids", new FieldParser<PushResponseReader>() {
                                @Override
                                public void parse(PushResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readMessageIds(jsonParser);
                                }
                            })
                            .put("content_urls", new FieldParser<PushResponseReader>() {
                                @Override
                                public void parse(PushResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readContentUrls(jsonParser);
                                }
                            })
                            .put("error", new FieldParser<PushResponseReader>() {
                                @Override
                                public void parse(PushResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readError(jsonParser);
                                }
                            })
                            .put("details", new FieldParser<PushResponseReader>() {
                                @Override
                                public void parse(PushResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readErrorDetails(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<PushResponse, ?> deserializer;

    // See Google Guava for Supplier details
    public PushResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<PushResponse, PushResponseReader>(
                FIELD_PARSERS,
                new Supplier<PushResponseReader>() {
                    @Override
                    public PushResponseReader get() {
                        return new PushResponseReader();
                    }
                }
        );
    }

    @Override
    public PushResponse deserialize(JsonParser parser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
