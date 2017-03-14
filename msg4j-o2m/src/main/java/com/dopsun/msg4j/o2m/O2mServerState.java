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
 * @author Dop Sun
 * @since 1.0.0
 */
public enum O2mServerState {
    /**
     * Initializes the server, e.g. hooking event listener to underlying transport.
     */
    INITIALIZING,

    /**
     * Starting up the server, including publishing heatbeat message, listening incoming requests.
     */
    STARTING,

    /**
     * Server is ready for accepting new requests, and publishing messages.
     * 
     * <p>
     * Next state:
     * <ul>
     * <li>{@link #STOPPING}: if {@link O2mServer#close()} called.</li>
     * <li>{@link #FAILED}: underlying transport is closing.</li>
     * </ul>
     * </p>
     */
    ACTIVE,

    /**
     * Server closing down. {@link O2mServer#close()} is called. Server sends STOPPED message,
     * unsubscribe request channel. Stop publishing heartbeat messages.
     * 
     * <p>
     * Next state:
     * <ul>
     * <li>{@link #FINALIZING}: closing successfully.</li>
     * <li>{@link #FAILED}: closing failed.</li>
     * </ul>
     * </p>
     */
    STOPPING,

    /**
     * Once disconnected, finalizing the client, cleaning up local resources.
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
