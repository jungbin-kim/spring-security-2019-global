package com.nhnent.edu.spring_security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/security"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:beans="http://www.springframework.org/schema/beans"
             xsi:schemaLocation="http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd
                                 http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <http use-expressions="true">
        <intercept-url pattern="/admin/**"           access="hasAuthority('ROLE_ADMIN')" />
        <intercept-url pattern="/private-project/**" access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_MEMBER')" />
        <intercept-url pattern="/project/**"         access="isAuthenticated()" />
        <intercept-url pattern="/redirect-index"     access="isAuthenticated()" />
        <intercept-url pattern="/**"                 access="permitAll()" />

        <csrf disabled="true" />
        <form-login />
        <logout />
    </http>

    <authentication-manager>
        <authentication-provider>
            <user-service>
                <user name="admin"  password="{noop}admin" authorities="ROLE_ADMIN" />
                <user name="member" password="{noop}member" authorities="ROLE_MEMBER" />
                <user name="guest"  password="{noop}guest" authorities="ROLE_GUEST" />
            </user-service>
        </authentication-provider>
    </authentication-manager>

</beans:beans>
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/private-project/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
                .antMatchers("/project/**").authenticated()
                .antMatchers("/redirect-index").authenticated()
                .anyRequest().permitAll()
                .and()
            // TODO : #2 secure channel
            .requiresChannel()
                /*
                 * TODO : #3 실습 - 관리툴/비공개프로젝트/공개 프로젝트 페이지를 secure로 설정해보세요.
                 */
                /*.anyRequest().requiresSecure()*/
                .antMatchers("/admin/**").requiresSecure()
                .antMatchers("/private-project/**").requiresSecure()
                .antMatchers("/project/**").requiresSecure()
                .anyRequest().requiresInsecure()
                .and()
            .formLogin()
                .and()
            .logout()
                .and()
            /* TODO : #4 실습 - enable csrf */
//            .csrf()
//                .disable()
            /*
             * TODO : #5 실습 - response headers
             */
            .headers()
                .defaultsDisabled()
                .cacheControl()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
                .password("{noop}admin")
                .authorities("ROLE_ADMIN")
                .and()
            .withUser("member")
                .password("{noop}member")
                .authorities("ROLE_MEMBER")
                .and()
            .withUser("guest")
                .password("{noop}guest")
                .authorities("ROLE_GUEST");
    }

}
