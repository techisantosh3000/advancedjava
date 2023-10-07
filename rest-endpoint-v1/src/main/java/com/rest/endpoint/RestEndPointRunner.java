package com.rest.endpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class RestEndPointRunner {
    public static void main(String[] args) {
        SpringApplication.run(RestEndPointRunner.class);
    }
}
