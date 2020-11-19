package xyz.jereznx.spring.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.jereznx.spring.mybatis.entity.Role;

/**
 * @author liqilin
 * @since 2020/10/21 13:10
 */
@Mapper
public interface RoleMapper {

    @Select("select * from role where id = #{id} ")
    Role findById(String id);

}
