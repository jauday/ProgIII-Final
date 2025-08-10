package main.java.com.utn.rpg.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import main.java.com.utn.rpg.model.Character;
import main.java.com.utn.rpg.model.Player;

public class GameLogger {
    private static final String LOG_FILE_PATH = "logs/game_log.txt";
    private final List<String> logEntries;
    private final DateTimeFormatter formatter;

    public GameLogger() {
        this.logEntries = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        createLogDirectoryIfNotExists();
    }

    private void createLogDirectoryIfNotExists() {
        try {
            Files.createDirectories(Paths.get("logs"));
        } catch (IOException e) {
            System.err.println("Error creando el directorio de Logs: " + e.getMessage());
        }
    }

    public void logGameStart() {
        String timestamp = LocalDateTime.now().format(formatter);
        logEntries.add("=== COMIENZA EL JUEGO " + timestamp + " ===");
    }

    public void logCharacterGeneration(List<Character> characters) {
        logEntries.add("\n--- PERSONAJES GENERADOS ---");
        for (int i = 0; i < characters.size(); i++) {
            String playerLabel = i < 3 ? "Jugador 1" : "Jugador 2";
            logEntries.add(String.format("%s - Personaje %d: %s",
                    playerLabel, (i % 3) + 1, characters.get(i)));
        }
        logEntries.add("");
    }

    public void logRoundStart(int round, Character char1, Character char2, int startingPlayer) {
        logEntries.add(String.format("--- ROUND %d ---", round));
        logEntries.add(String.format("Jugador %d Comienza Atacando", startingPlayer));
        logEntries.add(String.format("%s (%s) VS %s (%s)",
                char1.getNickname(), char1.getName(),
                char2.getNickname(), char2.getName()));
    }

    public void logAttack(Character attacker, Character defender, int damage) {
        logEntries.add(String.format("%s Ataca a %s y genera %d daño. %s tiene %d salud restante.",
                attacker.getNickname(), defender.getNickname(), damage,
                defender.getNickname(), defender.getHealth()));
    }

    public void logCharacterDeath(Character deadCharacter, Character winner) {
        logEntries.add(String.format("%s murió! %s gana el round y obtiene +10 de salud (Salud actual: %d).",
                deadCharacter.getNickname(), winner.getNickname(), winner.getHealth()));
    }

    public void logRoundDraw() {
        logEntries.add("EMPATE! - Ambos personajes sobreviven!");
    }

    public void logGameResult(Player winner) {
        logEntries.add(String.format("\n=== Jugador %d GANO EL TRONO DE HIERRO! ===", winner.getPlayerId()));
        logEntries.add("Personajes sobrevivientes:");
        for (Character character : winner.getAliveCharacters()) {
            logEntries.add("- " + character.toString());
        }
        logEntries.add("=== JUEGO FINALIZADO ===\n");
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            for (String entry : logEntries) {
                writer.println(entry);
            }
            writer.println(); // Extra line between games
            System.out.println("Logs guardados exitosamente en " + LOG_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error guardando los logs: " + e.getMessage());
        }
    }

    public String readLogs() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            return "Archivo de Logs no encontrado. Asegúrate de que el juego se haya jugado al menos una vez.";
        } catch (IOException e) {
            return "Error leyendo el archivo de Logs: " + e.getMessage();
        }
        return content.toString();
    }

    public void clearLogs() {
        try {
            Files.deleteIfExists(Paths.get(LOG_FILE_PATH));
            System.out.println("Archivo de Logs limpiado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error limpiando los logs: " + e.getMessage());
        }
    }

    public void displayCurrentLog() {
        logEntries.forEach(System.out::println);
    }

    public void logError(String error) {
        logEntries.add("ERROR: " + error);
    }

}