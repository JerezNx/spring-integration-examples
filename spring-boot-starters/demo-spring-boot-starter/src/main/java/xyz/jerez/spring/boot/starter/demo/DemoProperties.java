package xyz.jerez.spring.boot.starter.demo;

import lombok.Data;

/**
 * @author liqilin
 * @since 2021/4/6 16:56
 */
@Data
public class DemoProperties {

    public static final String PREFIX = "config.demo";

    /**
     * 名称
     */
    private String name = "lql";

}
