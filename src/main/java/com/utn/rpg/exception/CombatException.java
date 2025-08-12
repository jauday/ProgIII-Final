package main.java.com.utn.rpg.exception;

public class CombatException extends GameException {

    public CombatException(String message) {
        super("Error de Combate: " + message);
    }
}