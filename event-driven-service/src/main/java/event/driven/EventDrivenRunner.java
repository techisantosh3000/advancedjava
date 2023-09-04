package event.driven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "event.driven.*")
public class EventDrivenRunner {
    public static void main(String[] args) {
        SpringApplication.run(EventDrivenRunner.class, args);
    }
}
