package com.rujianbin.provider.security;

import com.rujianbin.provider.common.CookieKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Created by 汝建斌 on 2017/3/31.
 *
 * 首先会去到 OncePerRequestFilter 类的 doFilter 方法，执行doFilterInternal 方法
 *
 *
 *  public void configure(WebSecurity web) throws Exception {...}
    protected void configure(HttpSecurity http) throws Exception {...}
    protected void configure(AuthenticationManagerBuilder auth) {...}
 *
 * HttpSecurity：一般用它来具体控制权限，角色，url等安全的东西。
   AuthenticationManagerBuilder：用来做登录认证的。具体的注释，看org.springframework.security.config.annotation.web.configuration 包的 WebSecurityConfigurerAdapter 类的 protected void configure(AuthenticationManagerBuilder auth) throws Exception {...}方法的注释，很清楚，注释也教了怎么用这个东西。
   WebSecurity：For example, if you wish to ignore certain requests.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name="rjbUserDetailsService")
    private RjbUserDetailsService rjbUserDetailsService;

    @Resource(name="myAuthenticationDetailsSource")
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> myAuthenticationDetailsSource;

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/webjars/**", "/images/**");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        //set 'X-Frame-Options' to 'DENY'问题
        http.headers().frameOptions().sameOrigin();

        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler("/common/403"));
        http
                .authorizeRequests()
                //静态资源
                .antMatchers("/images/**","/js/**").permitAll()
                //通用请求
                .antMatchers("/common/**","/login/**","/websocket").permitAll()
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
                    .authenticationDetailsSource(myAuthenticationDetailsSource)
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

    public MyAccessDeniedHandler myAccessDeniedHandler(String errorPage){
        MyAccessDeniedHandler h = new MyAccessDeniedHandler();
        h.setErrorPage(errorPage);
        return h;
    }
}
