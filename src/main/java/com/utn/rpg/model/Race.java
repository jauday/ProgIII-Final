package main.java.com.utn.rpg.model;

public enum Race {
    HUMAN("Humano"),
    ELF("Elfo"),
    ORC("Orco");

    private final String description;

    Race(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}