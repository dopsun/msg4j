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

import java.time.Duration;
import java.util.Optional;

import com.dopsun.msg4j.o2m.O2mServerRequestHandler;
import com.dopsun.msg4j.o2m.O2mServerSnapshotProvider;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
interface ServerRequestHandlerContext {
    String getServerId();

    String getServerSessionId();

    String getVersion();

    Duration getHeartbeatInterval();

    O2mServerRequestHandler getUserRequestHandler();

    O2mServerEvents getEvents();

    /**
     * @param clientSessionId
     * @return
     */
    Optional<O2mServerClientSessionImpl> getClientSession(String clientSessionId);

    Optional<O2mServerClientSessionImpl> getClientSessionById(String clientId);

    O2mServerClientSessionImpl createClientSession(String clientId, String clientSessionId);

    ChannelPublishPublisher<Void> getOrCreatePublishPublisher(String channelCode);

    O2mServerSnapshotProvider getSnapshotProvider();
}
