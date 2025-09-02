package com.learning.drools.owntax.service;

import com.learning.drools.owntax.entity.Calculation;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnTaxService {


    @Autowired
    private KieBase kieBase;

    public Calculation CalculateOwnTax(Double wage) {
       // Calculation calculation = Calculation.builder().wage(wage).build();
        Calculation calculation = new Calculation();
        calculation.setWage(wage);

        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(calculation);
        kieSession.fireAllRules();
        kieSession.dispose();
        return calculation;
    }
}
