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
import com.dopsun.msg4j.core.messages.MessageReader;
import com.dopsun.msg4j.core.messages.MessageWriter;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public final class StringFieldInfo extends FieldInfo {
    /**
     * @param name
     */
    public StringFieldInfo(String name) {
        super(name);
    }

    @Override
    public final FieldType type() {
        return FieldType.STRING;
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#getString(String)
     */
    public String get(MessageReader message) {
        Objects.requireNonNull(message);

        return message.getString(name());
    }

    /**
     * @param message
     * @param defaultValue
     * @return
     * 
     * @see MessageReader#tryGetString(String, String)
     */
    public String tryGet(MessageReader message, String defaultValue) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(defaultValue);

        return message.tryGetString(name(), defaultValue);
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#tryGetString(String)
     */
    public Optional<String> tryGet(MessageReader message) {
        Objects.requireNonNull(message);

        return message.tryGetString(name());
    }

    /**
     * @param message
     * @param value
     * 
     * @see MessageWriter#putString(String, String)
     */
    public void put(MessageWriter message, String value) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(value);

        message.putString(name(), value);
    }
}
