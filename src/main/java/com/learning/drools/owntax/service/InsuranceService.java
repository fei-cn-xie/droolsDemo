package com.learning.drools.owntax.service;

import com.learning.drools.owntax.entity.InsuranceInfo;
import com.learning.drools.owntax.utils.KieSessionUtils;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InsuranceService {

    public List<String> insuranceInfoCheck(InsuranceInfo insuranceInfo) throws Exception {
        KieSession kieSession = KieSessionUtils.getKieSessionByXML("D:\\Users\\fei\\gitrepo\\owntax\\src\\main\\resources\\rules\\insurance.xls");
        kieSession.getAgenda().getAgendaGroup("sign").setFocus();
        kieSession.insert(insuranceInfo);
        ArrayList<String> listRules = new ArrayList<>();
        kieSession.setGlobal("listResults", listRules);
        kieSession.fireAllRules();
        return listRules;
    }

}
