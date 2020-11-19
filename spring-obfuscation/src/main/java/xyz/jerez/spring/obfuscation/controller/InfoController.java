package xyz.jerez.spring.obfuscation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.jerez.spring.obfuscation.service.DemoService;

/**
 * @author liqilin
 * @since 2020/11/19 16:29
 */
@RestController
public class InfoController {

    @Autowired
    private DemoService demoService1;

    /**
     * 参数必须加注解，否则名字会变，前端无法映射
     * Post参数可以用JSON或者DTO接，忽略DTO的混淆
     */
    @GetMapping("/info")
    String info(@RequestParam("name") String name) {
        return name + "success";
    }

    @GetMapping("/demo")
    String demo() {
        return demoService1.info();
    }

}
