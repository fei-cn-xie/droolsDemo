package com.learning.drools.owntax.controller;

import com.learning.drools.owntax.entity.Calculation;
import com.learning.drools.owntax.service.OwnTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnTaxController {


    @Autowired
    private OwnTaxService ownTaxService;

    @GetMapping("/calc/{wage}")
    public Calculation calc( @PathVariable Double wage) {
        return ownTaxService.CalculateOwnTax(wage);
    }
}
