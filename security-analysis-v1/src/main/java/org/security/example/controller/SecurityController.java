package org.security.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class SecurityController {

    @GetMapping(path="admin/sample")
    public ResponseEntity<String> getSampleMessageForAdmin(){
        return new ResponseEntity<>("Hello Admin !!!", HttpStatus.OK);
    }

    @GetMapping(path="user/sample")
    public ResponseEntity<String> getSampleMessageForUser(){
        return new ResponseEntity<>("Hello User !!!", HttpStatus.OK);
    }

}
