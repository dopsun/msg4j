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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Represents a subscription from client to server.
 * 
 * @see O2mClient#subscribe(String, O2mClientSubscriptionMode, O2mClientSubscriptionCallback)
 * @see O2mClient#subscribe(String, O2mClientSubscriptionMode, O2mClientSubscriptionFilter,
 *      O2mClientSubscriptionCallback)
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public interface O2mClientSubscription extends AutoCloseable {
    /**
     * Wait for reply coming.
     * 
     * @param timeout
     *            the maximum time to wait
     * @param unit
     *            the time unit of the timeout argument
     * 
     * @throws ExecutionException
     *             if reply returned with an exception.
     * @throws InterruptedException
     *             if the current thread was interrupted while waiting
     * @throws TimeoutException
     *             if the wait timed out
     */
    void waitForReply(long timeout, TimeUnit unit)
            throws ExecutionException, TimeoutException, InterruptedException;

    /**
     * Wait for snapshot completed.
     * 
     * @param timeout
     *            the maximum time to wait
     * @param unit
     *            the time unit of the timeout argument
     * 
     * @throws ExecutionException
     *             if snapshot coming with errors
     * @throws InterruptedException
     *             if the current thread was interrupted while waiting
     * @throws TimeoutException
     *             if the wait timed out
     */
    void waitForSnapshotCompleted(long timeout, TimeUnit unit)
            throws ExecutionException, TimeoutException, InterruptedException;
}
