package xyz.jereznx.spring.elasticsearch.repository;

import org.springframework.data.repository.Repository;
import xyz.jereznx.spring.elasticsearch.entity.Bank;

/**
 * @author LQL
 * @since Create in 2020/8/23 16:33
 */
public interface BankRepository extends Repository<Bank, String> {

    Bank findByFirstname(String firstname);
}
