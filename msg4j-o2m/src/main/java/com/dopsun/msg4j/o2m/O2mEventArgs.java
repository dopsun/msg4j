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
public abstract class O2mEventArgs {
    /**
     * Event Id prefix for error event id.
     */
    public static final int ERROR = 0x0100_0000;

    /**
     * Event Id prefix for warning event id.
     */
    public static final int WARN = 0x0200_0000;

    /**
     * Event Id prefix for informational event id.
     */
    public static final int INFO = 0x0300_0000;

    /**
     * Event Id prefix for informational event id.
     */
    public static final int DEBUG = 0x0400_0000;

    /**
     * Maximum possible event id. All event ids should be less than this.
     */
    public static final int ALL = 0x0500_0000;

    private final int eventId;

    @Nullable
    private final String text;

    @Nullable
    private final Throwable reason;

    /**
     * @param eventId
     * @param text
     * @param reason
     */
    protected O2mEventArgs(int eventId, @Nullable String text, @Nullable Throwable reason) {
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

    /**
     * @param eventId
     *            event id
     * @return <code>true</code> if event id represents an error
     */
    public boolean isError(int eventId) {
        return eventId >= ERROR && eventId < WARN;
    }

    /**
     * @param eventId
     *            event id
     * @return <code>true</code> if event id represents a warning
     */
    public boolean isWarn(int eventId) {
        return eventId >= WARN && eventId < INFO;
    }

    /**
     * @param eventId
     *            event id
     * @return <code>true</code> if event id represents an information.
     */
    public boolean isInfo(int eventId) {
        return eventId >= INFO && eventId < DEBUG;
    }

    /**
     * @param eventId
     *            event id
     * @return <code>true</code> if event id represents a debug.
     */
    public boolean isDebug(int eventId) {
        return eventId >= DEBUG && eventId < ALL;
    }
}
