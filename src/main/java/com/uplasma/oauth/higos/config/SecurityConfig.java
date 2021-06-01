package com.uplasma.oauth.higos.config;

import com.uplasma.oauth.higos.handler.MyAccessDeniedHandler;
import com.uplasma.oauth.higos.handler.MyAuthenticationFailureHandler;
import com.uplasma.oauth.higos.handler.MyAuthenticationSuccessHandler;
import com.uplasma.oauth.higos.service.UserDetailsServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private UserDetailsServerImpl userDetailsServer;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin().loginPage("/login").loginProcessingUrl("/login") // 自定义登录路径
                //.successForwardUrl("/index")
                .successHandler(new MyAuthenticationSuccessHandler("/web/main.html")) // 自定义登录成功处理器
                //.failureUrl("/err")  // 自定义异常跳转,本质就是`SimpleUrlAuthenticationFailureHandler`的封装
                .failureHandler(new MyAuthenticationFailureHandler("/err"))
                .usernameParameter("userName"); // 自定义入参

        // 授权
        http.authorizeRequests().antMatchers("/login","/err").permitAll()
                .antMatchers("/imgs/**","/js/**","/css/**").permitAll()  // 保证未授权的环境下也能访问静态资源
                //.antMatchers("/**/*.jpg").permitAll()  // ant表达式
                //.regexMatchers(HttpMethod.GET,"/login").permitAll() // 正则
                //.mvcMatchers("/login").servletPath("/api").permitAll()  // mvc.servlet
                //.antMatchers("/index").hasAuthority("admin")  // 基于权限控制
                //.antMatchers("/index").hasAnyAuthority("admin","ad") //基于权限控制
                .antMatchers("/index").hasIpAddress("127.0.0.1")
                .antMatchers("/index").hasAnyRole("adb","a") //基于角色控制
                //.antMatchers("/index").access(
                //        "@indexTestService.hasPermission(request,authentication)")
                .anyRequest().authenticated();

        // 异常处理
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

        // 记住我
        http.rememberMe()
                .rememberMeParameter("rememberMe")
                .tokenRepository(persistentTokenRepository)
                .userDetailsService(userDetailsServer)
                .tokenValiditySeconds(30);

        http.logout().logoutSuccessUrl("/login");

        // 关闭csrf
        //http.csrf().disable().cors().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Lazy
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

}
