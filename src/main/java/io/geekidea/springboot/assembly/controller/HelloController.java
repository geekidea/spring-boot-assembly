package io.geekidea.springboot.assembly.controller;

import com.alibaba.fastjson.JSON;
import io.geekidea.springboot.assembly.util.ClassPathFileUtil;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试控制器
 * @author geekidea
 * @date 2018/11/20
 */
@RestController
@Log
public class HelloController {
    private static final String UTF8 = "UTF-8";
    private static final String TEST_JKS = "test.jks";
    private static final String TEST_SQL = "db/test.sql";
    private static final String TEST_TXT = "test.txt";

    @Value("${spring.profiles.active}")
    private String profile;

    @RequestMapping("/hello")
    public Map<String,Object> hello(String name, HttpServletRequest request){
        log.info("requestURL：{}" + request.getRequestURL());
        log.info("queryString：{} " + request.getQueryString());

        Map<String,Object> map = new HashMap<>(3);
        map.put("code",200);
        map.put("msg","Hello:" + name);
        map.put("profile",profile);
        log.info("map json:" + JSON.toJSONString(map,true));
        return map;
    }

    /**
     * 两种方式，不将test.jks文件打包到外部，打到启动jar包中
     * 使用工具类获取文件
     * spring boot 默认打包，读取文件位置：
     *  /spring-boot-assembly/boot/spring-boot-assembly.jar!/BOOT-INF/classes!/test.jks
     * userDir在IDEA中启动的路径：E:\\github\\spring-boot-assembly
     * userDir在打包后的路径：E:\\github\\spring-boot-assembly\\target\\spring-boot-assembly\\bin
     * 打包后读取的路径：E:\github\spring-boot-assembly\target\spring-boot-assembly\config\test.jks
       jsonString = {
         "testJksFile":"E:\\github\\spring-boot-assembly\\target\\classes\\test.jks",
         "testJksText":[
         "hello test jks..."
         ],
         "testSqlFile":"E:\\github\\spring-boot-assembly\\target\\classes\\test.sql",
         "testSqlText":[
         "select",
         "  u.id as userId,",
         "  u.name as userName,",
         "  r.id as roleId,",
         "  r.name as roleName",
         "from sys_user u",
         "inner join sys_user_role ur",
         "on u.id = ur.user_id",
         "inner join sys_role r",
         "on r.id = ur.role_id",
         "where u.status = 1",
         "  and r.status = 1"
         ],
         "testTxtFile":"E:\\github\\spring-boot-assembly\\target\\classes\\test.txt",
         "testTxtText":[
         "Hello World...",
         "Test File..."
         ]
         }
     * @return
     * @throws IOException
     */
    @RequestMapping("/otherFile")
    public String otherFile() throws IOException {
        Map<String,Object> map = new LinkedHashMap<>(6);
        // 读取test.jks文件内容
        File testJksFile = ClassPathFileUtil.getFile(TEST_JKS);
        List<String> testJksText = FileUtils.readLines(testJksFile,UTF8);
        map.put("testJksFile",testJksFile);
        map.put("testJksText",testJksText);
        System.out.println("testJksText:");
        System.out.println(testJksText);

        // 读取test.sql文件内容
        File testSqlFile = ClassPathFileUtil.getFile(TEST_SQL);
        List<String> testSqlText = FileUtils.readLines(testSqlFile,UTF8);
        map.put("testSqlFile",testSqlFile);
        map.put("testSqlText",testSqlText);
        System.out.println("testSqlText:");
        System.out.println(testSqlText);

        // 读取test.txt文件内容
        File testTxtFile = ClassPathFileUtil.getFile(TEST_TXT);
        List<String> testTxtText = FileUtils.readLines(testTxtFile,UTF8);
        map.put("testTxtFile",testTxtFile);
        map.put("testTxtText",testTxtText);
        System.out.println("testTxtText:");
        System.out.println(testTxtText);

        String jsonString = JSON.toJSONString(map,true);
        System.out.println("jsonString = " + jsonString);
        return jsonString;
    }

}
