# One-2-Many Communications
One server, multiple clients, non-guarantee delivery. Used for server to UI communication.

**WIP**

## Summary
Defines a client server communication protocol over Transport, with following features in place:
* Client sends request to server and receives reply asynchronously
* Server publishes data to topics where more than one clients can receive it.
  * Snapshots will only be received by client who requests it.
* Client subscribes data with 3 different mode: SNAPSHOT, UPDATE and REALTIME.

## Motivation
Server-client, one-to-many, communications are widely used in different application scenarios. For example, GUI desktop applications to server. To have a barely workable solution seems not difficult, but it has following problems:
* Exception case handling. Network can fail in any stage of communications, and to handle all these cases correctly could be very difficult.
* Migrating from one messaging layer to the other. For example, moving from ActiveMQ to Tibco EMS etc.
* ... (**WIP**)

## Non Goals
* Guarantee delivery: it's not queue based.
* Client side persistent and recovery: it's assumed that client will be able to recover all data from server by subscribing on startup. Client side storage is not required.

**WIP**