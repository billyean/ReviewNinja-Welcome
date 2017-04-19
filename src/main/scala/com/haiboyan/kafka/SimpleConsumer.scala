package com.haiboyan.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties

/**
  * Created by hyan on 4/18/17.
  */
object SimpleConsumer extends App {


  import java.util

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("group.id", "test")
  props.put("enable.auto.commit", "true")
  props.put("auto.commit.interval.ms", "1000")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(util.Arrays.asList("foo", "bar"))


  while (true) {
    val records = consumer.poll(10)
    import scala.collection.JavaConversions._
    for (record <- records) {
      println("offset = " + record.offset + ", key = " +  record.key + ", value = " +  record.value)
    }
  }
}
