package xyz.jereznx.spring.mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jereznx.spring.mybatis.entity.User;
import xyz.jereznx.spring.mybatis.mapper.UserMapper;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class SpringMybatisApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private SqlSession sqlSession;

    @Test
    void contextLoads() {
        sqlSessionFactory.openSession();
        sqlSession.selectList("");
    }

    @Test
    void testQueryReturnEntity() {
        List<User> all = userMapper.findAll();
        System.out.println(all);
    }

    @Test
    void testQueryReturnMap() {
        List<Map> allMap = userMapper.findAllMap();
        System.out.println(allMap);
        List<Map> allMap1 = userMapper.findAllMap();
        System.out.println(allMap == allMap1);
    }

    @Test
    void testPage() {
        PageHelper.startPage(1,2);
        Page<User> page = userMapper.page();
        log.info("{}",page);
    }

    @Test
    void joinQueryTest() {
        List<User> allWithRole = userMapper.findAllWithRole();
        log.info("{}",allWithRole);
    }
}
