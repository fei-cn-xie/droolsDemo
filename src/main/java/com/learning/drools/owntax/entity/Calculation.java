package com.learning.drools.owntax.entity;

import lombok.Builder;
import lombok.Data;

@Data
public class Calculation {
    private double wage; // 税前工资
    private double wageMore;  // 应纳税所得额
    private double cess;// 税率
    private double preMinus; //速算扣除数
    private double wageMinus; // 扣税额
    private double actualWage; //税后工资
}
