package com.javastream.melles_crm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.javastream.melles_crm.security.UserPermission.*;
import static com.javastream.melles_crm.security.UserRole.*;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public AppSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
             .csrf().disable()
            .authorizeRequests()
            .antMatchers("/css/*", "/js/*", "/images/*").permitAll()
            .antMatchers("/orders/**").hasAnyAuthority(ORDERS_READ.getPermission(), ORDERS_WRITE.getPermission())
            .antMatchers("/clients/**").hasAnyAuthority(CLIENTS_READ.getPermission(), CLIENTS_WRITE.getPermission())
            .antMatchers("/remaining/**").hasAnyAuthority(REMAINING_READ.getPermission())
            .antMatchers("/category/**").hasAnyAuthority(STORE_READ.getPermission(), STORE_WRITE.getPermission())
            .antMatchers("/settings/**").hasAnyAuthority(SETTINGS_READ.getPermission(), SETTINGS_WRITE.getPermission())
            .antMatchers(HttpMethod.POST, "/category/**").hasAnyRole(ADMIN.name())
            .antMatchers("/").hasAnyRole(ADMIN.name(), SALE.name(), STORE.name())
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
                .loginPage("/login").permitAll()
            .and()
            .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("somethingverysecured")
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("pas"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails sale = User.builder()
                .username("sale")
                .password(passwordEncoder.encode("pas"))
                .authorities(SALE.getGrantedAuthorities())
                .build();

        UserDetails store = User.builder()
                .username("store")
                .password(passwordEncoder.encode("pas"))
                .authorities(STORE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(admin, sale, store);
    }
}
