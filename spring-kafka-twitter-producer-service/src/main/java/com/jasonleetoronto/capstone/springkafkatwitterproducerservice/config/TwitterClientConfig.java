package com.jasonleetoronto.capstone.springkafkatwitterproducerservice.config;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class TwitterClientConfig {

    private static Logger LOG = LoggerFactory.getLogger(TwitterClientConfig.class);

    @Value("${twitter.client.config.consumer.key}")
    private String consumerKey;

    @Value("${twitter.client.config.consumer.secret}")
    private String consumerSecret;

    @Value("${twitter.client.config.access.token}")
    private String accessToken;

    @Value("${twitter.client.config.access.secret}")
    private String accessSecret;

    @Value("${twitter.client.config.queue-size}")
    private int queueSize;

    @Value("${twitter.client.config.track-terms}")
    private String trackingTerms; // US 2020 election twitter tracking terms

    /* Authenticate Twitter Client Credentials using OAuth */
    @Bean
    public Authentication twitterHosebirdAuth() {
        LOG.info("Authenticating Twitter Client Credentials ...");
        return new OAuth1(consumerKey, consumerSecret, accessToken, accessSecret);
    }

    @Bean
    public BlockingQueue<String> messageQueue() {
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(queueSize);
        LOG.info(String.valueOf(queueSize));
        return msgQueue;
    }

    /* Connect to a twitter streaming api client with associate message queue */
    @Bean
    public Client twitterClientAndMessageQueue() {


        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        hosebirdEndpoint.trackTerms(Lists.newArrayList(trackingTerms));
        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")
                .hosts(hosebirdHosts)
                .authentication(twitterHosebirdAuth())
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(messageQueue()));

        Client client = builder.build();
        client.connect();
//        LOG.info(client.toString());
        return client;
    }
}

