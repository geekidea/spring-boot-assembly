package io.geekidea.springboot.assembly;

import org.springframework.stereotype.Service;

/**
 * 测试服务实现类
 * @author geekidea
 * @date 2018/11/20
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        System.out.println("service hello:" + name);
        return "service hello:" + name;
    }

}
