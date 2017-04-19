package com.haiboyan.kafka

import org.apache.kafka.clients.producer.{KafkaProducer, Producer, ProducerRecord}
/**
  * Created by hyan on 4/18/17.
  */
import java.util.Properties

import org.apache.kafka.clients.producer.ProducerRecord

object SimpleProducer extends App {
  val props = new Properties()

  val topic = "TEST"

  props.put("bootstrap.servers", "localhost:9092")
  props.put("acks", "all")
  props.put("retries", String.valueOf(0))
  props.put("batch.size", String.valueOf(16384))
  props.put("linger.ms", String.valueOf(1))
  props.put("buffer.memory", String.valueOf(33554432))
  props.put("producer.type", "sync")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  for (i <- 0 to 100 ){
    val future = producer.send(new ProducerRecord(topic, Integer.toString(i),  Integer.toString(i)))
  }

  producer.close()


}
