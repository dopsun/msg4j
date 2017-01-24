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
 * It defines whether producers wait for acknowledges.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public enum ProducerMode {
    /**
     * Message is not expected to be persisted by underlying messaging service. As a side effect,
     * the messaging service may, or may not acknowledge whether message delivered successfully.
     * <p>
     * Messages can be dropped if non persistent.
     * </p>
     */
    NON_PERSISTENT((byte) 1),

    /**
     * Message is expected to be persisted by underlying messaging service, and survive during
     * messaging service failure.
     * 
     * <pre>
     *  - - - - - - - - - - - - - - - - - - - - - - - - - -
     *  |          |                             |        |
     *  |          |      1. message             |        |
     *  |          |    - - - - - - - - - - - >  |        |
     *  | PRODUCER |                             | BROKER |
     *  |          |      2. acknowledge         |        |
     *  |          |  < - - - - - - - - - - -    |        |
     *  |          |                             |        |
     *  - - - - - - - - - - - - - - - - - - - - - - - - - -
     * </pre>
     * 
     * <p>
     * Generally speaking, this will be slower than messages delivered via {@link #NON_PERSISTENT}.
     * </p>
     */
    PERSISTENT((byte) 2);

    private final byte value;

    private ProducerMode(byte value) {
        this.value = value;
    }

    /**
     * @return numeric value for mode
     */
    public byte value() {
        return value;
    }
}
