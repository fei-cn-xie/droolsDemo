## 7. 案例

### 7.1 个人所得税计算

#### 7.1.1 计算规则



| 规则编号 | 名称                            | 描述                                                         |
| -------- | ------------------------------- | ------------------------------------------------------------ |
| 1        | 计算应纳税所得额                | 税前工资 - 5000                                              |
| 2        | 设置税率，应纳税所得额 <= 1500  | 税率0.03， 速算扣除数为0                                     |
| 3        | 设置税率，应纳税所得额 <= 4500  | 税率0.1， 速算扣除数为105                                    |
| 4        | 设置税率，应纳税所得额 <= 9000  | 税率0.2， 速算扣除数为555                                    |
| 5        | 设置税率，应纳税所得额 <= 35000 | 税率0.25， 速算扣除数为1005                                  |
| 6        | 设置税率，应纳税所得额 <= 55000 | 税率0.3， 速算扣除数为2755                                   |
| 7        | 设置税率，应纳税所得额 <= 80000 | 税率0.35， 速算扣除数为5505                                  |
| 8        | 设置税率，应纳税所得额 > 80000  |                                                              |
| 9        | 计算税后工资                    | 扣税额 = 应缴纳税所得额*税率 - 速算扣除数<br />税后工资 = 税前工资 - 扣税额 |

#### 7.1.2 编写drl

```sh
package rules

import com.learning.drools.owntax.entity.Calculation

rule "r1"

    when
    then
        System.out.println("ok");
end

// 第一类: 计算应纳税所得额
// 第二类： 计算税
// 第三类： 计算税后工资

/**
    private double wage; // 税前工资
    private double wageMore;  // 应纳税所得额
    private double cess;// 税率
    private double preMinus; //速算扣除数
    private double wageMinus; // 扣税额
    private double actualWage; //税后工资
*/
rule "计算应纳税所得额"
    salience 100
    date-effective "2022-09-01"
    no-loop true // 防止更改后发生死循环
    when
        $cal:Calculation(wage > 5000)
    then
        $cal.setWageMore($cal.getWage() - 3500);
        update($cal); // 工作内存中更新
end

rule "设置税率，应纳税所得额 <= 1500"
    salience 99
    date-effective "2022-09-01"
    activation-group "SetCessGroup" // 保证计算税率的规则只能有一个规则被触发
    no-loop true
    when
        $cal:Calculation(wageMore > 0 && wageMore <= 1500)
    then
        $cal.setCess(0.03);
        $cal.setPreMinus(0);
        $cal.setWageMinus($cal.getWageMore() * $cal.getCess() - $cal.getPreMinus());
        update($cal); // 工作内存中更新
end

rule "设置税率，应纳税所得额 <= 4500"
        salience 98
        date-effective "2022-09-01"
        activation-group "SetCessGroup" // 保证计算税率的规则只能有一个规则被触发
        no-loop true
        when
            $cal:Calculation(wageMore > 1500 && wageMore <= 4500)
        then
            $cal.setCess(0.1);
            $cal.setPreMinus(105);
            $cal.setWageMinus($cal.getWageMore() * $cal.getCess() - $cal.getPreMinus());
            update($cal); // 工作内存中更新
end

rule "设置税率，应纳税所得额 <= 9000"
        salience 97
        date-effective "2022-09-01"
        activation-group "SetCessGroup" // 保证计算税率的规则只能有一个规则被触发
        no-loop true
        when
            $cal:Calculation(wageMore > 4500 && wageMore <= 9000)
        then
            $cal.setCess(0.2);
            $cal.setPreMinus(555);
            $cal.setWageMinus($cal.getWageMore() * $cal.getCess() - $cal.getPreMinus());
            update($cal); // 工作内存中更新
end

rule "设置税率，应纳税所得额 <= 35000"
        salience 96
        date-effective "2022-09-01"
        activation-group "SetCessGroup" // 保证计算税率的规则只能有一个规则被触发
        no-loop true
        when
            $cal:Calculation(wageMore > 9000 && wageMore <= 35000)
        then
            $cal.setCess(0.25);
            $cal.setPreMinus(1005);
            $cal.setWageMinus($cal.getWageMore() * $cal.getCess() - $cal.getPreMinus());
            update($cal); // 工作内存中更新
end

rule "设置税率，应纳税所得额 <= 55000"
        salience 95
        date-effective "2022-09-01"
        activation-group "SetCessGroup" // 保证计算税率的规则只能有一个规则被触发
        no-loop true
        when
            $cal:Calculation(wageMore > 35000 && wageMore <= 55000)
        then
            $cal.setCess(0.3);
            $cal.setPreMinus(2755);
            $cal.setWageMinus($cal.getWageMore() * $cal.getCess() - $cal.getPreMinus());
            update($cal); // 工作内存中更新
end

rule "设置税率，应纳税所得额 <= 80000"
        salience 94
        date-effective "2022-09-01"
        activation-group "SetCessGroup" // 保证计算税率的规则只能有一个规则被触发
        no-loop true
        when
            $cal:Calculation(wageMore > 55000 && wageMore <= 80000)
        then
            $cal.setCess(0.5);
            $cal.setPreMinus(5505);
            $cal.setWageMinus($cal.getWageMore() * $cal.getCess() - $cal.getPreMinus());
            update($cal); // 工作内存中更新
end

rule "计算税后工资"
        salience 93
        date-effective "2022-09-01"
        no-loop true
        when
            $cal:Calculation(wage > 0)
        then
            $cal.setActualWage($cal.getWage() - $cal.getWageMinus());
            update($cal); // 工作内存中更新
end
```

