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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Writable message implementation.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
final class WritableMessageImpl extends AbstractMessage implements WritableMessage {
    WritableMessageImpl() {
        super(new HashMap<>());
    }

    /**
     * @param fields
     */
    WritableMessageImpl(Map<String, Field> fields) {
        super(fields);
    }

    @Override
    public boolean remove(String fieldName) {
        Objects.requireNonNull(fieldName);

        return fields().remove(fieldName) != null;
    }

    @Override
    public void putBoolean(String fieldName, boolean value) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.ByteField)) {
            throw new InvalidTypeException(fieldName, FieldType.BYTE, field.type());
        }

        fields().put(fieldName, new Field.BooleanField(value));
    }

    @Override
    public void putByte(String fieldName, byte value) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.ByteField)) {
            throw new InvalidTypeException(fieldName, FieldType.BYTE, field.type());
        }

        fields().put(fieldName, new Field.ByteField(value));
    }

    @Override
    public void putChar(String fieldName, char value) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.CharField)) {
            throw new InvalidTypeException(fieldName, FieldType.CHAR, field.type());
        }

        fields().put(fieldName, new Field.CharField(value));
    }

    @Override
    public void putShort(String fieldName, short value) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.ShortField)) {
            throw new InvalidTypeException(fieldName, FieldType.SHORT, field.type());
        }

        fields().put(fieldName, new Field.ShortField(value));
    }

    @Override
    public void putInt(String fieldName, int value) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.IntField)) {
            throw new InvalidTypeException(fieldName, FieldType.INT, field.type());
        }

        fields().put(fieldName, new Field.IntField(value));
    }

    @Override
    public void putLong(String fieldName, long value) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.LongField)) {
            throw new InvalidTypeException(fieldName, FieldType.LONG, field.type());
        }

        fields().put(fieldName, new Field.LongField(value));
    }

    @Override
    public void putFloat(String fieldName, float value) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.FloatField)) {
            throw new InvalidTypeException(fieldName, FieldType.FLOAT, field.type());
        }

        fields().put(fieldName, new Field.FloatField(value));
    }

    @Override
    public void putDouble(String fieldName, double value) {
        Objects.requireNonNull(fieldName);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.DoubleField)) {
            throw new InvalidTypeException(fieldName, FieldType.DOUBLE, field.type());
        }

        fields().put(fieldName, new Field.DoubleField(value));
    }

    @Override
    public void putString(String fieldName, String value) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(value);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.StringField)) {
            throw new InvalidTypeException(fieldName, FieldType.STRING, field.type());
        }

        fields().put(fieldName, new Field.StringField(value));
    }

    @Override
    public void putMessage(String fieldName, ImmutableMessage value) throws InvalidTypeException {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(value);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.MessageField)) {
            throw new InvalidTypeException(fieldName, FieldType.MESSAGE, field.type());
        }

        fields().put(fieldName, new Field.MessageField(value));
    }

    @Override
    public void putMessageList(String fieldName, List<ImmutableMessage> value)
            throws InvalidTypeException {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(value);

        Field field = fields().get(fieldName);
        if (field != null && !(field instanceof Field.MessageListField)) {
            throw new InvalidTypeException(fieldName, FieldType.MESSAGE_LIST, field.type());
        }

        fields().put(fieldName, new Field.MessageListField(ImmutableList.copyOf(value)));
    }

    @Override
    public ImmutableMessage toImmutable() {
        return new ImmutableMessageImpl(ImmutableMap.copyOf(fields()));
    }

}
