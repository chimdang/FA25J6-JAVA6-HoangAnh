package com.config;

import com.dao.DaoUserDetailsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new DaoUserDetailsManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Bỏ cấu hình mặc định CSRF và CORS
        http.csrf(config -> config.disable()).cors(config -> config.disable());
// Phân quyền sử dụng
        http.authorizeHttpRequests(config -> {
            config.requestMatchers("/poly/url1").authenticated();
            config.requestMatchers("/poly/url2").hasAuthority("ROLE_USER");
            config.requestMatchers("/poly/url3").hasAuthority("ROLE_ADMIN");
            config.requestMatchers("/poly/url4").hasAnyAuthority("ROLE_USER","ROLE_ADMIN");
            config.anyRequest().permitAll();
        });

        http.exceptionHandling(denied ->
                denied.accessDeniedPage("/access-denied"));

        http.formLogin(config -> {
            config.loginPage("/login/form");
            config.loginProcessingUrl("/login/check");
            config.defaultSuccessUrl("/login/success");
            config.failureUrl("/login/failure");
            config.permitAll();
            config.usernameParameter("username");
            config.passwordParameter("password");
        });
// Ghi nhớ tài khoản
        http.rememberMe(config -> {
            config.tokenValiditySeconds(3*24*60*60);
            config.rememberMeCookieName("remember-me");
            config.rememberMeParameter("remember-me");
        });
// Đăng xuất
        http.logout(config -> {
            config.logoutUrl("/logout");
            config.logoutSuccessUrl("/login/exit");
            config.clearAuthentication(true);
            config.invalidateHttpSession(true);
            config.deleteCookies("remember-me");
        });
        return http.build();
    }
}