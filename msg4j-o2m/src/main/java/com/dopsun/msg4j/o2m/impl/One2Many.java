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

import java.util.Objects;

import com.dopsun.msg4j.core.delivery.transports.Transport;
import com.dopsun.msg4j.core.delivery.transports.TransportException;
import com.dopsun.msg4j.o2m.O2mClient;
import com.dopsun.msg4j.o2m.O2mClientConfiguration;
import com.dopsun.msg4j.o2m.O2mServer;
import com.dopsun.msg4j.o2m.O2mServerConfiguration;
import com.dopsun.msg4j.o2m.O2mServiceException;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public final class One2Many {
    /**
     * Creates a new {@link O2mClient}.
     * 
     * @param transport
     * @param config
     * @return
     * 
     * @throws TransportException
     * @throws O2mServiceException
     * 
     * @throws InterruptedException
     *             if connecting was interrupted.
     */
    public static O2mClient create(Transport transport, O2mClientConfiguration config)
            throws TransportException, O2mServiceException, InterruptedException {
        Objects.requireNonNull(transport);
        Objects.requireNonNull(config);

        return new O2mClientImpl(transport, config);
    }

    /**
     * @param transport
     * @param config
     * @return
     * @throws TransportException
     * @throws O2mServiceException
     */
    public static O2mServer create(Transport transport, O2mServerConfiguration config)
            throws TransportException, O2mServiceException {
        Objects.requireNonNull(transport);
        Objects.requireNonNull(config);

        return new O2mServerImpl(transport, config);
    }

    private One2Many() {
    }
}
