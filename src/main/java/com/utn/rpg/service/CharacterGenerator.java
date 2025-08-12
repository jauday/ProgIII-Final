package main.java.com.utn.rpg.service;

import main.java.com.utn.rpg.exception.GameException;
import main.java.com.utn.rpg.model.Character;
import main.java.com.utn.rpg.model.Elf;
import main.java.com.utn.rpg.model.Human;
import main.java.com.utn.rpg.model.Orc;
import main.java.com.utn.rpg.model.Race;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CharacterGenerator {

    private static final Random RANDOM = new Random();

    private static final String[] NAMES = {
            "Aragorn", "Legolas", "Gimli", "Gandalf", "Boromir", "Frodo",
            "Thorin", "Thranduil", "Elrond", "Galadriel", "Saruman", "Sauron",
            "Uhtred", "Ragnar", "Bjorn", "Lagertha", "Rollo", "Floki"
    };

    private static final String[] NICKNAMES = {
            "The Brave", "Shadowbane", "Ironforge", "Stormcaller", "Nightwhisper",
            "Flameheart", "Frostborn", "Earthshaker", "Windwalker", "Stargazer",
            "Bloodfang", "Goldbeard", "Swiftstrike", "Doomhammer", "Lightbringer"
    };
    private final Scanner scanner;
    private final GameUI ui;

    public CharacterGenerator() {
        this.scanner = new Scanner(System.in);
        this.ui = new GameUI();
    }

    public Character generateRandomCharacter() {
        String name = NAMES[RANDOM.nextInt(NAMES.length)];
        String nickname = NICKNAMES[RANDOM.nextInt(NICKNAMES.length)];
        Race race = Race.values()[RANDOM.nextInt(Race.values().length)];

        int age = RANDOM.nextInt(300) + 1;
        LocalDate birthDate = LocalDate.now().minusYears(age);

        int speed = RANDOM.nextInt(10) + 1;
        int dexterity = RANDOM.nextInt(5) + 1;
        int strength = RANDOM.nextInt(10) + 1;
        int level = RANDOM.nextInt(10) + 1;
        int armor = RANDOM.nextInt(10) + 1;

        // Use factory pattern with polymorphism
        return createCharacter(race, name, nickname, birthDate, age, speed, dexterity, strength, level, armor);
    }

    private Character createCharacter(Race race, String name, String nickname, LocalDate birthDate,
            int age, int speed, int dexterity, int strength, int level, int armor) {
        return switch (race) {
            case HUMAN -> new Human(name, nickname, birthDate, age, speed, dexterity, strength, level, armor);
            case ELF -> new Elf(name, nickname, birthDate, age, speed, dexterity, strength, level, armor);
            case ORC -> new Orc(name, nickname, birthDate, age, speed, dexterity, strength, level, armor);
        };
    }

    public List<Character> generateRandomCharacters(int count) {
        List<Character> characters = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            characters.add(generateRandomCharacter());
        }
        return characters;
    }

    public Character createManualCharacter() {
        System.out.print("Ingrese el nombre del personaje: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("El nombre no puede estar vacio. Ingrese el nombre del personaje: ");
            name = scanner.nextLine().trim();
        }

        System.out.print("Ingrese el apodo del personaje: ");
        String nickname = scanner.nextLine().trim();
        while (nickname.isEmpty()) {
            System.out.print("El apodo no puede estar vacio. Ingrese el apodo del personaje: ");
            nickname = scanner.nextLine().trim();
        }

        Race race = chooseRace();

        int age = ui.getValidIntInput("Ingrese la Edad (1-300): ", 1, 300);
        LocalDate birthDate = LocalDate.now().minusYears(age);

        System.out.println("\nIngrese las siguientes estadisticas del personaje:");
        int speed = ui.getValidIntInput("Velocidad (1-10): ", 1, 10);
        int dexterity = ui.getValidIntInput("Destreza (1-5): ", 1, 5);
        int strength = ui.getValidIntInput("Fuerza (1-10): ", 1, 10);
        int level = ui.getValidIntInput("Nivel (1-10): ", 1, 10);
        int armor = ui.getValidIntInput("Armadura (1-10): ", 1, 10);

        return createCharacter(race, name, nickname, birthDate, age, speed, dexterity, strength, level, armor);
    }

    private Race chooseRace() {
        System.out.println("\nElija la raza del personaje:");
        System.out.println("1. Humano (Daño x1.0, +1 nivel cuando gana)");
        System.out.println("2. Elfo (Daño x1.05, +12 salud cuando gana)");
        System.out.println("3. Orco (Daño x1.1, +8 salud cuando gana)");

        int choice = ui.getValidIntInput("Seleccionar Raza (1-3): ", 1, 3);

        return switch (choice) {
            case 1 -> Race.HUMAN;
            case 2 -> Race.ELF;
            case 3 -> Race.ORC;
            default -> throw new GameException("Raza no válida seleccionada. Por favor, elija una raza válida.");
        };
    }

}
