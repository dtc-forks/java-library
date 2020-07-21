package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.customevents.model.CustomEventPropValue;

import java.io.IOException;
import java.math.BigDecimal;

public class CustomEventPropValueSerializer extends JsonSerializer<CustomEventPropValue> {

    @Override
     public void serialize(CustomEventPropValue customEventPropValue, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        if (customEventPropValue.isArray()) {
            jgen.writeObject(customEventPropValue.getAsList());
        }
        if (customEventPropValue.isObject()) {
            jgen.writeObject((customEventPropValue.getAsMap()));
        }
        if (customEventPropValue.isBoolean()) {
            jgen.writeObject((customEventPropValue.getAsBoolean()));
        }
        if (customEventPropValue.isNumber()) {
            jgen.writeNumber((BigDecimal) customEventPropValue.getAsNumber());
        }
        if (customEventPropValue.isString()) {
            jgen.writeString((customEventPropValue.getAsString()));
        }
    }

}

