/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        return FieldType.MESSAGE;
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
