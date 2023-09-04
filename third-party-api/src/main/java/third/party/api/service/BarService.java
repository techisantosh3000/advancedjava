package third.party.api.service;

import org.springframework.stereotype.Service;

@Service
public class BarService {

    public String doSomething(String input){
        return "Doing something "+input;
    }

    public String doNothing(String input){
        return "Doing nothing ";
    }
}
