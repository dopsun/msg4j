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

/**
 * @author Dop Sun
 * @since 1.0.0
 */
enum O2mChannelType {
    /**
     * System channel, where server publishing messages to all client.
     */
    SYSTEM,

    /**
     * Request channel, where client sends messages to server.
     */
    REQUEST,

    /**
     * Session channel, where server publishes messages to a particular client.
     */
    SESSION,

    /**
     * Publish channel, where server publishes messages to all client.
     */
    PUBLISH
}
