/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core.messages.schema;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.concurrent.Immutable;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
@Immutable
public final class MessageInfo {
    /**
     * @param messageType
     * @return
     */
    public static Builder builder(String messageType) {
        Objects.requireNonNull(messageType);

        return new Builder(messageType);
    }

    private final String messageType;

    MessageInfo(Builder builder) {
        Objects.requireNonNull(builder);

        this.messageType = Objects.requireNonNull(builder.messageType);
    }

    /**
     * @return type of the message.
     */
    public String messageType() {
        return messageType;
    }

    /**
     * @author Dop Sun
     * @since 1.0.0
     */
    public static class Builder {
        private final String messageType;

        private Map<String, FieldInfo> fields = new HashMap<>();

        Builder(String messageType) {
            Objects.requireNonNull(messageType);

            this.messageType = messageType;
        }

        /**
         * @return
         */
        public MessageInfo build() {
            return new MessageInfo(this);
        }

        /**
         * @param fieldInfo
         * @return
         */
        public Builder addField(FieldInfo fieldInfo) {
            Objects.requireNonNull(fieldInfo);
            fields.put(fieldInfo.name(), fieldInfo);

            return this;
        }

        /**
         * @return the messageType
         */
        public String getMessageType() {
            return messageType;
        }
    }
}
