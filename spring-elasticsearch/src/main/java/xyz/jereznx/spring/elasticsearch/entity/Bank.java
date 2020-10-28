package xyz.jereznx.spring.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author LQL
 * @since Create in 2020/8/23 16:13
 */

@Data
@Document(indexName = "bank")
public class Bank {

    @Id
    private String id;

    private String firstname;

    private int balance;

}
