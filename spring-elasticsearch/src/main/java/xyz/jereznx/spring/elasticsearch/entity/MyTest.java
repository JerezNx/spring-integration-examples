package xyz.jereznx.spring.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author LQL
 * @since Create in 2020/8/23 17:16
 */
@Data
@Document(indexName = "test")
public class MyTest {

    @Id
    private String id;

    private String name;

    @Field(type = FieldType.Keyword)
    private String desc;

    @Field(type = FieldType.Text)
    private String pro;

}
