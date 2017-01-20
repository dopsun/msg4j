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

package com.dopsun.msg4j.core;

import java.util.Optional;

/**
 * A message.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public interface Message extends MessageReader {
    /**
     * Gets a value indicating whether this message is immutable.
     * 
     * @return <code>true</code> if this message is immutable.
     */
    boolean isImmutable();

    /**
     * @return return this instance if is {@link ImmutableMessage}; otherwise
     *         {@link Optional#empty()}.
     */
    default Optional<ImmutableMessage> asImmutable() {
        if (this instanceof ImmutableMessage) {
            return Optional.of((ImmutableMessage) this);
        }

        return Optional.empty();
    }

    /**
     * @return return this instance if is {@link WritableMessage}; otherwise
     *         {@link Optional#empty()}.
     */
    default Optional<WritableMessage> asWritable() {
        if (this instanceof WritableMessage) {
            return Optional.of((WritableMessage) this);
        }

        return Optional.empty();
    }
}
