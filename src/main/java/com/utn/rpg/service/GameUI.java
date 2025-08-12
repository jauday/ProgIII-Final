package main.java.com.utn.rpg.service;

import static main.java.com.utn.rpg.model.Constants.FULL_HEALTH;

import java.util.List;
import java.util.Scanner;

import main.java.com.utn.rpg.exception.GameException;
import main.java.com.utn.rpg.model.Character;

public class GameUI {

    private final Scanner scanner;
    private final GameLogger logger;

    public GameUI() {

        this.scanner = new Scanner(System.in);
        this.logger = new GameLogger();
    }

    public void displayMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Nuevo juego con personajes aleatorios");
        System.out.println("2. Nuevo juego creando personajes");
        System.out.println("3. Leer logs de partidas");
        System.out.println("4. Limpiar logs de partidas");
        System.out.println("5. Salir");
        System.out.print("Elegir una opcion (1-5): ");
    }

    public int getValidMenuChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= 5) {
                    return choice;
                }
                System.out.print("Opcion invalida. Por favor, ingrese un numero entre 1 y 5: ");
            } catch (GameException e) {
                System.out.print("Entrada invalida. Por favor, ingrese un numero entre 1 y 5: ");
                logger.logError(e.getMessage());
            }
        }
    }

    public void displayCharacters(List<Character> characters) {
        System.out.println("\n--- JUGADOR 1 PERSONAJES ---");
        for (int i = 0; i < 3; i++) {
            System.out.println("Personaje " + (i + 1) + ": " + characters.get(i));
        }

        System.out.println("\n--- JUGADOR 2 PERSONAJES ---");
        for (int i = 3; i < 6; i++) {
            System.out.println("Personaje " + (i - 2) + ": " + characters.get(i));
        }
    }

    public int getValidIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Por favor, ingrese un numero entre %d y %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    public void showBattleStatus(Character char1, Character char2) {
        System.out.println("\nEstado de la batalla:");
        System.out.printf("%s: %d/%d Salud\n", char1.getName(), char1.getHealth(), FULL_HEALTH);
        System.out.printf("%s: %d/%d Salud\n", char2.getName(), char2.getHealth(), FULL_HEALTH);
    }
}