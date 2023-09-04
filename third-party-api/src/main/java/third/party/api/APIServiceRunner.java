package third.party.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "third.party.api.*")
@EnableAutoConfiguration
public class APIServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(APIServiceRunner.class, args);
    }
}
