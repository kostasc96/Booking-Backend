package gr.dit.tenants.entities;

import lombok.Getter;

@Getter
public enum Role {

    USER("USER"),
    HOST("HOST");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
