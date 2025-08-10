package main.java.com.utn.rpg.model;

import java.time.LocalDate;

public class Orc extends Character {

    public Orc(String name, String nickname, LocalDate birthDate, int age,
            int speed, int dexterity, int strength, int level, int armor) {
        super(name, nickname, Race.ORC, birthDate, age, speed, dexterity, strength, level, armor);
    }

    @Override
    protected double getDamageMultiplier() {
        return 1.1;
    }

    @Override
    public void improveStat() {

        this.health = Math.min(MAX_HEALTH, this.health + 8);
    }

    @Override
    public String toString() {
        return "ORCO - " + super.toString();
    }
}