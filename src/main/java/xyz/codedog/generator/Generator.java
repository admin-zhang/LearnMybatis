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
//        InputStream inputStream = Generator.class.getResourceAsStream("/generator/generatorConfig.xml");
        InputStream inputStream = Generator.class.getResourceAsStream("/generator/generatorConfig-country.xml");

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
