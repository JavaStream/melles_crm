package com.javastream.melles_crm.security;

import com.javastream.melles_crm.auth.AppUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.javastream.melles_crm.security.UserPermission.*;
import static com.javastream.melles_crm.security.UserRole.*;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AppUserService appUserService;

    public AppSecurityConfig(PasswordEncoder passwordEncoder, AppUserService appUserService) {
        this.passwordEncoder = passwordEncoder;
        this.appUserService = appUserService;
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
                .passwordParameter("password")
                .usernameParameter("username")
            .and()
            .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("somethingverysecured")
                .rememberMeParameter("remember-me")
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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

}
