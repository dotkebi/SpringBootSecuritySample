package com.github.dotkebi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebMvcConfig extends WebSecurityConfigurerAdapter {

    /*@Autowired
    @Qualifier("authServiceImpl")
    private UserDetailsService adminService;*/

    /*@Autowired
    AdminAuthenticationProvider provider;*/

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable();
        //httpSecurity.headers().frameOptions().disable();

        // static resources
        httpSecurity
                .authorizeRequests()
                .antMatchers(
                        "/css/**"
                        , "/js/**"
                        , "/img/**"
                        , "/resources/**"
                )
                .permitAll()
        ;

        httpSecurity
                .authorizeRequests()
                    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                    .and()
                .formLogin()
                    .loginPage("/login")
                    //.loginProcessingUrl("/login-process")
                    .failureUrl("/login?error")
                    .usernameParameter("id")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/normal/home", true)
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .deleteCookies("JSESSIONID")
        ;


        httpSecurity.exceptionHandling()
                .accessDeniedPage("/error")
        ;

        httpSecurity.sessionManagement()
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
        /*auth.userDetailsService(adminService)
                .passwordEncoder(passwordEncoder())
        ;*/
        /*auth.authenticationProvider(provider);*/

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
