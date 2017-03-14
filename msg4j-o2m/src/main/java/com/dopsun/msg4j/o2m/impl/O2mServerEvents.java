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

package com.dopsun.msg4j.o2m.impl;

import java.util.Objects;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dopsun.msg4j.core.messages.Message;
import com.dopsun.msg4j.o2m.O2mServerEventArgs;
import com.dopsun.msg4j.o2m.O2mServerEventListener;
import com.dopsun.msg4j.o2m.O2mServerState;
import com.dopsun.msg4j.o2m.O2mServerStateEventArgs;
import com.google.common.collect.ImmutableList;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class O2mServerEvents {
    private static final Logger log = LoggerFactory.getLogger(O2mServerEvents.class);

    private final O2mServerImpl o2mServer;

    /**
     * Listers is volatile and immutable. It can be replaced as a whole.
     * 
     * <p>
     * <ul>
     * <li>{@link #addEventListener(O2mServerEventListener)}: to add event listener.</li>
     * <li>{@link #removeEventListener(O2mServerEventListener)}: to remove event listener.</li>
     * <li><code>trigerServerEvent</code>: to call events.</li>
     * </ul>
     * </p>
     */
    private volatile ImmutableList<O2mServerEventListener> eventListeners = ImmutableList.of();

    public O2mServerEvents(O2mServerImpl o2mServer) {
        Objects.requireNonNull(o2mServer);

        this.o2mServer = o2mServer;
    }

    public void onFailedToCreateTopic(String subject, Exception cause) {
        final String FORMAT = "Failed to create topic for subject: %1$s.";
        String text = String.format(FORMAT, subject);

        log.error(text, cause);

        triggerEvent(O2mServerEventArgs.ERROR_CREATE_TOPIC_FAILED, text, cause);
    }

    public void onPublishSystemFailed(Message message, Exception cause) {
        final String FORMAT = "Failed to publish system message: %1$s";
        String text = String.format(FORMAT, message);

        log.error(text, cause);

        triggerEvent(O2mServerEventArgs.ERROR_PUBLISH_SYSTEM_FAILED, text, cause);
    }

    public void onPublishHeartbeatFailed(Exception cause) {
        final String text = "Failed to publish heartbeat";

        log.error(text, cause);

        triggerEvent(O2mServerEventArgs.ERROR_PUBLISH_HEARTBEAT_FAILED, text, cause);
    }

    public void onPublishSessionFailed(String clientId, String clientSessionId, Message message,
            Exception cause) {
        final String FORMAT = "Failed to publish session message. ClientId: %1$s, ClientSession: %2$s, message: %3$s";
        String text = String.format(FORMAT, clientId, clientSessionId, message);

        log.error(text, cause);

        triggerEvent(O2mServerEventArgs.ERROR_PUBLISH_SESSION_FAILED, text, cause);
    }

    public void onPublishClientSessionReplacedFailed(String clientId, String clientSessionId,
            Exception cause) {
        final String FORMAT = "Failed to publish client session replaced message. ClientId: %1$s, ClientSession: %2$s.";
        String text = String.format(FORMAT, clientId, clientSessionId);

        log.error(text, cause);

        triggerEvent(O2mServerEventArgs.ERROR_PUBLISH_SESSION_FAILED, text, cause);
    }

    public void onPublishPublishChannelFailed(String channelCode, Exception cause) {
        final String FORMAT = "Failed to publish publish channel message. channelCode: %1$s.";
        String text = String.format(FORMAT, channelCode);

        log.error(text, cause);

        triggerEvent(O2mServerEventArgs.ERROR_PUBLISH_PUBLISH_FAILED, text, cause);
    }

    public void onPublishSubscribeReplyFailed(String clientSessionId, String channelCode,
            Exception cause) {
        final String FORMAT = "Failed to publish subscribe reply message. session: %1$s, channelCode: %2$s.";
        String text = String.format(FORMAT, clientSessionId, channelCode);

        log.error(text, cause);

        triggerEvent(O2mServerEventArgs.ERROR_PUBLISH_SUB_REPLY_FAILED, text, cause);
    }

    public void onPublishSnapshotFailed(String clientSessionId, Message message, Exception cause) {
        final String FORMAT = "Failed to publish channel snapshot message. session: %1$s, message: %2$s.";
        String text = String.format(FORMAT, clientSessionId, message);

        log.error(text, cause);

        triggerEvent(O2mServerEventArgs.ERROR_PUBLISH_SUB_REPLY_FAILED, text, cause);
    }

    public void onPublishErrorReplyFailed(String clientSessionId, Message request,
            Exception cause) {
        final String FORMAT = "Failed to publish channel snapshot message. session: %1$s, request: %2$s.";
        String text = String.format(FORMAT, clientSessionId, request);

        log.error(text, cause);

        triggerEvent(O2mServerEventArgs.ERROR_PUBLISH_SUB_REPLY_FAILED, text, cause);
    }

    public void onPublishReplyFailed(String clientSessionId, Message request, Message reply,
            Exception cause) {
        final String FORMAT = "Failed to publish channel reply message. session: %1$s, request: %2$s, reply: %3$s.";
        String text = String.format(FORMAT, clientSessionId, request, reply);

        log.error(text, cause);

        triggerEvent(O2mServerEventArgs.ERROR_PUBLISH_SUB_REPLY_FAILED, text, cause);
    }

    public void onRequestHandlerNotFound(String messageType, Message message) {
        final String FORMAT = "Failed to find request handler for type: %1$s, message: %2$s.";
        String text = String.format(FORMAT, messageType, message);

        log.warn(text);

        triggerEvent(O2mServerEventArgs.WARN_REQUEST_HANDLER_NOT_FOUND, text, null);
    }

    public void onClientSessionNotFound(String clientSessionId) {
        final String FORMAT = "Failed to find client session: %1$s.";
        String text = String.format(FORMAT, clientSessionId);

        log.warn(text);

        triggerEvent(O2mServerEventArgs.WARN_CLIENT_SESSION_NOT_FOUND, text, null);
    }

    public void onStateChanged(O2mServerState oldState, O2mServerState newState) {
        final String FORMAT = "State changed from %1$s to %2$s.";
        String text = String.format(FORMAT, oldState, newState);

        log.info(text);

        trigerServerEvent(new O2mServerStateEventArgs(oldState, newState));
    }

    public void addEventListener(O2mServerEventListener listener) {
        Objects.requireNonNull(listener);

        /** @formatter:off */
        eventListeners = ImmutableList.<O2mServerEventListener> builder()
                .addAll(eventListeners)
                .add(listener)
                .build();
        /** @formatter:on */
    }

    public boolean removeEventListener(O2mServerEventListener listener) {
        Objects.requireNonNull(listener);

        ImmutableList.Builder<O2mServerEventListener> builder = ImmutableList.builder();

        boolean found = false;
        for (O2mServerEventListener item : eventListeners) {
            if (!found && item.equals(listener)) {
                found = true;
            } else {
                builder.add(item);
            }
        }

        eventListeners = builder.build();

        return found;
    }

    private void triggerEvent(int eventId, @Nullable String text, @Nullable Throwable reason) {
        O2mServerEventArgs eventArgs = new O2mServerEventArgs(eventId, text, reason);

        trigerServerEvent(eventArgs);
    }

    public void trigerServerEvent(O2mServerEventArgs eventArgs) {
        ImmutableList<O2mServerEventListener> listeners = eventListeners;
        for (O2mServerEventListener listener : listeners) {
            listener.onEvent(o2mServer, eventArgs);
        }
    }
}
