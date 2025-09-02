package com.learning.drools.owntax.entity;

import lombok.Data;

@Data
public class InsuranceInfo {
    private String insuranceCompany; // 保险公司
    private String caseCode; // 方案代码
    private String channelNum; //渠道号
    private String saleRegion; // 销售区域
    private Integer insuredAge; // 投保年龄
    private Integer insurancePeriod; // 保险期间
    private Integer payPeriod; //缴费期间
    private Integer insuranceQuota; // 保额
    private String payType; // 缴费方式

}


//"insuranceCompany": ,
//"caseCode": ,
//"channelNum": ,
//"saleRegion": ,
//"insuredAge": ,
//"insurancePeriod": ,
//"payPeriod": ,
//"insuranceQuota": ,
//"payType": ,
