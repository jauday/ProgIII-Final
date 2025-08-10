package main.java.com.utn.rpg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private final int playerId;
    private final List<Character> characters;

    public Player(int playerId) {
        this.playerId = playerId;
        this.characters = new ArrayList<>();
    }

    public void addCharacter(Character character) {
        if (characters.size() < 3) {
            characters.add(character);
        } else {
            throw new IllegalStateException("El jugador ya tiene 3 personajes.");
        }
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
    }

    public boolean hasAliveCharacters() {
        return characters.stream().anyMatch(Character::isAlive);
    }

    public List<Character> getAliveCharacters() {
        return characters.stream()
                .filter(Character::isAlive)
                .collect(Collectors.toList());
    }

    public List<Character> getAllCharacters() {
        return new ArrayList<>(characters);
    }

    public int getPlayerId() {
        return playerId;
    }
}