package main.java.com.utn.rpg.service;

import static main.java.com.utn.rpg.model.Constants.FULL_HEALTH;
import static main.java.com.utn.rpg.model.Constants.MAX_ATTACKS_PER_ROUND;

import main.java.com.utn.rpg.exception.*;
import main.java.com.utn.rpg.model.Character;
import main.java.com.utn.rpg.model.Player;
import main.java.com.utn.rpg.model.RoundResult;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final Random RANDOM = new Random();
    private final Player player1;
    private final Player player2;
    private int currentRound;
    private int lastRoundLoser;
    private final GameUI ui;
    private final GameLogger logger;

    public Game(Player player1, Player player2, GameUI ui, GameLogger logger) {
        this.player1 = player1;
        this.player2 = player2;
        this.ui = ui;
        this.logger = logger;
    }

    public Game(List<Character> characters, GameUI ui, GameLogger logger) {
        this.ui = ui;
        this.logger = logger;
        try {
            validateCharacters(characters);

            this.player1 = new Player(1);
            this.player2 = new Player(2);
            this.currentRound = 1;
            this.lastRoundLoser = RANDOM.nextInt(2) + 1;

            distributeCharacters(characters);
            this.logger.logGameStart();
            this.logger.logCharacterGeneration(characters);

        } catch (GameException e) {
            this.logger.logError("Inicio de juego fallido: " + e.getMessage());
            throw e;
        }
    }

    private void validateCharacters(List<Character> characters) {
        if (characters == null) {
            throw new GameException("Personajes no pueden ser nulos");
        }
        if (characters.size() != 6) {
            throw new GameException("Exactamente 6 personajes son necesarios se encontraron: " + characters.size());
        }
        if (characters.stream().anyMatch(c -> c == null)) {
            throw new GameException("Todos los personajes deben ser no nulos");
        }
    }

    private void distributeCharacters(List<Character> characters) {
        try {
            for (int i = 0; i < 3; i++) {
                player1.addCharacter(characters.get(i));
                player2.addCharacter(characters.get(i + 3));
            }
        } catch (IndexOutOfBoundsException e) {
            throw new GameException("Error repartiendo personajes: " + e.getMessage(), e);
        }
    }

    public void startGame() {
        try {
            System.out.println("\n¡El juego está comenzando!");
            Thread.sleep(1500);

            Scanner scanner = new Scanner(System.in);
            while (player1.hasAliveCharacters() && player2.hasAliveCharacters()) {
                System.out.println("\n=== ROUND " + currentRound + " ===");
                System.out.println("Presiona ENTER para comenzar el round...");
                scanner.nextLine();

                showTeamStatus();
                Thread.sleep(1000);

                RoundResult result = playRound();
                logger.displayCurrentLog();

                if (result.isGameEnded()) {
                    break;
                }

                System.out.println("\nPresiona ENTER para continuar...");
                scanner.nextLine();
                currentRound++;
            }

            System.out.println("\n=== FIN DEL JUEGO ===");
            Player winner = player1.hasAliveCharacters() ? player1 : player2;
            logger.logGameResult(winner);
            logger.displayCurrentLog();
            logger.saveToFile();

        } catch (InterruptedException e) {
            String error = "Juego interrumpido: " + e.getMessage();
            logger.logError(error);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            String error = "Error inesperado durante el juego: " + e.getMessage();
            logger.logError(error);
            throw new GameException(error, e);
        }
    }

    private void showTeamStatus() {
        System.out.println("\nEstado de los equipos:");
        System.out.println("\nEquipo 1:");
        player1.getAllCharacters().forEach(this::showCharacterStatus);

        System.out.println("\nEquipo 2:");
        player2.getAllCharacters().forEach(this::showCharacterStatus);
        System.out.println();
    }

    private void showCharacterStatus(Character character) {
        System.out.printf("- %s (%s): %s - Salud: %d/%d\n",
                character.getName(),
                character.getRace(),
                character.isAlive() ? "Vivo" : "Muerto",
                character.getHealth(),
                FULL_HEALTH
        );
    }

    public RoundResult playRound() {
        try {
            List<Character> aliveChars1 = player1.getAliveCharacters();
            List<Character> aliveChars2 = player2.getAliveCharacters();

            if (aliveChars1.isEmpty() || aliveChars2.isEmpty()) {
                return new RoundResult(null, null, currentRound, true);
            }

            Character char1 = selectRandomCharacter(aliveChars1);
            Character char2 = selectRandomCharacter(aliveChars2);

            logger.logRoundStart(currentRound, char1, char2, lastRoundLoser);

            return executeCombat(char1, char2);

        } catch (Exception e) {
            String error = "Error durante la ejecucion del round: " + e.getMessage();
            logger.logError(error);
            throw new CombatException(error);
        }
    }

    private Character selectRandomCharacter(List<Character> characters) {
        if (characters == null || characters.isEmpty()) {
            throw new CombatException("Personaje no disponibles para la batalla");
        }
        return characters.get(RANDOM.nextInt(characters.size()));
    }

    private RoundResult executeCombat(Character char1, Character char2) {
        Character firstAttacker = (lastRoundLoser == 1) ? char1 : char2;
        Character secondAttacker = (firstAttacker == char1) ? char2 : char1;
        Scanner scanner = new Scanner(System.in);

        for (int attack = 1; attack <= MAX_ATTACKS_PER_ROUND &&
                char1.isAlive() && char2.isAlive(); attack++) {

            System.out.printf("\n=== Ataque %d/%d ===\n", attack, MAX_ATTACKS_PER_ROUND);

            // First attack
            System.out.printf("\n%s se prepara para atacar a %s...\n",
                    firstAttacker.getName(), secondAttacker.getName());
            System.out.println("Presiona ENTER para continuar...");
            scanner.nextLine();

            executeSingleAttack(firstAttacker, secondAttacker);
            ui.showBattleStatus(char1, char2);

            if (!secondAttacker.isAlive()) break;

            // Second attack
            System.out.printf("\n%s contraataca a %s...\n",
                    secondAttacker.getName(), firstAttacker.getName());
            System.out.println("Presiona ENTER para continuar...");
            scanner.nextLine();

            executeSingleAttack(secondAttacker, firstAttacker);
            ui.showBattleStatus(char1, char2);
        }

        return determineRoundResult(char1, char2);
    }

    private void executeSingleAttack(Character attacker, Character defender) {
        try {
            int damage = attacker.attack(defender);
            logger.logAttack(attacker, defender, damage);
        } catch (Exception e) {
            throw new CombatException("Ataque fallido: " + e.getMessage());
        }
    }

    private RoundResult determineRoundResult(Character char1, Character char2) {
        Character winner = null;
        Character loser = null;

        if (!char1.isAlive() && char2.isAlive()) {
            winner = char2;
            loser = char1;
            lastRoundLoser = 1;
            char2.improveStat();
            logger.logCharacterDeath(char1, char2);
        } else if (!char2.isAlive() && char1.isAlive()) {
            winner = char1;
            loser = char2;
            lastRoundLoser = 2;
            char1.improveStat();
            logger.logCharacterDeath(char2, char1);
        } else {
            logger.logRoundDraw();
        }

        boolean gameEnded = !player1.hasAliveCharacters() || !player2.hasAliveCharacters();
        return new RoundResult(winner, loser, currentRound, gameEnded);
    }
}