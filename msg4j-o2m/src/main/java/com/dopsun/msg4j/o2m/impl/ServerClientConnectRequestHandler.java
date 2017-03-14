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

import com.dopsun.msg4j.core.messages.Message;
import com.dopsun.msg4j.core.messages.WritableMessage;
import com.dopsun.msg4j.o2m.O2mServiceException;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.ClientConnectReplyWriter;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.ClientConnectRequestReader;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class ServerClientConnectRequestHandler extends ServerRequestHandler {
    public ServerClientConnectRequestHandler(ServerRequestHandlerContext context) {
        super(context);
    }

    @Override
    public void onRequest(Message requestEnvelop) {
        ClientConnectReplyWriter writer = O2mMessages.newClientConnectReply();
        ClientConnectRequestReader reader = O2mMessages.readClientConnectRequest(requestEnvelop);

        String clientId = reader.getClientId();
        String clientSessionId = reader.getClientSessionId();

        O2mServerClientSessionImpl clientSession = context().createClientSession(clientId,
                clientSessionId);

        if (!context().getServerId().equals(reader.getServerId())) {
            writer.setErrorCode(O2mMessages.Consts.E_SERVER_UNEXPECTED_SERVER);
        } else {
            writer.setVersion(context().getVersion());
            writer.setServerHeartbeatInterval((int) context().getHeartbeatInterval().toMillis());
        }

        doSendReply(clientSession, requestEnvelop, writer.getMessage());
    }

    private void doSendReply(O2mServerClientSessionImpl clientSession, Message requestEnvelop,
            WritableMessage replyEnvelop) {
        long requestSeqNum = O2mMessages.Fields.SeqNum.get(requestEnvelop);

        O2mMessages.Fields.RequestSeqNum.put(replyEnvelop, requestSeqNum);

        ChannelSessionPublisher<Void> publisher = clientSession.getPublisher();
        try {
            publisher.syncPublish(replyEnvelop);
        } catch (O2mServiceException e) {
            context().getEvents().onPublishReplyFailed(clientSession.getSessionId(),
                    requestEnvelop, replyEnvelop, e);
        }
    }
}
