package main.java.com.utn.rpg.model;

import java.time.LocalDate;
import java.util.Random;

public abstract class Character {

    protected static final Random RANDOM = new Random();
    protected static final int MAX_HEALTH = 100;
    protected static final int MAX_LEVEL = 10;
    protected static final int MAX_SKILL = 5;

    protected final String name;
    protected final String nickname;
    protected final Race race;
    protected final LocalDate birthDate;
    protected final int age;

    protected int health;
    protected final int speed;
    protected int skill;
    protected final int strength;
    protected int level;
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
        double effectiveness = (double) (RANDOM.nextInt(100) + 1) / 100.0;
        double attackValue = powerShot * effectiveness;
        int defenseValue = defender.armor * defender.speed;

        double baseDamage = ((attackValue - defenseValue) / 500) * 100;
        double finalDamage = baseDamage * getDamageMultiplier();

        int damage = Math.max(0, (int) Math.round(finalDamage));
        defender.takeDamage(damage);

        return damage;
    }

    protected abstract double getDamageMultiplier();

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public abstract void improveStat();

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public int getHealth() {
        return health;
    }

    public String getRace() {
        return race.toString();
    }

    public int getLevel() {
        return level;
    }

    public int getSkill() {
        return skill;
    }

    @Override
    public String toString() {
        return String.format(
                """
                        ┌─ %s (%s) ─ %s ─ Nivel %d ─ Edad %d
                        ├─ Atributos:
                        │  ❤️  Salud    [%s] %d
                        │  ⚡ Velocidad  [%s] %d
                        │  🎯 Destreza  [%s] %d
                        │  💪 Fuerza    [%s] %d
                        │  🛡️  Armadura [%s] %d
                        └─────────────────────────────""",

                name, nickname, race, level, age,
                createProgressBar(health, 100), health,
                createProgressBar(speed, 10), speed,
                createProgressBar(skill, 5), skill,
                createProgressBar(strength, 10), strength,
                createProgressBar(armor, 10), armor
        );
    }

    private String createProgressBar(int value, int max) {
        int barLength = 10;
        int filledLength = (int) ((double) value / max * barLength);
        StringBuilder bar = new StringBuilder();

        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        return bar.toString();
    }
}