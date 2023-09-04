package third.party.api.service;

import org.springframework.stereotype.Service;

@Service
public class FooService {
    public Boolean eligible(){
        return true;
    }

    public Boolean notEligible(){
        return false;
    }
}
