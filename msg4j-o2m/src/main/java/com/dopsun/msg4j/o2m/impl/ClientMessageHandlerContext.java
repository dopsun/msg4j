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
import com.dopsun.msg4j.o2m.impl.O2mClientImpl.O2mSubscriptionImpl;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
interface ClientMessageHandlerContext {
    /**
     * Closes the client with a flag to tell whether the channel closed with failure.
     * 
     * @param failed
     * @throws Exception
     */
    void close(boolean failed) throws Exception;

    /**
     * Gets the events for client.
     * 
     * @return
     */
    O2mClientEvents getO2mClientEvents();

    /**
     * @param requestId
     * @return
     */
    @Nullable
    CompletableFuture<Message> getAndRemovePendingRequest(long requestId);

    /**
     * @param subscriptionId
     * @return
     */
    @Nullable
    O2mSubscriptionImpl getSubscriptionById(String subscriptionId);

    /**
     * @param channelCode
     * @return
     */
    @Nullable
    O2mSubscriptionImpl getSubscriptionByChannelCode(String channelCode);
}
