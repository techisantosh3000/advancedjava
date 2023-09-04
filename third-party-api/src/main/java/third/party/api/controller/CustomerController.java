package third.party.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import third.party.api.model.CustomerResponse;
import third.party.api.service.CheckCustomerRegistrationService;

@RestController
public class CustomerController {
    @Autowired
    private CheckCustomerRegistrationService customerRegistrationService;

    @GetMapping(path = "/details")
    public ResponseEntity<CustomerResponse> customerDetails() {
        CustomerResponse customerResponse = customerRegistrationService.getDetails();
        if(customerResponse == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }
}
