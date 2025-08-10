package main.java.com.utn.rpg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.java.com.utn.rpg.model.Character;
import main.java.com.utn.rpg.service.CharacterGenerator;
import main.java.com.utn.rpg.service.Game;
import main.java.com.utn.rpg.service.GameLogger;
import main.java.com.utn.rpg.service.GameUI;

public class GameController {
    private final Scanner scanner;
    private final CharacterGenerator characterGenerator;
    private final GameLogger logger;
    private final GameUI ui;

    public GameController() {
        this.ui = new GameUI();
        this.scanner = new Scanner(System.in);
        this.characterGenerator = new CharacterGenerator();
        this.logger = new GameLogger();
    }

    public void run() {
        System.out.println("=== BIENVENIDO AL JUEGO DEL TRONO DE HIERRO ===");

        boolean running = true;
        while (running) {
            ui.displayMenu();
            int choice = ui.getValidMenuChoice();

            switch (choice) {
                case 1:
                    startGameWithRandomCharacters();
                    break;
                case 2:
                    startGameWithManualCharacters();
                    break;
                case 3:
                    readGameLogs();
                    break;
                case 4:
                    clearGameLogs();
                    break;
                case 5:
                    running = false;
                    System.out.println("Gracias por jugar! El Trono de Hierrron te espera.");
                    break;
            }
        }
        scanner.close();
    }

    private void startGameWithRandomCharacters() {
        System.out.println("\n=== GENERANDO PERSONAJES ALEATORIOS ===");
        List<Character> characters = characterGenerator.generateRandomCharacters(6);

        ui.displayCharacters(characters);
        confirmAndStartGame(characters);
    }

    private void startGameWithManualCharacters() {
        System.out.println("\n=== CREACION MANUAL DE PERSONAJES ===");
        System.out.println("Debes crear 6 personajes, 3 para cada jugador.");

        List<Character> characters = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            int playerId = (i <= 3) ? 1 : 2;
            int charNumber = (i <= 3) ? i : i - 3;

            System.out.println(String.format("\n--- Creando Personaje %d Para el jugador %d ---", charNumber, playerId));
            Character character = characterGenerator.createManualCharacter();
            characters.add(character);

            System.out.println("Personaje creado: " + character);
        }

        ui.displayCharacters(characters);
        confirmAndStartGame(characters);
    }

    private void confirmAndStartGame(List<Character> characters) {
        System.out.print("\nComenzar el juego con estos personajes? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y") || response.equals("yes")) {
            Game game = new Game(characters, ui, logger);
            game.startGame();
        } else {
            System.out.println("Juego cancelado. Regresando al menu principal.");
        }
    }

    private void readGameLogs() {
        System.out.println("\n=== Logs de partidas ===");
        System.out.println(logger.readLogs());
    }

    private void clearGameLogs() {
        System.out.print("Estas seguro que quieres limpiar los logs de partidas? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y") || response.equals("yes") || response.equals("si")) {
            logger.clearLogs();
        } else {
            System.out.println("Operacion cancelada. Los logs no fueron borrados.");
        }
    }
}