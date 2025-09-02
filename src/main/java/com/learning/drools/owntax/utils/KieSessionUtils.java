package com.learning.drools.owntax.utils;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class KieSessionUtils {

    public static KieSession getKieSession() throws Exception {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        Resource resource = ResourceFactory.newFileResource("D:\\Users\\fei\\gitrepo\\owntax\\src\\main\\resources\\rules\\credit.drl");
        kieFileSystem.write(resource);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();

        if(kieBuilder.getResults().getMessages(Message.Level.ERROR).size() > 0) {
            throw new Exception();
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();

        return kieBase.newKieSession();
    }

    public static KieSession getKieSessionByXML(String realPath) throws FileNotFoundException {
        File file = new File(realPath);
        FileInputStream fileInputStream = new FileInputStream(file);
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String compile = compiler.compile(fileInputStream, InputType.XLS);
        //System.out.println(compile);

        KieHelper kieHelper = new KieHelper();

        kieHelper.addContent(compile, ResourceType.DRL);
        Results verify = kieHelper.verify();
        if(verify.hasMessages(Message.Level.ERROR)) {
            System.out.println("规则文件语法异常 Errors found: " + verify.getMessages(Message.Level.ERROR));
        }
        return kieHelper.build().newKieSession();
    }
}
