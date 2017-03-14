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

package com.dopsun.msg4j.activemq.o2m;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jms.JMSException;

import org.apache.activemq.broker.BrokerService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dopsun.msg4j.activemq.transport.ActiveMQTransport;
import com.dopsun.msg4j.activemq.transport.ActiveMQTransportConfiguration;
import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.Messages;
import com.dopsun.msg4j.core.messages.WritableMessage;
import com.dopsun.msg4j.o2m.O2mClient;
import com.dopsun.msg4j.o2m.O2mClientConfiguration;
import com.dopsun.msg4j.o2m.O2mClientState;
import com.dopsun.msg4j.o2m.O2mClientStateEventArgs;
import com.dopsun.msg4j.o2m.O2mClientSubscription;
import com.dopsun.msg4j.o2m.O2mClientSubscriptionMode;
import com.dopsun.msg4j.o2m.O2mServer;
import com.dopsun.msg4j.o2m.O2mServerConfiguration;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
public class ActiveMQO2mTest {
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

    private O2mServerConfiguration createServerConfig(String caseId) {
        O2mServerConfiguration serverConfig = O2mServerConfiguration.create();

        serverConfig.setServerId(caseId + ".SERVER");

        serverConfig.setSystemSubject(caseId + ".SYS");
        serverConfig.setRequestSubject(caseId + ".REQ");
        serverConfig.setPublishSubjectProducer(caseId + ".PUB.%1$s");
        serverConfig.setSessionSubjectProducer(caseId + ".SSN.%1$s");

        return serverConfig;
    }

    private O2mClientConfiguration createClientConfig(String caseId) {
        O2mClientConfiguration clientConfig = O2mClientConfiguration.create();
        clientConfig.setClientId(caseId + ".CLEINT");
        clientConfig.setServerId(caseId + ".SERVER");
        clientConfig.setVersion("1.0.0");

        clientConfig.setSystemSubject(caseId + ".SYS");
        clientConfig.setRequestSubject(caseId + ".REQ");
        clientConfig.setPublishSubjectProducer(caseId + ".PUB.%1$s");
        clientConfig.setSessionSubjectProducer(caseId + ".SSN.%1$s");

        return clientConfig;
    }

    @Test
    public void givenTransportWhenStartServerThenServerStartedSuccessfully()
            throws JMSException, Exception {
        ActiveMQTransportConfiguration config = new ActiveMQTransportConfiguration();
        config.setBrokerUrl("vm://" + BROKER_NAME);

        try (ActiveMQTransport transport = new ActiveMQTransport(config)) {
            O2mServerConfiguration serverConfig = O2mServerConfiguration.create();

            serverConfig.setServerId("TEST");
            serverConfig.setSystemSubject("TEST.SYS");
            serverConfig.setRequestSubject("TEST.REQ");
            serverConfig.setPublishSubjectProducer("TEST.PUB.%1$s");
            serverConfig.setSessionSubjectProducer("TEST.SSN.%1$s");

            try (O2mServer server = O2mServer.create(transport, serverConfig)) {

            }
        }
    }

    @Test
    public void givenTransportWhenServerStaratedThenClientConnectedSuccessfully()
            throws JMSException, Exception {
        ActiveMQTransportConfiguration config = new ActiveMQTransportConfiguration();
        config.setBrokerUrl("vm://" + BROKER_NAME);

        try (ActiveMQTransport transport = new ActiveMQTransport(config)) {
            O2mServerConfiguration serverConfig = createServerConfig("TEST");
            O2mClientConfiguration clientConfig = createClientConfig("TEST");

            try (O2mServer server = O2mServer.create(transport, serverConfig)) {
                try (O2mClient client = O2mClient.create(transport, clientConfig)) {
                    Assert.assertEquals(O2mClientState.ACTIVE, client.getState());
                }
            }
        }
    }

    @Test
    public void givenServiceConnectedWhenClientSendRequestThenServerReplies()
            throws JMSException, Exception {
        ActiveMQTransportConfiguration config = new ActiveMQTransportConfiguration();
        config.setBrokerUrl("vm://" + BROKER_NAME);

        try (ActiveMQTransport transport = new ActiveMQTransport(config)) {
            O2mServerConfiguration serverConfig = createServerConfig("TEST");
            serverConfig.setRequestHandler((session, request) -> {
                WritableMessage msg = Messages.create();
                msg.putLong("ID", request.getLong("ID"));
                return msg.toImmutable();
            });

            O2mClientConfiguration clientConfig = createClientConfig("TEST");

            try (O2mServer server = O2mServer.create(transport, serverConfig)) {
                try (O2mClient client = O2mClient.create(transport, clientConfig)) {
                    long id = System.currentTimeMillis();

                    WritableMessage msgRequest = Messages.create();
                    msgRequest.putLong("ID", id);

                    ImmutableMessage msgReply = client.request(msgRequest.toImmutable()).get();
                    Assert.assertEquals(id, msgReply.getLong("ID"));
                }
            }
        }
    }

