package com.webapp.userwebapp.controller;


import com.webapp.userwebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Date;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig implements WebSecurityCustomizer  {

    //implements WebSecurityCustomizer
    @Autowired
    private UserDetailsService userDetailsService;
  //  @Autowired
    //private DataSource dataSource;
/*
    @Bean
public UserDetailsService userDetailsService()
    {
        return new CustomUserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


 */
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    return provider;
    }

/*public AuthenticationManager authmanager(HttpSecurity http) throws Exception
{
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    return (AuthenticationManager) authenticationManagerBuilder.authenticationProvider(authenticationProvider());
}
 */


    /*
@Bean
public UserDetailsManager users(DataSource dataSource)
{
    UserDetails userDetails = User.withDefaultPasswordEncoder().username("username").password(passwordEncoder().encode("password")).build();
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    users.createUser(userDetails);
    return users;
}


     */


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {

        http.authorizeRequests().requestMatchers(HttpMethod.GET,"/healthz").permitAll();
        http.authorizeRequests().requestMatchers(HttpMethod.POST,"/v1/user").permitAll();
        http.authorizeRequests().requestMatchers(HttpMethod.PUT,"/v1/user/{userId}").authenticated().and().httpBasic();
        //http.authorizeRequests().requestMatchers(HttpMethod.GET,"/v1/user/{userId}").authenticated().and().httpBasic();
        http.authorizeRequests().requestMatchers(HttpMethod.GET,"/v1/user/{userId}").authenticated().and().httpBasic();
        http.authorizeRequests().requestMatchers(HttpMethod.GET,"/v1/product/{productId}").permitAll();
        http.authorizeRequests().requestMatchers(HttpMethod.POST,"/v1/product").authenticated();
        http.authorizeRequests().requestMatchers(HttpMethod.PUT,"/v1/product/{productId}").authenticated().and().httpBasic();
        http.authorizeRequests().requestMatchers(HttpMethod.DELETE,"/v1/product/{productId}").authenticated().and().httpBasic();

        http.authorizeRequests().requestMatchers(HttpMethod.GET,"v1/product/{productId}/image/{imageid}").authenticated().and().httpBasic();
        http.authorizeRequests().requestMatchers(HttpMethod.DELETE,"v1/product/{productId}/image/{imageid}").authenticated().and().httpBasic();
        http.authorizeRequests().requestMatchers(HttpMethod.GET,"v1/product/{productId}/image").authenticated().and().httpBasic();
        http.authorizeRequests().requestMatchers(HttpMethod.POST,"/v1/product/{productId}/image").authenticated();

        //  .requestMatchers(HttpMethod.PUT,"/v1/user/{userId}").hasAuthority("User")


          //      .and().formLogin()
 //             .usernameParameter("username").defaultSuccessUrl("/v1/user/{userId}").permitAll()
                //.and().httpBasic();
        http.csrf().disable();
        return http.build();

           }



    @Override
    public void customize(WebSecurity web) {
//        web.ignoring().requestMatchers("/v1/user");
    }







    /*
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource, @RequestBody User user)
    {
        UserDetails userDetails = User.builder().username(user.getUsername()).password(encoder().encode(user.getPassword())).build();
    return (UserDetailsManager) userDetails;
    }
@Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
{
     http
            .csrf().disable().cors().and().authorizeRequests((auth)->auth.requestMatchers("/v1/user").hasRole("User"))
            .httpBasic(Customizer.withDefaults());
    return http.build();
            }
*/
}
