package xyz.jereznx.spring.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liqilin
 * @since 2021/5/1 13:50
 */
@Data
@Document(indexName = "article_index",shards = 5, replicas = 1, indexStoreType = "fs", refreshInterval = "-1")
public class Article implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 551589397625941750L;
    @Id
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String abstracts;
    /**
     * 内容
     */
    private String content;
    /**
     * 发表时间
     */
    @Field(format = DateFormat.date_time, index = true, store = true, type = FieldType.Date)
    private Date postTime;
    /**
     * 点击率
     */
    private Long clickCount;

}
