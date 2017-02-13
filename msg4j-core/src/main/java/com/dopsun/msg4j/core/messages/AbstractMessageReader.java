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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    private void toString(StringBuilder stringBuilder) {
        stringBuilder.append("{");

        boolean first = true;
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append(", ");
            }

            String name = entry.getKey();
            Field field = entry.getValue();

            stringBuilder.append(name);
            stringBuilder.append(".");
            stringBuilder.append(field.type().name());
            stringBuilder.append("=");

            switch (entry.getValue().type()) {
            case BYTE:
                stringBuilder.append(((Field.ByteField) field).value());
                break;
            case CHAR:
                stringBuilder.append(((Field.CharField) field).value());
                break;
            case SHORT:
                stringBuilder.append(((Field.ShortField) field).value());
                break;
            case INT:
                stringBuilder.append(((Field.IntField) field).value());
                break;
            case LONG:
                stringBuilder.append(((Field.LongField) field).value());
                break;
            case FLOAT:
                stringBuilder.append(((Field.FloatField) field).value());
                break;
            case DOUBLE:
                stringBuilder.append(((Field.DoubleField) field).value());
                break;
            case STRING:
                stringBuilder.append(((Field.StringField) field).value());
                break;
            case MESSAGE:
                ImmutableMessage msg = ((Field.MessageField) field).value();
                if (msg instanceof AbstractMessageReader) {
                    ((AbstractMessageReader) msg).toString(stringBuilder);
                } else {
                    stringBuilder.append(((Field.MessageField) field).value());
                }
                break;
            case MESSAGE_LIST:
                stringBuilder.append(((Field.MessageListField) field).value());
                break;
            default:
                break;
            }
        }

        stringBuilder.append("}");
    }

    @Override
    public void accept(MessageVisitor visitor) {
        Objects.requireNonNull(visitor);

        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            Field field = entry.getValue();

            switch (field.type()) {
            case BYTE:
                visitor.visit(entry.getKey(), field.type(), ((Field.ByteField) field).value());
                break;
            case CHAR:
                visitor.visit(entry.getKey(), field.type(), ((Field.CharField) field).value());
                break;
            case SHORT:
                visitor.visit(entry.getKey(), field.type(), ((Field.ShortField) field).value());
                break;
            case INT:
                visitor.visit(entry.getKey(), field.type(), ((Field.IntField) field).value());
                break;
            case LONG:
                visitor.visit(entry.getKey(), field.type(), ((Field.LongField) field).value());
                break;
            case FLOAT:
                visitor.visit(entry.getKey(), field.type(), ((Field.FloatField) field).value());
                break;
            case DOUBLE:
                visitor.visit(entry.getKey(), field.type(), ((Field.DoubleField) field).value());
                break;
            case STRING:
                visitor.visit(entry.getKey(), field.type(), ((Field.StringField) field).value());
                break;
            case MESSAGE:
                visitor.visit(entry.getKey(), field.type(), ((Field.MessageField) field).value());
                break;
            case MESSAGE_LIST:
                visitor.visit(entry.getKey(), field.type(),
                        ((Field.MessageListField) field).value());
                break;
            default:
                break;
            }
        }
    }

    @Override
    public Collection<String> fieldNames() {
        return fields.keySet();
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

        throw new InvalidTypeException(fieldName, FieldType.BYTE, field.type());
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

        throw new InvalidTypeException(fieldName, FieldType.CHAR, field.type());
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

        throw new InvalidTypeException(fieldName, FieldType.SHORT, field.type());
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

        throw new InvalidTypeException(fieldName, FieldType.INT, field.type());
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

        throw new InvalidTypeException(fieldName, FieldType.LONG, field.type());
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

        throw new InvalidTypeException(fieldName, FieldType.FLOAT, field.type());
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

        throw new InvalidTypeException(fieldName, FieldType.DOUBLE, field.type());
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

        throw new InvalidTypeException(fieldName, FieldType.STRING, field.type());
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

        throw new InvalidTypeException(fieldName, FieldType.MESSAGE, field.type());
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

        throw new InvalidTypeException(fieldName, FieldType.MESSAGE_LIST, field.type());
    }
}
