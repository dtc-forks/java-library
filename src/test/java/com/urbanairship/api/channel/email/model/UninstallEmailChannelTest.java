package com.urbanairship.api.channel.email.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.email.UninstallEmailChannel;
import com.urbanairship.api.channel.model.email.UninstallEmailChannelRequest;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UninstallEmailChannelTest {

    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testUninstallEmailRequest() throws IOException {

        UninstallEmailChannel uninstallEmailChannel = UninstallEmailChannel.newBuilder()
                .setEmailAddress("name@example.com")
                .build();

        UninstallEmailChannelRequest.newRequest(uninstallEmailChannel);

        String jsonFromString = "\n" +
                "{\n" +
                "    \"email_address\": \"name@example.com\"\n" +
                "}";

        JsonNode actual = MAPPER.readTree(uninstallEmailChannel.toJSON());

        JsonNode expected = null;
        try {
            expected = MAPPER.readTree(jsonFromString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(expected, actual);
    }
}