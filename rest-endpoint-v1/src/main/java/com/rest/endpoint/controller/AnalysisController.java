package com.rest.endpoint.controller;

import com.rest.endpoint.model.Toy;
import com.rest.endpoint.service.AnalysisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getMyToy(@RequestParam String state){
        System.out.println("client response "+client.doSomethingMore(state));
        Optional<Toy> findMyToy = client.doSomethingMore(state).getToys().stream().findFirst();
        if(findMyToy.isPresent()) {
            return new ResponseEntity<>(findMyToy.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(client.doSomethingMore(state).getErrors(), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/toys")
    public ResponseEntity<?> getToys(@RequestParam String state){
        System.out.println("client response "+client.doSomethingMore(state));
        List<Toy> findMyToys = client.doSomethingMore(state).getToys();
        if(!findMyToys.isEmpty()) {
            return new ResponseEntity<>(findMyToys, HttpStatus.OK);
        }

        return new ResponseEntity<>(client.doSomethingMore(state).getErrors(), HttpStatus.BAD_REQUEST);

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
