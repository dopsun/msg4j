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

import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiConsumer;

import com.dopsun.msg4j.core.messages.ImmutableMessage;

/**
 * This is a trim down version of the {@link Collection}. Reason to define a new interface, is to
 * specify what's the features to be used, and gives flexibility for future extension.
 * 
 * <p>
 * {@link #forEach(BiConsumer)} should be run in a thread safe manner.
 * </p>
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
@FunctionalInterface
public interface O2mServerSnapshot {
    /**
     * Creates a snapshot from a collection of messages.
     * 
     * @param iterable
     *            snapshot messages.
     * @return
     */
    public static O2mServerSnapshot fromCollection(final Iterable<ImmutableMessage> iterable) {
        return (consumer) -> {
            int count = 0;
            Iterator<ImmutableMessage> iterator = iterable.iterator();
            boolean hasNext = iterator.hasNext();

            while (hasNext) {
                count++;

                ImmutableMessage next = iterator.next();
                hasNext = iterator.hasNext();

                consumer.accept(next, Boolean.valueOf(hasNext));
            }

            return count;
        };
    }

    /**
     * Empty snapshot.
     */
    public static O2mServerSnapshot EMPTY = consumer -> 0;

    /**
     * @param consumer
     * @return
     */
    int forEach(BiConsumer<ImmutableMessage, Boolean> consumer);
}
