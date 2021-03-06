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

import javax.annotation.Nullable;

import com.dopsun.msg4j.core.messages.Message;

/**
 * @author Dop Sun
 * @since 1.0.0
 * 
 * @see O2mMessages.Messages#SERVER_STARTED_NOTIFICATION
 */
final class ClientSystemServerStartedMessageHandler extends ClientMessageHandler {
    /**
     * @param context
     */
    public ClientSystemServerStartedMessageHandler(ClientMessageHandlerContext context) {
        super(context);
    }

    @Override
    public void onMessage(O2mChannelType channelType, @Nullable String channelCode, Message message)
            throws Exception {
        try {
            context().close(true);
        } finally {
            context().getO2mClientEvents().onServerStarted(message);
        }
    }

}
