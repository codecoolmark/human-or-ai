package at.codecool.humanoraiserver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;

@Component
public class CookieTokenResolver implements BearerTokenResolver {
    private final Cookies cookies;

    public CookieTokenResolver(Cookies cookies) {
        this.cookies = cookies;
    }

    @Override
    public String resolve(HttpServletRequest request) {
        return cookies.getToken(request).orElse(null);
    }
}
