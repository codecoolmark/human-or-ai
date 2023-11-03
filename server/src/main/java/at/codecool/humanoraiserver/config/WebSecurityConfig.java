package at.codecool.humanoraiserver.config;

import at.codecool.humanoraiserver.Cookies;
import at.codecool.humanoraiserver.JsonAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
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
                                                   JwtDecoder jwtDecoder,
                                                   RememberMeServices rememberMeServices) throws Exception {
        return httpSecurity.cors(withDefaults())
                // we disable CSRF (cross site request forgery) tokens because we rely on CORS to prevent
                // cross site request forgery (https://owasp.org/www-community/attacks/csrf#other-http-methods)
                .csrf(csrf -> csrf.disable())
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests(
                    requests -> requests
                        // the /session and /users endpoints are used for registration and querying login state
                        .requestMatchers("/session", "/users").permitAll()
                        // the /quotes endpoint should only be reachable for admins
                        .requestMatchers("/quotes").hasAuthority("isAdmin")
                        // any other request should be authenticated
                        .anyRequest().authenticated())
                // we don't use any HttpSession's
                // (https://docs.spring.io/spring-security/site/docs/6.0.1/api/org/springframework/security/config/http/SessionCreationPolicy.html)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set up the remember me functionality. Remember me is the term used by spring for remembering the login
                // state of a user. (https://docs.spring.io/spring-security/reference/servlet/authentication/rememberme.html)
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder)))
                .rememberMe(rememberMe -> rememberMe.rememberMeServices(rememberMeServices))
                .build();
    }

    /**
     * Register's our {@link AbstractAuthenticationProcessingFilter} implementation for checking if a user is logged in.
     * @param objectMapper Object mapper for generating json from objects.
     * @param authenticationManager The authentication manager to authenticated users.
     * @return The authentication filter to use.
     */
    @Bean
    public AbstractAuthenticationProcessingFilter authenticationFilter(ObjectMapper objectMapper,
                                                                       AuthenticationManager authenticationManager,
                                                                       Cookies cookies) {
        var authenticationFilter = new JsonAuthenticationFilter(new AndRequestMatcher(
                new AntPathRequestMatcher("/session"),
                request -> HttpMethod.POST.matches(request.getMethod())), objectMapper, authenticationManager);

        authenticationFilter.setAuthenticationSuccessHandler((request, response, authentication) ->
                cookies.setCookie((UserDetails) authentication.getPrincipal(), response));

        return authenticationFilter;
    }

    /**
     * Returns the {@link org.springframework.security.authentication.AuthenticationProvider} to be used.
     * We use this method here to configure the @{@link org.springframework.security.authentication.AuthenticationProvider}
     * to use a @{@link PasswordEncoder} and @{@link UserDetailsService} from the DI container and not the Spring defaults.
     * @param service The user details service for retrieving users.
     * @param passwordEncoder The password encoder to use.
     * @return The authentication provider to use
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService service, PasswordEncoder passwordEncoder) {
        var provider = new DaoAuthenticationProvider(passwordEncoder);
        provider.setUserDetailsService(service);
        return provider;
    }


    /**
     * Returns the @{@link AuthenticationManager} that we want to use. We need this method because we want to use the
     * {@link AuthenticationManager} to use two @{@link org.springframework.security.authentication.AuthenticationProvider}'s.
     * @param daoAuthenticationProvider The @{@link DaoAuthenticationProvider} to authenticate a user using a user name and password.
     * @param jwtAuthenticationProvider The @{@link  JwtAuthenticationProvider} to authenticate a user based on a token.
     * @return The authentication manager for our application.
     */
    @Bean
    AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider,
                                                      JwtAuthenticationProvider jwtAuthenticationProvider) {
        return new ProviderManager(daoAuthenticationProvider, jwtAuthenticationProvider);
    }

    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider(JwtDecoder decoder) {
        return new JwtAuthenticationProvider(decoder);
    }




    /**
     * @return Our customized @{@link PasswordEncoder}. We do this because the spring security defaults are not secure
     * and we follow the recommendations on this <a href="https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html">site</a>.
     */
    @Bean
    public PasswordEncoder encoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 19456, 2);
    }


    /**
     * @return Our (global) cors configuration.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // the only other origins we want to allow are localhost for development purposes
        configuration.setAllowedOriginPatterns(List.of("http://localhost**", "http://127.0.0.1**"));
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        // our cors configuration is valid for all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtDecoder jwtDecoder(@Value("${tokens.secret}") String secret) {
        return NimbusJwtDecoder.withSecretKey(Keys.hmacShaKeyFor(secret.getBytes())).build();
    }
}
