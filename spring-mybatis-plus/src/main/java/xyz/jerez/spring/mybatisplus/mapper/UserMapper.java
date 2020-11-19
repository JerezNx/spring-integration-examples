package xyz.jerez.spring.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.jerez.spring.mybatisplus.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author liqilin
 * @since 2020/10/14 14:40
 */
@CacheNamespace
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user")
    List<Map> findAllMap();

    @Select("select * from user")
    IPage<User> page(Page<User> page);
}
