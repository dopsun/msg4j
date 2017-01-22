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

package com.dopsun.msg4j.core.delivery.transports;

import javax.annotation.Nullable;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class TransportEventArgs {
    /** Transport closing. */
    public static int CLOSING_EVENT_ID = 0x010001;

    /** Transport closed. */
    public static int CLOSED_EVENT_ID = 0x010002;

    /** Transport exception. */
    public static int EXCEPTION_EVENT_ID = 0x020001;

    private final int eventId;

    @Nullable
    private final String text;

    @Nullable
    private final Throwable reason;

    /**
     * Events for transport.
     * 
     * @param eventId
     * @param text
     * @param reason
     */
    public TransportEventArgs(int eventId, @Nullable String text, @Nullable Throwable reason) {
        this.eventId = eventId;
        this.text = text;
        this.reason = reason;
    }

    /**
     * @return the eventId
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * @return the text
     */
    @Nullable
    public String getText() {
        return text;
    }

    /**
     * @return the reason
     */
    @Nullable
    public Throwable getReason() {
        return reason;
    }
}
