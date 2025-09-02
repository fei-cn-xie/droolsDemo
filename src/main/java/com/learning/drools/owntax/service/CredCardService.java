package com.learning.drools.owntax.service;

import com.learning.drools.owntax.entity.Education;
import com.learning.drools.owntax.entity.Person;
import com.learning.drools.owntax.utils.KieSessionUtils;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredCardService {

    @Autowired
    private KieBase kieBase;


    public Person executeRule(Person person) throws Exception {
        KieSession kieSession = kieBase.newKieSession();
        // KieSession kieSession = KieSessionUtils.getKieSession();
        kieSession.insert(person);
        kieSession.fireAllRules();
        kieSession.dispose();
        return person;
    }
}
