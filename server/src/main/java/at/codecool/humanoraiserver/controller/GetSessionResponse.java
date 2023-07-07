package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.model.User;

public class GetSessionResponse {
    private final String email;

    private final String nickname;

    private final boolean isAdmin;

    public GetSessionResponse(User user) {
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

    public boolean isAdmin() {
        return isAdmin;
    }
}
