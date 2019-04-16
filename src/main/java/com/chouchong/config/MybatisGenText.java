package com.chouchong.config;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yichenshanren
 * @date 2018/6/4
 */

public class MybatisGenText {

    public static void main(String[] args) throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        // 生成
        List<String> list = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(list);
        Configuration configuration = cp.parseConfiguration(
                MybatisGenText.class.getClassLoader()
                        .getResourceAsStream("generatorConfig.xml"));
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, list);
        myBatisGenerator.generate(null);

        for (String s : list) {
            System.out.println(s);
        }

    }

}
