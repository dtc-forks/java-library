package com.urbanairship.api.channel.model.email;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the payload to be used for registering or updating an email channel.
 */
public class RegisterEmailChannel extends PushModelObject {
    private final ChannelType type;
    private final Optional<Map<OptInLevel, String>> emailOptInLevel;
    private final String address;
    private final Optional<Boolean> setTags;
    private final Optional<ImmutableList<String>> tags;
    private final Optional<String> timezone;
    private final Optional<String> localeCountry;
    private final Optional<String> localeLanguage;
    private final Optional<OptInMode> emailOptInMode;
    private final Optional<Map<String, String>> properties;

    //Protected to facilitate subclassing for create and send child object
    protected RegisterEmailChannel(Builder builder) {
        this.type = ChannelType.EMAIL;
        this.emailOptInLevel = Optional.ofNullable((builder.emailOptInLevel));
        this.address = builder.address;
        this.setTags = Optional.ofNullable(builder.set_tags);
        this.timezone = Optional.ofNullable(builder.timezone);
        this.localeCountry = Optional.ofNullable(builder.localeCountry);
        this.localeLanguage = Optional.ofNullable(builder.localeLanguage);
        this.emailOptInMode = Optional.ofNullable((builder.emailOptInMode));

        if (builder.tags.build().isEmpty()) {
            this.tags = Optional.empty();
        } else {
            this.tags = Optional.of(builder.tags.build());
        }

        if (!builder.properties.build().isEmpty()) {
            this.properties = Optional.ofNullable(builder.properties.build());
        } else {
            properties = Optional.empty();
        }
    }

    /**
     * Get the RegisterEmailChannelType.
     *
     * @return ChannelType type
     */
    public ChannelType getType() {
        return ChannelType.EMAIL;
    }

    /**
     * Get the channel email opt in level.
     *
     * @return Optional OptInLevel emailOptInLevel
     */
    public Optional<Map<OptInLevel, String>> getEmailOptInLevel() {
        return emailOptInLevel;
    }

    /**
     * Get the email channel's address,
     *
     * @return String address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get a String representation of the timezone.
     *
     * @return Optional String timezone
     */
    public Optional<String> getTimezone() {
        return timezone;
    }

    /**
     * Get a String representation of the locale country.
     *
     * @return Optional String localeCountry
     */
    public Optional<String> getLocaleCountry() {
        return localeCountry;
    }

    /**
     * Get a String representation of the locale language.
     *
     * @return Optional String localeLanguage
     */
    public Optional<String> getLocaleLanguage() {
        return localeLanguage;
    }

    /**
     * Get the channel email opt in mode.
     *
     * @return Optional OptInMode emailOptInMode
     */
    public Optional<OptInMode> getEmailOptInMode() {
        return emailOptInMode;
    }

    /**
     * Get properties for the channel email.
     * 
     * @return Optional Map of Strings
     */
    public Optional<Map<String, String>> getProperties() {
        return properties;
    }

    /**
     * New RegisterEmailChannel builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterEmailChannel that = (RegisterEmailChannel) o;
        return type == that.type &&
                Objects.equal(address, that.address) &&
                Objects.equal(setTags, that.setTags) &&
                Objects.equal(tags, that.tags) &&
                Objects.equal(timezone, that.timezone) &&
                Objects.equal(localeCountry, that.localeCountry) &&
                Objects.equal(localeLanguage, that.localeLanguage) &&
                Objects.equal(emailOptInLevel, that.emailOptInLevel)&&
                Objects.equal(emailOptInMode, that.emailOptInMode)&&
                Objects.equal(properties, that.properties);
    }

    @Override
    public String toString() {
        return "RegisterEmailChannel{" +
                "type=" + type +
                ", emailOptInLevel=" + emailOptInLevel +
                ", address=" + address +
                ", setTags=" + setTags +
                ", tags=" + tags +
                ", timezone=" + timezone +
                ", localeCountry=" + localeCountry +
                ", localeLanguage=" + localeLanguage +
                ", emailOptInMode=" + emailOptInMode +
                ", properties=" + properties +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, emailOptInLevel, address, setTags, tags, timezone, localeCountry, localeLanguage, emailOptInMode, properties);
    }

    /**
     * Create RegisterEmailChannel Builder
     */
    public final static class Builder {
        private String address;
        private String timezone;
        private ImmutableList.Builder<String> tags = ImmutableList.builder();
        private boolean set_tags;
        private String localeCountry;
        private String localeLanguage;
        private Map<OptInLevel, String> emailOptInLevel = new HashMap<>();
        private OptInMode emailOptInMode;
        private ImmutableMap.Builder<String, String> properties = ImmutableMap.builder();

