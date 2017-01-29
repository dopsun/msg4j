/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core.messages;

import org.junit.Test;

import com.google.common.base.CaseFormat;

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
            String upperCamelName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
                    type.name());
            Class.forName(Field.class.getName() + "$" + upperCamelName + "Field");
        }
    }
}
