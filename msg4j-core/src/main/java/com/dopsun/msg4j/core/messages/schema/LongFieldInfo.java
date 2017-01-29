/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core.messages.schema;

import java.util.Objects;
import java.util.OptionalLong;

import com.dopsun.msg4j.core.messages.FieldType;
import com.dopsun.msg4j.core.messages.MessageReader;
import com.dopsun.msg4j.core.messages.MessageWriter;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public final class LongFieldInfo extends FieldInfo {
    /**
     * @param name
     */
    public LongFieldInfo(String name) {
        super(name);
    }

    @Override
    public final FieldType type() {
        return FieldType.LONG;
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#getLong(String)
     */
    public long get(MessageReader message) {
        Objects.requireNonNull(message);

        return message.getLong(name());
    }

    /**
     * @param message
     * @param defaultValue
     * @return
     * 
     * @see MessageReader#tryGetLong(String, long)
     */
    public long tryGet(MessageReader message, long defaultValue) {
        Objects.requireNonNull(message);

        return message.tryGetLong(name(), defaultValue);
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#tryGetLong(String)
     */
    public OptionalLong tryGet(MessageReader message) {
        Objects.requireNonNull(message);

        return message.tryGetLong(name());
    }

    /**
     * @param message
     * @param value
     * 
     * @see MessageWriter#putLong(String, long)
     */
    public void put(MessageWriter message, long value) {
        Objects.requireNonNull(message);

        message.putLong(name(), value);
    }
}
