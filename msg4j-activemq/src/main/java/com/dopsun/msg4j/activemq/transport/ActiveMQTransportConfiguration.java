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

package com.dopsun.msg4j.activemq.transport;

import com.dopsun.msg4j.core.delivery.transports.ConsumerMode;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class ActiveMQTransportConfiguration {
    private String brokerUrl;

    private ConsumerMode consumerMode = ConsumerMode.AUTO;

    private boolean isTransacted;

    /**
     * @return the brokerUrl
     */
    public String getBrokerUrl() {
        return brokerUrl;
    }

    /**
     * @param brokerUrl
     *            the brokerUrl to set
     */
    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    /**
     * @return the isTransacted
     */
    public boolean isTransacted() {
        return isTransacted;
    }

    /**
     * @param isTransacted
     *            the isTransacted to set
     */
    public void setTransacted(boolean isTransacted) {
        this.isTransacted = isTransacted;
    }

    /**
     * @return the consumerMode
     */
    public ConsumerMode getConsumerMode() {
        return consumerMode;
    }

    /**
     * @param consumerMode
     *            the consumerMode to set
     */
    public void setConsumerMode(ConsumerMode consumerMode) {
        this.consumerMode = consumerMode;
    }
}
