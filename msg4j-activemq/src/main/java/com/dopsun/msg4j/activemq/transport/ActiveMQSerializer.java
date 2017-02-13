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

package com.dopsun.msg4j.activemq.transport;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.jms.JMSException;
import javax.jms.Session;

import com.dopsun.msg4j.core.messages.FieldType;
import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.MessageReader;
import com.dopsun.msg4j.core.messages.MessageVisitor;
import com.dopsun.msg4j.core.messages.Messages;
import com.dopsun.msg4j.core.messages.WritableMessage;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class ActiveMQSerializer {
    private final ConcurrentHashMap<String, FieldKey> fieldKeyByJmsKey = new ConcurrentHashMap<>();

    private final ImmutableMap<FieldType, ConcurrentHashMap<String, FieldKey>> fieldKeyByTypeName;

    /**
     * 
     */
    public ActiveMQSerializer() {
        ImmutableMap.Builder<FieldType, ConcurrentHashMap<String, FieldKey>> builder = ImmutableMap
                .builder();

        for (FieldType fieldType : EnumSet.allOf(FieldType.class)) {
            builder.put(fieldType, new ConcurrentHashMap<>());
        }

        this.fieldKeyByTypeName = builder.build();
    }

    /**
     * @param session
     * @param message
     * @return
     * @throws JMSException
     */
    public javax.jms.Message toJms(@Nonnull Session session, @Nonnull MessageReader message)
            throws JMSException {
        Objects.requireNonNull(session);
        Objects.requireNonNull(message);

        final javax.jms.MapMessage jmsMessage = session.createMapMessage();
        message.accept(new MessageVisitor() {
            @Override
            public void visit(String fieldName, FieldType fieldType, byte value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                try {
                    jmsMessage.setByte(fieldKey.jmsKeyName, value);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, char value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                try {
                    jmsMessage.setChar(fieldKey.jmsKeyName, value);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, short value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                try {
                    jmsMessage.setShort(fieldKey.jmsKeyName, value);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, int value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                try {
                    jmsMessage.setInt(fieldKey.jmsKeyName, value);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, long value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                try {
                    jmsMessage.setLong(fieldKey.jmsKeyName, value);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, float value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                try {
                    jmsMessage.setFloat(fieldKey.jmsKeyName, value);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, double value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                try {
                    jmsMessage.setDouble(fieldKey.jmsKeyName, value);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, String value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                try {
                    jmsMessage.setString(fieldKey.jmsKeyName, value);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, ImmutableMessage value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                try {
                    Map<String, Object> mapMessage = messageToMap(value);
                    jmsMessage.setObject(fieldKey.jmsKeyName, mapMessage);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, List<ImmutableMessage> value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                try {
                    List<Map<String, Object>> jmsMessageList = new ArrayList<>();
                    for (ImmutableMessage message : value) {
                        jmsMessageList.add(messageToMap(message));
                    }
                    jmsMessage.setObject(fieldKey.jmsKeyName, jmsMessageList);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return jmsMessage;
    }

    private Map<String, Object> messageToMap(MessageReader message) {
        final Map<String, Object> map = new HashMap<>();

        message.accept(new MessageVisitor() {
            @Override
            public void visit(String fieldName, FieldType fieldType, byte value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                map.put(fieldKey.jmsKeyName, value);
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, char value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                map.put(fieldKey.jmsKeyName, value);
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, short value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                map.put(fieldKey.jmsKeyName, value);
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, int value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                map.put(fieldKey.jmsKeyName, value);
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, long value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                map.put(fieldKey.jmsKeyName, value);
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, float value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                map.put(fieldKey.jmsKeyName, value);
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, double value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                map.put(fieldKey.jmsKeyName, value);
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, String value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                map.put(fieldKey.jmsKeyName, value);
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, ImmutableMessage value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                Map<String, Object> mapMessage = messageToMap(value);
                map.put(fieldKey.jmsKeyName, mapMessage);
            }

            @Override
            public void visit(String fieldName, FieldType fieldType, List<ImmutableMessage> value) {
                FieldKey fieldKey = resolveFieldKeyFromNameAndType(fieldName, fieldType);
                List<Map<String, Object>> jmsMessageList = new ArrayList<>();
                for (ImmutableMessage message : value) {
                    jmsMessageList.add(messageToMap(message));
                }
                map.put(fieldKey.jmsKeyName, jmsMessageList);
            }
        });

        return map;
    }

    /**
     * @param jmsMessage
     * @return
     * @throws JMSException
     */
    public WritableMessage fromJms(@Nonnull javax.jms.Message jmsMessage) throws JMSException {
        Objects.requireNonNull(jmsMessage);
        if (!(jmsMessage instanceof javax.jms.MapMessage)) {
            throw new IllegalArgumentException("javax.jms.MapMessage expected.");
        }

        javax.jms.MapMessage jmsMapMessage = (javax.jms.MapMessage) jmsMessage;

        WritableMessage message = Messages.create();

        Enumeration<?> mapNames = jmsMapMessage.getMapNames();
        while (mapNames.hasMoreElements()) {
            String jmsFieldName = (String) mapNames.nextElement();
            FieldKey fieldKey = resolveFieldKeyFromJmsKey(jmsFieldName);

            switch (fieldKey.fieldType) {
            case BYTE:
                message.putByte(fieldKey.fieldName, jmsMapMessage.getByte(jmsFieldName));
                break;
            case CHAR:
                message.putChar(fieldKey.fieldName, jmsMapMessage.getChar(jmsFieldName));
                break;
            case SHORT:
                message.putShort(fieldKey.fieldName, jmsMapMessage.getShort(jmsFieldName));
                break;
            case INT:
                message.putInt(fieldKey.fieldName, jmsMapMessage.getInt(jmsFieldName));
                break;
            case LONG:
                message.putLong(fieldKey.fieldName, jmsMapMessage.getLong(jmsFieldName));
                break;
            case FLOAT:
                message.putFloat(fieldKey.fieldName, jmsMapMessage.getFloat(jmsFieldName));
                break;
            case DOUBLE:
                message.putDouble(fieldKey.fieldName, jmsMapMessage.getDouble(jmsFieldName));
                break;
            case STRING:
                message.putString(fieldKey.fieldName, jmsMapMessage.getString(jmsFieldName));
                break;
            case MESSAGE:
                @SuppressWarnings("unchecked")
                Map<String, Object> jmsMessageValue = (Map<String, Object>) jmsMapMessage
                        .getObject(jmsFieldName);
                message.putMessage(fieldKey.fieldName, mapToMesssage(jmsMessageValue));
                break;
            case MESSAGE_LIST:
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> jmsListValue = (List<Map<String, Object>>) jmsMapMessage
                        .getObject(jmsFieldName);

                Builder<ImmutableMessage> listBuilder = ImmutableList.builder();
                for (Map<String, Object> jmsListItem : jmsListValue) {
                    listBuilder.add(mapToMesssage(jmsListItem));
                }

                message.putMessageList(fieldKey.fieldName, listBuilder.build());
                break;
            }
        }

        return message;
    }

    private ImmutableMessage mapToMesssage(Map<String, Object> map) {
        WritableMessage message = Messages.create();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String jmsFieldName = entry.getKey();
            FieldKey fieldKey = resolveFieldKeyFromJmsKey(jmsFieldName);

            switch (fieldKey.fieldType) {
            case BYTE:
                message.putByte(fieldKey.fieldName, ((Byte) map.get(jmsFieldName)).byteValue());
                break;
            case CHAR:
                message.putChar(fieldKey.fieldName,
                        ((Character) map.get(jmsFieldName)).charValue());
                break;
            case SHORT:
                message.putShort(fieldKey.fieldName, ((Short) map.get(jmsFieldName)).shortValue());
                break;
            case INT:
                message.putInt(fieldKey.fieldName, ((Integer) map.get(jmsFieldName)).intValue());
                break;
            case LONG:
                message.putLong(fieldKey.fieldName, ((Long) map.get(jmsFieldName)).longValue());
                break;
            case FLOAT:
                message.putFloat(fieldKey.fieldName, ((Float) map.get(jmsFieldName)).floatValue());
                break;
            case DOUBLE:
                message.putDouble(fieldKey.fieldName,
                        ((Double) map.get(jmsFieldName)).doubleValue());
                break;
            case STRING:
                message.putString(fieldKey.fieldName, (String) map.get(jmsFieldName));
                break;
            case MESSAGE:
                @SuppressWarnings("unchecked")
                Map<String, Object> jmsMessageValue = (Map<String, Object>) map.get(jmsFieldName);
                message.putMessage(fieldKey.fieldName, mapToMesssage(jmsMessageValue));
                break;
            case MESSAGE_LIST:
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> jmsListValue = (List<Map<String, Object>>) map
                        .get(jmsFieldName);

                Builder<ImmutableMessage> listBuilder = ImmutableList.builder();
                for (Map<String, Object> jmsListItem : jmsListValue) {
                    listBuilder.add(mapToMesssage(jmsListItem));
                }

                message.putMessageList(fieldKey.fieldName, listBuilder.build());
                break;
            }
        }

        return message.toImmutable();
    }

    private FieldKey resolveFieldKeyFromJmsKey(String jmsKey) {
        return this.fieldKeyByJmsKey.computeIfAbsent(jmsKey, (key) -> FieldKey.parse(key));
    }

    private FieldKey resolveFieldKeyFromNameAndType(String fieldName, FieldType fieldType) {
        ConcurrentHashMap<String, FieldKey> map = fieldKeyByTypeName.get(fieldType);

        FieldKey fieldKey = map.get(fieldName);
        if (fieldKey != null) {
            return fieldKey;
        }

        fieldKey = new FieldKey(fieldName, fieldType);
        map.put(fieldName, fieldKey);

        return fieldKey;
    }

    private static class FieldKey {
        public static FieldKey parse(String jmsKey) {
            return new FieldKey(jmsKey);
        }

        private final String fieldName;
        private final FieldType fieldType;

        private final String jmsKeyName;

        public FieldKey(String fieldName, FieldType fieldType) {
            Objects.requireNonNull(fieldName);
            Objects.requireNonNull(fieldType);

            this.fieldName = fieldName;
            this.fieldType = fieldType;

            switch (fieldType) {
            case BYTE:
                this.jmsKeyName = fieldName + ".b";
                break;
            case CHAR:
                this.jmsKeyName = fieldName + ".c";
                break;
            case SHORT:
                this.jmsKeyName = fieldName + ".s";
                break;
            case INT:
                this.jmsKeyName = fieldName + ".i";
                break;
            case LONG:
                this.jmsKeyName = fieldName + ".l";
                break;
            case FLOAT:
                this.jmsKeyName = fieldName + ".f";
                break;
            case DOUBLE:
                this.jmsKeyName = fieldName + ".d";
                break;
            case STRING:
                this.jmsKeyName = fieldName + ".S";
                break;
            case MESSAGE:
                this.jmsKeyName = fieldName + ".M";
                break;
            case MESSAGE_LIST:
                this.jmsKeyName = fieldName + ".L";
                break;
            default:
                throw new RuntimeException("Unrecognized type: " + fieldType);
            }
        }

        public FieldKey(String jmsKey) {
            Objects.requireNonNull(jmsKey);
            if (jmsKey.length() <= 2 && jmsKey.charAt(jmsKey.length() - 2) != '.') {
                throw new IllegalArgumentException();
            }

            this.jmsKeyName = jmsKey;
            this.fieldName = jmsKey.substring(0, jmsKey.length() - 2);

            char typeKey = jmsKey.charAt(jmsKey.length() - 1);
            switch (typeKey) {
            case 'b':
                this.fieldType = FieldType.BYTE;
                break;
            case 'c':
                this.fieldType = FieldType.CHAR;
                break;
            case 's':
                this.fieldType = FieldType.SHORT;
                break;
            case 'i':
                this.fieldType = FieldType.INT;
                break;
            case 'l':
                this.fieldType = FieldType.LONG;
                break;
            case 'f':
                this.fieldType = FieldType.FLOAT;
                break;
            case 'd':
                this.fieldType = FieldType.DOUBLE;
                break;
            case 'S':
                this.fieldType = FieldType.STRING;
                break;
            case 'M':
                this.fieldType = FieldType.MESSAGE;
                break;
            case 'L':
                this.fieldType = FieldType.MESSAGE_LIST;
                break;
            default:
                throw new RuntimeException("Unrecognized JMS key: " + jmsKey);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || (!(obj instanceof FieldKey))) {
                return false;
            }

            FieldKey another = (FieldKey) obj;
            return fieldName.equals(another.fieldName) && fieldType.equals(another.fieldType);
        }

        @Override
        public int hashCode() {
            return jmsKeyName.hashCode();
        }

        @Override
        public String toString() {
            return jmsKeyName;
        }
    }
}
