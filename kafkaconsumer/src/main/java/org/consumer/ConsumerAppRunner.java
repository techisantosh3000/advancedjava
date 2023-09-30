package org.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.consumer.*")
public class ConsumerAppRunner {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerAppRunner.class);
    }
}
