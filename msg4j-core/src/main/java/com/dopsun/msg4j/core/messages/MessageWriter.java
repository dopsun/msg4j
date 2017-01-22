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
 * Writer for message.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public interface MessageWriter {
    /**
     * Removes field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return <code>true</code> if field removed successfully; otherwise <code>false</code>.
     */
    boolean remove(String fieldName);

    /**
     * Puts value to field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @param value
     *            value to put
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    void putByte(String fieldName, byte value) throws InvalidTypeException;

    /**
     * Puts value to field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @param value
     *            value to put
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    void putChar(String fieldName, char value) throws InvalidTypeException;

    /**
     * Puts value to field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @param value
     *            value to put
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    void putShort(String fieldName, short value) throws InvalidTypeException;

    /**
     * Puts value to field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @param value
     *            value to put
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    void putInt(String fieldName, int value) throws InvalidTypeException;

    /**
     * Puts value to field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @param value
     *            value to put
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    void putLong(String fieldName, long value) throws InvalidTypeException;

    /**
     * Puts value to field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @param value
     *            value to put
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    void putFloat(String fieldName, float value) throws InvalidTypeException;

    /**
     * Puts value to field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @param value
     *            value to put
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    void putDouble(String fieldName, double value);

    /**
     * Puts value to field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @param value
     *            value to put
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    void putString(String fieldName, String value) throws InvalidTypeException;

    /**
     * Puts value to field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @param value
     *            value to put
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    void putMessage(String fieldName, ImmutableMessage value) throws InvalidTypeException;

    /**
     * Puts value to field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @param value
     *            value to put
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    void putMessageList(String fieldName, List<ImmutableMessage> value) throws InvalidTypeException;
}
