/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core;

import java.util.List;

/**
 * Visitor to message.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public interface MessageVisitor {
    /**
     * @param fieldName
     * @param fieldType
     * @param value
     */
    void visit(String fieldName, FieldType fieldType, byte value);

    /**
     * @param fieldName
     * @param fieldType
     * @param value
     */
    void visit(String fieldName, FieldType fieldType, char value);

    /**
     * @param fieldName
     * @param fieldType
     * @param value
     */
    void visit(String fieldName, FieldType fieldType, short value);

    /**
     * @param fieldName
     * @param fieldType
     * @param value
     */
    void visit(String fieldName, FieldType fieldType, int value);

    /**
     * @param fieldName
     * @param fieldType
     * @param value
     */
    void visit(String fieldName, FieldType fieldType, long value);

    /**
     * @param fieldName
     * @param fieldType
     * @param value
     */
    void visit(String fieldName, FieldType fieldType, float value);

    /**
     * @param fieldName
     * @param fieldType
     * @param value
     */
    void visit(String fieldName, FieldType fieldType, double value);

    /**
     * @param fieldName
     * @param fieldType
     * @param value
     */
    void visit(String fieldName, FieldType fieldType, String value);

    /**
     * @param fieldName
     * @param fieldType
     * @param value
     */
    void visit(String fieldName, FieldType fieldType, ImmutableMessage value);

    /**
     * @param fieldName
     * @param fieldType
     * @param value
     */
    void visit(String fieldName, FieldType fieldType, List<ImmutableMessage> value);
}
