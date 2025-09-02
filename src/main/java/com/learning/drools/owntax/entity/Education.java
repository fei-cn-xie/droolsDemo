package com.learning.drools.owntax.entity;

import lombok.Getter;

@Getter
public enum Education {
    COLLEGE_BELOW("大专以下",0), COLLEGE("大专",1), UNDERGRADUATE("本科",2), UNDERGRADUATE_ABOVE("本科以上",3);


    private final String educationName;
    private final Integer code;

    Education(String name,Integer level) {
        this.educationName = name;
        this.code = level;
    }
}
