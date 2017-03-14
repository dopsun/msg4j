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
import com.dopsun.msg4j.o2m.O2mClientEventArgs;
import com.dopsun.msg4j.o2m.O2mClientEventListener;
import com.dopsun.msg4j.o2m.O2mClientState;
import com.dopsun.msg4j.o2m.O2mClientStateEventArgs;
import com.google.common.collect.ImmutableList;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class O2mClientEvents {
    private static final Logger log = LoggerFactory.getLogger(O2mClientEvents.class);

    /**
     * Listers is volatile and immutable. It can be replaced as a whole.
     * 
     * <p>
     * <ul>
     * <li>{@link #addEventListener(O2mClientEventListener)}: to add event listener.</li>
     * <li>{@link #removeEventListener(O2mClientEventListener)}: to remove event listener.</li>
     * <li><code>trigerClientEvent</code>: to call events.</li>
     * </ul>
     * </p>
     */
    private volatile ImmutableList<O2mClientEventListener> eventListeners = ImmutableList.of();

    private final O2mClientImpl o2mClient;

    public O2mClientEvents(O2mClientImpl o2mClient) {
        Objects.requireNonNull(o2mClient);

        this.o2mClient = o2mClient;

    }

    public void onConnectTimeOut(int numOfRetries) {
        log.info("Connecting {} time out.", numOfRetries);
    }

    public void onConnectFailed(Exception cause) {
        log.error("Failed to connecting.", cause);
    }

    public void onConnectRejected(int errorCode, String errorText) {
        final String FORMAT = "Failed to connect. code: %1$s, text: %2$s";
        String text = String.format(FORMAT, errorCode, errorText);

        log.error(text);

        triggerEvent(O2mClientEventArgs.ERROR_CONNECT_REJECTED, text, null);
    }

    public void onSystemMessageHandlerFailed(Message message, Exception cause) {
        final String FORMAT = "Failed to handle SYSTEM message: %1$s";
        String text = String.format(FORMAT, message);

        log.error(text, cause);

        triggerEvent(O2mClientEventArgs.ERROR_SYS_MSG_HANDLER_FAILED, text, cause);
    }

    public void onPublishMessageHandlerFailed(String channelCode, Message message,
            Exception cause) {
        final String FORMAT = "Failed to handle PUBLISH message: channel = %1$s, message = %2$s";
        String text = String.format(FORMAT, channelCode, message);

        log.error(text, cause);

        triggerEvent(O2mClientEventArgs.ERROR_PUB_MSG_HANDLER_FAILED, text, cause);
    }

    public void onSessionMessageHandlerFailed(Message message, Exception cause) {
        final String FORMAT = "Failed to handle SESSION message: %1$s";
        String text = String.format(FORMAT, message);

        log.error(text, cause);

        triggerEvent(O2mClientEventArgs.ERROR_PUB_MSG_HANDLER_FAILED, text, cause);
    }

    public void onStateChanged(O2mClientState oldState, O2mClientState newState) {
        final String FORMAT = "State changed from %1$s to %2$s.";
        String text = String.format(FORMAT, oldState, newState);

        log.info(text);

        trigerEvent(new O2mClientStateEventArgs(oldState, newState));
    }

    public void onUnknownMessageType(@Nullable String messageType) {
        final String FORMAT = "Unknown message type: %1$s";
        String text = String.format(FORMAT, messageType);

        log.error(text);

        triggerEvent(O2mClientEventArgs.ERROR_PUB_MSG_HANDLER_FAILED, text, null);
    }

    public void onChannelSubscriptionNotFound(String channelCode) {
        final String FORMAT = "Subscription cannot found for channel: %1$s.";
        String text = String.format(FORMAT, channelCode);

        log.warn(text);

        triggerEvent(O2mClientEventArgs.WARN_CHANNEL_SUBSCRIPTION_NOT_FOUND, text, null);
    }

    public void onSubscriptionNotFound(String subscriptionId) {
        final String FORMAT = "Subscription cannot found for id: %1$s.";
        String text = String.format(FORMAT, subscriptionId);

        log.warn(text);

        triggerEvent(O2mClientEventArgs.WARN_SUBSCRIPTION_NOT_FOUND, text, null);
    }

    public void onRequestNotFound(long requestSeqNum) {
        final String FORMAT = "Subscription cannot found for id: %1$s.";
        String text = String.format(FORMAT, requestSeqNum);

        log.warn(text);

        triggerEvent(O2mClientEventArgs.WARN_REQUEST_NOT_FOUND, text, null);
    }

    public void onServerStarted(Message message) {
        final String FORMAT = "ServerStarted notification received. %1$s.";
        String text = String.format(FORMAT, message);

        log.error(text);

        triggerEvent(O2mClientEventArgs.ERROR_SERVER_RESTARTED, text, null);
    }

    public void onServerStopped(Message message) {
        final String FORMAT = "ServerStopped notification received. %1$s.";
        String text = String.format(FORMAT, message);

        log.error(text);

        triggerEvent(O2mClientEventArgs.ERROR_SERVER_STOPPED, text, null);
    }

    public void onSessionReplaced() {
        String text = "Session replaced.";

        log.error(text);

        triggerEvent(O2mClientEventArgs.ERROR_SESSION_REPLACED, text, null);
    }

    public void onClientDisconnectedReplyReceived() {
        String text = "Client disconnected reply received.";

        log.debug(text);

        triggerEvent(O2mClientEventArgs.INFO_CLIENT_DISCONNECT_RECEIVED, text, null);
    }

    void addEventListener(O2mClientEventListener listener) {
        Objects.requireNonNull(listener);

        /** @formatter:off */
        eventListeners = ImmutableList.<O2mClientEventListener> builder()
                .addAll(eventListeners)
                .add(listener)
                .build();
        /** @formatter:on */
    }

    boolean removeEventListener(O2mClientEventListener listener) {
        Objects.requireNonNull(listener);

        ImmutableList.Builder<O2mClientEventListener> builder = ImmutableList.builder();

        boolean found = false;
        for (O2mClientEventListener item : eventListeners) {
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
        O2mClientEventArgs eventArgs = new O2mClientEventArgs(eventId, text, reason);

        trigerEvent(eventArgs);
    }

    void trigerEvent(O2mClientEventArgs eventArgs) {
        ImmutableList<O2mClientEventListener> listeners = eventListeners;
        for (O2mClientEventListener listener : listeners) {
            listener.onEvent(o2mClient, eventArgs);
        }
    }
}
