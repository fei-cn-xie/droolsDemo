package com.learning.drools.owntax.controller;

import com.learning.drools.owntax.entity.InsuranceInfo;
import com.learning.drools.owntax.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class InsuranceController {


    @Autowired
    private InsuranceService insuranceService;

    @PostMapping("/insuranceCheck")
    public Map<String,Object> insuranceInfoCheck(@RequestBody InsuranceInfo insuranceInfo) throws Exception {

        List<String> strings = insuranceService.insuranceInfoCheck(insuranceInfo);
        Map<String, Object> map = new HashMap<>();
        if(!strings.isEmpty()){
            map.put("checkResult", false);
            map.put("msg","准入失败");
            map.put("details", strings);
        }else {
            map.put("checkResult", true);
            map.put("msg", "准入成功");
        }

        return map;

    }
}
