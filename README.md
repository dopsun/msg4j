# msg4j: Message for Java
[![Build Status](https://travis-ci.org/dopsun/msg4j.svg?branch=master)](https://travis-ci.org/dopsun/msg4j)

Define, codec, store and deliver, and more ...

## Goals
* Message, a map with key type as string
  * [MessageReader](msg4j-core/src/main/java/com/dopsun/msg4j/core/messages/MessageReader.java) 
  * [MessageWriter](msg4j-core/src/main/java/com/dopsun/msg4j/core/messages/MessageWriter.java)
  * [ImmutableMessage](msg4j-core/src/main/java/com/dopsun/msg4j/core/messages/ImmutableMessage.java)
  * [WritableMessage](msg4j-core/src/main/java/com/dopsun/msg4j/core/messages/WritableMessage.java)
* Transport, an abstract of underlying message delivery infrastructure

## Non Goals
* GC free: this library is not targeting to GC free scenario.
  * It's recommended all writable messages to be short lived, and only immutable objects will be go to the old gen then.
* Message broker or service provider: message will be delivered via other messaging delivering infrastructure, e.g. ActiveMQ, Tibco RV. msg4j is not a messaging broker.