        protected Builder() {
        }

        /**
         * Set the EmailOptInLevel status and time.
         *
         * @param level time OptInLevel, String
         * @param time String
         * @return RegisterEmailChannel Builder
         */
        public Builder setEmailOptInLevel(OptInLevel level, String time) {
            this.emailOptInLevel.put(level, time);
            return this;
        }

        /**
         * Set the channel's address, a Unique identifier of the object
         * used as the primary ID in the delivery tier (Email).
         *
         * @param address String
         * @return RegisterEmailChannel Builder
         */
        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        /**
         * Optional, though required if tags is present.
         * If true on update, value of tags overwrites any existing tags.
         * If false, tags are unioned with existing tags.
         *
         * @param setTags boolean
         * @return Channel Builder
         */
        public Builder setTags(boolean setTags) {
            this.set_tags = setTags;
            return this;
        }

        /**
         * Add a List of String representations of tags.
         *
         * @param tags A List of Strings
         * @return Channel Builder
         */
        public Builder addAllTags(List<String> tags) {
            this.tags.addAll(tags);
            return this;
        }

        /**
         * Set a String representation of a tag.
         *
         * @param tag String
         * @return Channel Builder
         */
        public Builder addTag(String tag) {
            tags.add(tag);
            return this;
        }

        /**
         * Set timezone string. An IANA tzdata identifier for the timezone
         * as a string, e.g., "America/Los Angeles". Will set the timezone
         * tag group tag with the specified value.
         *
         * @param timezone String
         * @return RegisterEmailChannel Builder
         */
        public Builder setTimeZone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        /**
         * Set a the localeCountry The two-letter country locale shortcode.
         * Will set the ua_locale_country tag group to the specified value.
         * Used _ notation to conform with previously written code
         *
         * @param locale_country String
         * @return RegisterEmailChannel Builder
         */
        public Builder setLocaleCountry(String locale_country) {
            this.localeCountry = locale_country;
            return this;
        }

        /**
         * Set a String localeLanguage, the two-letter language locale
         * shortcode. Will set the ua_locale_language tag group to the
         * specified value
         * Used _ notation to conform with previously written code
         *
         * @param locale_language String
         * @return RegisterEmailChannel Builder
         */
        public Builder setLocaleLanguage(String locale_language) {
            this.localeLanguage = locale_language;
            return this;
        }

        /**
         * Set the channel's address, a Unique identifier of the object
         * used as the primary ID in the delivery tier (Email).
         *
         * @param emailOptInMode OptInMode
         * @return RegisterEmailChannel Builder
         */
        public Builder setEmailOptInMode(OptInMode emailOptInMode) {
            this.emailOptInMode = emailOptInMode;
            return this;
        }

        /**
         * Add a property.
         * 
         * @param key String
         * @param value String
         * @return EmailChannel Builder
         */
        public Builder addProperty(String key, String value) {
            properties.put(key, value);
            return this;
        }

        /**
         * Add all properties values.
         * 
         * @param properties Map of Strings.
         * @return EmailChannel Builder
         */
        public Builder addAllProperties(Map<String, String> properties) {
            this.properties.putAll(properties);
            return this;
        }

        public RegisterEmailChannel build() {
            Preconditions.checkNotNull(address, "address cannot be null.");
            Preconditions.checkNotNull(emailOptInLevel, "'emailOptInLevel' cannot be null.");

            return new RegisterEmailChannel(this);
        }
    }
}
