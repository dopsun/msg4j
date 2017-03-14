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
public class O2mServerEventArgs extends O2mEventArgs {

    /** O2M Server: failed to publish system message. */
    public static int ERROR_PUBLISH_SYSTEM_FAILED = ERROR + 1;

    /** O2M Server: failed to publish heartbeat message. */
    public static int ERROR_PUBLISH_HEARTBEAT_FAILED = ERROR + 2;

    /** O2M Server: failed to publish to session. */
    public static int ERROR_PUBLISH_SESSION_FAILED = ERROR + 3;

    /** O2M Server: failed to publish client session replaced message. */
    public static int ERROR_PUBLISH_CLIENT_SESSION_REPLACED_FAILED = ERROR + 4;

    /** O2M Server: failed to publish message to publish channel. */
    public static int ERROR_PUBLISH_PUBLISH_FAILED = ERROR + 5;

    /** O2M Server: failed to create topic. */
    public static int ERROR_CREATE_TOPIC_FAILED = ERROR + 6;

    /** O2M Server: failed to publish subscription reply */
    public static int ERROR_PUBLISH_SUB_REPLY_FAILED = ERROR + 7;

    /** O2M Server: failed to publish snapshot */
    public static int ERROR_PUBLISH_SNAPSHOT_FAILED = ERROR + 8;

    /** O2M Server: failed to publish reply */
    public static int ERROR_PUBLISH_ERROR_REPLY_FAILED = ERROR + 9;

    /** O2M Server: failed to publish reply */
    public static int ERROR_PUBLISH_REPLY_FAILED = ERROR + 10;

    /** O2M Server: failed to find request handler. */
    public static int WARN_REQUEST_HANDLER_NOT_FOUND = WARN + 1;

    /** O2M Server: failed to find client session. */
    public static int WARN_CLIENT_SESSION_NOT_FOUND = WARN + 2;

    /** O2M Server: event changed. */
    public static int INFO_STATE_CHANGED = INFO + 1;

    /**
     * Events for server.
     * 
     * @param eventId
     */
    public O2mServerEventArgs(int eventId) {
        this(eventId, null, null);
    }

    /**
     * Events for server.
     * 
     * @param eventId
     * @param text
     */
    public O2mServerEventArgs(int eventId, @Nullable String text) {
        this(eventId, text, null);
    }

    /**
     * Events for server.
     * 
     * @param eventId
     * @param reason
     */
    public O2mServerEventArgs(int eventId, @Nullable Throwable reason) {
        this(eventId, null, reason);
    }

    /**
     * Events for server.
     * 
     * @param eventId
     * @param text
     * @param reason
     */
    public O2mServerEventArgs(int eventId, @Nullable String text, @Nullable Throwable reason) {
        super(eventId, text, reason);
    }
}
