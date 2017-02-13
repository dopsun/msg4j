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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableMap;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
@Immutable
public final class DataDictionary {
    /**
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    private final Map<String, FieldInfo> fields;
    private final Map<String, MessageInfo> messages;

    DataDictionary(Builder builder) {
        Objects.requireNonNull(builder);

        this.fields = ImmutableMap.copyOf(builder.fields);
        this.messages = ImmutableMap.copyOf(builder.messages);
    }

    /**
     * @return the fields
     */
    public Map<String, FieldInfo> fields() {
        return fields;
    }

    /**
     * @return the messages
     */
    public Map<String, MessageInfo> messages() {
        return messages;
    }

    /**
     * @author Dop Sun
     * @since 1.0.0
     */
    public static class Builder {

        private final Map<String, FieldInfo> fields = new HashMap<>();
        private final Map<String, MessageInfo> messages = new HashMap<>();

        Builder() {
        }

        /**
         * @return
         */
        public DataDictionary build() {
            return new DataDictionary(this);
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
         * @param messageInfo
         * @return
         */
        public Builder addMessage(MessageInfo messageInfo) {
            Objects.requireNonNull(messageInfo);

            messages.put(messageInfo.messageType(), messageInfo);

            return this;
        }
    }
}
