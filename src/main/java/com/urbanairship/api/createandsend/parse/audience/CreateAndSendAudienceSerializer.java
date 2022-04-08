package com.urbanairship.api.createandsend.parse.audience;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.createandsend.model.audience.CreateAndSendAudience;
import com.urbanairship.api.createandsend.model.audience.email.EmailChannel;
import com.urbanairship.api.createandsend.model.audience.sms.SmsChannel;

import java.io.IOException;

public class CreateAndSendAudienceSerializer extends JsonSerializer<CreateAndSendAudience> {

    @Override
    public void serialize(CreateAndSendAudience payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();
        jgen.writeArrayFieldStart("create_and_send");

        if (payload.getEmailChannels().isPresent()) {
            for (EmailChannel channel : payload.getEmailChannels().get().getEmailChannels()) {
                jgen.writeStartObject();
                jgen.writeStringField("ua_address",
                        channel.getUaAddress());

                if (channel.getCommercialOptedIn().isPresent()) {
                    jgen.writeStringField("ua_commercial_opted_in", DateFormats.DATE_FORMATTER.print(channel.getCommercialOptedIn().get()));
                }
                if (channel.getTransactionalOptedIn().isPresent()) {
                    jgen.writeStringField("ua_transactional_opted_in", DateFormats.DATE_FORMATTER.print(channel.getTransactionalOptedIn().get()));
                }
                if (channel.getSubstitutions().isPresent()) {
                    for (String key : channel.getSubstitutions().get().keySet()) {
                        jgen.writeStringField(key, channel.getSubstitutions().get().get(key));
                    }
                }
                if (channel.getPersonalizationVariables().isPresent()) {
                    for (String key : channel.getPersonalizationVariables().get().keySet()) {
                        jgen.writePOJOField(key, channel.getPersonalizationVariables().get().get(key));
                    }
                }
                jgen.writeEndObject();
            }
        }

        if (payload.getSmsChannels().isPresent()) {
            for (SmsChannel smsChannel : payload.getSmsChannels().get().getSmsChannels()) {
                jgen.writeStartObject();
                jgen.writeStringField("ua_msisdn", smsChannel.getMsisdn());
                jgen.writeStringField("ua_sender", smsChannel.getSender());
                jgen.writeStringField("ua_opted_in", DateFormats.DATE_FORMATTER.print(smsChannel.getOptedIn()));

                if (smsChannel.getSubstitutions().isPresent()) {
                    for (String key : smsChannel.getSubstitutions().get().keySet()) {
                        jgen.writeStringField(key, smsChannel.getSubstitutions().get().get(key));
                    }
                }
                if (smsChannel.getPersonalizationVariables().isPresent()) {
                    for (String key : smsChannel.getPersonalizationVariables().get().keySet()) {
                        jgen.writePOJOField(key, smsChannel.getPersonalizationVariables().get().get(key));
                    }
                }
                jgen.writeEndObject();
            }
        }

        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}
