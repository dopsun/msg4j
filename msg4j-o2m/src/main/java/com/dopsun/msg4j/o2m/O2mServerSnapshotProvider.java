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

import com.dopsun.msg4j.core.messages.ImmutableMessage;

/**
 * Snapshot provider for {@link O2mServer}, is called when subscription request received from
 * client.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public interface O2mServerSnapshotProvider {
    /**
     * A snapshot provider always returns {@link O2mServerSnapshot#EMPTY}.
     */
    public static O2mServerSnapshotProvider EMPTY = new O2mServerSnapshotProvider() {
        @Override
        public void onPublished(String channelCode, ImmutableMessage message) {
        }

        @Override
        public O2mServerSnapshot apply(O2mServerClientSession session, String channelCode,
                ImmutableMessage filter) {
            return O2mServerSnapshot.EMPTY;
        }
    };

    /**
     * @param session
     * @param channelCode
     * @param filter
     * @return
     */
    O2mServerSnapshot apply(O2mServerClientSession session, String channelCode,
            @Nullable ImmutableMessage filter);

    /**
     * @param channelCode
     * @param message
     */
    void onPublished(String channelCode, ImmutableMessage message);
}
