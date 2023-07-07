package at.codecool.humanoraiserver.config;

import at.codecool.humanoraiserver.JsonAuthenticationFilter;
import at.codecool.humanoraiserver.JwtAuthenticationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   AuthenticationManager authenticationManager,
                                                   RememberMeServices rememberMeServices) throws Exception {
        return httpSecurity.cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests(
                    requests -> requests
                        .requestMatchers("/session", "/users").permitAll()
                        .requestMatchers("/quotes").hasAuthority("isAdmin")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .rememberMe(remember -> remember.rememberMeServices(rememberMeServices))
                .build();
    }

    @Bean
    public AbstractAuthenticationProcessingFilter authenticationFilter(ObjectMapper objectMapper,
                                                                       AuthenticationManager authenticationManager,
                                                                       RememberMeServices rememberMeServices) {
        return new JsonAuthenticationFilter(new AndRequestMatcher(
                new AntPathRequestMatcher("/session"),
                request -> HttpMethod.POST.matches(request.getMethod())), objectMapper, authenticationManager,
                rememberMeServices);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService service, PasswordEncoder passwordEncoder) {
        var provider = new DaoAuthenticationProvider(passwordEncoder);
        provider.setUserDetailsService(service);
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider,
                                                      JwtAuthenticationProvider jwtAuthenticationProvider) {
        return new ProviderManager(daoAuthenticationProvider, jwtAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 19456, 2);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost**", "http://127.0.0.1**"));
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
