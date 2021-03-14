package xyz.jerez.spring.json;

import java.io.InputStream;

/**
 * @author liqilin
 * @since 2021/3/5 16:01
 */
public class BaseTest {

    private final String fileName = "data/data.json";

    protected InputStream getJsonFile() {
        return this.getClass().getClassLoader().getResourceAsStream(fileName);
    }

}
