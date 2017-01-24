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

import java.util.List;

import org.apache.activemq.broker.BrokerService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dopsun.msg4j.core.delivery.transports.TransportSubscriberSettings;
import com.dopsun.msg4j.core.delivery.transports.TransportSubscription;
import com.dopsun.msg4j.core.delivery.transports.TransportTopic;
import com.dopsun.msg4j.core.messages.Message;
import com.dopsun.msg4j.core.messages.Messages;
import com.dopsun.msg4j.core.messages.WritableMessage;
import com.google.common.collect.Lists;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class ActiveMQTransportTest {
    private static final String BROKER_NAME = "ActiveMQTransportTest";

    private static BrokerService mBroker;

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void onBeforeClass() throws Exception {
        mBroker = new BrokerService();
        mBroker.setBrokerName(BROKER_NAME);
        mBroker.start();
    }

    /**
     * @throws Exception
     */
    @AfterClass
    public static void onAfterClass() throws Exception {
        mBroker.stop();
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSantity() throws Exception {
        ActiveMQTransportConfiguration config = new ActiveMQTransportConfiguration();
        config.setBrokerUrl("vm://" + BROKER_NAME);

        final List<Message> list = Lists.newArrayList();

        try (ActiveMQTransport transport = new ActiveMQTransport(config)) {
            TransportTopic topic = transport.createTopic("TEST", false);
            TransportSubscriberSettings settings = TransportSubscriberSettings.create();

            TransportSubscription subscription = transport.subscribe(topic, settings, (m) -> {
                synchronized (list) {
                    list.add(m);
                    list.notifyAll();
                }
            });

            try {
                WritableMessage msg = Messages.create();
                msg.putString("MSG", "Hello Transport!");
                transport.publish(topic, msg);

                synchronized (list) {
                    if (list.size() == 0) {
                        list.wait(3000);
                    }
                }

                Assert.assertTrue(list.size() == 1);
                Message msgReceived = list.get(0);
                Assert.assertEquals(msg.getString("MSG"), msgReceived.getString("MSG"));
            } finally {
                subscription.close();
            }
        }
    }
}
