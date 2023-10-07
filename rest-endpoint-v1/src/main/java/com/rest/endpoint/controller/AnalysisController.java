package com.rest.endpoint.controller;

import com.rest.endpoint.model.Error;
import com.rest.endpoint.model.Toy;
import com.rest.endpoint.service.AnalysisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class AnalysisController {

    @Autowired
    private AnalysisClient client;

    @GetMapping("/check")
    public ResponseEntity<List<?>> exchangeDataError(){
        System.out.println("client response "+client.doSomething());
        return new ResponseEntity<>(client.doSomething(), HttpStatus.OK);
    }

    @GetMapping("/getMyToy")
    public ResponseEntity<?> getMyToy(){
        System.out.println("client response "+client.doSomethingMore());
        Optional<Toy> findMyToy = client.doSomethingMore().getToys().stream().findFirst();
        if(findMyToy.isPresent()) {
            return new ResponseEntity<>(findMyToy.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(client.doSomethingMore().getErrors(), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/getMyToy1")
    public ResponseEntity<Toy> getMyToy1(){
        System.out.println("client response "+client.doSomethingMoreMore());
        Optional<Toy> findMyToy = client.doSomethingMoreMore().stream().filter(e -> e instanceof Toy).findFirst();
        //Optional<Error> findMyError = client.doSomethingMore().getErrors().stream().findFirst();
        if(findMyToy.isPresent()) {
            return new ResponseEntity<>(findMyToy.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/donotShowError")
    public ResponseEntity<List<?>> exchangeDataSuccess(){
        return null;
    }
}
