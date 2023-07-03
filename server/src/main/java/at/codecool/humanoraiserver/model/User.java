package at.codecool.humanoraiserver.model;

public record User(String email, String nickname, String passwordHash) {
}
