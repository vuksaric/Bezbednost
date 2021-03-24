package com.example.KT1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http
                // komunikacija izmedju klijenta i servera je stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // za neautorizovane zahteve posalji 401 gresku
                // svim korisnicima dopusti da pristupe putanjama / i /api/foo
                .authorizeRequests().antMatchers("/").permitAll().antMatchers("/api/foo").permitAll()
                .anyRequest().authenticated().and()
                .cors();*/
        http.csrf().disable();
    }
}
