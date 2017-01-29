/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core.messages.schema;

import java.util.Objects;
import java.util.OptionalInt;

import com.dopsun.msg4j.core.messages.FieldType;
import com.dopsun.msg4j.core.messages.MessageReader;
import com.dopsun.msg4j.core.messages.MessageWriter;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public final class IntFieldInfo extends FieldInfo {
    /**
     * @param name
     */
    public IntFieldInfo(String name) {
        super(name);
    }

    @Override
    public final FieldType type() {
        return FieldType.INT;
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#getInt(String)
     */
    public int get(MessageReader message) {
        Objects.requireNonNull(message);

        return message.getInt(name());
    }

    /**
     * @param message
     * @param defaultValue
     * @return
     * 
     * @see MessageReader#tryGetInt(String, int)
     */
    public int tryGet(MessageReader message, int defaultValue) {
        Objects.requireNonNull(message);

        return message.tryGetInt(name(), defaultValue);
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#tryGetInt(String)
     */
    public OptionalInt tryGet(MessageReader message) {
        Objects.requireNonNull(message);

        return message.tryGetInt(name());
    }

    /**
     * @param message
     * @param value
     * 
     * @see MessageWriter#putInt(String, int)
     */
    public void put(MessageWriter message, int value) {
        Objects.requireNonNull(message);

        message.putInt(name(), value);
    }
}
