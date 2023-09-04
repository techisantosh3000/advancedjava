package third.party.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import third.party.api.entity.FinancialMessage;
import third.party.api.service.APIServiceImpl;
import third.party.api.model.IncomingFinancialMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class APIServiceController {

    @Autowired
    private APIServiceImpl apiServiceImpl;

    @GetMapping(path = "/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("UP", HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<FinancialMessage>> getMessages() {
        List<FinancialMessage> result = apiServiceImpl.findAll();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinancialMessage> storeMessage(@RequestBody IncomingFinancialMessage incomingFinancialMessage) {
        FinancialMessage result = apiServiceImpl.saveMessage(incomingFinancialMessage);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