#### 7.1.3 配置

```java
@Configuration
public class DroolsConfig {
    private final KieServices kieServices;

    DroolsConfig(){
        System.setProperty("drools.dateformat","yyyy-MM-dd");
        kieServices = KieServices.Factory.get();

    }

    @Bean
    @ConditionalOnMissingBean
    public KieBase kieBase() {
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        return kieContainer.getKieBase();
    }

}
```

```xml
<!--resource/META-INF/kmodule.xml-->
<kmodule xmlns="http://www.drools.org/xsd/kmodule">
    <!--name：指定kbase名称，任意但必须唯一， packages：规则文件的目录， default： 指定当前kbase是否为默认-->
    <kbase name="myKbase1" packages="rules" default="true">
        <!--name: 指定session名称，任意但必须唯一， default： 当前session是否为默认-->
        <ksession name="ksession-rule" default="true"/>
    </kbase>
</kmodule>
```

#### 7.1.4 java代码

- controller

```java
@RestController
public class OwnTaxController {


    @Autowired
    private OwnTaxService ownTaxService;

    @GetMapping("/calc/{wage}")
    public Calculation calc( @PathVariable Double wage) {
        return ownTaxService.CalculateOwnTax(wage);
    }
}
```



- service

```java
@Service
public class OwnTaxService {


    @Autowired
    private KieBase kieBase;

    public Calculation CalculateOwnTax(Double wage) {
       // Calculation calculation = Calculation.builder().wage(wage).build();
        Calculation calculation = new Calculation();
        calculation.setWage(wage);

        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(calculation);
        kieSession.fireAllRules();
        kieSession.dispose();
        return calculation;
    }
}

```

- entity

```java
package com.learning.drools.owntax.entity;
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
```

### 7.2 信用卡申请

#### 7.2.1 计算规则

- 合法性检查

| 规则编号 | 名称                       | 描述                                                         |
| -------- | -------------------------- | ------------------------------------------------------------ |
| 1        | 检查学历与薪水             | 如果申请人没房没车，同时学历大专以下，月薪少于5000，那么不通过 |
| 2        | 检查学历与薪水             | 如果申请人没房没车，同时学历大专或本科，并且月薪少于3000，那么不通过 |
| 3        | 检查学历与薪水             | 如果申请人没房也没车，同时学历为本科以上，并且月薪少于2000，同时之前没有信用卡的，那么不通过 |
| 4        | 检查申请人已有的信用卡数量 | 如果申请人现有的信用卡数量大于10，那么不通过                 |

- 信用卡额度

| 规则编号 | 名称  | 描述                                                       |
| -------- | ----- | ---------------------------------------------------------- |
| 1        | 规则1 | 如果申请人有房有车，或者月收入在20000以上，那么额度为15000 |
| 2        | 规则2 | 没房没车，收入在10000 ~ 20000，额度为6000                  |
| 3        | 规则3 | 没房没车，收入在10000以下，额度为3000                      |
| 4        | 规则4 | 有房没车或者有车没房，收入在10000以下，额度为5000          |
| 5        | 规则5 | 有房没车或者有车没房，收入在10000~20000，额度为8000        |

#### 7.2.2 配置

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!--drools-->
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-compiler</artifactId>
            <version>7.50.0.Final</version>
        </dependency>

        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-core</artifactId>
            <version>7.50.0.Final</version>
        </dependency>

        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-templates</artifactId>
            <version>7.50.0.Final</version>
        </dependency>

    </dependencies>
