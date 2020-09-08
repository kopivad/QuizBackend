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
import org.springframework.security.config.http.SessionCreationPolicy;
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

                .antMatchers(HttpMethod.GET, "/api/v1/user/all").hasAuthority("ADMINISTRATOR")
                .antMatchers(HttpMethod.POST,"/api/v1/user").hasAuthority("ADMINISTRATOR")
                .antMatchers(HttpMethod.PUT,"/api/v1/user").hasAuthority("ADMINISTRATOR")
                .antMatchers(HttpMethod.DELETE,"/api/v1/user/*").hasAuthority("ADMINISTRATOR")

                .antMatchers(HttpMethod.POST,"/api/v1/group").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")
                .antMatchers(HttpMethod.PUT,"/api/v1/group").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")
                .antMatchers(HttpMethod.DELETE,"/api/v1/group/*").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")

                .antMatchers(HttpMethod.POST,"/api/v1/quiz").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")
                .antMatchers(HttpMethod.PUT,"/api/v1/quiz").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")
                .antMatchers(HttpMethod.DELETE,"/api/v1/quiz/*").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")

                .antMatchers(HttpMethod.POST,"/api/v1/question").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")
                .antMatchers(HttpMethod.PUT,"/api/v1/question").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")
                .antMatchers(HttpMethod.DELETE,"/api/v1/question/*").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")

                .antMatchers(HttpMethod.POST,"/api/v1/answer").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")
                .antMatchers(HttpMethod.PUT,"/api/v1/answer").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")
                .antMatchers(HttpMethod.DELETE,"/api/v1/answer/*").hasAnyAuthority("ADMINISTRATOR", "MODERATOR")

                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}