package com.rujianbin.provider.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
//                .antMatchers("/upload", "/css/**", "/js/**", "/images/**",
//                        "/resources/**", "/lib/**", "/skin/**", "/template/**").permitAll()
//                .antMatchers("/providerFMK/index").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/providerFMK/login?no-permit")
                    .loginProcessingUrl("/providerFMK/login")
                    .failureUrl("/providerFMK/login?error")
                    .permitAll()
                    .successHandler(loginSuccessHandler())
                .and()
                .logout().logoutSuccessUrl("/providerFMK/login").permitAll().invalidateHttpSession(true);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义用户和权限查询
        auth.userDetailsService(rjbUserDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
//        auth
//                .inMemoryAuthentication()
//                .withUser("rjb").password("1234").roles("USER");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }


    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler("/providerFMK/index");
    }

}
