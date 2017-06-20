package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.PartialPushPayload;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PartialPushPayloadDeserializerTest {

    private static final ObjectMapper MAPPER = ExperimentObjectMapper.getInstance();

    @Test
    public void testPartialPushPayload() throws Exception {
        String partialPushPayloadString =
                "{" +
                        "\"notification\": {\"alert\": \"hello everyone\"}," +
                        "\"in_app\": {" +
                        "\"alert\": \"This is in-app!\"" +
                        "}" +
                        "}";

        PartialPushPayload payload = MAPPER.readValue(partialPushPayloadString, PartialPushPayload.class);
        assertNotNull(payload);
        assertTrue(payload.getNotification().isPresent());
        assertTrue(payload.getInApp().isPresent());
        assertFalse(payload.getPushOptions().isPresent());
    }

    @Test(expected = Exception.class)
    public void testEmptyPartialPushPayload() {
        PartialPushPayload payload = PartialPushPayload.newBuilder().build();
    }

}
