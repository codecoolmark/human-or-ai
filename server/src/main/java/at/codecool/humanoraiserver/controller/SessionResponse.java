package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionResponse {
    private final String email;

    private final String nickname;

    private final boolean isAdmin;

    public SessionResponse(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.isAdmin = user.isAdmin();
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    @JsonProperty("isAdmin")
    public boolean isAdmin() {
        return isAdmin;
    }
}
