package com.example.security.demo.config;

import com.example.security.demo.security.BearerTokenAuthenticationEntryPoint;
import com.example.security.demo.security.BearerTokenAuthenticationFilter;
import com.example.security.demo.security.BearerTokenProvider;
import com.example.security.demo.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManagerBuilder builder,
                                                 BearerTokenAuthenticationEntryPoint bearerTokenAuthenticationEntryPoint,
                                                 BearerTokenAuthenticationFilter bearerTokenAuthenticationFilter) throws Exception {
    http.csrf(CsrfConfigurer::disable);
    http.authorizeHttpRequests(authorize ->
        authorize.antMatchers("/actuator/**").permitAll()
            .antMatchers("/signin").permitAll()
            .antMatchers("/signup").permitAll()
            .anyRequest().authenticated()
    );
    http.authenticationManager(builder.getOrBuild());
    http.exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(bearerTokenAuthenticationEntryPoint));
    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.addFilterBefore(bearerTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public WebSecurityCustomizer ignore(){
    return web -> web.ignoring().antMatchers("/v3/api-docs/**",
     "/swagger-ui/**",
     "/swagger-ui.html");
  }

  @Bean
  public AuthenticationManager authenticationManagerBean(HttpSecurity http,
                                                         UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    return authenticationManagerBuilder.build();
  }

  @Bean
  public BearerTokenAuthenticationFilter bearerTokenAuthenticationFilter(BearerTokenProvider bearerTokenProvider,
                                                                         CustomUserDetailsService userDetailsService) {
    return new BearerTokenAuthenticationFilter(bearerTokenProvider, userDetailsService);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    //return new BCryptPasswordEncoder();
  }

}
