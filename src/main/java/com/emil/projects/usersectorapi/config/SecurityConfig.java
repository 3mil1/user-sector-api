package com.emil.projects.usersectorapi.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Value("${cors.allowed-origins:http://localhost:5173}")
        private List<String> corsAllowedOrigins;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                CsrfTokenRequestAttributeHandler csrfAttrHandler = new CsrfTokenRequestAttributeHandler();
                csrfAttrHandler.setCsrfRequestAttributeName("_csrf");

                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                                .csrf(csrf -> csrf
                                                .csrfTokenRepository(csrfTokenRepository())
                                                .csrfTokenRequestHandler(csrfAttrHandler))

                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/sectors").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                                                .requestMatchers("/api/users/current").authenticated()
                                                .requestMatchers(HttpMethod.PUT, "/api/users").authenticated()
                                                .anyRequest().denyAll())

                                .sessionManagement(sess -> sess
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                                .sessionFixation().migrateSession()
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(false)
                                                .sessionRegistry(sessionRegistry()))

                                .headers(headers -> headers
                                                .contentSecurityPolicy(
                                                                csp -> csp.policyDirectives("default-src 'self'"))
                                                .frameOptions(frame -> frame.sameOrigin()))

                                .httpBasic(basic -> basic.disable())
                                .formLogin(login -> login.disable());

                return http.build();
        }

        @Bean
        public SessionRegistry sessionRegistry() {
                return new SessionRegistryImpl();
        }

        @Bean
        public SessionAuthenticationStrategy sessionAuthenticationStrategy(SessionRegistry registry) {
                return new RegisterSessionAuthenticationStrategy(registry);
        }

        @Bean
        public HttpSessionSecurityContextRepository securityContextRepository() {
                return new HttpSessionSecurityContextRepository();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(corsAllowedOrigins);
                cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-XSRF-TOKEN"));
                cfg.setExposedHeaders(Arrays.asList("Location"));
                cfg.setAllowCredentials(true);
                cfg.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/api/**", cfg);
                return source;
        }

        @Bean
        public CookieCsrfTokenRepository csrfTokenRepository() {
                CookieCsrfTokenRepository repo = new CookieCsrfTokenRepository();
                repo.setCookieName("XSRF-TOKEN");
                repo.setCookiePath("/");
                return repo;
        }
}