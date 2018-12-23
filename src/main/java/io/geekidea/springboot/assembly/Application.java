package io.geekidea.springboot.assembly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Spring Boot 项目启动入口
 * @author geekidea
 * @since 2018/11/20
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        // 当前项目环境
        System.out.println("spring.profiles.active = " + environment.getProperty("spring.profiles.active"));
        String port = environment.getProperty("server.port");
        String contextPath = environment.getProperty("server.servlet.context-path");
        System.out.println("contextPath = " + contextPath);
        System.out.println("server.port = " + port);
        System.out.println("hello = " + environment.getProperty("hello"));

        System.out.println("http://localhost:" + port + contextPath + "/hello?name=123");

    }
}
