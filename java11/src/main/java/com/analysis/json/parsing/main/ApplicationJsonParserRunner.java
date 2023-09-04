package com.analysis.json.parsing.main;

import com.analysis.json.parsing.model.Address;
import com.analysis.json.parsing.model.BankAccount;
import com.analysis.json.parsing.model.UserDetail;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ApplicationJsonParserRunner {
    public static void main(String[] args) {
        UserDetail user = new UserDetail(Arrays.asList(new BankAccount(200, "HDFC"), new BankAccount(201, "HDFC")), Arrays.asList(new Address(300,"address300"),new Address(301,"address301")));
        Gson gson = new Gson();
        System.out.println(""+gson.toJson(user));
    }
}
