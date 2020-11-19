package xyz.jereznx.spring.mybatis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liqilin
 * @since 2020/10/21 13:09
 */
@Data
public class Role implements Serializable {

    private Integer id;
    private String name;

}
