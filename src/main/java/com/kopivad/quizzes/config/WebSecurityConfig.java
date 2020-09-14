package com.kopivad.quizzes.config;

//import com.kopivad.quizzes.filter.JwtRequestFilter;

import com.kopivad.quizzes.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final AuthenticationFilter authenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/user/all").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.POST, "/api/v1/user", "/api/v1/group", "/api/v1/quiz", "/api/v1/question", "/api/v1/answer").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.PATCH, "/api/v1/user", "/api/v1/group", "/api/v1/quiz", "/api/v1/question", "/api/v1/answer", "/api/v1/user/password").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user/*", "/api/v1/group/*", "/api/v1/quiz/*", "/api/v1/question/*", "/api/v1/answer/*").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.POST, "/api/v1/group", "/api/v1/quiz", "/api/v1/question", "/api/v1/answer").hasAnyRole( "MODERATOR")
                .antMatchers(HttpMethod.PATCH,"/api/v1/group", "/api/v1/quiz", "/api/v1/question", "/api/v1/answer").hasAnyRole( "MODERATOR")
                .antMatchers(HttpMethod.DELETE,"/api/v1/group/*", "/api/v1/quiz/*", "/api/v1/question/*", "/api/v1/answer/*").hasAnyRole("MODERATOR")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}