package com.spring.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringSamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSamplesApplication.class, args);
	}
	
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
