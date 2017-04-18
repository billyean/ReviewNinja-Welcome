# Apache Kafka Programming



```
vi config/server.properties

# Start zookeeper at background
KAFKA_HOME=~/open/kafka_2.12-0.10.2.0
${KAFKA_HOME}/bin/zookeeper-server-start.sh ${KAFKA_HOME}/config/zookeeper.properties &
${KAFKA_HOME}/bin/kafka-server-start.sh ${KAFKA_HOME}/config/server.properties &

cp ${KAFKA_HOME}/config/server.properties ${KAFKA_HOME}/config/server2.properties

vi ${KAFKA_HOME}/config/server2.properties
# broker.id=1
# listeners=PLAINTEXT://:9091
# log.dirs= ~/code/Kafka-Programming/kafka-log-2

${KAFKA_HOME}/bin/kafka-server-start.sh ${KAFKA_HOME}/config/server2.properties &

${KAFKA_HOME}/bin/kafka-topics.sh  # Show help

# Create a topic 
${KAFKA_HOME}/bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic TEST1 --partitions 2 --replication-factor 2

${KAFKA_HOME}/bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic TEST1 

${KAFKA_HOME}/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic TEST # Use broker
${KAFKA_HOME}/bin/kafka-console-consumer.sh --bootstrap-server localhost:2181 --topic TEST # Use zookeeper

${KAFKA_HOME}/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic TEST --from-beginning # Read all messages from beginning
```

# Question list
1. Why do Kafka consumers connect to zookeeper, and producers get metadata from brokers?

```
> First of all, zookeeper is needed only for high level consumer. SimpleConsumer does not require zookeeper to work.

> The main reason zookeeper is needed for a high level consumer is to track consumed offsets and handle load balancing.

> Now in more detail.

> Regarding offset tracking, imagine following scenario: you start a consumer, consume 100 messages and shut the consumer down. Next time you start your consumer you'll probably want to resume from your last consumed offset (which is 100), and that means you have to store the maximum consumed offset somewhere. Here's where zookeeper kicks in: it stores offsets for every group/topic/partition. So this way next time you start your consumer it may ask "hey zookeeper, what's the offset I should start consuming from?". Kafka is actually moving towards being able to store offsets not only in zookeeper, but in other storages as well (for now only zookeeper and kafka offset storages are available and i'm not sure kafka storage is fully implemented).

> Regarding load balancing, the amount of messages produced can be quite large to be handled by 1 machine and you'll probably want to add computing power at some point. Lets say you have a topic with 100 partitions and to handle this amount of messages you have 10 machines. There are several questions that arise here actually:

> how should these 10 machines divide partitions between each other? what happens if one of machines die? what happens if you want to add another machine?

> And again, here's where zookeeper kicks in: it tracks all consumers in group and each high level consumer is subscribed for changes in this group. The point is that when a consumer appears or disappears, zookeeper notifies all consumers and triggers rebalance so that they split partitions near-equally (e.g. to balance load). This way it guarantees if one of consumer dies others will continue processing partitions that were owned by this consumer.
```