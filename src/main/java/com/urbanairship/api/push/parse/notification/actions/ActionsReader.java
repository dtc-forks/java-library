
/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.BaseEncoding;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.actions.Action;
import com.urbanairship.api.push.model.notification.actions.ActionNameRegistry;
import com.urbanairship.api.push.model.notification.actions.ActionType;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AddTagAction;
import com.urbanairship.api.push.model.notification.actions.AppDefinedAction;
import com.urbanairship.api.push.model.notification.actions.DeepLinkAction;
import com.urbanairship.api.push.model.notification.actions.LandingPageContent;
import com.urbanairship.api.push.model.notification.actions.OpenExternalURLAction;
import com.urbanairship.api.push.model.notification.actions.OpenLandingPageWithContentAction;
import com.urbanairship.api.push.model.notification.actions.RemoveTagAction;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import com.urbanairship.api.push.model.notification.actions.TagActionData;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

public final class ActionsReader implements JsonObjectReader<Actions> {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    private interface OpenActionReader {
        Action.OpenAction readOpen(JsonParser parser, JsonNode definition) throws IOException;
    }

    private final static Map<String, OpenActionReader> OPEN_ACTIONS = ImmutableMap.<String, OpenActionReader>builder()
            .put("url", new OpenActionReader() {
                @Override
                public Action.OpenAction readOpen(JsonParser parser, JsonNode definition) throws IOException {
                    return getExternalURLData(definition);
                }
            })
            .put("landing_page", new OpenActionReader() {
                @Override
                public Action.OpenAction readOpen(JsonParser parser, JsonNode definition) {
                    return getLandingPageData(definition);
                }

            })
            .put("deep_link", new OpenActionReader() {
                @Override
                public Action.OpenAction readOpen(JsonParser parser, JsonNode definition) throws IOException {
                    JsonNode content = definition.path("content");
                    if (content.isMissingNode() || !content.isTextual()) {
                        throw new APIParsingException("The content attribute must be present and it must be a string.");
                    }

                    return new DeepLinkAction(content.textValue());
                }
            })
            .build();
    private Actions.Builder builder = new Actions.Builder();


    private static JsonNode getContentEncoding(JsonNode content) {
        JsonNode c1 = content.path("content-encoding");
        JsonNode c2 = content.path("content_encoding");
        if(! c1.isMissingNode() && ! c2.isMissingNode()) {
            throw new APIParsingException("A request cannot specify both content-encoding and content_encoding.");
        }

        return c1.isMissingNode() ? c2 : c1;
    }

    private static JsonNode getContentType(JsonNode content) {
        JsonNode c1 = content.path("content-type");
        JsonNode c2 = content.path("content_type");
        if(! c1.isMissingNode() && ! c2.isMissingNode()) {
            throw new APIParsingException("A request cannot specify both content-type and content_type.");
        }

        return c1.isMissingNode() ? c2 : c1;
    }

    private static Action.OpenAction getLandingPageData(JsonNode definition) {
        JsonNode content = definition.path("content");
        if (content.isMissingNode()) {
            throw new APIParsingException("The content attribute must be present.");
        }

        JsonNode body = content.path("body");
        JsonNode contentType = getContentType(content);
        JsonNode contentEncoding = getContentEncoding(content);

        if (body.isMissingNode() || !body.isTextual()) {
            throw new APIParsingException("The content object must have a body attribute, and it must be a string value.");
        }

        if (contentType.isMissingNode() || !contentType.isTextual()) {
            throw new APIParsingException("The content object must have a content type attribute, and it must be a string value..");
        }

        {
            String typeSubtype = contentType.textValue().split(";")[0].trim().toLowerCase();
            if (!LandingPageContent.ALLOWED_CONTENT_TYPES.contains(typeSubtype)) {
                throw new APIParsingException("The content type '" + typeSubtype + "' is not allowed.");
            }
        }

        if (!contentEncoding.isMissingNode()) {
            if (!contentEncoding.isTextual() || (!contentEncoding.textValue().equals("utf-8") &&
                    !contentEncoding.textValue().equals("base64"))) {
                throw new APIParsingException("The content encoding attribute must be either 'utf-8' or 'base64'");

            } else if (!contentEncoding.isMissingNode() && contentEncoding.textValue().equals("base64")) {
                try {
                    BaseEncoding.base64().decode(body.textValue());
                } catch (IllegalArgumentException e) {
                    throw new APIParsingException("Content contains invalid data that is not valid for base64 encoding.");
                }
            }
        }

        Optional<LandingPageContent.Encoding> optEncoding = contentEncoding.isMissingNode() ?
                Optional.<LandingPageContent.Encoding>empty() :
                Optional.of(contentEncoding.textValue().equals("base64") ?
                        LandingPageContent.Encoding.Base64 :
                        LandingPageContent.Encoding.UTF8);

        String bodyString = body.textValue();
        if (bodyString != null) {
            int max_size = optEncoding.isPresent() && optEncoding.get() == LandingPageContent.Encoding.Base64 ?
                    LandingPageContent.MAX_BODY_SIZE_BASE64 : LandingPageContent.MAX_BODY_SIZE_BYTES;
            if (bodyString.length() > max_size) {
                throw new APIParsingException("Maximum upload size exceeded.");
            }
        }

        return new OpenLandingPageWithContentAction(LandingPageContent.newBuilder().setContentType(contentType.textValue())
                .setBody(bodyString)
                .setEncoding(optEncoding)
                .build());
    }

