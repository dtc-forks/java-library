/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public final class ChannelViewDeserializer extends JsonDeserializer<ChannelView> {

    private static final FieldParserRegistry<ChannelView, ChannelViewReader> FIELD_PARSERS = new MapFieldParserRegistry<ChannelView, ChannelViewReader>(
            ImmutableMap.<String, FieldParser<ChannelViewReader>>builder()
                    .put(Constants.CHANNEL_ID, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readChannelId(jsonParser);
                        }
                    })
                    .put(Constants.DEVICE_TYPE, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readDeviceType(jsonParser);
                        }
                    })
                    .put(Constants.INSTALLED, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readInstalled(jsonParser);
                        }
                    })
                    .put(Constants.OPT_IN, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readOptIn(jsonParser);
                        }
                    })
                    .put(Constants.BACKGROUND, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readBackground(jsonParser);
                        }
                    })
                    .put(Constants.PUSH_ADDRESS, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushAddress(jsonParser);
                        }
                    })
                    .put(Constants.CREATED, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readCreated(jsonParser);
                        }
                    })
                    .put(Constants.LAST_REGISTRATION, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readLastRegistration(jsonParser);
                        }
                    })
                    .put(Constants.ALIAS, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAlias(jsonParser);
                        }
                    })
                    .put(Constants.TAGS, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readTags(jsonParser);
                        }
                    })
                    .put(Constants.TAG_GROUPS, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readTagGroups(jsonParser);
                        }
                    })
                    .put(Constants.IOS, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readIosSettings(jsonParser);
                        }
                    })
                    .put(Constants.WEB, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readWeb(jsonParser);
                        }
                    })
                    .put(Constants.OPEN_CHANNEL, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readOpenChannel(jsonParser);
                        }
                    })
                    .put(Constants.ADDRESS, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAddress(jsonParser);
                        }
                    })
                    .put(Constants.NAMED_USER, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readNamedUser(jsonParser);
                        }
                    })
                    .put("attributes", new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAttributes(jsonParser);
                        }
                    })
                    .put("device_attributes", new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readDeviceAttributes(jsonParser);
                        }
                    })
                    .put("commercial_opted_in", new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readCommercialOptedIn(jsonParser);
                        }
                    })
                    .put("commercial_opted_out", new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readCommercialOptedOut(jsonParser);
                        }
                    })
                    .put("transactional_opted_in", new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readTransactionalOptedIn(jsonParser);
                        }
                    })
                    .put("transactional_opted_out", new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readTransactionalOptedOut(jsonParser);
                        }
                    })
                    .build());

    private final StandardObjectDeserializer<ChannelView, ?> deserializer;

    public ChannelViewDeserializer() {
        deserializer = new StandardObjectDeserializer<ChannelView, ChannelViewReader>(
                FIELD_PARSERS,
                new Supplier<ChannelViewReader>() {
                    @Override
                    public ChannelViewReader get() {
                        return new ChannelViewReader();
                    }
                }
        );
    }

    @Override
    public ChannelView deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
