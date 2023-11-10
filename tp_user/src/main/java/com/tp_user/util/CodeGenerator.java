package com.tp_user.util;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Collections;
import java.util.Scanner;


/**
 *  mybatis-plus代码生成器
 *
 */
public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        //项目或者模块的路径地址，这里直接写死，要用自己改D:\code\java\cppp_spring_cloud_demo\cppp_spring_cloud_demo
        String projectPath = "D:\\Work\\Project\\WEifuwu\\TicketPark\\tp_movie";
        //数据库的连接信息
        String url="localhost:3306";
        String dataBaseName="Ticket";
        String userName="root";
        String password="123456";
//        String password="123456";

        //项目的父包名
        String parentPackageName="com";
        //获取模块名
        String packageModule = scanner("模块名");
        String tables=scanner("要生成的表名");
        FastAutoGenerator.create("jdbc:mysql://"+url+"/"+dataBaseName+"?" +
                "characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true" +
                "&rewriteBatchedStatements=true",userName,password)
                .globalConfig(builder -> {
                    builder.author("Jackvideo") // 设置作者
                            .outputDir(projectPath + "/src/main/java")// 指定输出目录
//                            .disableOpenDir()//禁止打开输出目录
                            .commentDate("yyyy-MM-dd HH:mm");
                })
                //包名配置，这个配置指/src/main/java下开始的包路径
                .packageConfig(builder -> {
                    builder
                            .parent(parentPackageName) // 设置父包名
                            .moduleName(packageModule) // 设置父包模块名
                            .entity("model.entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .mapper("mapper")
                            .xml("/resources/mapper/" + packageModule)
                            .pathInfo(Collections.singletonMap(OutputFile.xml,projectPath + "/src/main/resources/mapper/" + packageModule));
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder
                            .addInclude(tables) // 设置需要生成的表名
                            //service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            //mapper策略配置
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            //自定义配置继承父包 HmBaseMapper  此类为我们公司扩展类
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper")
                            //实体类配置
                            .entityBuilder()
                            //配置数据表与实体类名之间映射的策略
                            .naming(NamingStrategy.underline_to_camel)
                            //配置数据表的字段与实体类的属性名之间映射的策略
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .enableLombok()
                            .enableChainModel()
                            .enableTableFieldAnnotation()
                            //控制器策略配置
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .build();
                })
                .templateConfig(builder -> {
                    builder.controller("template/controller.java.vm");
                    builder.entity("template/entity.java.vm");
                })
                .execute();
    }
}
