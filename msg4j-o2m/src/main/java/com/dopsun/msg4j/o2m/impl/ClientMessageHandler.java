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

/**
 * @author Dop Sun
 * @since 1.0.0
 */
abstract class ClientMessageHandler {
    private final ClientMessageHandlerContext context;

    protected ClientMessageHandler(ClientMessageHandlerContext context) {
        Objects.requireNonNull(context);

        this.context = context;
    }

    /**
     * @return the context
     */
    public ClientMessageHandlerContext context() {
        return context;
    }

    /**
     * @param channelType
     * @param channelCode
     * @param message
     * @throws Exception
     */
    public abstract void onMessage(O2mChannelType channelType, @Nullable String channelCode,
            Message message) throws Exception;
}
