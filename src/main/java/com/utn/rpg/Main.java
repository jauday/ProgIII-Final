package main.java.com.utn.rpg;

import main.java.com.utn.rpg.controller.GameController;

public class Main {
    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.run();
    }
}
