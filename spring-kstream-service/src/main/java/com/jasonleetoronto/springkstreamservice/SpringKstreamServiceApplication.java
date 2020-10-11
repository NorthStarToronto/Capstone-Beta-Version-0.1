package com.jasonleetoronto.springkstreamservice;

import com.jasonleetoronto.Tweet;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@EnableKafkaStreams
public class SpringKstreamServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SpringKstreamServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringKstreamServiceApplication.class, args);
    }

    @Bean
    public KStream<String, Tweet> handleStream(StreamsBuilder builder) {
        final Serde<String> stringSerde = Serdes.String();
		final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url",
				"http://localhost:8081");
        final Serde<Tweet> valueSpecificAvroSerde = new SpecificAvroSerde<>();
        valueSpecificAvroSerde.configure(serdeConfig, false);
        KStream<String, Tweet> tweetKStream = builder.stream("tweet", Consumed.with(stringSerde, valueSpecificAvroSerde));
        tweetKStream.peek((key, tweet) -> LOG.info("Key = {}; Value = {}", key, tweet.toString()));
//		KStream<String, Tweet> filteredStream = tweetKStream.filter(
//				(key, tweet) -> tweet.getUser().getFollowersCount() > 2);
//		filteredStream.to("filtered-tweet");
        return tweetKStream;
    }
}
