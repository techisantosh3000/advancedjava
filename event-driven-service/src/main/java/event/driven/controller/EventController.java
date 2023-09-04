package event.driven.controller;

import event.driven.consumer.KafKaConsumer;
import event.driven.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
public class EventController {

    @Autowired
    private KafkaProducer producer;

    /* @Autowired
    private KafKaConsumer consumer;*/

    @GetMapping(path = "/produce")
    public ResponseEntity<String> produceMessage(@RequestParam("message") String message) {
        producer.sendMessage(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

   /* @GetMapping(path = "/consume")
    public ResponseEntity<String> consumeMessage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}
