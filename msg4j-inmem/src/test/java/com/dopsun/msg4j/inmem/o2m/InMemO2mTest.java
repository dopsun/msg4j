/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.inmem.o2m;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dopsun.msg4j.core.delivery.o2m.O2mClient;
import com.dopsun.msg4j.core.delivery.o2m.O2mClientConfiguration;
import com.dopsun.msg4j.core.delivery.o2m.O2mClientState;
import com.dopsun.msg4j.core.delivery.o2m.O2mServer;
import com.dopsun.msg4j.core.delivery.o2m.O2mServerConfiguration;
import com.dopsun.msg4j.core.delivery.o2m.One2Many;
import com.dopsun.msg4j.inmem.transports.InMemBroker;
import com.dopsun.msg4j.inmem.transports.InMemTransport;

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
            O2mServerConfiguration serverConfig = One2Many.createServerConfiguration();

            serverConfig.setServerId("TEST");
            serverConfig.setVersion("1.0.0");
            serverConfig.setSystemSubject("TEST.SYS");
            serverConfig.setRequestSubject("TEST.REQ");
            serverConfig.setPublishSubjectPattern("TEST.PUB.%1$s");
            serverConfig.setSessionSubjectPattern("TEST.SSN.%1$s");

            try (O2mServer server = One2Many.createServer(transport, serverConfig)) {

            }
        }
    }

    @Test
    public void givenTransportWhenServerStaratedThenClientConnectedSuccessfully() throws Exception {
        try (InMemTransport transport = broker.createTransport()) {
            O2mServerConfiguration serverConfig = One2Many.createServerConfiguration();
            serverConfig.setServerId("TestServer");
            serverConfig.setVersion("1.0.0");
            serverConfig.setSystemSubject("TEST.SYS");
            serverConfig.setRequestSubject("TEST.REQ");
            serverConfig.setPublishSubjectPattern("TEST.PUB.%1$s");
            serverConfig.setSessionSubjectPattern("TEST.SSN.%1$s");

            O2mClientConfiguration clientConfig = One2Many.createClientConfiguration();
            clientConfig.setClientId("TestClient");
            clientConfig.setServerId("TestServer");
            clientConfig.setVersion("1.0.0");
            clientConfig.setSystemSubject("TEST.SYS");
            clientConfig.setRequestSubject("TEST.REQ");
            clientConfig.setPublishSubjectPattern("TEST.PUB.%1$s");
            clientConfig.setSessionSubjectPattern("TEST.SSN.%1$s");

            try (O2mServer server = One2Many.createServer(transport, serverConfig)) {
                try (O2mClient client = One2Many.createClient(transport, clientConfig)) {
                    Assert.assertEquals(O2mClientState.ACTIVE, client.getState());
                }
            }
        }
    }
}
