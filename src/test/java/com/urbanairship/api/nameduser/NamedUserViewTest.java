package com.urbanairship.api.nameduser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.nameduser.model.NamedUserView;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class NamedUserViewTest {

    private final ObjectMapper mapper = NamedUserObjectMapper.getInstance();

    @Test
    public void testMinimal() throws Exception {
        String json =
            "{" +
                "\"named_user_id\": \"user-id-1234\"," +
                "\"tags\": {" +
                    "\"crm\": [\"tag1\", \"tag2\"]" +
                "}," +
                "\"channels\": [" +
                    "{" +
                        "\"channel_id\" : \"abcdef\"," +
                        "\"device_type\" : \"ios\"," +
                        "\"installed\" : true," +
                        "\"opt_in\" : false," +
                        "\"created\" : \"2013-08-08T20:41:06.000Z\"" +
                    "}" +
                "]" +
            "}";

        NamedUserView namedUserView = mapper.readValue(json, NamedUserView.class);
        Assert.assertEquals(namedUserView.getNamedUserId(), "user-id-1234");
        Assert.assertTrue(namedUserView.getNamedUserTags().containsKey("crm"));
        Set<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        Assert.assertEquals(namedUserView.getNamedUserTags().get("crm"), tags);

        ChannelView channel = namedUserView.getChannelViews().iterator().next();
        Assert.assertFalse(channel.isOptIn());
        Assert.assertFalse(channel.getBackground().isPresent());
        Assert.assertEquals(ChannelType.IOS.getIdentifier(), channel.getChannelType());
        Assert.assertFalse(channel.getAlias().isPresent());
        Assert.assertFalse(channel.getIosSettings().isPresent());
        Assert.assertFalse(channel.getPushAddress().isPresent());
        Assert.assertTrue(channel.getTags().isEmpty());

    }

    @Test
    public void testMaximal() throws Exception {
        String json =
            "{" +
                "\"named_user_id\": \"user-id-1234\"," +
                "\"tags\": {" +
                    "\"crm\": [\"tag1\", \"tag2\"]" +
                "}," +
                "\"attributes\": {\n" +
                "        \"item_purchased\": \"Fur removal tool\",\n" +
                "        \"cats_name\": \"Sammy\",\n" +
                "        \"pets_age\": 12\n" +
                "      },\n" +
                "\"user_attributes\": {\n" +
                "        \"ua_country\": \"US\",\n" +
                "        \"ua_language\": \"en\",\n" +
                "        \"ua_tz\": \"America/Los_Angeles\"\n" +
                "      }," +
                "\"channels\": [" +
                    "{" +
                        "\"channel_id\" : \"abcdef\"," +
                        "\"device_type\" : \"ios\"," +
                        "\"installed\" : true," +
                        "\"opt_in\" : true," +
                        "\"background\" : true," +
                        "\"ios\" : {" +
                            "\"badge\": 0," +
                            "\"quiettime\": {" +
                                "\"start\": \"22:00\"," +
                                "\"end\": \"06:00\"" +
                            "}," +
                            "\"tz\": \"America/Los_Angeles\"" +
                        "}," +
                        "\"tags\" : [\"tag1\", \"tag2\"]," +
                        "\"tag_groups\" : {" +
                            "\"group1\" : [" +
                                "\"tag1OfGroup1\"," +
                                "\"tag2OfGroup1\"" +
                            "]," +
                            "\"group2\" : [" +
                                "\"tag1OfGroup2\"," +
                                "\"tag2OfGroup2\"" +
                            "]" +
                        "}," +
                        "\"alias\" : \"alias\"," +
                        "\"created\" : \"2013-08-08T20:41:06.000Z\"," +
                        "\"push_address\" : \"address\"" +
                    "}," +
                    "{" +
                        "\"channel_id\" : \"abcdef\"," +
                        "\"device_type\" : \"ios\"," +
                        "\"installed\" : true," +
                        "\"opt_in\" : false," +
                        "\"created\" : \"2013-08-08T20:41:06.000Z\"" +
                    "}" +
                "]" +
            "}";
        NamedUserView namedUserView =  mapper.readValue(json, NamedUserView.class);
        Assert.assertEquals(namedUserView.getNamedUserId(), "user-id-1234");
        Assert.assertTrue(namedUserView.getNamedUserTags().containsKey("crm"));
        Set<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        Assert.assertEquals(namedUserView.getNamedUserTags().get("crm"), tags);

        ChannelView firstChannel;
        ChannelView secondChannel;
        if (namedUserView.getChannelViews().asList().get(1).isOptIn()) {
            firstChannel = namedUserView.getChannelViews().asList().get(1);
            secondChannel = namedUserView.getChannelViews().asList().get(0);
        } else {
            firstChannel = namedUserView.getChannelViews().asList().get(0);
            secondChannel = namedUserView.getChannelViews().asList().get(1);
        }

        Assert.assertTrue(firstChannel.isOptIn());
        Assert.assertTrue(firstChannel.getBackground().isPresent());
        Assert.assertTrue(firstChannel.getBackground().get());
        Assert.assertEquals(ChannelType.IOS.getIdentifier(), firstChannel.getChannelType());
        Assert.assertTrue(firstChannel.getIosSettings().isPresent());
        Assert.assertEquals("12", namedUserView.getAttributes().get("pets_age"));
        Assert.assertEquals("US", namedUserView.getUserAttributes().get("ua_country"));
        Assert.assertEquals(0, firstChannel.getIosSettings().get().getBadge());
        Assert.assertEquals("22:00", firstChannel.getIosSettings().get().getQuietTime().get().getStart());
        Assert.assertEquals("06:00", firstChannel.getIosSettings().get().getQuietTime().get().getEnd());
        Assert.assertEquals("America/Los_Angeles", firstChannel.getIosSettings().get().getTimezone().get());
        Assert.assertEquals("address", firstChannel.getPushAddress().get());
        Assert.assertEquals("alias", firstChannel.getAlias().get());
        ImmutableSet<String> expectedTags = new ImmutableSet.Builder<String>()
            .addAll(Sets.newHashSet("tag1", "tag2")).build();
        Assert.assertEquals(expectedTags, firstChannel.getTags());
        ImmutableMap<String, ImmutableSet<String>> expectedTagGroups = new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put("group1", new ImmutableSet.Builder<String>()
                .addAll(Sets.newHashSet("tag1OfGroup1", "tag2OfGroup1")).build())
            .put("group2", new ImmutableSet.Builder<String>()
                .addAll(Sets.newHashSet("tag1OfGroup2", "tag2OfGroup2")).build())
            .build();
        Assert.assertEquals(expectedTagGroups, firstChannel.getTagGroups());
        Assert.assertFalse(secondChannel.isOptIn());
        Assert.assertFalse(secondChannel.getBackground().isPresent());
        Assert.assertEquals(ChannelType.IOS.getIdentifier(), secondChannel.getChannelType());
        Assert.assertFalse(secondChannel.getAlias().isPresent());
        Assert.assertFalse(secondChannel.getIosSettings().isPresent());
        Assert.assertFalse(secondChannel.getPushAddress().isPresent());
        Assert.assertTrue(secondChannel.getTags().isEmpty());

    }

}
