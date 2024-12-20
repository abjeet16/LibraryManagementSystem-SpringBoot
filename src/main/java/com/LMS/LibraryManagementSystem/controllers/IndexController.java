package com.LMS.LibraryManagementSystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class IndexController {

    @GetMapping("/user")
    public ResponseEntity test(){
        return new ResponseEntity("USER", HttpStatus.OK);
    }
    @GetMapping("/admin")
    public ResponseEntity test1(){
        return new ResponseEntity("ADMIN", HttpStatus.OK);
    }
}
// END OF INDEX CONTROLLER CLASS.
