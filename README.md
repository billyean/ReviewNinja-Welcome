# Apache Kafka Programming

* vi config/server.properties

```
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