/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core.messages.schema;

import java.util.List;
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
public final class MessageListFieldInfo extends FieldInfo {
    /**
     * @param name
     */
    public MessageListFieldInfo(String name) {
        super(name);
    }

    @Override
    public final FieldType type() {
        return FieldType.MessageList;
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#getMessageList(String)
     */
    public List<ImmutableMessage> get(MessageReader message) {
        Objects.requireNonNull(message);

        return message.getMessageList(name());
    }

    /**
     * @param message
     * @param defaultValue
     * @return
     * 
     * @see MessageReader#tryGetMessageList(String)
     */
    public List<ImmutableMessage> tryGet(MessageReader message,
            List<ImmutableMessage> defaultValue) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(defaultValue);

        return message.tryGetMessageList(name(), defaultValue);
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#tryGetMessageList(String)
     */
    public Optional<List<ImmutableMessage>> tryGet(MessageReader message) {
        Objects.requireNonNull(message);

        return message.tryGetMessageList(name());
    }

    /**
     * @param message
     * @param value
     * 
     * @see MessageWriter#putMessageList(String, List)
     */
    public void put(MessageWriter message, List<ImmutableMessage> value) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(value);

        message.putMessageList(name(), value);
    }
}
