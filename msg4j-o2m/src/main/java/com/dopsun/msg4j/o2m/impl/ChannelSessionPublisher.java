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

import javax.annotation.Nullable;

import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.Messages;
import com.dopsun.msg4j.core.messages.WritableMessage;
import com.dopsun.msg4j.o2m.O2mServiceException;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.ChannelSnapshotWriter;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.SubscribeReplyWriter;

/**
 * Publisher for session channel where server sends messages to a particular session.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
final class ChannelSessionPublisher<T> extends ChannelServerPublisher<T> {
    private final String clientSessionId;

    /**
     * @param serverSessionId
     * @param clientSessionId
     * @param sender
     */
    public ChannelSessionPublisher(String serverSessionId, String clientSessionId,
            ChannelMessageSender<T> sender) {
        super(serverSessionId, sender);

        Objects.requireNonNull(clientSessionId);

        this.clientSessionId = clientSessionId;
    }

    public void sendSnapshot(String subscriptionId, ImmutableMessage payload, boolean isLast)
            throws O2mServiceException {
        ChannelSnapshotWriter snapshotWriter = O2mMessages.newChannelSnapshot();

        snapshotWriter.setSubscriptionId(subscriptionId);
        snapshotWriter.setPayload(payload);

        if (isLast) {
            snapshotWriter.setSnapshotIsLast(true);
        }

        this.syncPublish(snapshotWriter.getMessage());
    }

    public void sendSubscribeReply(long requestSeqNum, String subscriptionId,
            long nextChannelSeqNum, boolean snapshotExists) throws O2mServiceException {

        SubscribeReplyWriter writer = O2mMessages.newSubscribeReply();
        writer.setSubscriptionId(subscriptionId);
        writer.setNextChannelSeqNum(nextChannelSeqNum);

        if (snapshotExists) {
            writer.setSnapshotExists(true);
        }

        writer.setRequestSeqNum(requestSeqNum);

        this.syncPublish(writer.getMessage());
    }

    public void sendUserReply(long requestSeqNum, ImmutableMessage payload)
            throws O2mServiceException {
        Objects.requireNonNull(payload);

        WritableMessage envelop = Messages.create();
        O2mMessages.Fields.MessageType.put(envelop, O2mMessages.Messages.USER_REPLY);
        O2mMessages.Fields.Payload.put(envelop, payload);
        O2mMessages.Fields.RequestSeqNum.put(envelop, requestSeqNum);

        this.syncPublish(envelop);
    }

    public void sendErrorReply(long requestSeqNum, int errorCode, @Nullable String errorText)
            throws O2mServiceException {
        WritableMessage envelop = Messages.create();
        O2mMessages.Fields.MessageType.put(envelop, O2mMessages.Messages.USER_REPLY);

        O2mMessages.Fields.RequestSeqNum.put(envelop, requestSeqNum);
        O2mMessages.Fields.ErrorCode.put(envelop, errorCode);
        if (errorText != null) {
            O2mMessages.Fields.ErrorText.put(envelop, errorText);
        }

        this.syncPublish(envelop);
    }

    public void sendClientSessionReplaced() throws O2mServiceException {
        WritableMessage envelop = Messages.create();
        O2mMessages.Fields.MessageType.put(envelop,
                O2mMessages.Messages.CLIENT_SESSION_REPLACED_REPLY);

        O2mMessages.Fields.ErrorCode.put(envelop, O2mMessages.Consts.E_CLIENT_SESSION_REPLACED);

        this.syncPublish(envelop);
    }

    @Override
    protected void beforePublish(WritableMessage message) {
        super.beforePublish(message);

        O2mMessages.Fields.ClientSessionId.put(message, clientSessionId);
    }
}
