package com.urbanairship.api.customevents.parse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.customevents.model.CustomEventChannelType;
import com.urbanairship.api.customevents.model.CustomEventUser;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomEventUserSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testChannelTypes() throws Exception {
        CustomEventUser iosUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.IOS_CHANNEL)
                .setChannel("iOSChannel")
                .build();

        CustomEventUser androidUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .setChannel("androidChannel")
                .build();

        CustomEventUser amazonUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.AMAZON_CHANNEL)
                .setChannel("amazonChannel")
                .build();

        CustomEventUser namedUserUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.NAMED_USER_CHANNEL)
                .setChannel("namedUserChannel")
                .build();

        String iosJson = MAPPER.writeValueAsString(iosUser);
        String iosExpected = "{\"ios_channel\":\"iOSChannel\"}";
        assertEquals(iosJson, iosExpected);

        String androidJson = MAPPER.writeValueAsString(androidUser);
        String androidExpected = "{\"android_channel\":\"androidChannel\"}";
        assertEquals(androidJson, androidExpected);

        String amazonJson = MAPPER.writeValueAsString(amazonUser);
        String amazonExpected = "{\"amazon_channel\":\"amazonChannel\"}";
        assertEquals(amazonJson, amazonExpected);

        String namedUserJson = MAPPER.writeValueAsString(namedUserUser);
        String namedUserExpected = "{\"named_user_channel\":\"namedUserChannel\"}";
        assertEquals(amazonJson, amazonExpected);
    }
}
