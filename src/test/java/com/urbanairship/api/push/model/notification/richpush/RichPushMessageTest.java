package com.urbanairship.api.push.model.notification.richpush;

import com.urbanairship.api.push.model.PushExpiry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RichPushMessageTest {
    @Test
    public void testEquality1() {
        assertEquals(RichPushMessage.newBuilder()
                        .setTitle("T")
                        .setBody("B")
                        .setIcons(RichPushIcon.newBuilder().setListIcon("L").build())
                        .build(),
                RichPushMessage.newBuilder()
                        .setBody("B")
                        .setTitle("T")
                        .setIcons(RichPushIcon.newBuilder().setListIcon("L").build())
                        .build());
    }

    @Test
    public void testHash1() {
        assertEquals(RichPushMessage.newBuilder()
                        .setTitle("T")
                        .setBody("B")
                        .setIcons(RichPushIcon.newBuilder().setListIcon("L").build())
                        .build()
                        .hashCode(),
                RichPushMessage.newBuilder()
                        .setBody("B")
                        .setTitle("T")
                        .setIcons(RichPushIcon.newBuilder().setListIcon("L").build())
                        .build()
                        .hashCode());
    }

    @Test
    public void testDefaults() {
        RichPushMessage m = RichPushMessage.newBuilder()
                .setTitle("T").setBody("B").setIcons(RichPushIcon.newBuilder().setListIcon("L").build()).build();
        assertEquals("text/html", m.getContentType());
        assertEquals("utf8", m.getContentEncoding());
    }

    @Test
    public void testBuilder() {
        RichPushMessage m = RichPushMessage.newBuilder()
                .setTitle("T")
                .setBody("B")
                .setContentType("application/json")
                .setContentEncoding("base64")
                .setExpiry(PushExpiry.newBuilder().setExpirySeconds(3600).build())
                .addExtraEntry("this", "that")
                .setIcons(RichPushIcon.newBuilder().setListIcon("L").build())
                .setRichPushTemplate(RichPushTemplate.newBuilder().setTemplateId("876624ff-0120-4364-bf02-dba3d0cb5b85").build())
                .build();
        assertEquals("T", m.getTitle());
        assertEquals("B", m.getBody());
        assertEquals("application/json", m.getContentType());
        assertEquals("base64", m.getContentEncoding());
        assertTrue(m.getExtra().isPresent());
        assertEquals(1, m.getExtra().get().size());
        assertTrue(m.getExtra().get().containsKey("this"));
        assertEquals("that", m.getExtra().get().get("this"));
        assertTrue(m.getExpiry().get().getExpirySeconds().isPresent());
        assertEquals("L", m.getIcons().get().getListIcon());
        assertEquals("876624ff-0120-4364-bf02-dba3d0cb5b85", m.getRichPushTemplate().get().getTemplateId().get());

    }

    @Test(expected = Exception.class)
    public void testValidation1() {
        RichPushMessage.newBuilder()
                .build();
    }

    @Test(expected = Exception.class)
    public void testValidation2() {
        RichPushMessage.newBuilder()
                .setTitle("T")
                .build();
    }

    @Test(expected = Exception.class)
    public void testValidation3() {
        RichPushMessage.newBuilder()
                .setBody("B")
                .build();
    }
}
