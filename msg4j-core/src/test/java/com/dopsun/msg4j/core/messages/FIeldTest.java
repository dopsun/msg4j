/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core.messages;

import org.junit.Test;

import com.dopsun.msg4j.core.messages.Field;
import com.dopsun.msg4j.core.messages.FieldType;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
public class FIeldTest {
    @Test
    public void givenFieldTypeThenEveryItemHasItsOwnFieldDefinition()
            throws ClassNotFoundException {
        for (FieldType type : FieldType.values()) {
            Class.forName(Field.class.getName() + "$" + type.name() + "Field");
        }
    }
}
