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

package com.dopsun.msg4j.core.messages;

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
