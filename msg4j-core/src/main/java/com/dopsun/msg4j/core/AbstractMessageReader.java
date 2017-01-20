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

package com.dopsun.msg4j.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Base class for {@link MessageReader}.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
abstract class AbstractMessageReader implements MessageReader {
    private final Map<String, Field> fields;

    protected AbstractMessageReader(Map<String, Field> fields) {
        Objects.requireNonNull(fields);

        this.fields = fields;
    }

    /**
     * This is used internal only.
     * 
     * @return fields for this message
     */
    final Map<String, Field> fields() {
        return fields;
    }

    @Override
    public int size() {
        return fields.size();
    }

    @Override
    public boolean contains(String fieldName) {
        Objects.requireNonNull(fieldName);

        return fields.containsKey(fieldName);
    }

    @Override
    public FieldType getFieldType(String fieldName) throws FieldNotFoundException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        return field.type();
    }

    @Override
    public byte getByte(String fieldName) throws FieldNotFoundException, InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        if (field instanceof Field.ByteField) {
            return ((Field.ByteField) field).value();
        }

        throw new InvalidTypeException(fieldName, FieldType.Byte, field.type());
    }

    @Override
    public char getChar(String fieldName) throws FieldNotFoundException, InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        if (field instanceof Field.CharField) {
            return ((Field.CharField) field).value();
        }

        throw new InvalidTypeException(fieldName, FieldType.Char, field.type());
    }

    @Override
    public short getShort(String fieldName) throws FieldNotFoundException, InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        if (field instanceof Field.ShortField) {
            return ((Field.ShortField) field).value();
        }

        throw new InvalidTypeException(fieldName, FieldType.Short, field.type());
    }

    @Override
    public int getInt(String fieldName) throws FieldNotFoundException, InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        if (field instanceof Field.IntField) {
            return ((Field.IntField) field).value();
        }

        throw new InvalidTypeException(fieldName, FieldType.Int, field.type());
    }

    @Override
    public long getLong(String fieldName) throws FieldNotFoundException, InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        if (field instanceof Field.LongField) {
            return ((Field.LongField) field).value();
        }

        throw new InvalidTypeException(fieldName, FieldType.Long, field.type());
    }

    @Override
    public float getFloat(String fieldName) throws FieldNotFoundException, InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        if (field instanceof Field.FloatField) {
            return ((Field.FloatField) field).value();
        }

        throw new InvalidTypeException(fieldName, FieldType.Float, field.type());
    }

    @Override
    public double getDouble(String fieldName) throws FieldNotFoundException, InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        if (field instanceof Field.DoubleField) {
            return ((Field.DoubleField) field).value();
        }

        throw new InvalidTypeException(fieldName, FieldType.Double, field.type());
    }

    @Override
    public String getString(String fieldName) throws FieldNotFoundException, InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        if (field instanceof Field.StringField) {
            return ((Field.StringField) field).value();
        }

        throw new InvalidTypeException(fieldName, FieldType.String, field.type());
    }

    @Override
    public ImmutableMessage getMessage(String fieldName)
            throws FieldNotFoundException, InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        if (field instanceof Field.MessageField) {
            return ((Field.MessageField) field).value();
        }

        throw new InvalidTypeException(fieldName, FieldType.Message, field.type());
    }

    @Override
    public List<ImmutableMessage> getMessageList(String fieldName)
            throws FieldNotFoundException, InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields.get(fieldName);
        if (field == null) {
            throw new FieldNotFoundException(fieldName);
        }

        if (field instanceof Field.MessageListField) {
            return ((Field.MessageListField) field).value();
        }

        throw new InvalidTypeException(fieldName, FieldType.MessageList, field.type());
    }
}
