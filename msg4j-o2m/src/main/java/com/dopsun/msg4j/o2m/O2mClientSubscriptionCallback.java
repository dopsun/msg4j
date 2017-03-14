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

import com.dopsun.msg4j.core.messages.ImmutableMessage;

/**
 * O2mClient subscription callback.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
@FunctionalInterface
public interface O2mClientSubscriptionCallback {

    /**
     * @param isSnapshot
     *            <code>true</code> if message is snapshot
     * @param message
     *            message received
     */
    void accept(boolean isSnapshot, ImmutableMessage message);
}