    @Test
    public void givenServiceConnectedWhenServerPublishThenClientReceived()
            throws JMSException, Exception {
        ActiveMQTransportConfiguration config = new ActiveMQTransportConfiguration();
        config.setBrokerUrl("vm://" + BROKER_NAME);

        final ConcurrentMap<String, ImmutableMessage> map = new ConcurrentHashMap<>();
        final CountDownLatch latch = new CountDownLatch(1);

        try (ActiveMQTransport transport = new ActiveMQTransport(config)) {
            O2mServerConfiguration serverConfig = createServerConfig("TEST");
            O2mClientConfiguration clientConfig = createClientConfig("TEST");

            try (O2mServer server = O2mServer.create(transport, serverConfig)) {
                try (O2mClient client = O2mClient.create(transport, clientConfig)) {
                    try (O2mClientSubscription subscription = client.subscribe("GREETINGS",
                            O2mClientSubscriptionMode.REALTIME, (isSnapshot, message) -> {
                                map.put(message.getString("ID"), message);
                                latch.countDown();
                            })) {

                        subscription.waitForReply(3, TimeUnit.SECONDS);

                        String id = System.currentTimeMillis() + "";
                        WritableMessage msgData = Messages.create();
                        msgData.putString("ID", id);
                        server.publish("GREETINGS", msgData.toImmutable());

                        latch.await(3, TimeUnit.SECONDS);

                        Assert.assertEquals(1, map.size());
                        Assert.assertEquals(id, map.get(id).getString("ID"));
                    }
                }
            }
        }
    }

    @Test
    public void givenServiceConnectedWhenAnotherServerStartedThenClientEntersFailedState()
            throws JMSException, Exception {
        ActiveMQTransportConfiguration config = new ActiveMQTransportConfiguration();
        config.setBrokerUrl("vm://" + BROKER_NAME);

        final AtomicBoolean clientFailed = new AtomicBoolean(false);
        final CountDownLatch latch = new CountDownLatch(1);

        try (ActiveMQTransport transport = new ActiveMQTransport(config)) {
            O2mServerConfiguration serverConfig = createServerConfig("TEST");
            O2mClientConfiguration clientConfig = createClientConfig("TEST");

            try (O2mServer server = O2mServer.create(transport, serverConfig)) {
                try (O2mClient client = O2mClient.create(transport, clientConfig)) {
                    client.addEventListener((o2mClient, args) -> {
                        if (args instanceof O2mClientStateEventArgs) {
                            O2mClientStateEventArgs stateEventArgs = (O2mClientStateEventArgs) args;
                            if (stateEventArgs.getNewState() == O2mClientState.FAILED) {
                                clientFailed.set(true);
                                latch.countDown();
                            }
                        }
                    });

                    try (O2mServer server2 = O2mServer.create(transport, serverConfig)) {

                    }

                    latch.await(3, TimeUnit.SECONDS);

                    Assert.assertEquals(true, clientFailed.get());
                }
            }
        }
    }

    @Test
    public void givenServiceConnectedWhenServerStoppedThenClientEntersFailedState()
            throws JMSException, Exception {
        ActiveMQTransportConfiguration config = new ActiveMQTransportConfiguration();
        config.setBrokerUrl("vm://" + BROKER_NAME);

        final AtomicBoolean clientFailed = new AtomicBoolean(false);
        final CountDownLatch latch = new CountDownLatch(1);

        try (ActiveMQTransport transport = new ActiveMQTransport(config)) {
            O2mServerConfiguration serverConfig = createServerConfig("TEST");
            O2mClientConfiguration clientConfig = createClientConfig("TEST");

            O2mServer server = O2mServer.create(transport, serverConfig);

            try (O2mClient client = O2mClient.create(transport, clientConfig)) {
                client.addEventListener((o2mClient, args) -> {
                    if (args instanceof O2mClientStateEventArgs) {
                        O2mClientStateEventArgs stateEventArgs = (O2mClientStateEventArgs) args;
                        if (stateEventArgs.getNewState() == O2mClientState.FAILED) {
                            clientFailed.set(true);
                            latch.countDown();
                        }
                    }
                });

                server.close();
                latch.await(3, TimeUnit.SECONDS);

                Assert.assertEquals(true, clientFailed.get());
            }
        }
    }
}
