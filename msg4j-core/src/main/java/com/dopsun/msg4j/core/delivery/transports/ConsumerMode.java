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
 * It defines how consumer acknowledges received messages.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public enum ConsumerMode {
    /**
     * Consumer will not send acknowledge to underlying messaging service.
     * <p>
     * Not all messaging service providers support this mode. And if not supported, this will be
     * fall back to {@link #DUPS_OK}.
     * </p>
     */
    NO_ACK((byte) 0),

    /**
     * Message will be acknowledged one by one. In most of the messaging service providers, this is
     * the default acknowledge mode.
     * 
     * <p>
     * Depends on messaging service provider, most of the case, it works as below:
     * </p>
     * 
     * <pre>
     *  - - - - - - - - - - - - - - - - - - - - - - - - - -
     *  |        |                             |          |
     *  |        |      1. message             |          |
     *  |        |    - - - - - - - - - - - >  |          |
     *  |        |                             |          |
     *  |        |      2. acknowledge         |          |
     *  | BROKER |  < - - - - - - - - - - -    | CONSUMER |
     *  |        |                             |          |
     *  |        |      3. confirmation of     |          |
     *  |        |         acknowledge         |          |
     *  |        |    - - - - - - - - - - - >  |          |
     *  |        |                             |          |
     *  - - - - - - - - - - - - - - - - - - - - - - - - - -
     * </pre>
     * 
     * <p>
     * <b>Note:</b>
     * </p>
     * 
     * <ol>
     * <li>Consumer receives message, processed and acknowledged successfully.</li>
     * <li>Consumer receives message, processed but crashed before acknowledged.</li>
     * <li>Consumer receives message, but crashed.</li>
     * </ol>
     * 
     * <p>
     * In both 2) and 3) cases, the messages will be delivered against to consumer. But in case 2),
     * consumer has completed message processing, consumer should take care the last duplication
     * messages.
     * </p>
     */
    AUTO((byte) 1),

    /**
     * Application needs to acknowledge messages received.As shown below, all messages sent before
     * acknowledge message, will be acknowledge in a batch.
     * 
     * <pre>
     *  - - - - - - - - - - - - - - - - - - - - - - - - - -
     *  |        |                             |          |
     *  |        |      1. message             |          |
     *  |        |      2. message             |          |
     *  |        |      3. message             |          |
     *  |        |      4. message             |          |
     *  |        |    - - - - - - - - - - - >  |          |
     *  |        |                             |          |
     *  |        |      5. acknowledge         |          |
     *  | BROKER |  < - - - - - - - - - - -    | CONSUMER |
     *  |        |                             |          |
     *  |        |      6. confirmation of     |          |
     *  |        |         acknowledge         |          |
     *  |        |    - - - - - - - - - - - >  |          |
     *  |        |                             |          |
     *  - - - - - - - - - - - - - - - - - - - - - - - - - -
     * </pre>
     * 
     * <p>
     * This mode is useful if consumer wants to batch processing the messages and committed in a
     * single transaction, and acknowledge all messages if transaction committed successfully.
     * </p>
     * 
     */
    CLIENT((byte) 2),

    /**
     * Similar to {@link #CLIENT} mode, messages will be acknowledged in batch. But acknowledge is
     * automatically sent from session, instead of application code.
     * 
     * <p>
     * If messaging service provides does not support this mode, it will be fall back to
     * {@link #AUTO}.
     * </p>
     */
    DUPS_OK((byte) 3);

    private final byte value;

    private ConsumerMode(byte value) {
        this.value = value;
    }

    /**
     * @return numeric value for mode
     */
    public byte value() {
        return value;
    }

}
