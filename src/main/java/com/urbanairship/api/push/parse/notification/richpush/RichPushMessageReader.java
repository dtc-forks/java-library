/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.richpush;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.MapOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.richpush.RichPushIcon;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.model.notification.richpush.RichPushTemplate;

import java.io.IOException;

public class RichPushMessageReader implements JsonObjectReader<RichPushMessage> {

    private RichPushMessage.Builder builder = RichPushMessage.newBuilder();

    public RichPushMessageReader() {
    }

    public void readTitle(JsonParser parser) throws IOException {
        builder.setTitle(StringFieldDeserializer.INSTANCE.deserialize(parser, "title"));
    }

    public void readBody(JsonParser parser) throws IOException {
        builder.setBody(StringFieldDeserializer.INSTANCE.deserialize(parser, "body"));
    }

    public void readContentType(JsonParser parser) throws IOException {
        builder.setContentType(StringFieldDeserializer.INSTANCE.deserialize(parser, "content_type"));
    }

    public void readContentEncoding(JsonParser parser) throws IOException {
        builder.setContentEncoding(StringFieldDeserializer.INSTANCE.deserialize(parser, "content_encoding"));
    }

    public void readExtra(JsonParser parser) throws IOException {
        builder.addAllExtraEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, "extra"));
    }

    public void readExpiry(JsonParser parser) throws IOException {
        builder.setExpiry(parser.readValueAs(PushExpiry.class));
    }

    public void readIcons(JsonParser parser) throws IOException {
        builder.setIcons(parser.readValueAs(RichPushIcon.class));
    }

    public void readRichPushTemplate(JsonParser parser) throws IOException {
        builder.setRichPushTemplate(parser.readValueAs(RichPushTemplate.class));
    }

    @Override
    public RichPushMessage validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
