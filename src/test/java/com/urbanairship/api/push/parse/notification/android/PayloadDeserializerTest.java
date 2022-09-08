package com.urbanairship.api.push.parse.notification.android;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.android.AndroidFields;
import com.urbanairship.api.push.model.notification.android.AndroidTemplate;
import com.urbanairship.api.push.model.notification.android.Category;
import com.urbanairship.api.push.model.notification.android.PublicNotification;
import com.urbanairship.api.push.model.notification.android.Wearable;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PayloadDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testAlert() throws Exception {
        String json
                = "{"
                + "  \"alert\": \"android override\""
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .setAlert("android override")
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertTrue(payload.getAlert().isPresent());
        assertFalse(payload.getCollapseKey().isPresent());
        assertFalse(payload.getExtra().isPresent());
        assertEquals("android override", payload.getAlert().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testCollapseKey() throws Exception {
        String json
                = "{"
                + "  \"collapse_key\": \"1234\""
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .setCollapseKey("1234")
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getCollapseKey());
        assertFalse(payload.getAlert().isPresent());
        assertFalse(payload.getExtra().isPresent());
        assertTrue(payload.getCollapseKey().isPresent());
        assertEquals("1234", payload.getCollapseKey().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testNotificationChannel() throws Exception {
        String json
                = "{"
                + "  \"notification_channel\": \"channel1\""
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .setNotificationChannel("channel1")
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getNotificationChannel());
        assertFalse(payload.getAlert().isPresent());
        assertFalse(payload.getExtra().isPresent());
        assertFalse(payload.getNotificationTag().isPresent());
        assertTrue(payload.getNotificationChannel().isPresent());
        assertEquals("channel1", payload.getNotificationChannel().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testNotificationTag() throws Exception {
        String json
                = "{"
                + "  \"notification_tag\": \"nt1\""
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .setNotificationTag("nt1")
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getNotificationTag());
        assertFalse(payload.getAlert().isPresent());
        assertFalse(payload.getExtra().isPresent());
        assertFalse(payload.getNotificationChannel().isPresent());
        assertTrue(payload.getNotificationTag().isPresent());
        assertEquals("nt1", payload.getNotificationTag().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testTimeToLive() throws Exception {
        String json
                = "{"
                + "  \"time_to_live\": 1234"
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .setTimeToLive(PushExpiry.newBuilder()
                        .setExpirySeconds(1234)
                        .build())
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertEquals(expected, payload);
        assertTrue(payload.getTimeToLive().isPresent());
        assertEquals(1234, payload.getTimeToLive().get().getExpirySeconds().get().intValue());
    }

    @Test(expected = APIParsingException.class)
    public void testTimeToLiveBadFormat() throws Exception {
        String json
                = "{"
                + "  \"time_to_live\": true"
                + "}";
        mapper.readValue(json, AndroidDevicePayload.class);
    }

    @Test
    public void testDelayWhileIdle() throws Exception {
        String json
                = "{"
                + "  \"delay_while_idle\": true"
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .setDelayWhileIdle(true)
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertEquals(expected, payload);
        assertTrue(payload.getDelayWhileIdle().isPresent());
        assertEquals(true, payload.getDelayWhileIdle().get());
    }

    @Test(expected = APIParsingException.class)
    public void testDelayWhileIdleBadFormat() throws Exception {
        String json
                = "{"
                + "  \"delay_while_idle\": 1010"
                + "}";
        mapper.readValue(json, AndroidDevicePayload.class);
    }

    @Test
    public void testExtra() throws Exception {
        String json
                = "{"
                + "  \"extra\": {"
                + "    \"k1\" : \"v1\","
                + "    \"k2\" : \"v2\""
                + "  }"
                + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
                .addExtraEntry("k1", "v1")
                .addExtraEntry("k2", "v2")
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getExtra());
        assertTrue(payload.getExtra().isPresent());
        assertFalse(payload.getAlert().isPresent());
        assertFalse(payload.getCollapseKey().isPresent());
        Map<String, String> extra = payload.getExtra().get();
        assertTrue(extra.containsKey("k1"));
        assertTrue(extra.containsKey("k2"));
        assertEquals("v1", extra.get("k1"));
        assertEquals("v2", extra.get("k2"));
        assertEquals(expected, payload);
    }

    @Test
    public void testValidate_Empty() throws Exception {
        AndroidDevicePayload payload = mapper.readValue("{}", AndroidDevicePayload.class);
        assertNotNull(payload);
        assertFalse(payload.getAlert().isPresent());
        assertFalse(payload.getExtra().isPresent());
        assertFalse(payload.getCollapseKey().isPresent());
    }

    @Test
    public void testInteractiveNotificationActions() throws Exception {
        String json
            = "{"
            + "  \"interactive\": {"
            + "    \"type\" : \"ua_yes_no_foreground\","
            + "    \"button_actions\" : {"
            + "      \"yes\" : {"
            + "        \"share\" : \"foo\""
            + "      }"
            + "    }"
            + "  }"
            + "}";
        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);

        Interactive interactive = Interactive.newBuilder()
            .setType("ua_yes_no_foreground")
            .setButtonActions(
                ImmutableMap.of(
                    "yes",
                    Actions.newBuilder()
                        .setShare(new ShareAction("foo"))
                        .build()))
            .build();
        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
            .setInteractive(interactive)
            .build();

        assertEquals(expected, payload);
        Interactive returned = payload.getInteractive().get();
        assertEquals(interactive, returned);
    }

    @Test
    public void testTitle() throws Exception {
        String json
            = "{"
            + "  \"title\": \"title\""
            + "}";

        AndroidDevicePayload expected = AndroidDevicePayload.newBuilder()
            .setTitle("title")
            .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertEquals(expected, payload);
        assertTrue(payload.getTitle().isPresent());
        assertEquals("title", payload.getTitle().get());
    }

    @Test
    public void testDeliveryPriority() throws Exception {
        String json =
                "{" +
                        "\"delivery_priority\": \"high\"" +
                "}";

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertEquals(payload.getDeliveryPriority().get(), "high");
    }

    @Test
    public void testLocalOnly() throws Exception {
        String json =
                "{" +
                        "\"local_only\": true" +
                "}";

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertEquals(payload.getLocalOnly().get(), true);
    }

    @Test
    public void testWearable() throws Exception {
        String json =
                "{" +
                        "\"wearable\": {" +
                            "\"background_image\": \"https://yolo.pizza.biz/\"" +
                        "}" +
                "}";

        Wearable wearable = Wearable.newBuilder()
                .setBackgroundImage("https://yolo.pizza.biz/")
                .build();

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertEquals(payload.getWearable().get(), wearable);
    }

    @Test
    public void testSummary() throws Exception {
        String json =
                "{" +
                        "\"summary\": \"A summary\"" +
                "}";

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertEquals(payload.getSummary().get(), "A summary");
    }

    @Test
    public void testStyle() throws Exception {
        String bigTextJson =
                "{" +
                        "\"style\":{" +
                            "\"summary\": \"A summary\"," +
                            "\"title\": \"A title\"," +
                            "\"type\": \"big_text\"," +
                            "\"big_text\": \"BlahBlah\"" +
                        "}" +
                "}";

        String bigPicJson =
                "{" +
                        "\"style\":{" +
                            "\"summary\": \"A summary\"," +
                            "\"title\": \"A title\"," +
                            "\"type\": \"big_picture\"," +
                            "\"big_picture\": \"pic.png\"" +
                        "}" +
                "}";

        String inboxJson =
                "{" +
                        "\"style\":{" +
                            "\"type\":\"inbox\"," +
                            "\"lines\":[\"line_1\",\"line_2\",\"line_3\"]," +
                            "\"title\":\"lines title\"," +
                            "\"summary\":\"lines summary\"" +
                        "}" +
                "}";


        AndroidDevicePayload bigTextPayload = mapper.readValue(bigTextJson, AndroidDevicePayload.class);
        assertNotNull(bigTextPayload);
        assertEquals(bigTextPayload.getStyle().get().getType().getStyleType(), "big_text");
        assertEquals(bigTextPayload.getStyle().get().getSummary().get(), "A summary");
        assertEquals(bigTextPayload.getStyle().get().getTitle().get(), "A title");
        assertEquals(bigTextPayload.getStyle().get().getContent(), "BlahBlah");

        AndroidDevicePayload bigPicPayload = mapper.readValue(bigPicJson, AndroidDevicePayload.class);
        assertNotNull(bigPicPayload);
        assertEquals(bigPicPayload.getStyle().get().getType().getStyleType(), "big_picture");
        assertEquals(bigPicPayload.getStyle().get().getSummary().get(), "A summary");
        assertEquals(bigPicPayload.getStyle().get().getTitle().get(), "A title");
        assertEquals(bigPicPayload.getStyle().get().getContent(), "pic.png");

        AndroidDevicePayload inboxPayload = mapper.readValue(inboxJson, AndroidDevicePayload.class);
        assertNotNull(inboxPayload);
        assertEquals(inboxPayload.getStyle().get().getType().getStyleType(), "inbox");
        assertEquals(inboxPayload.getStyle().get().getSummary().get(), "lines summary");
        assertEquals(inboxPayload.getStyle().get().getTitle().get(), "lines title");

    }

    @Test
    public void testSound() throws Exception {
        String json =
                "{" +
                        "\"sound\": \"cowbell.mp3\"" +
                "}";

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertEquals(payload.getSound().get(), "cowbell.mp3");
    }

    @Test
    public void testIcon() throws Exception {
        String json =
                "{" +
                        "\"icon\": \"icon.xml\"" +
                 "}";

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertEquals(payload.getIcon().get(), "icon.xml");
    }

    @Test
    public void testIconColor() throws Exception {
        String json =
                "{" +
                        "\"icon_color\": \"#012345\"" +
                 "}";

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertEquals(payload.getIconColor().get(), "#012345");
    }

    @Test
    public void testPriority() throws Exception {
        String json =
                "{" +
                        "\"priority\": 1" +
                "}";

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertEquals(payload.getPriority().get().intValue(), 1);
    }

    @Test
    public void testCategory() throws Exception {
        String json =
                "{" +
                        "\"category\": \"promo\"" +
                "}";

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertEquals(payload.getCategory().get().getCategory(), Category.PROMO.getCategory());
    }

    @Test
    public void testVisibility() throws Exception {
        String json =
                "{" +
                        "\"visibility\": 1" +
                "}";

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        assertEquals(payload.getVisibility().get().intValue(), 1);

    }

    @Test
    public void testPublicNotification() throws Exception {
        String json =
                "{" +
                        "\"public_notification\":{" +
                            "\"summary\": \"A summary\"," +
                            "\"title\": \"A title\"," +
                            "\"alert\": \"An alert\"" +
                        "}" +
                "}";

        AndroidDevicePayload payload = mapper.readValue(json, AndroidDevicePayload.class);
        assertNotNull(payload);
        PublicNotification publicNotification = payload.getPublicNotification().get();
        assertEquals(publicNotification.getSummary().get(), "A summary");
        assertEquals(publicNotification.getTitle().get(), "A title");
        assertEquals(publicNotification.getAlert().get(), "An alert");
    }

    @Test
    public void testTemplate() throws Exception {
        AndroidFields fields = AndroidFields.newBuilder()
                .setTitle("Shoe sale on {{level}} floor!")
                .setAlert("All the shoes are on sale {{name}}!")
                .setSummary("Don't miss out!")
                .setIcon("shoes")
                .setIconColor("{{iconColor}}")
                .build();

        AndroidTemplate template = AndroidTemplate.newBuilder()
                .setFields(fields)
                .build();

        AndroidTemplate templateWithId = AndroidTemplate.newBuilder()
                .setTemplateId("608f1f6c-8860-c617-a803-b187b491568e")
                .build();

        Map<String, String> entries = new HashMap<>();
        entries.put("url", "http://example.com");
        entries.put("story_id", "1234");

        AndroidDevicePayload androidDevicePayload = AndroidDevicePayload.newBuilder()
                .setTemplate(template)
                .setNotificationChannel("promos")
                .addAllExtraEntries(entries)
                .build();

        AndroidDevicePayload androidDevicePayloadWithId = AndroidDevicePayload.newBuilder()
                .setTemplate(templateWithId)
                .setNotificationChannel("promos")
                .addAllExtraEntries(entries)
                .build();


        String actualJsonStr = mapper.writeValueAsString(androidDevicePayload);

        AndroidDevicePayload androidDevicePayloadRoundTrip = mapper.readValue(actualJsonStr, AndroidDevicePayload.class);

        assertEquals(androidDevicePayload, androidDevicePayloadRoundTrip);

        String json = "{\n" +
                "  \"notification_channel\": \"promos\",\n" +
                "  \"extra\": {\n" +
                "    \"story_id\": \"1234\",\n" +
                "    \"url\": \"http://example.com\"\n" +
                "  },\n" +
                "  \"template\": {\n" +
                "    \"fields\": {\n" +
                "      \"alert\": \"All the shoes are on sale {{name}}!\",\n" +
                "      \"icon\": \"shoes\",\n" +
                "      \"icon_color\": \"{{iconColor}}\",\n" +
                "      \"summary\": \"Don't miss out!\",\n" +
                "      \"title\": \"Shoe sale on {{level}} floor!\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        JsonNode expectedJson = mapper.readTree(json);
        JsonNode actualJson = mapper.readTree(actualJsonStr);

        assertEquals(expectedJson, actualJson);

        String jsonWithId = "{\n" +
                "  \"notification_channel\": \"promos\",\n" +
                "  \"extra\": {\n" +
                "    \"story_id\": \"1234\",\n" +
                "    \"url\": \"http://example.com\"\n" +
                "  },\n" +
                "  \"template\": {\n" +
                "    \"template_id\": \"608f1f6c-8860-c617-a803-b187b491568e\"\n" +
                "  }\n" +
                "}";

        String jsonWithIdActual = mapper.writeValueAsString(androidDevicePayloadWithId);

        AndroidDevicePayload roundTripPayloadWithId = mapper.readValue(jsonWithIdActual, AndroidDevicePayload.class);

        JsonNode expectedJsonWithTemplateId = mapper.readTree(jsonWithId);
        JsonNode actualJsonWithTemplateId = mapper.readTree(jsonWithIdActual);

        assertEquals(expectedJsonWithTemplateId, actualJsonWithTemplateId);
        assertEquals(androidDevicePayloadWithId, roundTripPayloadWithId);
    }
}
