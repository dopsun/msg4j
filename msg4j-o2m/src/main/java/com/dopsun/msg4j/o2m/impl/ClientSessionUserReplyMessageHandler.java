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

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.dopsun.msg4j.core.messages.Message;
import com.dopsun.msg4j.o2m.O2mServiceException;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class ClientSessionUserReplyMessageHandler extends ClientMessageHandler {

    /**
     * @param context
     */
    public ClientSessionUserReplyMessageHandler(ClientMessageHandlerContext context) {
        super(context);
    }

    @Override
    public void onMessage(O2mChannelType channelType, @Nullable String channelCode,
            Message message) {
        long requestSeqNum = O2mMessages.Fields.RequestSeqNum.get(message);

        CompletableFuture<Message> future = context().getAndRemovePendingRequest(requestSeqNum);
        if (future == null) {
            context().getO2mClientEvents().onRequestNotFound(requestSeqNum);
            return;
        }

        int errorCode = O2mMessages.Fields.ErrorCode.tryGet(message, O2mMessages.Consts.E_OK);
        if (errorCode != O2mMessages.Consts.E_OK) {
            String errorText = O2mMessages.Fields.ErrorText.tryGet(message, "");

            O2mServiceException reason = new O2mServiceException(
                    "Server replies: code = " + errorCode + " text: " + errorText);
            future.completeExceptionally(reason);
        } else {
            future.complete(message);
        }
    }

}
