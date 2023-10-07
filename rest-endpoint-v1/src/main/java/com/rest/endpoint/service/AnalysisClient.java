package com.rest.endpoint.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.endpoint.model.APIResponse;
import com.rest.endpoint.model.Error;
import com.rest.endpoint.model.Toy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnalysisClient {

    @Autowired
    private RestTemplate restTemplate;

    public List<?> doSomething(){
        String resourceUrl
                = "http://localhost:9001/v1/data";

        // Create the request body by wrapping
        // the object in HttpEntity
        HttpEntity<String> request =
                new HttpEntity<String>(new String("success"));

        ResponseEntity<List<?>> productCreateResponse =
                restTemplate
                        .exchange(resourceUrl,
                                HttpMethod.POST,
                                request,
                                new ParameterizedTypeReference<>() {
                                });
       return productCreateResponse.getBody();
    }

    private List<Toy> extractToys(final ResponseEntity<List<?>> productCreateResponse){
        System.out.println(" Status Code "+productCreateResponse.getStatusCode()+" Response Body "+productCreateResponse.getBody());
        return (List<Toy>)productCreateResponse.getBody();
    }

    // Here
    // Focused
    //
    public APIResponse doSomethingMore() {
        String resourceUrl
                = "http://localhost:9001/v1/data";

        // Create the request body by wrapping
        // the object in HttpEntity
        ObjectMapper mapper = new ObjectMapper();
        List<Toy> toyList = null;
        Map<String,List<Error>> errorList = null;
        APIResponse apiResponse = new APIResponse();

        HttpEntity<String> request =
                new HttpEntity<String>(new String("success"));

        ResponseEntity<?> productCreateResponse = null;

        productCreateResponse =
                restTemplate
                        .exchange(resourceUrl,
                                HttpMethod.POST,
                                request,
                                new ParameterizedTypeReference<>() {
                                });
        try {
            toyList = mapper.convertValue(productCreateResponse.getBody(), new TypeReference<List<Toy>>() {
            });
            apiResponse.setToys(toyList);
        } catch (RuntimeException re) {
            errorList = mapper.convertValue(productCreateResponse.getBody(), new TypeReference<Map<String,List<Error>>>() {});
            log.error("Loqate API Failure : {}", errorList);
            apiResponse.setErrors(errorList);
        }

        return apiResponse;
    }


    public List<Toy> doSomethingMoreMore(){
        String resourceUrl
                = "http://localhost:9001/v1/data1";

        // Create the request body by wrapping
        // the object in HttpEntity
        HttpEntity<String> request =
                new HttpEntity<String>(new String("success"));

        ResponseEntity<List<Toy>> productCreateResponse =
                restTemplate
                        .exchange(resourceUrl,
                                HttpMethod.POST,
                                request,
                                new ParameterizedTypeReference<>() {
                                });
        return productCreateResponse.getBody();
    }


    public ResponseEntity<List<?>> clientExchangeDataError(){
        String resourceUrl
                = "http://localhost:9001/v1/data";

        // Create the request body by wrapping
        // the object in HttpEntity
        HttpEntity<String> request =
                new HttpEntity<String>(new String("error"));

        ResponseEntity<List<?>> productCreateResponse =
                restTemplate
                        .exchange(resourceUrl,
                                HttpMethod.POST,
                                request,
                                new ParameterizedTypeReference<List<?>>() {
                                });
        analysis1(productCreateResponse);
        return productCreateResponse;
    }

    public ResponseEntity<List<?>> clientExchangeDataSuccess(){
        String resourceUrl
                = "http://localhost:9001/v1/data";

        // Create the request body by wrapping
        // the object in HttpEntity
        HttpEntity<String> request =
                new HttpEntity<String>(new String("HAAAHAAA"));

        ResponseEntity<List<?>> productCreateResponse =
                restTemplate
                        .exchange(resourceUrl,
                                HttpMethod.POST,
                                request,
                                new ParameterizedTypeReference<List<?>>() {
                                });
        analysis(productCreateResponse);
        return productCreateResponse;
    }

    private void analysis(ResponseEntity<List<?>> productCreateResponse){
        Optional<List<Toy>> optionalOfToys = (Optional<List<Toy>>) productCreateResponse.getBody().stream().findFirst();
        Optional<List<Error>>  optionalOfErrors = (Optional<List<Error>>) productCreateResponse.getBody().stream().findFirst();
        if(optionalOfToys.isPresent()){
            System.out.println("Your toys are here.");
        }
        if(optionalOfErrors.isPresent()){
            System.out.println("Joke is on you.");
        }
    }

    private void analysis1(ResponseEntity<List<?>> productCreateResponse){
        Optional<List<Toy>>  optionalOfToys = (Optional<List<Toy>>) productCreateResponse.getBody().stream().findFirst();
        //Optional<List<Error>>  optionalOfErrors = (Optional<List<Error>>) productCreateResponse.getBody().stream().findFirst();
        if(optionalOfToys.isPresent()){
            System.out.println("ExperimentalController.analysis1 :: Your toys are here.");
        }
    }

}

