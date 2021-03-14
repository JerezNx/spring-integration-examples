package xyz.jerez.spring.validator.bean;

import lombok.Data;

/**
 * 被校验的实体
 *
 * @author liqilin
 * @since 2021/3/3 9:22
 */
@Data
public class User {

    private Integer id;

    private String name;

    private Integer age;

}
