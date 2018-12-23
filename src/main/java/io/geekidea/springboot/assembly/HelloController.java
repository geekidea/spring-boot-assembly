package io.geekidea.springboot.assembly;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 * @author geekidea
 * @date 2018/11/20
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @Value("${spring.profiles.active}")
    private String profile;

    @RequestMapping("/hello")
    @ResponseBody
    public Map<String,Object> hello(String name, HttpServletRequest request){
        System.out.println("Hello:" + name);

        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        System.out.println("request.getQueryString() = " + request.getQueryString());
        System.out.println("request.getSession().getId() = " + request.getSession().getId());
        
        String result = helloService.hello(name);
        Map<String,Object> map = new HashMap<>(2);
        map.put("code",200);
        map.put("msg",result);
        map.put("profile",profile);
        System.out.println("map json:" + JSON.toJSONString(map,true));
        return map;
    }

}
