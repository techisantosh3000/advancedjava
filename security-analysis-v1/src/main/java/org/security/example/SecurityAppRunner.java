package org.security.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.security.*")
public class SecurityAppRunner {
    public static void main(String[] args) {
        SpringApplication.run(SecurityAppRunner.class);
    }
}
