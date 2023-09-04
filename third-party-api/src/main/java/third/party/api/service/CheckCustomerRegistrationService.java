package third.party.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import third.party.api.model.CustomerResponse;

@Service
public class CheckCustomerRegistrationService {

    @Autowired
    private FooService fooService;

    @Autowired
    private BarService barService;

    public CustomerResponse getDetails() {
        CustomerResponse response = new CustomerResponse();
        response.setId(1000);
        response.setName("cust1000");

        if (fooService.eligible()) {
            barService.doSomething("do it.");
        } else {
            if (fooService.notEligible()) {
                System.out.println("not eligible.");
            } else {
                barService.doSomething("not able to do it.");
            }
        }

        if (what()) {
            why();
        } else {
            whom();
        }

        return response;
    }

    public String why() {
        return "why?";
    }

    public boolean what() {
        return true;
    }

    public void whom() {
        when();
    }

    public int when() {
        barService.doNothing("dum dum dum.");
        return 0;
    }
}
