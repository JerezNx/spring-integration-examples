package xyz.jereznx.spring.elasticsearch.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jereznx.spring.elasticsearch.entity.Bank;

/**
 * @author LQL
 * @since Create in 2020/8/23 16:35
 */
@SpringBootTest
public class BankRepositoryTest {

    @Autowired
    private BankRepository bankRepository;

    @Test
    void test() {
        final Bank amber = bankRepository.findByFirstname("Amber");
        System.out.println(amber.getBalance());
    }

}
