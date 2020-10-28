package xyz.jerez.spring.shirostarter.config;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liqilin
 * @since 2020/10/17 15:19
 */
@Configuration
public class ShiroConfig {

    /**
     * https://blog.csdn.net/qq_37186947/article/details/103847945
     * 自定义实现，需要注意，如果名称不叫这个的话，那得自己配置 ModularRealmAuthorizer
     * 或者自己配置 SessionsSecurityManager
     *
     * @return
     */
//    @Bean("authorizer")
    @Bean
    public SimpleRealm simpleRealm() {
        return new SimpleRealm();
    }

    @Bean
    public Authorizer authorizer() {
        return new ModularRealmAuthorizer();
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // all paths are managed via annotations
        chainDefinition.addPathDefinition("/**", "anon");
        // or allow basic authentication, but NOT require it.
        // chainDefinition.addPathDefinition("/**", "authcBasic[permissive]");
        return chainDefinition;
    }

    /**
     * 用于避免每次都调用鉴权方法进行鉴权，shiro默认实现内存缓存
     * @return 内存缓存实现
     */
    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

}
