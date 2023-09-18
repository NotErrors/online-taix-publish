package com.yuren.serviceprice.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-11:18
 **/
public class MysqlGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-price?characterEncoding=utf-8&serverTimezone=GMT%2B8"
        ,"root","123456")
                .globalConfig(builder -> {
                    builder.author("拉丁").fileOverride().outputDir("D:\\Demo\\Java\\online-taix-publish\\service-price\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.yuren.serviceprice").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\Demo\\Java\\online-taix-publish\\service-price\\src\\main\\java\\com\\yuren\\serviceprice\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("price_rule");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
