package xyz.jerez.spring.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jerez.spring.mybatisplus.entity.User;
import xyz.jerez.spring.mybatisplus.mapper.UserMapper;

import java.util.List;

@Slf4j
@SpringBootTest
class SpringMybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void listTest() {
        List<User> users = userMapper.selectList(null);
        String ad = "a1232" +
                "d";
        log.info("{}",users);
    }

    @Test
    void pageTest() {
        IPage<User> res = userMapper.page(new Page<>(1, 2));
        log.info("{}",res);
    }

    @Test
    void pageTest1() {
        IPage<User> res = userMapper.selectPage(new Page<>(1, 2),null);
        log.info("{}",res);
    }
}
