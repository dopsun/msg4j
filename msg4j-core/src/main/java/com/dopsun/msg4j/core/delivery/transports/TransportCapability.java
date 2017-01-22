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

/**
 * Transport capability.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public enum TransportCapability {
    /**
     * Messages stored in transport and guaranteed to be delivered to consumers.
     */
    Guaranteed,

    /**
     * Consumer can reconnect restarted
     */
    DurableSubscriber,

    /**
     * Supports selector while subscription.
     */
    Selector,

    /**
     * Consumer can browse messages without removing messages from underneath transport.
     */
    Browse,

    /**
     * Only last message will be stored.
     */
    LastValue,

    /**
     * Supports transaction.
     */
    Transaction
}
