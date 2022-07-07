/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;


import java.util.Optional;

/**
 * Provides details on processing errors that are returned by the Urban Airship
 * API. These are available in some error cases, and can assist in debugging.
 */
public final class RequestErrorDetails {

    private final static String ERROR_FORMAT = "\nAPIErrorDetails: \nPath:%s\nError:%s\nOptional Location:%s";

    private final String path;
    private final String error;
    private final Optional<Location> location;


    private RequestErrorDetails(String path, String error, Optional<Location> location) {
        this.path = path;
        this.error = error;
        this.location = location;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Simple string describing the details of this error.
     *
     * @return The error
     */
    public String getError() {
        return error;
    }

    /**
     * Location of the error in the error object.
     *
     * @return Path to error.
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns the location object that may be present for this error.
     *
     * @return Location
     */
    public Optional<Location> getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        RequestErrorDetails that = (RequestErrorDetails) o;
        return (that.path.equals(this.path)
                && that.error.equals(this.error)
                && that.location.equals(this.location));
    }

    @Override
    public int hashCode() {
        return String.format("path:%s:error:%s:location:%s",
                path, error, location).hashCode();
    }

    @Override
    public String toString() {
        return String.format(ERROR_FORMAT, path, error, location);
    }

    /**
     * APIErrorDetails Builder
     */
    public static class Builder {

        private String path;
        private String error;
        private Location location;

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public RequestErrorDetails build() {
            return new RequestErrorDetails(path, error,
                    Optional.ofNullable(location));
        }
    }

    /**
     * Location of an error. This is the line and column where the error
     * occurred. Useful for debugging.
     */
    public static class Location {

        private final Number line;
        private final Number column;

        private Location(Number line, Number column) {
            this.line = line;
            this.column = column;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Number getLine() {
            return line;
        }

        public Number getColumn() {
            return column;
        }

        @Override
        public int hashCode() {
            return String.format("line:%s:column:%s", line, column).hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }

            Location that = (Location) o;
            return (this.line.equals(that.line) && this.column.equals(that.column));
        }

        @Override
        public String toString() {
            return String.format("Location:\nLine:%s\nColumn:%s", line, column);
        }

        /**
         * Location Builder
         */
        public static class Builder {
            private Number line;
            private Number column;

            private Builder() {
            }

            public Builder setLine(Number line) {
                this.line = line;
                return this;
            }

            public Builder setColumn(Number column) {
                this.column = column;
                return this;
            }

            public Location build() {
                return new Location(line, column);
            }
        }

    }
}
