/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.richpush;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.PushExpiry;

import java.util.Map;
import java.util.Optional;

public final class RichPushMessage {

    public static final String CONTENT_TYPE_DEFAULT = "text/html";
    public static final String CONTENT_ENCODING_DEFAULT = "utf8";

    private final String title;
    private final String body;
    private final String contentType;
    private final String contentEncoding;
    private final Optional<ImmutableMap<String, String>> extra;
    private final Optional<PushExpiry> expiry;
    private final Optional<RichPushIcon> icons;
    private final Optional<RichPushTemplate> richPushTemplate;

    private RichPushMessage(String title,
                            String body,
                            String contentType,
                            String contentEncoding,
                            Optional<ImmutableMap<String, String>> extra,
                            Optional<PushExpiry> expiry,
                            Optional<RichPushIcon> icons,
                            Optional<RichPushTemplate> richPushTemplate)
    {
        this.title = title;
        this.body = body;
        this.contentType = contentType;
        this.contentEncoding = contentEncoding;
        this.extra = extra;
        this.expiry = expiry;
        this.icons = icons;
        this.richPushTemplate = richPushTemplate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public Optional<ImmutableMap<String, String>> getExtra() {
        return extra;
    }

    public Optional<PushExpiry> getExpiry() {
        return expiry;
    }

    public Optional<RichPushIcon> getIcons() {
        return icons;
    }

    public Optional<RichPushTemplate> getRichPushTemplate() {
        return richPushTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RichPushMessage that = (RichPushMessage)o;
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        if (body != null ? !body.equals(that.body) : that.body != null) {
            return false;
        }
        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) {
            return false;
        }
        if (contentEncoding != null ? !contentEncoding.equals(that.contentEncoding) : that.contentEncoding != null) {
            return false;
        }
        if (extra != null ? !extra.equals(that.extra) : that.extra != null) {
            return false;
        }
        if (expiry != null ? !expiry.equals(that.expiry) : that.expiry != null) {
            return false;
        }
        if (icons != null ? !icons.equals(that.icons) : that.icons != null) {
            return false;
        }
        if (richPushTemplate != null ? !richPushTemplate.equals(that.richPushTemplate) : that.richPushTemplate != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (title != null ? title.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (contentEncoding != null ? contentEncoding.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        result = 31 * result + (expiry != null ? expiry.hashCode() : 0);
        result = 31 * result + (icons != null ? icons.hashCode() : 0);
        result = 31 * result + (richPushTemplate != null ? richPushTemplate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RichPushMessage{" +
                "title='" + title +
                "', body='" + body +
                "', contentType=" + contentType +
                ", contentEncoding=" + contentEncoding +
                ", extra=" + extra +
                ", expiry=" + expiry +
                ", icons=" + icons +
                ", richPushTemplate=" + richPushTemplate +
                '}';
    }

    public static final class Builder {
        private String title = null;
        private String body = null;
        private String contentType = null;
        private String contentEncoding = null;
        private ImmutableMap.Builder<String, String> extra = null;
        private PushExpiry expiry = null;
        private RichPushIcon icons = null;
        private RichPushTemplate richPushTemplate = null;

        private Builder() { }

        public Builder setTitle(String value) {
            this.title = value;
            return this;
        }

        public Builder setBody(String value) {
            this.body = value;
            return this;
        }

        public Builder setContentType(String value) {
            this.contentType = value;
            return this;
        }

        public Builder setContentEncoding(String value) {
            this.contentEncoding = value;
            return this;
        }

        public Builder addExtraEntry(String key, String value) {
            if (extra == null) {
                extra = ImmutableMap.builder();
            }
            this.extra.put(key, value);
            return this;
        }

        public Builder addAllExtraEntries(Map<String, String> entries) {
            if (extra == null) {
                extra = ImmutableMap.builder();
            }
            this.extra.putAll(entries);
            return this;
        }

        public Builder setExpiry (PushExpiry value) {
            this.expiry = value;
            return this;
        }

        public Builder setIcons (RichPushIcon value) {
            this.icons = value;
            return this;
        }

        public Builder setRichPushTemplate (RichPushTemplate value) {
            this.richPushTemplate = value;
            return this;
        }

        public RichPushMessage build() {
            Preconditions.checkNotNull(title, "Must supply a value for 'title'");
            Preconditions.checkNotNull(body, "Must supply a value for 'body'");
            return new RichPushMessage(title,
                    body,
                    contentType != null ? contentType : CONTENT_TYPE_DEFAULT,
                    contentEncoding != null ? contentEncoding : CONTENT_ENCODING_DEFAULT,
                    extra != null ? Optional.ofNullable(extra.build()) : Optional.<ImmutableMap<String, String>>empty(),
                    Optional.ofNullable(expiry),
                    Optional.ofNullable(icons),
                    Optional.ofNullable(richPushTemplate));
        }
    }
}
