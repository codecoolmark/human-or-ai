package at.codecool.humanoraiserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public final class Quote {
    private String text;

    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String newText) {
        text = newText;
    }
}
