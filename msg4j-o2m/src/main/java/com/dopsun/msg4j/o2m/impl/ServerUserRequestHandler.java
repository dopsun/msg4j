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

import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.Message;
import com.dopsun.msg4j.o2m.O2mServerRequestErrorException;
import com.dopsun.msg4j.o2m.O2mServiceException;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.UserRequestReader;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class ServerUserRequestHandler extends ServerRequestHandler {
    public ServerUserRequestHandler(ServerRequestHandlerContext context) {
        super(context);
    }

    @Override
    public void onRequest(Message request) {
        UserRequestReader reader = O2mMessages.readUserRequest(request);
        String clientSessionId = reader.getClientSessionId();

        Optional<O2mServerClientSessionImpl> optClientSession = context()
                .getClientSession(clientSessionId);

        if (!optClientSession.isPresent()) {
            context().getEvents().onClientSessionNotFound(clientSessionId);

            return;
        }

        O2mServerClientSessionImpl clientSession = optClientSession.get();
        ChannelSessionPublisher<Void> publisher = clientSession.getPublisher();

        long requestSeqNum = O2mMessages.Fields.SeqNum.get(request);
        ImmutableMessage payload = reader.getPayload();

        ImmutableMessage replyPayload = null;
        try {
            replyPayload = context().getUserRequestHandler().apply(clientSession, payload);
        } catch (O2mServerRequestErrorException ex) {
            try {
                publisher.sendErrorReply(requestSeqNum, ex.getErrorCode(), ex.getErrorText());
            } catch (O2mServiceException e) {
                context().getEvents().onPublishErrorReplyFailed(clientSessionId, request, e);
            }
        } catch (Throwable ex) {
            try {
                publisher.sendErrorReply(requestSeqNum,
                        O2mMessages.Consts.E_SERVER_REQUEST_HANDLER_EXCEPTION, null);
            } catch (O2mServiceException e) {
                context().getEvents().onPublishErrorReplyFailed(clientSessionId, request, e);
            }
        }

        if (replyPayload != null) {
            try {
                publisher.sendUserReply(requestSeqNum, replyPayload);
            } catch (O2mServiceException e) {
                context().getEvents().onPublishReplyFailed(clientSessionId, request,
                        replyPayload, e);
            }
        }
    }

}
