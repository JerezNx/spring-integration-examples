package xyz.jerez.spring.spel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.PropertyResolver;

@SpringBootTest
class SpringSpelApplicationTests {

    @Autowired
    private PropertyResolver propertyResolver;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        System.out.println(propertyResolver.resolvePlaceholders("${key:233}"));
    }

}
