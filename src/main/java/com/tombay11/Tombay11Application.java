package com.tombay11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Tombay11Application {

	private static final Logger log = LoggerFactory.getLogger(Tombay11Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Tombay11Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate() { 

		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

		factory.setConnectTimeout(30000);
		factory.setReadTimeout(30000);

		return new RestTemplate(factory);
	}
}
