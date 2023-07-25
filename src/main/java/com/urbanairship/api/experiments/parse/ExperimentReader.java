/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.Variant;
import com.urbanairship.api.push.model.Campaigns;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.Orchestration;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.notification.email.MessageType;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ExperimentReader implements JsonObjectReader<Experiment> {

    private final Experiment.Builder builder;

    public ExperimentReader() {
        this.builder = Experiment.newBuilder();
    }

    public void readName(JsonParser jsonParser) throws IOException {
        builder.setName(jsonParser.readValueAs(String.class));
    }

    public void readDescription(JsonParser jsonParser) throws IOException {
        builder.setDescription(jsonParser.readValueAs(String.class));
    }

    public void readControl(JsonParser jsonParser) throws IOException {
        builder.setControl(jsonParser.readValueAs(BigDecimal.class));
    }

    public void readAudience(JsonParser jsonParser) throws IOException {
        builder.setAudience(jsonParser.readValueAs(Selector.class));
    }

    public void readDeviceTypes(JsonParser parser) throws IOException {
        builder.setDeviceTypes(parser.readValueAs(DeviceTypeData.class));
    }

    public void readVariants(JsonParser jsonParser) throws IOException {
        builder.addAllVariants(jsonParser.readValueAs(new TypeReference<List<Variant>>() { }));
    }

    public void readOrchestration(JsonParser parser) throws IOException {
        builder.setOrchestration(parser.readValueAs(Orchestration.class));
    }

    public void readMessageType(JsonParser parser) throws IOException {
        builder.setMessageType(MessageType.find(StringFieldDeserializer.INSTANCE.deserialize(parser, "message_type")).get());
    }

    public void readCampaigns(JsonParser parser) throws IOException {
        builder.setCampaigns(parser.readValueAs(Campaigns.class));
    }

    @Override
    public Experiment validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }


}
