package xyz.jerez.spring.shirostarter.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import xyz.jerez.spring.shirostarter.entity.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义实现，参考 JdbcRealm
 *
 * @author liqilin
 * @see org.apache.shiro.realm.jdbc.JdbcRealm
 * @since 2020/10/17 15:18
 */
@Slf4j
public class SimpleRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        使用缓存后，后续鉴权不会再进入此方法
        log.info("鉴权...");
//        此处表示所有用户角色都为 admin ，权限都为 user:show . 正常逻辑是根据 username 去查询获取
//        当前用户拥有的角色集合
        Set<String> roleNames = new HashSet<>(Collections.singletonList("admin"));
//        当前用户拥有的权限集合
        Set<String> permissions = new HashSet<>(Collections.singletonList("user:show"));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        认证不会走缓存，每次都执行
        log.info("认证...");
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        } else {
//            注意，此处无需拿前端传过来的密码，与正确密码对比. 只需要设置正确的用户名和密码即可，对比会由shiro进行
//            此处表示所有用户密码都为 lql . 正常逻辑是根据 username 去查询获取
            String passwordOfUser = "lql";
            User user = new User();
            user.setName(username);
//            如果存储的用户信息是实体对象，则必须指定唯一标识字段，且设置值
//            在配置文件通过设置  shiro-redis.cache-manager.principal-id-field-name ，默认为id
            user.setId(1);
            return new SimpleAuthenticationInfo(user, passwordOfUser.toCharArray(), this.getName());
        }
    }
}
