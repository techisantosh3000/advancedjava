package com.analysis.json.parsing.main;

import com.analysis.json.parsing.model.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ApplicationJsonParserRunner {
    public static void main(String[] args) {
        //manipulateSampleResponse();
        userjsonParser();
    }

    public static void manipulateSampleResponse(){
        DependentObject1 dependentObject1 = new DependentObject1();
        dependentObject1.setObject1Id(10000);

        DependentObject2 dependentObject2 = null;

        SampleResponse sampleResponse1 = SampleResponse.SampleResponseBuilder.newInstance()
                .setObj1(dependentObject1)
                .setObj2(dependentObject2)
                .build();

        Gson gson3 = new Gson();
        System.out.println(""+gson3.toJson(sampleResponse1));
    }

    public static void userjsonParser(){
        UserDetail user = new UserDetail(Arrays.asList(new BankAccount(200, "HDFC"), new BankAccount(201, "HDFC")), Arrays.asList(new Address(300,"address300"),new Address(301,"address301")));
        Gson gson = new Gson();
        System.out.println(""+gson.toJson(user));

        User user1 = new User();
        user1.setUserAddress(new Address(1000,"1000"));
        Gson gson1 = new Gson();
        System.out.println(""+gson1.toJson(user1));
    }
}
