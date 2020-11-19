package xyz.jereznx.spring.mybatis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liqilin
 * @since 2020/10/14 14:39
 */
@Data
public class User implements Serializable {

    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private String roleId;

    private Role role;
}
