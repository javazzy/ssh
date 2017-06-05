package my.ssh.initializer.config;

import my.ssh.biz.ssh.sys.service.SysUserService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private DataSource dataSource;
    @Resource
    private CacheManager cacheManager;
    @Resource
    private UserCache userCache;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                // Spring Security should completely ignore URLs starting with /resources/
                .antMatchers("/resources/**")
                .antMatchers("/upload/**")
                .antMatchers("/assets/**")
                .antMatchers("/css/**")
                .antMatchers("/image/**")
                .antMatchers("/js/**")
                .antMatchers("/lib/**")
                .antMatchers("/ueditor/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 自定义登录页面
        http
                .formLogin()
                .loginPage("/login.jsp")
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll();
        // 自定义注销
        http
                .logout()
                .logoutUrl("/j_spring_security_logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login.jsp")
                .permitAll();
        // 设置拦截规则
        http
                .authorizeRequests()
                .antMatchers("/upload/**").permitAll()
                .antMatchers("/view/**").permitAll()
                .antMatchers("/demo/**").permitAll()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/admin/**").authenticated()
                .anyRequest().authenticated();

        // session管理
        http
                .sessionManagement()
                .sessionAuthenticationErrorUrl("/login.jsp")
                .maximumSessions(1)
                .expiredUrl("/login.jsp");

        // RemeberMe
        http
                .rememberMe()
                .tokenRepository(tokenRepository());

        http
                .csrf().disable();
        sysUserService.setUserCache(userCache);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(daoAuthenticationProvider())
                .eraseCredentials(false)
                .userDetailsService(sysUserService);
    }

    @Bean
    public SpringCacheBasedUserCache userCache() throws Exception {
        return new SpringCacheBasedUserCache(cacheManager.getCache("spring-security"));
    }

    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    public AuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(sysUserService);

        daoAuthenticationProvider.setUserCache(userCache);
        daoAuthenticationProvider.setPasswordEncoder(new Md5PasswordEncoder());
        return daoAuthenticationProvider;
    }

}

