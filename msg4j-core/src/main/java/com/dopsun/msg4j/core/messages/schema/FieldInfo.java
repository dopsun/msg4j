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

import javax.annotation.concurrent.Immutable;

import com.dopsun.msg4j.core.messages.FieldType;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
@Immutable
public abstract class FieldInfo {
    private final String name;

    /**
     * @param name
     */
    protected FieldInfo(String name) {
        Objects.requireNonNull(name);

        this.name = name;
    }

    /**
     * @return name of this field.
     */
    public final String name() {
        return this.name;
    }

    /**
     * @return type of this field.
     */
    public abstract FieldType type();
}
