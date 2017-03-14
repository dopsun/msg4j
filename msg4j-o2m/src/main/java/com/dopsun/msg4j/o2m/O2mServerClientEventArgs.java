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

import javax.annotation.Nullable;

/**
 * O2mServer events for client related.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public class O2mServerClientEventArgs extends O2mServerEventArgs {
    private final O2mServerClientSession clientSession;

    /**
     * Events for transport.
     * 
     * @param eventId
     * @param clientSession
     * @param text
     */
    public O2mServerClientEventArgs(int eventId, O2mServerClientSession clientSession,
            @Nullable String text) {
        super(eventId, text, /* reason */ null);

        Objects.requireNonNull(clientSession);

        this.clientSession = clientSession;
    }

    /**
     * @return the clientSession
     */
    public O2mServerClientSession getClientSession() {
        return clientSession;
    }
}
