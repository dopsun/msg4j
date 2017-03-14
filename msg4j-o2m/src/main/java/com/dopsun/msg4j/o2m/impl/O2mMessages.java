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

package com.dopsun.msg4j.o2m.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import javax.annotation.Generated;

import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.MessageReader;
import com.dopsun.msg4j.core.messages.WritableMessage;
import com.dopsun.msg4j.core.messages.schema.BooleanFieldInfo;
import com.dopsun.msg4j.core.messages.schema.DataDictionary;
import com.dopsun.msg4j.core.messages.schema.IntFieldInfo;
import com.dopsun.msg4j.core.messages.schema.LongFieldInfo;
import com.dopsun.msg4j.core.messages.schema.MessageFieldInfo;
import com.dopsun.msg4j.core.messages.schema.StringFieldInfo;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
@Generated("msg4j-tools")
@SuppressWarnings({ "javadoc" })
public final class O2mMessages {
    public static final Messages.ServerStartedNotificationReader readServerStartedNotification(MessageReader message) {
        return new Messages.ServerStartedNotificationReader(message);
    }

    public static final Messages.ServerStartedNotificationWriter writeServerStartedNotification(WritableMessage message) {
        return new Messages.ServerStartedNotificationWriter(message);
    }

    public static final Messages.ServerStartedNotificationWriter newServerStartedNotification() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.SERVER_STARTED_NOTIFICATION);
        
        return new Messages.ServerStartedNotificationWriter(message);
    }

    public static final Messages.ServerStoppedNotificationReader readServerStoppedNotification(MessageReader message) {
        return new Messages.ServerStoppedNotificationReader(message);
    }

    public static final Messages.ServerStoppedNotificationWriter writeServerStoppedNotification(WritableMessage message) {
        return new Messages.ServerStoppedNotificationWriter(message);
    }

    public static final Messages.ServerStoppedNotificationWriter newServerStoppedNotification() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.SERVER_STOPPED_NOTIFICATION);
        
        return new Messages.ServerStoppedNotificationWriter(message);
    }

    public static final Messages.ClientConnectRequestReader readClientConnectRequest(MessageReader message) {
        return new Messages.ClientConnectRequestReader(message);
    }

    public static final Messages.ClientConnectRequestWriter writeClientConnectRequest(WritableMessage message) {
        return new Messages.ClientConnectRequestWriter(message);
    }

    public static final Messages.ClientConnectRequestWriter newClientConnectRequest() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.CLIENT_CONNECT_REQUEST);
        
        return new Messages.ClientConnectRequestWriter(message);
    }

    public static final Messages.ClientConnectReplyReader readClientConnectReply(MessageReader message) {
        return new Messages.ClientConnectReplyReader(message);
    }

    public static final Messages.ClientConnectReplyWriter writeClientConnectReply(WritableMessage message) {
        return new Messages.ClientConnectReplyWriter(message);
    }

    public static final Messages.ClientConnectReplyWriter newClientConnectReply() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.CLIENT_CONNECT_REPLY);
        
        return new Messages.ClientConnectReplyWriter(message);
    }

    public static final Messages.ClientDisconnectRequestReader readClientDisconnectRequest(MessageReader message) {
        return new Messages.ClientDisconnectRequestReader(message);
    }

    public static final Messages.ClientDisconnectRequestWriter writeClientDisconnectRequest(WritableMessage message) {
        return new Messages.ClientDisconnectRequestWriter(message);
    }

    public static final Messages.ClientDisconnectRequestWriter newClientDisconnectRequest() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.CLIENT_DISCONNECT_REQUEST);
        
        return new Messages.ClientDisconnectRequestWriter(message);
    }

    public static final Messages.ClientDisconnectReplyReader readClientDisconnectReply(MessageReader message) {
        return new Messages.ClientDisconnectReplyReader(message);
    }

    public static final Messages.ClientDisconnectReplyWriter writeClientDisconnectReply(WritableMessage message) {
        return new Messages.ClientDisconnectReplyWriter(message);
    }

    public static final Messages.ClientDisconnectReplyWriter newClientDisconnectReply() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.CLIENT_DISCONNECT_REPLY);
        
        return new Messages.ClientDisconnectReplyWriter(message);
    }

    public static final Messages.SubscribeRequestReader readSubscribeRequest(MessageReader message) {
        return new Messages.SubscribeRequestReader(message);
    }

    public static final Messages.SubscribeRequestWriter writeSubscribeRequest(WritableMessage message) {
        return new Messages.SubscribeRequestWriter(message);
    }

    public static final Messages.SubscribeRequestWriter newSubscribeRequest() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.SUBSCRIBE_REQUEST);
        
        return new Messages.SubscribeRequestWriter(message);
    }

    public static final Messages.SubscribeReplyReader readSubscribeReply(MessageReader message) {
        return new Messages.SubscribeReplyReader(message);
    }

    public static final Messages.SubscribeReplyWriter writeSubscribeReply(WritableMessage message) {
        return new Messages.SubscribeReplyWriter(message);
    }

    public static final Messages.SubscribeReplyWriter newSubscribeReply() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.SUBSCRIBE_REPLY);
        
        return new Messages.SubscribeReplyWriter(message);
    }

    public static final Messages.UnsubscribeRequestReader readUnsubscribeRequest(MessageReader message) {
        return new Messages.UnsubscribeRequestReader(message);
    }

    public static final Messages.UnsubscribeRequestWriter writeUnsubscribeRequest(WritableMessage message) {
        return new Messages.UnsubscribeRequestWriter(message);
    }

    public static final Messages.UnsubscribeRequestWriter newUnsubscribeRequest() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.UNSUBSCRIBE_REQUEST);
        
        return new Messages.UnsubscribeRequestWriter(message);
    }

    public static final Messages.UnsubscribeReplyReader readUnsubscribeReply(MessageReader message) {
        return new Messages.UnsubscribeReplyReader(message);
    }

    public static final Messages.UnsubscribeReplyWriter writeUnsubscribeReply(WritableMessage message) {
        return new Messages.UnsubscribeReplyWriter(message);
    }

    public static final Messages.UnsubscribeReplyWriter newUnsubscribeReply() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.UNSUBSCRIBE_REPLY);
        
        return new Messages.UnsubscribeReplyWriter(message);
    }

    public static final Messages.ChannelSnapshotReader readChannelSnapshot(MessageReader message) {
        return new Messages.ChannelSnapshotReader(message);
    }

    public static final Messages.ChannelSnapshotWriter writeChannelSnapshot(WritableMessage message) {
        return new Messages.ChannelSnapshotWriter(message);
    }

    public static final Messages.ChannelSnapshotWriter newChannelSnapshot() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.CHANNEL_SNAPSHOT);
        
        return new Messages.ChannelSnapshotWriter(message);
    }

    public static final Messages.ChannelUpdateReader readChannelUpdate(MessageReader message) {
        return new Messages.ChannelUpdateReader(message);
    }

    public static final Messages.ChannelUpdateWriter writeChannelUpdate(WritableMessage message) {
        return new Messages.ChannelUpdateWriter(message);
    }

    public static final Messages.ChannelUpdateWriter newChannelUpdate() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.CHANNEL_UPDATE);
        
        return new Messages.ChannelUpdateWriter(message);
    }

    public static final Messages.ServerClientHeartbeatReader readServerClientHeartbeat(MessageReader message) {
        return new Messages.ServerClientHeartbeatReader(message);
    }

    public static final Messages.ServerClientHeartbeatWriter writeServerClientHeartbeat(WritableMessage message) {
        return new Messages.ServerClientHeartbeatWriter(message);
    }

    public static final Messages.ServerClientHeartbeatWriter newServerClientHeartbeat() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.SERVER_CLIENT_HEARTBEAT);
        
        return new Messages.ServerClientHeartbeatWriter(message);
    }

    public static final Messages.ClientSessionReplacedReplyReader readClientSessionReplacedReply(MessageReader message) {
        return new Messages.ClientSessionReplacedReplyReader(message);
    }

    public static final Messages.ClientSessionReplacedReplyWriter writeClientSessionReplacedReply(WritableMessage message) {
        return new Messages.ClientSessionReplacedReplyWriter(message);
    }

    public static final Messages.ClientSessionReplacedReplyWriter newClientSessionReplacedReply() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.CLIENT_SESSION_REPLACED_REPLY);
        
        return new Messages.ClientSessionReplacedReplyWriter(message);
    }

    public static final Messages.UserRequestReader readUserRequest(MessageReader message) {
        return new Messages.UserRequestReader(message);
    }

    public static final Messages.UserRequestWriter writeUserRequest(WritableMessage message) {
        return new Messages.UserRequestWriter(message);
    }

    public static final Messages.UserRequestWriter newUserRequest() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.USER_REQUEST);
        
        return new Messages.UserRequestWriter(message);
    }

    public static final Messages.UserReplyReader readUserReply(MessageReader message) {
        return new Messages.UserReplyReader(message);
    }

    public static final Messages.UserReplyWriter writeUserReply(WritableMessage message) {
        return new Messages.UserReplyWriter(message);
    }

    public static final Messages.UserReplyWriter newUserReply() {
        WritableMessage message = com.dopsun.msg4j.core.messages.Messages.create();
        Fields.MessageType.put(message, Messages.USER_REPLY);
        
        return new Messages.UserReplyWriter(message);
    }


    /** @formatter:off */
    public static final DataDictionary DICTIONARY = DataDictionary.builder()
            .addField(Fields.MessageType)
            .addField(Fields.SeqNum)
            .addField(Fields.Timestamp)
            .addField(Fields.ClientSessionId)
            .addField(Fields.ServerSessionId)
            .addField(Fields.RequestSeqNum)
            .addField(Fields.Payload)
            .addField(Fields.ErrorCode)
            .addField(Fields.ErrorText)
            .addField(Fields.ServerId)
            .addField(Fields.ClientId)
            .addField(Fields.Version)
            .addField(Fields.SubscriptionId)
            .addField(Fields.Selector)
            .addField(Fields.ChannelCode)
            .addField(Fields.SubscribeMode)
            .addField(Fields.SnapshotExists)
            .addField(Fields.NextChannelSeqNum)
            .addField(Fields.SnapshotIsLast)
            .addField(Fields.ServerHeartbeatInterval)
            .build();
    /** @formatter:on */

    public static class Consts {
        public static final int E_OK = 0;
        public static final int E_SERVER_UNEXPECTED_SERVER = 1;
        public static final int E_SERVER_REQUEST_HANDLER_EXCEPTION = 2;
        public static final int E_CLIENT_SESSION_REPLACED = 3;
        public static final String TRUE = "T";
        public static final String FALSE = "F";

    }

    public static class Fields {
        public static final String MESSAGE_TYPE = "0";
        public static final String SEQ_NUM = "1";
        public static final String TIMESTAMP = "2";
        public static final String CLIENT_SESSION_ID = "3";
        public static final String SERVER_SESSION_ID = "4";
        public static final String REQUEST_SEQ_NUM = "5";
        public static final String PAYLOAD = "6";
        public static final String ERROR_CODE = "10";
        public static final String ERROR_TEXT = "11";
        public static final String SERVER_ID = "12";
        public static final String CLIENT_ID = "13";
        public static final String VERSION = "14";
        public static final String SUBSCRIPTION_ID = "16";
        public static final String SELECTOR = "17";
        public static final String CHANNEL_CODE = "18";
        public static final String SUBSCRIBE_MODE = "19";
        public static final String SNAPSHOT_EXISTS = "20";
        public static final String NEXT_CHANNEL_SEQ_NUM = "21";
        public static final String SNAPSHOT_IS_LAST = "22";
        public static final String SERVER_HEARTBEAT_INTERVAL = "23";

        public static final StringFieldInfo MessageType = new StringFieldInfo(MESSAGE_TYPE);
        public static final LongFieldInfo SeqNum = new LongFieldInfo(SEQ_NUM);
        public static final LongFieldInfo Timestamp = new LongFieldInfo(TIMESTAMP);
        public static final StringFieldInfo ClientSessionId = new StringFieldInfo(CLIENT_SESSION_ID);
        public static final StringFieldInfo ServerSessionId = new StringFieldInfo(SERVER_SESSION_ID);
        public static final LongFieldInfo RequestSeqNum = new LongFieldInfo(REQUEST_SEQ_NUM);
        public static final MessageFieldInfo Payload = new MessageFieldInfo(PAYLOAD);
        public static final IntFieldInfo ErrorCode = new IntFieldInfo(ERROR_CODE);
        public static final StringFieldInfo ErrorText = new StringFieldInfo(ERROR_TEXT);
        public static final StringFieldInfo ServerId = new StringFieldInfo(SERVER_ID);
        public static final StringFieldInfo ClientId = new StringFieldInfo(CLIENT_ID);
        public static final StringFieldInfo Version = new StringFieldInfo(VERSION);
        public static final StringFieldInfo SubscriptionId = new StringFieldInfo(SUBSCRIPTION_ID);
        public static final MessageFieldInfo Selector = new MessageFieldInfo(SELECTOR);
        public static final StringFieldInfo ChannelCode = new StringFieldInfo(CHANNEL_CODE);
        public static final StringFieldInfo SubscribeMode = new StringFieldInfo(SUBSCRIBE_MODE);
        public static final BooleanFieldInfo SnapshotExists = new BooleanFieldInfo(SNAPSHOT_EXISTS);
        public static final LongFieldInfo NextChannelSeqNum = new LongFieldInfo(NEXT_CHANNEL_SEQ_NUM);
        public static final BooleanFieldInfo SnapshotIsLast = new BooleanFieldInfo(SNAPSHOT_IS_LAST);
        public static final IntFieldInfo ServerHeartbeatInterval = new IntFieldInfo(SERVER_HEARTBEAT_INTERVAL);
    }

    public static class Messages {
        public static final String SERVER_STARTED_NOTIFICATION = "1";
        public static final String SERVER_STOPPED_NOTIFICATION = "2";
        public static final String CLIENT_CONNECT_REQUEST = "3";
        public static final String CLIENT_CONNECT_REPLY = "4";
        public static final String CLIENT_DISCONNECT_REQUEST = "5";
        public static final String CLIENT_DISCONNECT_REPLY = "6";
        public static final String SUBSCRIBE_REQUEST = "7";
        public static final String SUBSCRIBE_REPLY = "8";
        public static final String UNSUBSCRIBE_REQUEST = "9";
        public static final String UNSUBSCRIBE_REPLY = "10";
        public static final String CHANNEL_SNAPSHOT = "11";
        public static final String CHANNEL_UPDATE = "12";
        public static final String SERVER_CLIENT_HEARTBEAT = "13";
        public static final String CLIENT_SESSION_REPLACED_REPLY = "14";
        public static final String USER_REQUEST = "60";
        public static final String USER_REPLY = "61";

        public static abstract class AbstractReader {
            protected final MessageReader message;

            protected AbstractReader(MessageReader message) {
                Objects.requireNonNull(message);

                this.message = message;
            }

            /**
             * @return the message
             */
            public MessageReader getMessage() {
                return message;
            }
        }

        public static abstract class AbstractWriter {
            protected final WritableMessage message;

            protected AbstractWriter(WritableMessage message) {
                Objects.requireNonNull(message);

                this.message = message;
            }

            /**
             * @return the message
             */
            public WritableMessage getMessage() {
                return message;
            }
        }
        
        public static final class ServerStartedNotificationReader extends AbstractReader {
            public ServerStartedNotificationReader(MessageReader message) {
                super(message);
            }
            
            public final String getServerId() {
                return Fields.ServerId.get(message);
            }

            public final String getVersion() {
                return Fields.Version.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

        }

        public static final class ServerStartedNotificationWriter extends AbstractWriter {
            public ServerStartedNotificationWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setServerId(String value) {
                Fields.ServerId.put(message, value);
            }

            public final void setVersion(String value) {
                Fields.Version.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

        }

        public static final class ServerStoppedNotificationReader extends AbstractReader {
            public ServerStoppedNotificationReader(MessageReader message) {
                super(message);
            }
            
            public final String getServerId() {
                return Fields.ServerId.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

        }

        public static final class ServerStoppedNotificationWriter extends AbstractWriter {
            public ServerStoppedNotificationWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setServerId(String value) {
                Fields.ServerId.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

        }

        public static final class ClientConnectRequestReader extends AbstractReader {
            public ClientConnectRequestReader(MessageReader message) {
                super(message);
            }
            
            public final String getServerId() {
                return Fields.ServerId.get(message);
            }

            public final String getClientId() {
                return Fields.ClientId.get(message);
            }

            public final String getVersion() {
                return Fields.Version.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

        }

        public static final class ClientConnectRequestWriter extends AbstractWriter {
            public ClientConnectRequestWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setServerId(String value) {
                Fields.ServerId.put(message, value);
            }

            public final void setClientId(String value) {
                Fields.ClientId.put(message, value);
            }

            public final void setVersion(String value) {
                Fields.Version.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

        }

        public static final class ClientConnectReplyReader extends AbstractReader {
            public ClientConnectReplyReader(MessageReader message) {
                super(message);
            }
            
            public final String getVersion() {
                return Fields.Version.get(message);
            }

            public final int getServerHeartbeatInterval() {
                return Fields.ServerHeartbeatInterval.get(message);
            }

            public final long getRequestSeqNum() {
                return Fields.RequestSeqNum.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

            public final int getErrorCode() {
                return Fields.ErrorCode.get(message);
            }

            public final int tryGetErrorCode(int defaultValue) {
                return Fields.ErrorCode.tryGet(message, defaultValue);
            }

            public final OptionalInt tryGetErrorCode() {
                return Fields.ErrorCode.tryGet(message);
            }
    
            public final String getErrorText() {
                return Fields.ErrorText.get(message);
            }

            public final String tryGetErrorText(String defaultValue) {
                return Fields.ErrorText.tryGet(message, defaultValue);
            }

            public final Optional<String> tryGetErrorText() {
                return Fields.ErrorText.tryGet(message);
            }
    
        }

        public static final class ClientConnectReplyWriter extends AbstractWriter {
            public ClientConnectReplyWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setVersion(String value) {
                Fields.Version.put(message, value);
            }

            public final void setServerHeartbeatInterval(int value) {
                Fields.ServerHeartbeatInterval.put(message, value);
            }

            public final void setRequestSeqNum(long value) {
                Fields.RequestSeqNum.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

            public final void setErrorCode(int value) {
                Fields.ErrorCode.put(message, value);
            }

            public final void setErrorText(String value) {
                Fields.ErrorText.put(message, value);
            }

        }

        public static final class ClientDisconnectRequestReader extends AbstractReader {
            public ClientDisconnectRequestReader(MessageReader message) {
                super(message);
            }
            
            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

        }

        public static final class ClientDisconnectRequestWriter extends AbstractWriter {
            public ClientDisconnectRequestWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

        }

        public static final class ClientDisconnectReplyReader extends AbstractReader {
            public ClientDisconnectReplyReader(MessageReader message) {
                super(message);
            }
            
            public final long getRequestSeqNum() {
                return Fields.RequestSeqNum.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

            public final int getErrorCode() {
                return Fields.ErrorCode.get(message);
            }

            public final int tryGetErrorCode(int defaultValue) {
                return Fields.ErrorCode.tryGet(message, defaultValue);
            }

            public final OptionalInt tryGetErrorCode() {
                return Fields.ErrorCode.tryGet(message);
            }
    
            public final String getErrorText() {
                return Fields.ErrorText.get(message);
            }

            public final String tryGetErrorText(String defaultValue) {
                return Fields.ErrorText.tryGet(message, defaultValue);
            }

            public final Optional<String> tryGetErrorText() {
                return Fields.ErrorText.tryGet(message);
            }
    
        }

        public static final class ClientDisconnectReplyWriter extends AbstractWriter {
            public ClientDisconnectReplyWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setRequestSeqNum(long value) {
                Fields.RequestSeqNum.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

            public final void setErrorCode(int value) {
                Fields.ErrorCode.put(message, value);
            }

            public final void setErrorText(String value) {
                Fields.ErrorText.put(message, value);
            }

        }

        public static final class SubscribeRequestReader extends AbstractReader {
            public SubscribeRequestReader(MessageReader message) {
                super(message);
            }
            
            public final String getChannelCode() {
                return Fields.ChannelCode.get(message);
            }

            public final String getSubscriptionId() {
                return Fields.SubscriptionId.get(message);
            }

            public final String getSubscribeMode() {
                return Fields.SubscribeMode.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

            public final ImmutableMessage getSelector() {
                return Fields.Selector.get(message);
            }

            public final ImmutableMessage tryGetSelector(ImmutableMessage defaultValue) {
                return Fields.Selector.tryGet(message, defaultValue);
            }

            public final Optional<ImmutableMessage> tryGetSelector() {
                return Fields.Selector.tryGet(message);
            }
    
        }

        public static final class SubscribeRequestWriter extends AbstractWriter {
            public SubscribeRequestWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setChannelCode(String value) {
                Fields.ChannelCode.put(message, value);
            }

            public final void setSubscriptionId(String value) {
                Fields.SubscriptionId.put(message, value);
            }

            public final void setSubscribeMode(String value) {
                Fields.SubscribeMode.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

            public final void setSelector(ImmutableMessage value) {
                Fields.Selector.put(message, value);
            }

        }

        public static final class SubscribeReplyReader extends AbstractReader {
            public SubscribeReplyReader(MessageReader message) {
                super(message);
            }
            
            public final String getSubscriptionId() {
                return Fields.SubscriptionId.get(message);
            }

            public final long getNextChannelSeqNum() {
                return Fields.NextChannelSeqNum.get(message);
            }

            public final long getRequestSeqNum() {
                return Fields.RequestSeqNum.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

            public final boolean getSnapshotExists() {
                return Fields.SnapshotExists.get(message);
            }

            public final boolean tryGetSnapshotExists(boolean defaultValue) {
                return Fields.SnapshotExists.tryGet(message, defaultValue);
            }

            public final int getErrorCode() {
                return Fields.ErrorCode.get(message);
            }

            public final int tryGetErrorCode(int defaultValue) {
                return Fields.ErrorCode.tryGet(message, defaultValue);
            }

            public final OptionalInt tryGetErrorCode() {
                return Fields.ErrorCode.tryGet(message);
            }
    
            public final String getErrorText() {
                return Fields.ErrorText.get(message);
            }

            public final String tryGetErrorText(String defaultValue) {
                return Fields.ErrorText.tryGet(message, defaultValue);
            }

            public final Optional<String> tryGetErrorText() {
                return Fields.ErrorText.tryGet(message);
            }
    
        }

        public static final class SubscribeReplyWriter extends AbstractWriter {
            public SubscribeReplyWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setSubscriptionId(String value) {
                Fields.SubscriptionId.put(message, value);
            }

            public final void setNextChannelSeqNum(long value) {
                Fields.NextChannelSeqNum.put(message, value);
            }

            public final void setRequestSeqNum(long value) {
                Fields.RequestSeqNum.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

            public final void setSnapshotExists(boolean value) {
                Fields.SnapshotExists.put(message, value);
            }

            public final void setErrorCode(int value) {
                Fields.ErrorCode.put(message, value);
            }

            public final void setErrorText(String value) {
                Fields.ErrorText.put(message, value);
            }

        }

        public static final class UnsubscribeRequestReader extends AbstractReader {
            public UnsubscribeRequestReader(MessageReader message) {
                super(message);
            }
            
            public final String getChannelCode() {
                return Fields.ChannelCode.get(message);
            }

            public final String getSubscriptionId() {
                return Fields.SubscriptionId.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

        }

        public static final class UnsubscribeRequestWriter extends AbstractWriter {
            public UnsubscribeRequestWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setChannelCode(String value) {
                Fields.ChannelCode.put(message, value);
            }

            public final void setSubscriptionId(String value) {
                Fields.SubscriptionId.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

        }

        public static final class UnsubscribeReplyReader extends AbstractReader {
            public UnsubscribeReplyReader(MessageReader message) {
                super(message);
            }
            
            public final String getSubscriptionId() {
                return Fields.SubscriptionId.get(message);
            }

            public final long getRequestSeqNum() {
                return Fields.RequestSeqNum.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

            public final int getErrorCode() {
                return Fields.ErrorCode.get(message);
            }

            public final int tryGetErrorCode(int defaultValue) {
                return Fields.ErrorCode.tryGet(message, defaultValue);
            }

            public final OptionalInt tryGetErrorCode() {
                return Fields.ErrorCode.tryGet(message);
            }
    
            public final String getErrorText() {
                return Fields.ErrorText.get(message);
            }

            public final String tryGetErrorText(String defaultValue) {
                return Fields.ErrorText.tryGet(message, defaultValue);
            }

            public final Optional<String> tryGetErrorText() {
                return Fields.ErrorText.tryGet(message);
            }
    
        }

        public static final class UnsubscribeReplyWriter extends AbstractWriter {
            public UnsubscribeReplyWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setSubscriptionId(String value) {
                Fields.SubscriptionId.put(message, value);
            }

            public final void setRequestSeqNum(long value) {
                Fields.RequestSeqNum.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

            public final void setErrorCode(int value) {
                Fields.ErrorCode.put(message, value);
            }

            public final void setErrorText(String value) {
                Fields.ErrorText.put(message, value);
            }

        }

        public static final class ChannelSnapshotReader extends AbstractReader {
            public ChannelSnapshotReader(MessageReader message) {
                super(message);
            }
            
            public final ImmutableMessage getPayload() {
                return Fields.Payload.get(message);
            }

            public final String getSubscriptionId() {
                return Fields.SubscriptionId.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

            public final boolean getSnapshotIsLast() {
                return Fields.SnapshotIsLast.get(message);
            }

            public final boolean tryGetSnapshotIsLast(boolean defaultValue) {
                return Fields.SnapshotIsLast.tryGet(message, defaultValue);
            }

        }

        public static final class ChannelSnapshotWriter extends AbstractWriter {
            public ChannelSnapshotWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setPayload(ImmutableMessage value) {
                Fields.Payload.put(message, value);
            }

            public final void setSubscriptionId(String value) {
                Fields.SubscriptionId.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

            public final void setSnapshotIsLast(boolean value) {
                Fields.SnapshotIsLast.put(message, value);
            }

        }

        public static final class ChannelUpdateReader extends AbstractReader {
            public ChannelUpdateReader(MessageReader message) {
                super(message);
            }
            
            public final ImmutableMessage getPayload() {
                return Fields.Payload.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

        }

        public static final class ChannelUpdateWriter extends AbstractWriter {
            public ChannelUpdateWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setPayload(ImmutableMessage value) {
                Fields.Payload.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

        }

        public static final class ServerClientHeartbeatReader extends AbstractReader {
            public ServerClientHeartbeatReader(MessageReader message) {
                super(message);
            }
            
            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

        }

        public static final class ServerClientHeartbeatWriter extends AbstractWriter {
            public ServerClientHeartbeatWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

        }

        public static final class ClientSessionReplacedReplyReader extends AbstractReader {
            public ClientSessionReplacedReplyReader(MessageReader message) {
                super(message);
            }
            
            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

        }

        public static final class ClientSessionReplacedReplyWriter extends AbstractWriter {
            public ClientSessionReplacedReplyWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

        }

        public static final class UserRequestReader extends AbstractReader {
            public UserRequestReader(MessageReader message) {
                super(message);
            }
            
            public final ImmutableMessage getPayload() {
                return Fields.Payload.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

        }

        public static final class UserRequestWriter extends AbstractWriter {
            public UserRequestWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setPayload(ImmutableMessage value) {
                Fields.Payload.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

        }

        public static final class UserReplyReader extends AbstractReader {
            public UserReplyReader(MessageReader message) {
                super(message);
            }
            
            public final ImmutableMessage getPayload() {
                return Fields.Payload.get(message);
            }

            public final long getRequestSeqNum() {
                return Fields.RequestSeqNum.get(message);
            }

            public final String getClientSessionId() {
                return Fields.ClientSessionId.get(message);
            }

            public final String getServerSessionId() {
                return Fields.ServerSessionId.get(message);
            }

            public final String getMessageType() {
                return Fields.MessageType.get(message);
            }

            public final long getSeqNum() {
                return Fields.SeqNum.get(message);
            }

            public final long getTimestamp() {
                return Fields.Timestamp.get(message);
            }

            public final int getErrorCode() {
                return Fields.ErrorCode.get(message);
            }

            public final int tryGetErrorCode(int defaultValue) {
                return Fields.ErrorCode.tryGet(message, defaultValue);
            }

            public final OptionalInt tryGetErrorCode() {
                return Fields.ErrorCode.tryGet(message);
            }
    
            public final String getErrorText() {
                return Fields.ErrorText.get(message);
            }

            public final String tryGetErrorText(String defaultValue) {
                return Fields.ErrorText.tryGet(message, defaultValue);
            }

            public final Optional<String> tryGetErrorText() {
                return Fields.ErrorText.tryGet(message);
            }
    
        }

        public static final class UserReplyWriter extends AbstractWriter {
            public UserReplyWriter(WritableMessage message) {
                super(message);
            }
            
            public final void setPayload(ImmutableMessage value) {
                Fields.Payload.put(message, value);
            }

            public final void setRequestSeqNum(long value) {
                Fields.RequestSeqNum.put(message, value);
            }

            public final void setClientSessionId(String value) {
                Fields.ClientSessionId.put(message, value);
            }

            public final void setServerSessionId(String value) {
                Fields.ServerSessionId.put(message, value);
            }

            public final void setSeqNum(long value) {
                Fields.SeqNum.put(message, value);
            }

            public final void setTimestamp(long value) {
                Fields.Timestamp.put(message, value);
            }

            public final void setErrorCode(int value) {
                Fields.ErrorCode.put(message, value);
            }

            public final void setErrorText(String value) {
                Fields.ErrorText.put(message, value);
            }

        }

    }
}
