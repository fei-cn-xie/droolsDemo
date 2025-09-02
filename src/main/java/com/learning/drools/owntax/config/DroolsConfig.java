package com.learning.drools.owntax.config;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.internal.utils.KieService;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(value = {DroolsProperties.class})
public class DroolsConfig {
    private final KieServices kieServices;


    DroolsConfig(){
        System.setProperty("drools.dateformat","yyyy-MM-dd");
        kieServices = KieServices.Factory.get();

    }

    @Bean
    @ConditionalOnMissingBean
    public KieBase kieBase(DroolsProperties droolsProperties) {

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        Resource resource = ResourceFactory.newFileResource("D:\\Users\\fei\\gitrepo\\owntax\\src\\main\\resources\\rules\\credit.drl");
        kieFileSystem.write(resource);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();

        if(kieBuilder.getResults().getMessages(Message.Level.ERROR).size() > 0) {
            return null;
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        return kieContainer.getKieBase();
    }





//    @Bean
//    @ConditionalOnMissingBean
//    public KieContainer kieContainer() {
//        return kieServices.newKieClasspathContainer();
//    }
}
