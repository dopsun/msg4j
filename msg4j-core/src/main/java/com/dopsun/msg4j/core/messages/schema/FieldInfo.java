/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
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
