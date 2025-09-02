package com.learning.drools.owntax.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "drools")
@Data
public class DroolsProperties {
    private String rulePath;
}
