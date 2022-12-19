package com.urbanairship.api.createandsend.parse.notification;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.CreateAndSendPayload;

import java.io.IOException;

public class CreateAndSendPayloadSerializer extends JsonSerializer<CreateAndSendPayload> {
    @Override
    public void serialize(CreateAndSendPayload payload, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", payload.getAudience());
        jgen.writeObjectField("device_types", payload.getNotification().getDeviceTypePayloadOverrides().keySet().toArray());
        jgen.writeObjectField("notification", payload.getNotification());

        if (payload.getCampaigns().isPresent()) {
            jgen.writeObjectField("campaigns", payload.getCampaigns().get());
        }

        if (!payload.getGlobalAttributes().get().isEmpty()) {
            jgen.writeObjectField("global_attributes", payload.getGlobalAttributes().get());
        }

        jgen.writeEndObject();
    }
}
