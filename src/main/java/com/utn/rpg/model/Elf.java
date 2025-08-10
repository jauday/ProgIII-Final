package main.java.com.utn.rpg.model;

import java.time.LocalDate;

public class Elf extends Character {

    public Elf(String name, String nickname, LocalDate birthDate, int age,
            int speed, int dexterity, int strength, int level, int armor) {
        super(name, nickname, Race.ELF, birthDate, age, speed, dexterity, strength, level, armor);
    }

    @Override
    protected double getDamageMultiplier() {
        return 1.05;
    }

    @Override
    public void improveStat() {
        this.health = Math.min(MAX_HEALTH, this.health + 12);
    }

    @Override
    public String toString() {
        return "ELFO - " + super.toString();
    }
}