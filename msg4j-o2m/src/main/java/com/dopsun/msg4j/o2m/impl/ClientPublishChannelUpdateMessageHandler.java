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

import com.dopsun.msg4j.core.messages.Message;
import com.dopsun.msg4j.o2m.impl.O2mClientImpl.O2mSubscriptionImpl;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class ClientPublishChannelUpdateMessageHandler extends ClientMessageHandler {

    /**
     * @param context
     */
    public ClientPublishChannelUpdateMessageHandler(ClientMessageHandlerContext context) {
        super(context);
    }

    @Override
    public void onMessage(O2mChannelType channelType, @Nullable String channelCode,
            Message message) {
        Objects.requireNonNull(channelType);
        if (channelType != O2mChannelType.PUBLISH) {
            throw new IllegalArgumentException();
        }
        Objects.requireNonNull(channelCode);

        O2mSubscriptionImpl subscription = context().getSubscriptionByChannelCode(channelCode);
        if (subscription == null) {
            context().getO2mClientEvents().onChannelSubscriptionNotFound(channelCode);
            return;
        }

        subscription.onPublishMessageReceived(message);
    }

}
