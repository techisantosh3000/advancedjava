package com.rest.endpoint.model;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class APIResponse {
    private List<Toy> toys;
    private Map<String,List<Error>> errors;

    public APIResponse(){
        this.toys = new ArrayList<>();
        this.errors = new HashMap<>();
    }

}
