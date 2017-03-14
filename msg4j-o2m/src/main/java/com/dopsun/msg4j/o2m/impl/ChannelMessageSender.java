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
import com.dopsun.msg4j.o2m.O2mServiceException;

/**
 * A sender to sends message.
 * 
 * @see ChannelPublisher
 * 
 * @param <T>
 *            type of return value
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
@FunctionalInterface
interface ChannelMessageSender<T> {
    /**
     * Sends the message. Throws {@link O2mServiceException} if checked exception happens while
     * sending.
     * 
     * @param message
     * @return
     * 
     * @throws O2mServiceException
     *             if sending failed.
     */
    @Nullable
    T send(Message message) throws O2mServiceException;
}
