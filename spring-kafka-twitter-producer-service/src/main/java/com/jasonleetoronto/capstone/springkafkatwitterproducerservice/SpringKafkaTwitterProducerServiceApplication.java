package com.jasonleetoronto.capstone.springkafkatwitterproducerservice;

import com.jasonleetoronto.capstone.springkafkatwitterproducerservice.service.TwitterStreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringKafkaTwitterProducerServiceApplication implements CommandLineRunner {

	@Autowired
	TwitterStreamingService twitterStreamingService;

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaTwitterProducerServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		twitterStreamingService.run();
	}
}
