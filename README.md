# msg4j: Message for Java
Define, codec, store and deliver, and more ...

## Goals
* Message, a map with key type as string
  * [MessageReader](msg4j-core/src/main/java/com/dopsun/msg4j/core/MessageReader.java) 
  * [MessageWriter](msg4j-core/src/main/java/com/dopsun/msg4j/core/MessageWriter.java)
  * [ImmutableMessage](msg4j-core/src/main/java/com/dopsun/msg4j/core/ImmutableMessage.java)
  * [WritableMessage](msg4j-core/src/main/java/com/dopsun/msg4j/core/WritableMessage.java)

## Non Goals
* GC free: this library is not targeting to GC free scenario.
  * It's recommended all writable messages to be short lived, and only immutable objects will be go to the old gen then.