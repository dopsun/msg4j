/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core.messages.schema;

import java.util.Objects;
import java.util.Optional;

import com.dopsun.msg4j.core.messages.FieldType;
import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.MessageReader;
import com.dopsun.msg4j.core.messages.MessageWriter;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public final class MessageFieldInfo extends FieldInfo {
    /**
     * @param name
     */
    public MessageFieldInfo(String name) {
        super(name);
    }

    @Override
    public final FieldType type() {
        return FieldType.Message;
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#getMessage(String)
     */
    public ImmutableMessage get(MessageReader message) {
        Objects.requireNonNull(message);

        return message.getMessage(name());
    }

    /**
     * @param message
     * @param defaultValue
     * @return
     * 
     * @see MessageReader#tryGetMessage(String, ImmutableMessage)
     */
    public ImmutableMessage tryGet(MessageReader message, ImmutableMessage defaultValue) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(defaultValue);

        return message.tryGetMessage(name(), defaultValue);
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#tryGetMessage(String)
     */
    public Optional<ImmutableMessage> tryGet(MessageReader message) {
        Objects.requireNonNull(message);

        return message.tryGetMessage(name());
    }

    /**
     * @param message
     * @param value
     * 
     * @see MessageWriter#putMessage(String, ImmutableMessage)
     */
    public void put(MessageWriter message, ImmutableMessage value) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(value);

        message.putMessage(name(), value);
    }
}
