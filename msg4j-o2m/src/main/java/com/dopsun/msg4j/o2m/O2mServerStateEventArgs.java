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

import java.util.Objects;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class O2mServerStateEventArgs extends O2mServerEventArgs {
    private static final String TEXT_FORMAT = "State changed from %1$s to %2$s.";

    private final O2mServerState oldState;
    private final O2mServerState newState;

    /**
     * Events for transport.
     * 
     * @param oldState
     * @param newState
     */
    public O2mServerStateEventArgs(O2mServerState oldState, O2mServerState newState) {
        super(INFO_STATE_CHANGED, String.format(TEXT_FORMAT, oldState, newState),
                /* reason */ null);

        Objects.requireNonNull(oldState);
        Objects.requireNonNull(newState);

        this.oldState = oldState;
        this.newState = newState;
    }

    /**
     * @return the oldState
     */
    public O2mServerState getOldState() {
        return oldState;
    }

    /**
     * @return the newState
     */
    public O2mServerState getNewState() {
        return newState;
    }
}
