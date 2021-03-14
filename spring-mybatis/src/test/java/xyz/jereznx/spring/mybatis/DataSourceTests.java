package xyz.jereznx.spring.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author liqilin
 * @since 2021/2/1 19:05
 */
@Slf4j
@SpringBootTest
public class DataSourceTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        for (int i = 0; i < 5; i++) {
            final Connection connection = dataSource.getConnection();
            System.out.println(connection);
//            close即将connection返还到池中
            connection.close();
        }
    }

    /**
     * {@link com.zaxxer.hikari.HikariDataSource} connection 和 statement 在断网情况下都是close
     * 但DruidDatasource不是,其只有在 connection中有个 disabled属性
     * 所以想判断连接是否断开，最好还是使用 ${@link Connection#isValid(int)}
     */
    @Test
    public void test1() throws SQLException {
        String sql = "insert into test (name) values ('test')";
        Connection connection = dataSource.getConnection();
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
        preparedStatement.execute();
        System.out.println("---------断网---------");
//        false
        System.out.println(preparedStatement.isClosed());
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.isValid(5);
//        true
        System.out.println(preparedStatement.isClosed());
//        true
        System.out.println(connection.isClosed());
        System.out.println("---------联网---------");
//        true
        System.out.println(preparedStatement.isClosed());
//        true
        System.out.println(connection.isClosed());
//        false
        System.out.println(dataSource.getConnection().isClosed());

        System.out.println("---------断网---------");
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("---------联网---------");
        connection = dataSource.getConnection();
    }
}