    private static Action.OpenAction getExternalURLData(JsonNode def) {
        JsonNode content = def.path("content");
        if (content.isMissingNode() || !content.isTextual()) {
            throw new APIParsingException("The content attribute for an url action must be present and it must be a string.");
        }

        URI url;
        try {
            url = new URI(content.textValue());
        } catch (URISyntaxException e) {
            throw new APIParsingException("The content attribute for a url action must be a URL.");
        }

        if (!url.isAbsolute() || (!url.getScheme().equals("http") && !url.getScheme().equals("https"))) {
            throw new APIParsingException("The url for a url action must use either 'http' or 'https'");
        }

        return new OpenExternalURLAction(url);
    }

    private TagActionData getTagActionData(JsonParser parser, ActionType tagActionType) throws IOException {
        TagActionData data = MAPPER.readValue(parser, TagActionData.class);
        if (data == null) {
            throw new APIParsingException("The value for '" + ActionNameRegistry.INSTANCE.getFieldName(tagActionType) +
                    "' cannot be 'null' or the empty string.");
        }

        return data;
    }

    public void readAddTags(JsonParser parser) throws IOException {
        builder.addTags(new AddTagAction(getTagActionData(parser, ActionType.ADD_TAG)));
    }

    public void readAppDefined(JsonParser parser) throws IOException {
        JsonNode jsonNode;
        try {
            jsonNode = parser.readValueAsTree();
        } catch (JsonParseException ex) {
            throw new APIParsingException("Invalid JSON found while parsing app_defined");
        }

        if (!jsonNode.isObject()) {
            throw new APIParsingException("The value for app_defined actions MUST be an object.");
        }

        ObjectNode appDefinedObject = (ObjectNode) jsonNode;
        if (!appDefinedObject.fieldNames().hasNext()) {
            throw new APIParsingException("The app_defined actions object MUST not be empty.");
        }

        builder.addAppDefined(new AppDefinedAction(appDefinedObject));
    }

    public void readOpen(JsonParser parser) throws IOException {
        JsonNode definition = parser.readValueAsTree();
        JsonNode type = definition.path("type");

        if (type.isMissingNode() || !type.isTextual()) {
            throw new APIParsingException("The open object MUST have a 'type' attribute.");
        }

        OpenActionReader reader = OPEN_ACTIONS.get(type.textValue());
        if (reader == null) {
            throw new APIParsingException("The type attribute '" + type.textValue() +
                    "' was not recognized.");
        }

        Action.OpenAction action = reader.readOpen(parser, definition);
        if (action == null) {
            throw new APIParsingException("The open action cannot be null.");
        }

        builder.setOpen(action);
    }

    public void readRemoveTags(JsonParser parser) throws IOException {
        builder.removeTags(new RemoveTagAction(getTagActionData(parser, ActionType.REMOVE_TAG)));
    }

    public void readShare(JsonParser parser) throws IOException {
        ShareAction action = parser.readValueAs(ShareAction.class);
        if (StringUtils.isEmpty(action.getValue())) {
            throw new APIParsingException("The share text may not be an empty string.");
        }
        builder.setShare(action);
    }

    @Override
    public Actions validateAndBuild() throws IOException {
        return builder.build();
    }
}
