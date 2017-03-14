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

/**
 * State of O2M client.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public enum O2mClientState {
    /**
     * Initializing. E.g. subscribe session channel.
     * 
     * <p>
     * Next state:
     * <ul>
     * <li>{@link #CONNECTING}: initialized successfully.</li>
     * <li>{@link #FAILED}: initialization failed.</li>
     * </ul>
     * </p>
     */
    INITIALIZING,

    /**
     * Client is trying to connecting to server. And it keeps sending
     * <code>CLIENT_CONNECT_REQUEST</code> till the server accepts.
     * 
     * <p>
     * This is the state once {@link #INITIALIZING} finished, or when server heartbeats missing.
     * </p>
     * 
     * <p>
     * Next state:
     * <ul>
     * <li>{@link #ACTIVE}: connected successfully.</li>
     * <li>{@link #FAILED}: connection failed, server does not reply.</li>
     * </ul>
     * </p>
     */
    CONNECTING,

    /**
     * Client connected and server responses returned.
     * 
     * <p>
     * Next state:
     * <ul>
     * <li>{@link #CONNECTING}: server heartbeat missed.</li>
     * <li>{@link #DISCONNECTING}: {@link O2mClient#close()} was called, or server publishes stopped
     * message.</li>
     * <li>{@link #FAILED}: underlying transport is closing.</li>
     * </ul>
     * </p>
     */
    ACTIVE,

    /**
     * Client tries to closing the connection.
     * 
     * <p>
     * Next state:
     * <ul>
     * <li>{@link #FINALIZING}: disconnected and server replied.</li>
     * <li>{@link #FAILED}: disconnect request sent but server does not replied.</li>
     * <li>{@link #CLOSED}: disconnected and does not wait for server reply.</li>
     * </ul>
     * </p>
     */
    DISCONNECTING,

    /**
     * Once disconnected, finalizing the client, un-subscribe session channel etc.
     * 
     * <p>
     * Next state:
     * <ul>
     * <li>{@link #CLOSED}: finalizing successfully.</li>
     * <li>{@link #FAILED}: finalizing failed.</li>
     * </ul>
     * </p>
     */
    FINALIZING,

    /**
     * Client closes successfully.
     * 
     * <p>
     * This is the final state of client.
     * </p>
     */
    CLOSED,

    /**
     * Channel in error state, e.g. connecting failed.
     * 
     * <p>
     * This is the final state of client.
     * </p>
     */
    FAILED
}
