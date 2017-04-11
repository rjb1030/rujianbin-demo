package com.rujianbin.provider.security;

import com.rujianbin.provider.common.CookieKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;

/**
 * Created by 汝建斌 on 2017/3/31.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name="rjbUserDetailsService")
    private RjbUserDetailsService rjbUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http

                //可以自己实现AbstractAuthenticationProcessingFilter做登录验证，此时successHandler也得实现在你的实现类里
//                .addFilterBefore(usernamePasswordAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                //静态资源
                .antMatchers("/images/**").permitAll()
                //通用请求
                .antMatchers("/common/**","/login/**").permitAll()
                //权限控制请求
                .antMatchers("/providerFMK/index").hasAuthority("p1:f1:read")
                .antMatchers("/home").hasAuthority("p1:f1:read")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login?no-permit")
                    .loginProcessingUrl("/login")
                    .failureHandler(myLoginFailureHandler("/login?error="))
                    .permitAll()
                    .successHandler(myLoginSuccessHandler("/home"))
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/login/logout", "GET"))
                    .logoutSuccessHandler(myLogoutSuccessHandler("/login?logout"))
                    .permitAll()
                    .deleteCookies(CookieKey.cookie_user_key)
                    .invalidateHttpSession(true);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义用户和权限查询

        MyAuthenticationProvider provider = new MyAuthenticationProvider(false);
        provider.setUserDetailsService(rjbUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        auth.authenticationProvider(provider);

//        auth.userDetailsService(rjbUserDetailsService).passwordEncoder(passwordEncoder());
//        auth.eraseCredentials(false);

//        auth
//                .inMemoryAuthentication()
//                .withUser("rjb").password("1234").roles("USER");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }


    public MyLoginSuccessHandler myLoginSuccessHandler(String defaultTargetUrl){
        return new MyLoginSuccessHandler(defaultTargetUrl);
    }

    public MyLoginFailureHandler myLoginFailureHandler(String defaultTargetUrl){
        return new MyLoginFailureHandler(defaultTargetUrl);
    }

    public MyLogoutSuccessHandle myLogoutSuccessHandler(String defaultTargetUrl){
        return new MyLogoutSuccessHandle(defaultTargetUrl);
    }

}
