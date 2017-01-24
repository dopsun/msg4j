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

import java.util.Objects;

import javax.annotation.Nullable;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public final class TransportSubscriberSettings {
    /**
     * @return creates a settings
     */
    public static TransportSubscriberSettings create() {
        return new TransportSubscriberSettings();
    }

    private boolean browsingOnly;

    private boolean durable;

    @Nullable
    private String name;

    @Nullable
    private String selector;

    TransportSubscriberSettings() {
    }

    /**
     * This requires {@link Transport#capabilities()} to include
     * {@link TransportCapability#DurableSubscriber}.
     * 
     * @param name
     *            durable subscriber name
     * @return this settings
     */
    public TransportSubscriberSettings name(String name) {
        Objects.requireNonNull(name);

        this.name = name;
        return this;
    }

    /**
     * This requires {@link Transport#capabilities()} to include
     * {@link TransportCapability#DurableSubscriber}.
     * 
     * @return this settings
     */
    public TransportSubscriberSettings durable() {
        this.durable = true;

        return this;
    }

    /**
     * To include selector. This requires {@link Transport#capabilities()} to include
     * {@link TransportCapability#Selector}.
     * 
     * @param selector
     *            message selector
     * @return this settings
     */
    public TransportSubscriberSettings selector(String selector) {
        Objects.requireNonNull(selector);

        this.selector = selector;
        return this;
    }

    /**
     * For browsing the data only. This requires {@link Transport#capabilities()} to include
     * {@link TransportCapability#Browse}.
     * 
     * @return this settings
     */
    public TransportSubscriberSettings browsingOnly() {
        browsingOnly = true;
        return this;
    }

    /**
     * @return the browsingOnly
     */
    public boolean isBrowsingOnly() {
        return browsingOnly;
    }

    /**
     * @return <code>true</code> if this is for durable subscriber.
     */
    public boolean isDurable() {
        return durable;
    }

    /**
     * @return the durable subscriber name. Not <code>null</code> if this is for durable subscriber.
     */
    @Nullable
    public String getName() {
        return name;
    }

    /**
     * @return the selector
     */
    @Nullable
    public String getSelector() {
        return selector;
    }
}
