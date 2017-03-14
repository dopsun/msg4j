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

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.Message;
import com.dopsun.msg4j.o2m.O2mClientSubscriptionMode;
import com.dopsun.msg4j.o2m.O2mServerSnapshot;
import com.dopsun.msg4j.o2m.O2mServiceException;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.SubscribeRequestReader;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class ServerSubscribeRequestHandler extends ServerRequestHandler {
    public ServerSubscribeRequestHandler(ServerRequestHandlerContext context) {
        super(context);
    }

    @Override
    public void onRequest(Message requestEnvelop) {
        SubscribeRequestReader reader = O2mMessages.readSubscribeRequest(requestEnvelop);

        String channelCode = reader.getChannelCode();
        String subscriptionId = reader.getSubscriptionId();
        String mode = reader.getSubscribeMode();
        Optional<ImmutableMessage> optSelector = reader.tryGetSelector();

        String clientSessionId = reader.getClientSessionId();
        Optional<O2mServerClientSessionImpl> optClientSession = context()
                .getClientSession(clientSessionId);

        if (!optClientSession.isPresent()) {
            context().getEvents().onClientSessionNotFound(clientSessionId);
            return;
        }

        O2mServerClientSessionImpl clientSession = optClientSession.get();

        final AtomicReference<O2mServerSnapshot> snapshot = new AtomicReference<>(
                O2mServerSnapshot.EMPTY);
        final AtomicLong nextUpdateSeqNo = new AtomicLong(-1);

        final ChannelPublishPublisher<Void> publisher = context()
                .getOrCreatePublishPublisher(channelCode);

        publisher.syncRun(() -> {
            if (!O2mClientSubscriptionMode.UPDATE.name().equals(mode)) {
                snapshot.set(context().getSnapshotProvider().apply(clientSession, channelCode,
                        optSelector.orElse(null)));
            }

            nextUpdateSeqNo.set(publisher.getNextSeqNum());
        });

        long requestSeqNum = O2mMessages.Fields.SeqNum.get(requestEnvelop);

        final AtomicBoolean replyHasSent = new AtomicBoolean(false);
        snapshot.get().forEach((message, hasNext) -> {
            if (!replyHasSent.get()) {
                try {
                    clientSession.getPublisher().sendSubscribeReply(requestSeqNum, subscriptionId,
                            nextUpdateSeqNo.get(), true);
                } catch (O2mServiceException e) {
                    context().getEvents().onPublishSubscribeReplyFailed(clientSessionId,
                            channelCode, e);

                    return;
                }

                replyHasSent.set(true);
            }

            try {
                clientSession.getPublisher().sendSnapshot(subscriptionId, message, !hasNext);
            } catch (O2mServiceException e) {
                context().getEvents().onPublishSnapshotFailed(clientSessionId, message, e);
            }
        });

        if (!replyHasSent.get()) {
            try {
                clientSession.getPublisher().sendSubscribeReply(requestSeqNum, subscriptionId,
                        nextUpdateSeqNo.get(), false);
            } catch (O2mServiceException e) {
                context().getEvents().onPublishSubscribeReplyFailed(clientSessionId, channelCode,
                        e);

                return;
            }

            replyHasSent.set(true);
        }
    }
}
