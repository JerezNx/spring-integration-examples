package xyz.jerez.spring.jpa.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liqilin
 * @since 2021/5/1 13:50
 */
@Data
@Entity
// 当表名和实体类并非驼峰映射时，可用 @Table注解指定，否则不必要加
@Table(name = "article_01")
public class Article implements Serializable {

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
    private Date postTime;
    /**
     * 点击率
     */
    private Long clickCount;

}
