/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;


import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.Optional;

/**
 * Represents a response from the Urban Airship API for Push Notifications.
 */
public final class PushResponse {

    private final Optional<String> operationId;
    private final Optional<ImmutableList<String>> pushIds;
    private final boolean ok;
    private final Optional<ImmutableList<String>> messageIds;
    private final Optional<ImmutableList<String>> contentUrls;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    public PushResponse(String operationId, ImmutableList<String> pushIds, boolean ok, ImmutableList<String> messageIds, ImmutableList<String> contentUrls, String error, ErrorDetails errorDetails) {
        this.operationId = Optional.ofNullable(operationId);
        this.pushIds = Optional.ofNullable(pushIds);
        this.ok = ok;
        this.messageIds = Optional.ofNullable(messageIds);
        this.contentUrls = Optional.ofNullable(contentUrls);
        this.error = Optional.ofNullable(error);
        this.errorDetails = Optional.ofNullable(errorDetails);
    
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the operation id for this response. This is used by Urban Airship
     * to track an operation through our system, and should be used when support
     * is needed.
     *
     * @return Operation id for this API request
     */
    public Optional<String> getOperationId() {
        return operationId;
    }

    /**
     * List of push id's, one for every actual push message that moves through
     * the API. This is useful for tracking an individual message as part of
     * an operation, and can be used when support is needed.
     *
     * @return List of push ids.
     */
    public Optional<ImmutableList<String>> getPushIds() {
        return pushIds;
    }

    /**
     * Get the response status as a boolean
     * @return Response status
     */
    public boolean getOk() {
        return ok;
    }

    public Optional<ImmutableList<String>> getMessageIds() {
        return messageIds;
    }

    public Optional<ImmutableList<String>> getContentUrls() {
        return contentUrls;
    }

    /**
     * Get the error if present
     *
     * @return An Optional String
     */
    public Optional<String> getError() {
        return error;
    }

    /**
     * Get the error details if present
     *
     * @return An Optional String
     */
    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "PushResponse{" +
                "operationId=" + operationId +
                ", pushIds=" + pushIds +
                ", ok=" + ok +
                ", messagesIds=" + messageIds +
                ", contentUrls=" + contentUrls +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId, pushIds, ok, messageIds, contentUrls, error, errorDetails);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PushResponse other = (PushResponse) obj;
        return Objects.equal(this.operationId, other.operationId) && Objects.equal(this.pushIds, other.pushIds) && Objects.equal(this.ok, other.ok) && Objects.equal(this.messageIds, other.messageIds) && Objects.equal(this.contentUrls, other.contentUrls) && Objects.equal(this.error, other.error) && Objects.equal(this.errorDetails, other.errorDetails);
    }

    /**
     * PushResponse Builder
     */
    public static class Builder {
        private String operationId;
        private ImmutableList.Builder<String> pushIds = ImmutableList.builder();
        private boolean ok = false;
        private ImmutableList.Builder<String> messageIds = ImmutableList.builder();
        private ImmutableList.Builder<String> contentUrls = ImmutableList.builder();
        private String error;
        private ErrorDetails errorDetails;

        private Builder() {
        }

        public Builder setOperationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        public Builder addPushId(String pushId) {
            this.pushIds.add(pushId);
            return this;
        }

        public Builder addAllPushIds(Iterable<? extends String> pushIds) {
            this.pushIds.addAll(pushIds);
            return this;
        }

        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        public Builder addMessageId(String messageId) {
            this.messageIds.add(messageId);
            return this;
        }

        public Builder addAllMessageIds(Iterable<? extends String> messageIds) {
            this.messageIds.addAll(messageIds);
            return this;
        }

        public Builder addContentUrl(String contentUrl) {
            this.contentUrls.add(contentUrl);
            return this;
        }

        public Builder addAllContentUrls(Iterable<? extends String> contentUrls) {
            this.contentUrls.addAll(contentUrls);
            return this;
        }

        public Builder setError(String error) {
            this.error = error;
            return this;
        }
    
        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        public PushResponse build() {
            return new PushResponse(operationId, pushIds.build(), ok, messageIds.build(), contentUrls.build(), error, errorDetails);

        }
    }
}
