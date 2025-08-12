package main.java.com.utn.rpg.model;

import java.time.LocalDate;

public class Human extends Character {

    public Human(String name, String nickname, LocalDate birthDate, int age,
            int speed, int dexterity, int strength, int level, int armor) {
        super(name, nickname, Race.HUMAN, birthDate, age, speed, dexterity, strength, level, armor);
    }

    @Override
    protected double getDamageMultiplier() {
        return 1.0;
    }

    @Override
    public void improveStat() {
        this.level = Math.min(MAX_LEVEL, this.level + 1);
    }

    @Override
    public String toString() {
        return "HUMANO - " + super.toString();
    }
}