```



```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!--resource/META-INF/kmodule.xml-->
<kmodule xmlns="http://www.drools.org/xsd/kmodule">
    <!--name：指定kbase名称，任意但必须唯一， packages：规则文件的目录， default： 指定当前kbase是否为默认-->
    <kbase name="myKbase1" packages="rules" default="true">
        <!--name: 指定session名称，任意但必须唯一， default： 当前session是否为默认-->
        <ksession name="ksession-rule" default="true"/>
    </kbase>
</kmodule>
```

```java
@Configuration
public class DroolsConfig {
    private final KieServices kieServices;

    DroolsConfig(){
        System.setProperty("drools.dateformat","yyyy-MM-dd");
        kieServices = KieServices.Factory.get();

    }

    @Bean
    @ConditionalOnMissingBean
    public KieBase kieBase() {
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        return kieContainer.getKieBase();
    }

}
```





#### 7.2.3 编写drl



```sh
package rules
import com.learning.drools.owntax.entity.Person


// 合法性检查
rule "检查学历或薪水1"
    salience 100
    activation-group "credit_check"
    no-loop true
    when
        $person:Person(!hasHouse && !hasCar && salary < 5000 && educationCode < 1)
    then
        $person.setHasCredit(false);
        update($person)
        drools.halt(); // 审核不通过直接退出
end

rule "检查学历或薪水2"
    salience 99
    activation-group "credit_check"
    no-loop true
    when
        $person:Person(!hasHouse && !hasCar && salary < 3000 && (educationCode == 1 || educationCode == 2))
    then
        $person.setHasCredit(false);
        update($person)
        drools.halt(); // 审核不通过直接退出
end

rule "检查学历或薪水3"
    salience 98
    activation-group "credit_check"
    no-loop true
    when
        $person:Person(!hasHouse && !hasCar && salary < 2000 && educationCode > 2 && creditCardNum <= 0)
    then
        $person.setHasCredit(false);
        update($person)
        drools.halt(); // 审核不通过直接退出
end

rule "检查学历或薪水4"
    salience 97
    activation-group "credit_check"
    no-loop true
    when
        $person:Person(creditCardNum > 10)
    then
        $person.setHasCredit(false);
        update($person)
        drools.halt(); // 审核不通过直接退出
end

// 信用卡额度

rule "信用卡额度规则1"
    salience 90
    activation-group "credit_quota"
    no-loop true
    when
        $person:Person( hasCredit && (hasCar && hasHouse || salary > 20000) )
    then
        $person.setQuota(15000.0);
        update($person)
        System.out.println($person);
end

rule "信用卡额度规则2"
    salience 89
    activation-group "credit_quota"
    no-loop true
    when
        $person:Person( hasCredit && (!hasCar && !hasHouse && (salary >= 10000 && salary <= 20000)) )
    then
        $person.setQuota(6000.0);
        update($person)
end

rule "信用卡额度规则3"
    salience 88
    activation-group "credit_quota"
    no-loop true
    when
        $person:Person( hasCredit && (!hasCar && !hasHouse && salary < 10000) )
    then
        $person.setQuota(3000.0);
        update($person)
end

rule "信用卡额度规则4"
    salience 87
    activation-group "credit_quota"
    no-loop true
    when
        $person:Person( hasCredit && (hasCar || hasHouse) && salary < 10000)
    then
        $person.setQuota(5000.0);
        update($person)
end

rule "信用卡额度规则5"
    salience 86
    activation-group "credit_quota"
    no-loop true
    when
        $person:Person( hasCredit && (hasCar || hasHouse) && ( salary > 10000 && salary <= 20000))
    then
        $person.setQuota(8000.0);
        update($person)
end
```



#### 7.2.4 Java代码

```java
@Service
public class CredCardService {

    @Autowired
    private KieBase kieBase;

    public Person executeRule(Person person){
        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(person);
        kieSession.fireAllRules();
        kieSession.dispose();
        return person;
    }
}

