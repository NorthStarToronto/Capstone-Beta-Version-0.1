package com.jasonleetoronto.capstone.springkafkatwitterproducerservice.consumer;

import com.jasonleetoronto.Tweet;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class TwitterAvroConsumer {

    private static Logger LOG = LoggerFactory.getLogger(TwitterAvroConsumer.class);

//    @Value("${topic.name}")
//    private String topic;
//
//    @KafkaListener(topics = "tweet-us-election-new", groupId = "group_id")
//    public void receiveMessage(ConsumerRecord<String, Tweet> record) {
//        LOG.info("Received message -> {}", record.value());
//    }
}
