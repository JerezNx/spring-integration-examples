package xyz.jerez.spring.obfuscation.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author liqilin
 * @since 2020/11/19 17:01
 */
@Primary
@Service
public class DemoServiceImpl1 implements DemoService{

    private String name = "demo1";

    @Override
    public String info() {
        return name;
    }
}
