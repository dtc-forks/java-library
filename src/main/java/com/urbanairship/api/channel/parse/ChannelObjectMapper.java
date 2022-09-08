/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.urbanairship.api.channel.model.ChannelAttributesResponse;
import com.urbanairship.api.channel.model.ChannelAudience;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.model.ChannelUninstallDevice;
import com.urbanairship.api.channel.model.ChannelUninstallResponse;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.channel.model.OpenChannelResponse;
import com.urbanairship.api.channel.model.SmsRegistrationResponse;
import com.urbanairship.api.channel.model.attributes.Attribute;
import com.urbanairship.api.channel.model.attributes.ChannelAttributesPayload;
import com.urbanairship.api.channel.model.attributes.audience.AttributeAudience;
import com.urbanairship.api.channel.model.email.EmailChannelResponse;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.channel.model.ios.IosSettings;
import com.urbanairship.api.channel.model.ios.QuietTime;
import com.urbanairship.api.channel.model.open.OpenChannel;
import com.urbanairship.api.channel.model.sms.UpdateSmsChannel;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionListPayload;
import com.urbanairship.api.channel.model.subscriptionlist.SubscriptionListResponse;
import com.urbanairship.api.channel.model.web.Subscription;
import com.urbanairship.api.channel.model.web.WebSettings;
import com.urbanairship.api.channel.parse.attributes.AttributeSerializer;
import com.urbanairship.api.channel.parse.attributes.ChannelAttributesPayloadSerializer;
import com.urbanairship.api.channel.parse.attributes.ChannelAttributesResponseDeserializer;
import com.urbanairship.api.channel.parse.attributes.audience.AttributeAudienceSerializer;
import com.urbanairship.api.channel.parse.email.RegisterEmailChannelResponseDeserializer;
import com.urbanairship.api.channel.parse.email.RegisterEmailChannelSerializer;
import com.urbanairship.api.channel.parse.ios.IosSettingsDeserializer;
import com.urbanairship.api.channel.parse.ios.QuietTimeDeserializer;
import com.urbanairship.api.channel.parse.open.OpenChannelDeserializer;
import com.urbanairship.api.channel.parse.sms.UpdateSmsChannelSerializer;
import com.urbanairship.api.channel.parse.subscriptionlist.SubscriptionListPayloadSerializer;
import com.urbanairship.api.channel.parse.subscriptionlist.SubscriptionListResponseDeserializer;
import com.urbanairship.api.channel.parse.web.SubscriptionDeserializer;
import com.urbanairship.api.channel.parse.web.WebSettingsDeserializer;
import com.urbanairship.api.createandsend.model.audience.email.EmailChannel;
import com.urbanairship.api.createandsend.parse.CreateAndSendEmailChannelSerializer;
import com.urbanairship.api.push.parse.PushObjectMapper;

public class ChannelObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Channels API Module");

    static {
        MODULE.addDeserializer(IosSettings.class, new IosSettingsDeserializer());
        MODULE.addDeserializer(QuietTime.class, new QuietTimeDeserializer());
        MODULE.addDeserializer(ChannelView.class, new ChannelViewDeserializer());
        MODULE.addDeserializer(ChannelResponse.class, new ChannelsResponseDeserializer());
        MODULE.addDeserializer(Subscription.class, new SubscriptionDeserializer());
        MODULE.addDeserializer(WebSettings.class, new WebSettingsDeserializer());
        MODULE.addDeserializer(OpenChannel.class, new OpenChannelDeserializer());
        MODULE.addDeserializer(OpenChannelResponse.class, new OpenChannelResponseDeserializer());
        MODULE.addDeserializer(EmailChannelResponse.class, new RegisterEmailChannelResponseDeserializer());
        MODULE.addSerializer(RegisterEmailChannel.class, new RegisterEmailChannelSerializer());
        MODULE.addDeserializer(SmsRegistrationResponse.class, new SmsRegistrationResponseDeserializer());
        MODULE.addSerializer(EmailChannel.class, new CreateAndSendEmailChannelSerializer());
        MODULE.addSerializer(Attribute.class, new AttributeSerializer());
        MODULE.addSerializer(ChannelAttributesPayload.class, new ChannelAttributesPayloadSerializer());
        MODULE.addDeserializer(ChannelAttributesResponse.class, new ChannelAttributesResponseDeserializer());
        MODULE.addSerializer(AttributeAudience.class, new AttributeAudienceSerializer());
        MODULE.addSerializer(ChannelUninstallDevice.class, new ChannelUninstallDeviceSerializer());
        MODULE.addDeserializer(ChannelUninstallResponse.class, new ChannelUninstallResponseDeserializer());
        MODULE.addSerializer(SubscriptionListPayload.class, new SubscriptionListPayloadSerializer());
        MODULE.addDeserializer(SubscriptionListResponse.class, new SubscriptionListResponseDeserializer());
        MODULE.addSerializer(ChannelAudience.class, new ChannelAudienceSerializer());
        MODULE.addSerializer(UpdateSmsChannel.class, new UpdateSmsChannelSerializer());


        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(new JodaModule());
        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
}

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private ChannelObjectMapper() { }
}