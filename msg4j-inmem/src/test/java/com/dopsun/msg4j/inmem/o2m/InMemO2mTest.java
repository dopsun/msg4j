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

package com.dopsun.msg4j.inmem.o2m;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dopsun.msg4j.inmem.transports.InMemBroker;
import com.dopsun.msg4j.inmem.transports.InMemTransport;
import com.dopsun.msg4j.o2m.O2mClient;
import com.dopsun.msg4j.o2m.O2mClientConfiguration;
import com.dopsun.msg4j.o2m.O2mClientState;
import com.dopsun.msg4j.o2m.O2mServer;
import com.dopsun.msg4j.o2m.O2mServerConfiguration;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
public class InMemO2mTest {
    private static InMemBroker broker;

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void onBeforeClass() throws Exception {
        broker = new InMemBroker();
    }

    /**
     * @throws Exception
     */
    @AfterClass
    public static void onAfterClass() throws Exception {
        broker.close();
    }

    @Test
    public void givenTransportWhenStartServerThenServerStartedSuccessfully() throws Exception {
        try (InMemTransport transport = broker.createTransport()) {
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
    public void givenTransportWhenServerStaratedThenClientConnectedSuccessfully() throws Exception {
        try (InMemTransport transport = broker.createTransport()) {
            O2mServerConfiguration serverConfig = O2mServerConfiguration.create();
            serverConfig.setServerId("TestServer");
            serverConfig.setSystemSubject("TEST.SYS");
            serverConfig.setRequestSubject("TEST.REQ");
            serverConfig.setPublishSubjectProducer("TEST.PUB.%1$s");
            serverConfig.setSessionSubjectProducer("TEST.SSN.%1$s");

            O2mClientConfiguration clientConfig = O2mClientConfiguration.create();
            clientConfig.setClientId("TestClient");
            clientConfig.setServerId("TestServer");
            clientConfig.setVersion("1.0.0");
            clientConfig.setSystemSubject("TEST.SYS");
            clientConfig.setRequestSubject("TEST.REQ");
            clientConfig.setPublishSubjectProducer("TEST.PUB.%1$s");
            clientConfig.setSessionSubjectProducer("TEST.SSN.%1$s");

            try (O2mServer server = O2mServer.create(transport, serverConfig)) {
                try (O2mClient client = O2mClient.create(transport, clientConfig)) {
                    Assert.assertEquals(O2mClientState.ACTIVE, client.getState());
                }
            }
        }
    }
}