```

### 7.3 保险产品准入规则

#### 7.3.1 决策表

Drools除了支持drl形式的文件外，还支持xls格式的文件（即，excel文件）。这种xls格式的文件通常称为决策表（decision table）。

决策表是一个“精确而紧凑的”表示条件逻辑的方式，非常适合商业级别的规则。决策表与现有的drl文件可以无缝替换。Drools提供了响应的API可以将xls文件编译为drl格式的字符串。

![image-20250902132037926](2025-08-16-Sat-T-Drools规则引擎/image-20250902132037926.png)

决策表语法：



| 关键字       | 说明                                                         | 是否必须                                                     |
| ------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| RuleSet      | 相当于drl文件中的package                                     | 必须，只能有一个。如果没有设置RuleSet对应的值，则使用默认rule_table |
| Sequential   | 取值为Boolean，true表示规则从上到下顺序执行，false表示乱序   | 可选                                                         |
| Import       | 相当于drl中的import，用逗号分隔                              | 可选                                                         |
| Variables    | 相当于drl 中的global，如果有多个全局变量则中间用逗号分隔     | 可选                                                         |
| RuleTable    | 它指示了后面将会有一批rule，RuleTable的名称将会作为以后生成rule的前缀 | 必须                                                         |
| CONDITION    | 规则条件关键字，相当于drl文件中的when。下面两行则表示LHS部分，第三行为注释行，从第四行开始，每一行代表一条规则 | 每一个规则至少有一个                                         |
| ACTION       | 相当于drl中的then                                            | 每个规则至少一个                                             |
| NO-LOOP      |                                                              | 可选                                                         |
| AGENDA-GROUP | 议程分组，只有获取焦点的组中的规则才有可能触发 `agenda-group 'group2'` <br />获取焦点：  java 代码或规则文件`kieSession.getAgenda().getAgendaGroup("group2").setFocus();`  `auto-focus true` | 可选                                                         |
|              |                                                              |                                                              |

在决策表中经常使用到占位符，语法为`$number`,用于替换每条规则中设置的具体值。



#### 7.3.2 测试案例

- 准备决策表

![image-20250902154431628](2025-08-16-Sat-T-Drools规则引擎/image-20250902154431628.png)

- 格式转换

```java
public static KieSession getKieSessionByXML(String realPath) throws FileNotFoundException {
    File file = new File(realPath);
    FileInputStream fileInputStream = new FileInputStream(file);
    SpreadsheetCompiler compiler = new SpreadsheetCompiler();
    String compile = compiler.compile(fileInputStream, InputType.XLS);
    System.out.println(compile);

    KieHelper kieHelper = new KieHelper();
    kieHelper.addContent(compile, ResourceType.DRL);
    return kieHelper.build().newKieSession();
}
```



- 测试

```java
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
```



#### 7.3.3 规则说明

以下规则全部满足才能准入成功

| 规则编号 | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| 1        | 保险公司是：PICC                                             |
| 2        | 销售区域是：北京、天津                                       |
| 3        | 投保年龄是：0 ~17 岁                                         |
| 4        | 保险期间是：20，25，30年                                     |
| 5        | 缴费方式是：一次性交清或年缴                                 |
| 6        | 保险期与缴费期规则一：保险期间为20年，缴费期间最长10年缴费，且不能选择一次性交清 |
| 7        | 保险期与缴费期规则一：保险期间为25年，缴费期间最长15年缴费，且不能选择一次性交清 |
| 8        | 保险期与缴费期规则一：保险期间为30年，缴费期间最长20年缴费，且不能选择一次性交清 |
| 9        | 被保人要求：（投保年龄 + 保险期间）不得大于40周岁            |
| 10       | 保险金额规则：投保时约定，最低5万元，超过的部分必须为1000元的整数倍 |
| 11       | 出单基本保额限额规则：线上出单基本保额限额62.5万元，超过62.5万元需要配合转线下出单 |

本案例中规则文件是一个excel文件，业务人员可以直接更改这个文件中指标的值，系统不需要做任何变更

#### 7.3.4 配置

同上

#### 7.3.5 编写决策表

![image-20250902182730510](2025-08-16-Sat-T-Drools规则引擎/image-20250902182730510.png)

#### 7.3.6 Java代码

- utils

```java
public static KieSession getKieSessionByXML(String realPath) throws FileNotFoundException {
    File file = new File(realPath);
    FileInputStream fileInputStream = new FileInputStream(file);
    SpreadsheetCompiler compiler = new SpreadsheetCompiler();
    String compile = compiler.compile(fileInputStream, InputType.XLS);
    System.out.println(compile);

    KieHelper kieHelper = new KieHelper();

    kieHelper.addContent(compile, ResourceType.DRL);
    Results verify = kieHelper.verify();
    if(verify.hasMessages(Message.Level.ERROR)) {
        System.out.println("规则文件语法异常 Errors found: " + verify.getMessages(Message.Level.ERROR));
    }
    return kieHelper.build().newKieSession();
}
```

- service

```java
public List<String> insuranceInfoCheck(InsuranceInfo insuranceInfo) throws Exception {
    KieSession kieSession = KieSessionUtils.getKieSessionByXML("D:\\Users\\fei\\gitrepo\\owntax\\src\\main\\resources\\rules\\insurance.xls");
    kieSession.getAgenda().getAgendaGroup("sign").setFocus();
    kieSession.insert(insuranceInfo);
    ArrayList<String> listRules = new ArrayList<>();
    kieSession.setGlobal("listResults", listRules);
    kieSession.fireAllRules();
    return listRules;
}
```

