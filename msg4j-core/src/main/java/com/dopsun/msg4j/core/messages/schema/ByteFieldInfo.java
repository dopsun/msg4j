/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core.messages.schema;

import java.util.Objects;

import com.dopsun.msg4j.core.messages.FieldType;
import com.dopsun.msg4j.core.messages.MessageReader;
import com.dopsun.msg4j.core.messages.MessageWriter;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public final class ByteFieldInfo extends FieldInfo {
    /**
     * @param name
     */
    public ByteFieldInfo(String name) {
        super(name);
    }

    @Override
    public final FieldType type() {
        return FieldType.BYTE;
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#getByte(String)
     */
    public byte get(MessageReader message) {
        Objects.requireNonNull(message);

        return message.getByte(name());
    }

    /**
     * @param message
     * @param defaultValue
     * @return
     * 
     * @see MessageReader#tryGetByte(String, byte)
     */
    public byte tryGet(MessageReader message, byte defaultValue) {
        Objects.requireNonNull(message);

        return message.tryGetByte(name(), defaultValue);
    }

    /**
     * @param message
     * @param value
     * 
     * @see MessageWriter#putByte(String, byte)
     */
    public void put(MessageWriter message, byte value) {
        Objects.requireNonNull(message);

        message.putByte(name(), value);
    }
}
