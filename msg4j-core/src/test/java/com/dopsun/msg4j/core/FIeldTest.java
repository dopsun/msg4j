/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core;

import org.junit.Test;

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
