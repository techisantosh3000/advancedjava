package event.driven;

import event.driven.constant.AppConstants;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ComponentScan(basePackages = "event.driven.*")
public class EventDrivenRunner {
    public static void main(String[] args) {
        SpringApplication.run(EventDrivenRunner.class, args);
    }

    /*@Bean
    public ApplicationRunner runner(KafkaAdmin admin) {
        return args -> {
            try (AdminClient client = AdminClient.create(admin.getConfigurationProperties())) {
                while (true) {
                    Map<String, ConsumerGroupDescription> map =
                            client.describeConsumerGroups(Collections.singletonList(AppConstants.GROUP_ID)).all().get(10, TimeUnit.SECONDS);
                    System.out.println("++++"+map);
                    //System.in.read();
                }
            }
        };
    }*/
}
