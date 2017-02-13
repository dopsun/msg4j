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
import java.util.OptionalDouble;

import com.dopsun.msg4j.core.messages.FieldType;
import com.dopsun.msg4j.core.messages.MessageReader;
import com.dopsun.msg4j.core.messages.MessageWriter;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public final class DoubleFieldInfo extends FieldInfo {
    /**
     * @param name
     */
    public DoubleFieldInfo(String name) {
        super(name);
    }

    @Override
    public final FieldType type() {
        return FieldType.DOUBLE;
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#getDouble(String)
     */
    public double get(MessageReader message) {
        Objects.requireNonNull(message);

        return message.getDouble(name());
    }

    /**
     * @param message
     * @param defaultValue
     * @return
     * 
     * @see MessageReader#tryGetDouble(String, double)
     */
    public double tryGet(MessageReader message, double defaultValue) {
        Objects.requireNonNull(message);

        return message.tryGetDouble(name(), defaultValue);
    }

    /**
     * @param message
     * @return
     * 
     * @see MessageReader#tryGetDouble(String)
     */
    public OptionalDouble tryGet(MessageReader message) {
        Objects.requireNonNull(message);

        return message.tryGetDouble(name());
    }

    /**
     * @param message
     * @param value
     * 
     * @see MessageWriter#putDouble(String, double)
     */
    public void put(MessageWriter message, double value) {
        Objects.requireNonNull(message);

        message.putDouble(name(), value);
    }
}
