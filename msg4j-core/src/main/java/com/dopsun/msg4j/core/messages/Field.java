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
import java.util.Objects;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
abstract class Field {
    public abstract FieldType type();

    public static class ByteField extends Field {
        private final byte value;

        public ByteField(byte value) {
            this.value = value;
        }

        @Override
        public FieldType type() {
            return FieldType.BYTE;
        }

        public byte value() {
            return value;
        }
    }

    public static class CharField extends Field {
        private final char value;

        public CharField(char value) {
            this.value = value;
        }

        @Override
        public FieldType type() {
            return FieldType.CHAR;
        }

        public char value() {
            return value;
        }
    }

    public static class ShortField extends Field {
        private final short value;

        public ShortField(short value) {
            this.value = value;
        }

        @Override
        public FieldType type() {
            return FieldType.SHORT;
        }

        public short value() {
            return value;
        }
    }

    public static class IntField extends Field {
        private final int value;

        public IntField(int value) {
            this.value = value;
        }

        @Override
        public FieldType type() {
            return FieldType.INT;
        }

        public int value() {
            return value;
        }
    }

    public static class LongField extends Field {
        private final long value;

        public LongField(long value) {
            this.value = value;
        }

        @Override
        public FieldType type() {
            return FieldType.LONG;
        }

        public long value() {
            return value;
        }
    }

    public static class FloatField extends Field {
        private final float value;

        public FloatField(float value) {
            this.value = value;
        }

        @Override
        public FieldType type() {
            return FieldType.FLOAT;
        }

        public float value() {
            return value;
        }
    }

    public static class DoubleField extends Field {
        private final double value;

        public DoubleField(double value) {
            this.value = value;
        }

        @Override
        public FieldType type() {
            return FieldType.DOUBLE;
        }

        public double value() {
            return value;
        }
    }

    public static class StringField extends Field {
        private final String value;

        public StringField(String value) {
            Objects.requireNonNull(value);

            this.value = value;
        }

        @Override
        public FieldType type() {
            return FieldType.STRING;
        }

        public String value() {
            return value;
        }
    }

    public static class MessageField extends Field {
        private final ImmutableMessage value;

        public MessageField(ImmutableMessage value) {
            Objects.requireNonNull(value);

            this.value = value;
        }

        @Override
        public FieldType type() {
            return FieldType.MESSAGE;
        }

        public ImmutableMessage value() {
            return value;
        }
    }

    public static class MessageListField extends Field {
        private final List<ImmutableMessage> value;

        public MessageListField(List<ImmutableMessage> value) {
            Objects.requireNonNull(value);

            this.value = value;
        }

        @Override
        public FieldType type() {
            return FieldType.MESSAGE_LIST;
        }

        public List<ImmutableMessage> value() {
            return value;
        }
    }

}
