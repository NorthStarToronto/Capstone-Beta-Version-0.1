package com.jasonleetoronto.capstone.springkafkatwitterproducerservice.service;

import com.jasonleetoronto.Tweet;
import com.jasonleetoronto.User;
import com.jasonleetoronto.capstone.springkafkatwitterproducerservice.producer.TwitterAvroProducer;
import com.twitter.hbc.core.Client;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class TwitterStreamingService {

    private static Logger LOG = LoggerFactory.getLogger(TwitterStreamingService.class);

    @Autowired
    Client client;

    @Autowired
    BlockingQueue<String> msgQueue;

    @Autowired
    TwitterAvroProducer twitterAvroProducer;

    public void run() {

        /* Add a Shutdown Hook*/
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.info("Stopping Twitter Kafka Producer ...");
            LOG.info("Shutting down the Twitter Client ...");
            client.stop();
            LOG.info("Closing Twitter Kafka Producer ...");
        }));

        while (!client.isDone()) {
            Tweet tweet = null;
            try {
                Optional<String> msg = Optional.ofNullable(msgQueue.poll(5, TimeUnit.SECONDS));
                if (msg.isPresent()) {
                    JSONParser parser = new JSONParser();
                    JSONObject msgJSON = (JSONObject) parser.parse(msg.get());

                    if (msgJSON.get("user") != null) {
                        tweet = parseTweet(msgJSON);

                        twitterAvroProducer.sendMessage(tweet);
                    }
                }
            } catch (InterruptedException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public Tweet parseTweet(JSONObject msgJSON) {
        JSONObject userJSON = (JSONObject) msgJSON.get("user");
        User user = User.newBuilder()
                .setId((Long) userJSON.get("id"))
                .setName((String) userJSON.get("name"))
                .setFollowersCount((Long) userJSON.get("followers_count"))
                .setFriendsCount((Long) userJSON.get("friends_count"))
                .setListedCount((Long) userJSON.get("listed_count"))
                .setFavouritesCount((Long) userJSON.get("favourites_count"))
                .setCreatedAt((String) userJSON.get("created_at"))
                .build();

        Tweet tweet = Tweet.newBuilder()
                .setCreatedAt((String) msgJSON.get("created_at"))
                .setText((String) msgJSON.get("text"))
                .setUser(user)
                .setQuoteCount((Long) msgJSON.get("quote_count"))
                .setReplyCount((Long) msgJSON.get("reply_count"))
                .setFavorited((Boolean) msgJSON.get("favorited"))
                .setRetweeted((Boolean) msgJSON.get("retweeted"))
                .setTimestampMs((String) msgJSON.get("timestamp_ms"))
                .build();
        return tweet;
    }


}
