package com.learning.drools.owntax.controller;

import com.learning.drools.owntax.entity.Person;
import com.learning.drools.owntax.service.CredCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CredCardController {


    @Autowired
    private CredCardService credCardService;

    @PostMapping("/credit")
    public Person calcCredit(@RequestBody Person person) throws Exception {
        log.info("Person== {}", person);
        return credCardService.executeRule(person);
    }
}
