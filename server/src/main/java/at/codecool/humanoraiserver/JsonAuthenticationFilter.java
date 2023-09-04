package at.codecool.humanoraiserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

/**
 * Request filter that reads user credentials (username and password) from a request.
 * The credentials have to be sent as the request body in the format defined by the {@link PostSessionRequest} class.
 * Upon receiving credentials the user is authenticated using the applications {@link AuthenticationManager}.
 */
public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    /**
     * Constructs a new {@link JsonAuthenticationFilter}.
     *
     * @param requestMatcher Determines to which request allow log in.
     * @param objectMapper The object mapper for mapping the request body to an instance of {@link PostSessionRequest}.
     * @param authenticationManager The authentication manager.
     * @param rememberMeServices The remember me services.
     */
    public JsonAuthenticationFilter(RequestMatcher requestMatcher,
                                    ObjectMapper objectMapper,
                                    AuthenticationManager authenticationManager,
                                    RememberMeServices rememberMeServices) {
        super(requestMatcher, authenticationManager);
        this.objectMapper = objectMapper;
        super.setAuthenticationSuccessHandler((request, response, authentication) -> {});
        super.setRememberMeServices(rememberMeServices);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
            var inputStream = request.getInputStream();
            var loginRequest = objectMapper.readValue(inputStream, PostSessionRequest.class);
            var authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                    loginRequest.getPassword());
            return super.getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                             HttpServletResponse response,
                                             FilterChain chain,
                                             Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
