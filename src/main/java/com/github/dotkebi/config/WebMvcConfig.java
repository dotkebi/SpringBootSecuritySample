package com.github.dotkebi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebMvcConfig extends WebSecurityConfigurerAdapter {

    /*@Bean
    public UserDetailsService adminUserDetailsService() {
        return new AuthServiceImpl();
    }*/

    /*@Bean
    public AuthenticationProvider provider() {
        return new AdminAuthenticationProvider(passwordEncoder(), authService());
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/css*")
                .antMatchers("/js*")
                .antMatchers("/img*")
                .antMatchers("/resources*")
        ;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()

                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .and()

                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .usernameParameter("id")
                .passwordParameter("password")
                .defaultSuccessUrl("/normal/home", true)
                .permitAll()
                .and()

                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()

                .exceptionHandling()
                .accessDeniedPage("/error")
                .and()

                .sessionManagement()
                .invalidSessionUrl("/")
                .maximumSessions(1)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("1234")
                .roles("ADMIN")
        ;
        /*auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from admin_users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from user_roles where username=?")
        ;*/
        /*auth.userDetailsService(adminUserDetailsService())
                .passwordEncoder(passwordEncoder())
        ;*/
        /*auth.authenticationProvider(provider);*/

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
