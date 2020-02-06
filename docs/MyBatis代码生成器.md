# 1. 参考配置实例 #
## 1.1 在pom.xml中导入依赖 ##
```xml
 <dependency>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-core</artifactId>
      <version>1.3.3</version>
    </dependency>
```
## 1.2. 在项目的 sec/main/resources 中创建一个 generator 目录,并在该目录下创建一个`generatorConfig.xml`文件,内容如下: ##
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="MySqlContext" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>

        <commentGenerator>
            <property name="suppressDate" value="true"/>
            <property name="addRemarkComments" value="true" />
        </commentGenerator>

        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/mybatis"
                        userId="Zhangzb"
                        password="Zhangzb1024">
        </jdbcConnection>

        <javaModelGenerator targetPackage="test.model" targetProject="src\main\java">
            <property name="trimStrings" value="true" />
        </javaModelGenerator>

        <sqlMapGenerator targetPackage="test.xml"  targetProject="src\main\resources" />

        <javaClientGenerator type="XMLMAPPER" targetPackage="test.dao"  targetProject="src\main\java" />

        <table tableName="%" >
            <generatedKey column="id" sqlStatement="MySql"  />
        </table>
    </context>
</generatorConfiguration>
```
# 2. 运行MyBatis Generator #
## 2.1 使用Java编写代码运行 ##
> [xyz.codedog.generator.Generator.java](https://github.com/admin-zhang/LearnMybatis/blob/master/src/main/java/xyz/codedog/generator/Generator.java)

```java
/**
 * Copyright (C), 2015-2020
 * FileName: Generator
 * Author:   Administrator
 * Date:     2020/2/6 11:25
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Generator {
    public static void main(String[] args) throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        //MBG执行过程中的警告信息
        List<String> warnings = new ArrayList<String>();
        //当生成的代码重复时,覆盖原代码
        boolean overwrite = true;
        //读取MBG配置文件
        InputStream inputStream = Generator.class.getResourceAsStream("/generator/generatorConfig.xml");
        ConfigurationParser configurationParser = new ConfigurationParser(warnings);
        Configuration configuration = configurationParser.parseConfiguration(inputStream);
        inputStream.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        //创建MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration,callback,warnings);
        //执行生成代码
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}

```
