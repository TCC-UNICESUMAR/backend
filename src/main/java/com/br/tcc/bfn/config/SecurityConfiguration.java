package com.br.tcc.bfn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements WebMvcConfigurer {
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private LogoutHandler logoutHandler;

    private final static Logger LOGGER = Logger.getLogger(SecurityConfiguration.class.getName());

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/user/register")
                        , new AntPathRequestMatcher("/api/v1/auth/authenticate")
                        , new AntPathRequestMatcher("/h2-console/**")
                        , new AntPathRequestMatcher("/api/v1/user/register-admin")
                        , new AntPathRequestMatcher("/swagger-ui/**")
                        , new AntPathRequestMatcher("/v3/api-docs/**")
                        , new AntPathRequestMatcher("/api-docs/**")
                        , new AntPathRequestMatcher("/api/v1/user/status/**")
                        , new AntPathRequestMatcher("/api/v1/donation/findAllDonations")
                        , new AntPathRequestMatcher("/api/v1/donation/findAllDonationsOrder")
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
        http.headers().frameOptions().disable();
        return http.build();
    }
}
