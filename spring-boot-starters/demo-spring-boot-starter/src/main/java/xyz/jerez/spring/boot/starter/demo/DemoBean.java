package xyz.jerez.spring.boot.starter.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liqilin
 * @since 2021/4/6 16:56
 */
@Data
@AllArgsConstructor
public class DemoBean {

    private String name;

    public void doSth() {
        System.out.println(name);
    }

}
