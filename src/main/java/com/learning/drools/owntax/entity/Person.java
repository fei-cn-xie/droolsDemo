package com.learning.drools.owntax.entity;

import lombok.Data;

@Data
public class Person {
    private String name;
    private int age;
    private String address;
    private Integer educationCode; // 0, 1, 2, 3
    private Double salary;
    private Integer creditCardNum;
    private Boolean hasHouse;
    private Boolean hasCar;
    private Boolean hasCredit;
    private Double quota;

//
//    "name": "tom",
//    "age": 22,
//    "address": "China",
//    "educationCode":  3,
//    "salary": 23000,
//    "creditCardNum": 0,
//    "hasHouse":  true,
//    "hasCar": true,
//    "hasCredit": true,
//    "quota": 0.0

}
