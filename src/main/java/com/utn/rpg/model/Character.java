package main.java.com.utn.rpg.model;

import java.time.LocalDate;
import java.util.Random;

public abstract class Character {
    protected static final Random RANDOM = new Random();
    protected static final int MAX_HEALTH = 100;

    protected final String name;
    protected final String nickname;
    protected final Race race;
    protected final LocalDate birthDate;
    protected final int age;

    protected int health;
    protected final int speed;
    protected final int skill;
    protected final int strength;
    protected final int level;
    protected final int armor;

    public Character(String name, String nickname, Race race, LocalDate birthDate,
            int age, int speed, int skill, int strength, int level, int armor) {
        this.name = name;
        this.nickname = nickname;
        this.race = race;
        this.birthDate = birthDate;
        this.age = age;
        this.health = MAX_HEALTH;
        this.speed = speed;
        this.skill = skill;
        this.strength = strength;
        this.level = level;
        this.armor = armor;
    }


    public int attack(Character defender) {
        int powerShot = this.skill * this.strength * this.level;
        int effectiveness = RANDOM.nextInt(100) + 1;
        int attackValue = (powerShot * effectiveness) / 100;
        int defenseValue = defender.armor * defender.speed;

        double baseDamage = ((double)(attackValue - defenseValue) / 500) * 100;
        double finalDamage = baseDamage * getDamageMultiplier();

        int damage = Math.max(0, (int)Math.round(finalDamage));
        defender.takeDamage(damage);

        return damage;
    }

    // Metodo abstracto para que cada raza implemente su propio multiplicador de daño
    protected abstract double getDamageMultiplier();

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public abstract void improveStat(); // Cada Raza mejora de manera distinta
    
    public String getName() { return name; }
    public String getNickname() { return nickname; }
    public int getHealth() { return health; }
    public String getRace(){return race.toString();}

    @Override
    public String toString() {
        return String.format(
                "Nombre: %s, Apodo: %s, Raza: %s, Salud: %d, Velocidad: %d, Destreza: %d, Fuerza: %d, Nivel: %d, Armadura: %d, Edad: %d",
                name, nickname, race, health, speed, skill, strength, level, armor, age);
    }
}