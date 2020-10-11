package com.jasonleetoronto.capstone.springkafkatwitterproducerservice.producer;

import com.jasonleetoronto.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TwitterAvroProducer {

    private static Logger LOG = LoggerFactory.getLogger(TwitterAvroProducer.class);

    @Value("${topic.name}")
    private String topic;

    private KafkaTemplate<String, Tweet> kafkaTemplate;

    @Autowired
    public TwitterAvroProducer(KafkaTemplate<String, Tweet> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Tweet tweet) {
        this.kafkaTemplate.send("tweet", tweet.getUser().getId()+ tweet.getCreatedAt(), tweet);
    }
}
