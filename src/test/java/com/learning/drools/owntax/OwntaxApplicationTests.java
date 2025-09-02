package com.learning.drools.owntax;

import com.learning.drools.owntax.entity.Person;
import com.learning.drools.owntax.utils.KieSessionUtils;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
class OwntaxApplicationTests {

    @Test
    void contextLoads() throws FileNotFoundException {

        KieSession kieSessionByXML = KieSessionUtils.getKieSessionByXML("D:\\Users\\fei\\gitrepo\\owntax\\src\\main\\resources\\rules\\decisionTable.xls");
        Person person = new Person();
        person.setAge(20);
        person.setSalary(34.0);
        kieSessionByXML.insert(person);
        kieSessionByXML.fireAllRules();
        kieSessionByXML.dispose();

    }

}
