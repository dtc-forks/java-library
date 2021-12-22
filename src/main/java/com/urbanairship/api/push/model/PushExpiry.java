/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import org.joda.time.DateTime;


/**
 * Optional expiry feature for a Push payload for the Urban Airship API.
 **/

public class PushExpiry extends PushModelObject {

    private final Optional<Integer> expirySeconds;
    private final Optional<DateTime> expiryTimeStamp;
    private final Optional<String> expiryPersonalization;

    private PushExpiry(Optional<Integer> expirySeconds,
                       Optional<DateTime> expiryTimeStamp,
                       Optional<String> expiryPersonalization) {
        this.expirySeconds = expirySeconds;
        this.expiryTimeStamp = expiryTimeStamp;
        this.expiryPersonalization = expiryPersonalization;
    }

    /**
     * Get a PushExpiryPayloadBuilder
     * @return PushExpiryPayloadBuilder
     **/
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the expiry (TTL) as a long.  This is optional.
     * @return Optional&lt;ExpirySeconds&gt;
     */
    public Optional<Integer> getExpirySeconds() {
        return expirySeconds;
    }

    /**
     * Get the expiry (TTL) as a timestamp.  This is optional.
     * @return Optional&lt;ExpiryTimestamp&gt;
     */
    public Optional<DateTime> getExpiryTimeStamp() {
        return expiryTimeStamp;
    }

    /**
     * Get the expiry as a personalized field.  This is optional.
     * @return Optional&lt;expiryPersonalization&gt;
     */
    public Optional<String> getExpiryPersonalization() {
        return expiryPersonalization;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PushExpiry that = (PushExpiry) o;

        if (expirySeconds != null ? !expirySeconds.equals(that.expirySeconds) : that.expirySeconds != null) {
            return false;
        }

        return !(expiryTimeStamp != null ? !expiryTimeStamp.equals(that.expiryTimeStamp) : that.expiryTimeStamp != null);

    }

    @Override
    public int hashCode() {
        int result = (expirySeconds != null ? expirySeconds.hashCode() : 0);
        result = 31 * result + (expiryTimeStamp != null ? expiryTimeStamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PushOptionsPayload{" +
                "expirySeconds=" + expirySeconds +
                ", expiryTimeStamp=" + expiryTimeStamp +
                ", expiryPersonalization=" + expiryPersonalization +
                '}';
    }

    public static class Builder {
        private Integer expirySeconds = null;
        private DateTime expiryTimeStamp = null;
        private String expiryPersonalization = null;

        private Builder() { }

        /**
         * Set the expiry as a long
         * @param value Long
         * @return Long
         **/
        public Builder setExpirySeconds(int value) {
            this.expirySeconds = value;
            return this;
        }

        /**
         * Set the expiry as a timestamp
         * @param value DateTime
         * @return DateTime
         **/
        public Builder setExpiryTimeStamp(DateTime value) {
            this.expiryTimeStamp = value;
            return this;
        }

        /**
         * Set the expiry as a personalized field (only for templates)
         * @param value String
         * @return String
         **/
        public Builder setExpiryPersonalization(String value) {
            this.expiryPersonalization = value;
            return this;
        }        

        public PushExpiry build() {
            if (expiryTimeStamp == null && expirySeconds == null && expiryPersonalization == null) {
                throw new APIParsingException("Expiry time can not be null");
            }
            if (expiryTimeStamp != null && expirySeconds != null) {
                throw new APIParsingException("Expiry time may contain a relative offset or an absolute time, but not both");
            }
            if (expirySeconds != null && expirySeconds < 0) {
                throw new APIParsingException("Expiry time may not be negative");
            }
            if (expiryPersonalization != null && !expiryPersonalization.startsWith("{{") && !expiryPersonalization.endsWith("}}")) {
                throw new APIParsingException("Expiry string must be a personalized field");
            }
            return new PushExpiry(Optional.fromNullable(expirySeconds),
                                  Optional.fromNullable(expiryTimeStamp),
                                  Optional.fromNullable(expiryPersonalization));
        }
    }


}
