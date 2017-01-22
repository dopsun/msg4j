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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * Reader for message.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public interface MessageReader {
    /**
     * @param visitor
     */
    void accept(MessageVisitor visitor);

    /**
     * @return number of field
     */
    int size();

    /**
     * @return all field names in this message.
     */
    Collection<String> fieldNames();

    /**
     * @param fieldName
     *            field name
     * @return <code>true</code> if field exists.
     */
    boolean contains(String fieldName);

    /**
     * @param fieldName
     *            field name
     * @return type of field
     * 
     * @throws FieldNotFoundException
     *             if field not found
     */
    FieldType getFieldType(String fieldName) throws FieldNotFoundException;

    /**
     * @param fieldName
     * @return
     */
    default Optional<FieldType> tryGetFieldType(String fieldName) {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return Optional.of(getFieldType(fieldName));
        }

        return Optional.empty();
    }

    /**
     * Gets value for field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return value for field
     * 
     * @throws FieldNotFoundException
     *             if field not found
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    byte getByte(String fieldName) throws FieldNotFoundException, InvalidTypeException;

    /**
     * @param fieldName
     *            field name
     * @param defaultValue
     *            value returned if field not exist
     * @return value of field if exist, otherwise <code>defaultValue</code>.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default byte tryGetByte(String fieldName, byte defaultValue) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return getByte(fieldName);
        }

        return defaultValue;
    }

    /**
     * Gets value for field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return value for field
     * 
     * @throws FieldNotFoundException
     *             if field not found
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    char getChar(String fieldName) throws FieldNotFoundException, InvalidTypeException;

    /**
     * @param fieldName
     *            field name
     * @param defaultValue
     *            value returned if field not exist
     * @return value of field if exist, otherwise <code>defaultValue</code>.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default char tryGetChar(String fieldName, char defaultValue) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return getChar(fieldName);
        }

        return defaultValue;
    }

    /**
     * Gets value for field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return value for field
     * 
     * @throws FieldNotFoundException
     *             if field not found
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    short getShort(String fieldName) throws FieldNotFoundException, InvalidTypeException;

    /**
     * @param fieldName
     *            field name
     * @param defaultValue
     *            value returned if field not exist
     * @return value of field if exist, otherwise <code>defaultValue</code>.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default short tryGetShort(String fieldName, short defaultValue) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return getShort(fieldName);
        }

        return defaultValue;
    }

    /**
     * Gets value for field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return value for field
     * 
     * @throws FieldNotFoundException
     *             if field not found
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    int getInt(String fieldName) throws FieldNotFoundException, InvalidTypeException;

    /**
     * @param fieldName
     *            field name
     * @param defaultValue
     *            value returned if field not exist
     * @return value of field if exist, otherwise <code>defaultValue</code>.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default int tryGetInt(String fieldName, int defaultValue) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return getInt(fieldName);
        }

        return defaultValue;
    }

    /**
     * Tries get the value if present.
     * 
     * @param fieldName
     *            field name
     * @return optional value if present; otherwise {@link OptionalInt#empty()}
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default OptionalInt tryGetInt(String fieldName) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return OptionalInt.of(getInt(fieldName));
        }

        return OptionalInt.empty();
    }

    /**
     * Gets value for field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return value for field
     * 
     * @throws FieldNotFoundException
     *             if field not found
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    long getLong(String fieldName) throws FieldNotFoundException, InvalidTypeException;

    /**
     * @param fieldName
     *            field name
     * @param defaultValue
     *            value returned if field not exist
     * @return value of field if exist, otherwise <code>defaultValue</code>.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default long tryGetLong(String fieldName, long defaultValue) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return getLong(fieldName);
        }

        return defaultValue;
    }

    /**
     * Tries get the value if present.
     * 
     * @param fieldName
     *            field name
     * @return optional value if present; otherwise {@link OptionalLong#empty()}
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default OptionalLong tryGetLong(String fieldName) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return OptionalLong.of(getLong(fieldName));
        }

        return OptionalLong.empty();
    }

    /**
     * Gets value for field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return value for field
     * 
     * @throws FieldNotFoundException
     *             if field not found.
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    float getFloat(String fieldName) throws FieldNotFoundException, InvalidTypeException;

    /**
     * @param fieldName
     *            field name
     * @param defaultValue
     *            value returned if field not exist
     * @return value of field if exist, otherwise <code>defaultValue</code>.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default float tryGetFloat(String fieldName, float defaultValue) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return getFloat(fieldName);
        }

        return defaultValue;
    }

    /**
     * Gets value for field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return value for field
     * @throws FieldNotFoundException
     *             if field not found.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    double getDouble(String fieldName) throws FieldNotFoundException, InvalidTypeException;

    /**
     * @param fieldName
     *            field name
     * @param defaultValue
     *            value returned if field not exist
     * @return value of field if exist, otherwise <code>defaultValue</code>.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default double tryGetDouble(String fieldName, double defaultValue) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return getDouble(fieldName);
        }

        return defaultValue;
    }

    /**
     * Tries get the value if present.
     * 
     * @param fieldName
     *            field name
     * @return optional value if present; otherwise {@link OptionalDouble#empty()}
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default OptionalDouble tryGetDouble(String fieldName) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return OptionalDouble.of(getDouble(fieldName));
        }

        return OptionalDouble.empty();
    }

    /**
     * Gets value for field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return value for field
     * @throws FieldNotFoundException
     *             if field not found.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    String getString(String fieldName) throws FieldNotFoundException, InvalidTypeException;

    /**
     * @param fieldName
     *            field name
     * @param defaultValue
     *            value returned if field not exist
     * @return value of field if exist, otherwise <code>defaultValue</code>.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default String tryGetString(String fieldName, String defaultValue) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(defaultValue);

        if (contains(fieldName)) {
            return getString(fieldName);
        }

        return defaultValue;
    }

    /**
     * Tries get the value if present.
     * 
     * @param fieldName
     *            field name
     * @return optional value if present; otherwise {@link Optional#empty()}
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default Optional<String> tryGetString(String fieldName) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return Optional.of(getString(fieldName));
        }

        return Optional.empty();
    }

    /**
     * Consumes field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return value for field
     * 
     * @throws FieldNotFoundException
     *             if field not found.
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    ImmutableMessage getMessage(String fieldName)
            throws FieldNotFoundException, InvalidTypeException;

    /**
     * @param fieldName
     *            field name
     * @param defaultValue
     *            value returned if field not exist
     * @return value of field if exist, otherwise <code>defaultValue</code>.
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default ImmutableMessage tryGetMessage(String fieldName, ImmutableMessage defaultValue)
            throws InvalidTypeException {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(defaultValue);

        if (contains(fieldName)) {
            return getMessage(fieldName);
        }

        return defaultValue;
    }

    /**
     * Tries get the value if present.
     * 
     * @param fieldName
     *            field name
     * @return optional value if present; otherwise {@link Optional#empty()}
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default Optional<ImmutableMessage> tryGetMessage(String fieldName) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return Optional.of(getMessage(fieldName));
        }

        return Optional.empty();
    }

    /**
     * Consumes field specified by <code>fieldName</code>.
     * 
     * @param fieldName
     *            field name
     * @return value for field
     * 
     * @throws FieldNotFoundException
     *             if field not found.
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    List<ImmutableMessage> getMessageList(String fieldName)
            throws FieldNotFoundException, InvalidTypeException;

    /**
     * Tries get the value if present.
     * 
     * @param fieldName
     *            field name
     * @return optional value if present; otherwise {@link Optional#empty()}
     * 
     * @throws InvalidTypeException
     *             if field exists but not expected type.
     */
    default Optional<List<ImmutableMessage>> tryGetMessageList(String fieldName)
            throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        if (contains(fieldName)) {
            return Optional.of(getMessageList(fieldName));
        }

        return Optional.empty();
    }
}
