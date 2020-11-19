package xyz.jerez.spring.obfuscation.service;

import org.springframework.stereotype.Service;

/**
 * @author liqilin
 * @since 2020/11/19 17:01
 */
@Service
public class DemoServiceImpl2 implements DemoService{
    @Override
    public String info() {
        return "demo2";
    }
}
