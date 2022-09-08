/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class APIParsingException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger("com.urbanairship.api");

    private final Optional<String> path;
    private final Optional<JsonLocation> location;

    public APIParsingException(String message, String path, JsonLocation location) {
        super(message);
        this.path = Optional.ofNullable(path);
        this.location = Optional.ofNullable(location);
    }

    public APIParsingException(String message) {
        super(message);
        this.path = Optional.empty();
        this.location = Optional.empty();
    }

    public APIParsingException(String message, Throwable cause) {
        super(message, cause);
        this.path = Optional.empty();
        this.location = Optional.empty();
    }

    public APIParsingException(Throwable cause) {
        super(cause);
        this.path = Optional.empty();
        this.location = Optional.empty();
    }

    public Optional<String> getPath() {
        return path;
    }

    public Optional<JsonLocation> getLocation() {
        return location;
    }

    public static APIParsingException raise(String msg, JsonParser parser) throws APIParsingException {
        if (log.isDebugEnabled()) {
            try {
                log.debug(String.format("%s; at line %d, col %d, '%s'",
                    msg,
                    parser.getCurrentLocation().getLineNr(),
                    parser.getCurrentLocation().getColumnNr(),
                    getPath(parser)));
            } catch (Exception ex) {
                log.debug("Exception while formatting exception.", ex);
            }
        }
        throw new APIParsingException(msg, getPath(parser), parser.getCurrentLocation());
    }

    private static String getPath(JsonParser parser) {
        StringBuffer sb = new StringBuffer();
        JsonStreamContext context = parser.getParsingContext();
        doGetPath(context, sb);
        return sb.toString();
    }

    private static void doGetPath(JsonStreamContext context, StringBuffer sb) {
        if (!context.inRoot()) {
            doGetPath(context.getParent(), sb);
            if (context.inObject()) {
                String name = context.getCurrentName();
                if (name != null) {
                    if (sb.length() > 0) {
                        sb.append('.');
                    }
                    sb.append(name);
                }
            } else if (context.inArray()) {
                sb.append('[')
                    .append(context.getCurrentIndex())
                    .append(']');
            }
        }
    }
}
