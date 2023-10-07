package com.rest.endpoint.controller;

import com.rest.endpoint.dto.APIResponse;
import com.rest.endpoint.model.Error;
import com.rest.endpoint.model.Toy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class ToysController {

    @PostMapping("/data")
    public ResponseEntity<?> getToysDetailsSuccess(@RequestBody String param){
        List<Toy> toys = Arrays.asList(
                new Toy(101,"Action Figure",Boolean.TRUE),
                new Toy(102,"Not An Action Figure",Boolean.FALSE)
        );

        List<Error> errors = Arrays.asList(
               new Error(400,"This is wrong on all levels."),
               new Error(404, "Super nova.")
        );

        Map<String,List<Error>> mapOfError = new HashMap<>();
        mapOfError.put("errors",errors);

        if(param.equalsIgnoreCase("error")){
            return new ResponseEntity<>(mapOfError, HttpStatus.OK);
        }
        return new ResponseEntity<>(toys,HttpStatus.OK);
    }

    @PostMapping("/data1")
    public ResponseEntity<List<Toy>> getToysDetails(@RequestBody String param){
        List<Toy> toys = Arrays.asList(
                new Toy(101,"Action Figure",Boolean.TRUE),
                new Toy(102,"Not An Action Figure",Boolean.FALSE)
        );

        return new ResponseEntity<>(toys,HttpStatus.OK);
    }

    @PostMapping("/data2")
    public ResponseEntity<List<Error>> getErrorsDetails(@RequestBody String param){

        List<Error> errors = Arrays.asList(
                new Error(400,"This is wrong on all levels."),
                new Error(404, "Super nova.")
        );

        return new ResponseEntity<>(errors, HttpStatus.OK);

    }


}
