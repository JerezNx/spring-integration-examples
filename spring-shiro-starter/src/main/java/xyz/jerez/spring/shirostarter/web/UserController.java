package xyz.jerez.spring.shirostarter.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liqilin
 * @since 2020/10/17 15:41
 */
@RestController
public class UserController {

    /**
     * 登录方法无需认证
     * @param username 用户名
     * @param password 密码
     * @return success tip
     */
    @GetMapping("/login")
    public String login(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return "login success";
    }

    /**
     * 需要登录认证后才可访问
     * @return success tip
     */
    @RequiresAuthentication
    @GetMapping("/info")
    public String info() {
        return "info success";
    }

    /**
     * 需要拥有角色 admin
     * @return success tip
     */
    @RequiresRoles("admin")
    @GetMapping("/show")
    public String show() {
        return "show success";
    }

    /**
     * 需要拥有权限 user:create
     * @return success tip
     */
    @RequiresPermissions("user:create")
    @GetMapping("/create")
    public String create() {
        return "create success";
    }

}
