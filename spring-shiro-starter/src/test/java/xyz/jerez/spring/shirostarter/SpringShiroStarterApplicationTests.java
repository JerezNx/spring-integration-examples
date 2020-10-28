package xyz.jerez.spring.shirostarter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jerez.spring.shirostarter.config.SimpleRealm;

@SpringBootTest
class SpringShiroStarterApplicationTests {

    @Autowired
    private SimpleRealm simpleRealm;

    @Test
    void contextLoads() {
        System.out.println(simpleRealm);
    }

    @Test
    void testLogin() {
        UsernamePasswordToken token = new UsernamePasswordToken("lql", "lql");
        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

}
