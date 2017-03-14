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

package com.dopsun.msg4j.o2m;

import javax.annotation.Nullable;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class O2mClientEventArgs extends O2mEventArgs {
    /** O2M Client state changed. */
    public static int INFO_STATE_CHANGED = INFO + 1;

    /**
     * O2M Client: received
     * {@link com.dopsun.msg4j.o2m.impl.O2mMessages.Messages#CLIENT_DISCONNECT_REPLY}
     */
    public static int INFO_CLIENT_DISCONNECT_RECEIVED = INFO + 2;

    /** O2M Client: failed to handle system message. */
    public static int ERROR_SYS_MSG_HANDLER_FAILED = ERROR + 1;

    /** O2M Client: failed to handle channel update message. */
    public static int ERROR_PUB_MSG_HANDLER_FAILED = ERROR + 2;

    /** O2M Client: failed to handle session message. */
    public static int ERROR_SSN_MSG_HANDLER_FAILED = ERROR + 3;

    /** O2M Client: unknown message type. */
    public static int ERROR_UNKNOWN_MSG_TYPE = ERROR + 4;

    /** O2M Client: connect rejected */
    public static int ERROR_CONNECT_REJECTED = ERROR + 5;

    /** O2M Client: server restarted */
    public static int ERROR_SERVER_RESTARTED = ERROR + 6;

    /** O2M Client: server stopped */
    public static int ERROR_SERVER_STOPPED = ERROR + 7;

    /** O2M Client: client session replaced */
    public static int ERROR_SESSION_REPLACED = ERROR + 8;

    /** O2M Client: channel subscription cannot be found by channel code. */
    public static int WARN_CHANNEL_SUBSCRIPTION_NOT_FOUND = WARN + 1;

    /** O2M Client: subscription cannot be found by id. */
    public static int WARN_SUBSCRIPTION_NOT_FOUND = WARN + 2;

    /** O2M Client: request cannot be found by id. */
    public static int WARN_REQUEST_NOT_FOUND = WARN + 3;

    /**
     * Events for transport.
     * 
     * @param eventId
     */
    public O2mClientEventArgs(int eventId) {
        this(eventId, null, null);
    }

    /**
     * Events for transport.
     * 
     * @param eventId
     * @param text
     */
    public O2mClientEventArgs(int eventId, @Nullable String text) {
        this(eventId, text, null);
    }

    /**
     * Events for transport.
     * 
     * @param eventId
     * @param reason
     */
    public O2mClientEventArgs(int eventId, @Nullable Throwable reason) {
        this(eventId, null, reason);
    }

    /**
     * Events for transport.
     * 
     * @param eventId
     * @param text
     * @param reason
     */
    public O2mClientEventArgs(int eventId, @Nullable String text, @Nullable Throwable reason) {
        super(eventId, text, reason);
    }
}
