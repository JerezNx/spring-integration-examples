package xyz.jereznx.spring.mybatis.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import xyz.jereznx.spring.mybatis.entity.Role;
import xyz.jereznx.spring.mybatis.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author liqilin
 * @since 2020/10/14 14:40
 */
@CacheNamespace
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> findAll();

    //    @Options(useCache = true)
    @Select("select * from user")
    List<Map> findAllMap();

    @Select("select * from user")
    Page<User> page();

    /**
     * 这里的one 替换成many 也能成功
     */
    @Select("select * from user")
    @Results(
            @Result(property = "role", javaType = Role.class, column = "role_id",
                    one = @One(select = "xyz.jereznx.spring.mybatis.mapper.RoleMapper.findById"))
    )
    List<User> findAllWithRole();
}
