package main.java.com.utn.rpg.model;

public class RoundResult {
    private final Character winner;
    private final Character loser;
    private final int roundNumber;
    private final boolean gameEnded;

    public RoundResult(Character winner, Character loser, int roundNumber, boolean gameEnded) {
        this.winner = winner;
        this.loser = loser;
        this.roundNumber = roundNumber;
        this.gameEnded = gameEnded;
    }

    public Character getWinner() { return winner; }
    public Character getLoser() { return loser; }
    public int getRoundNumber() { return roundNumber; }
    public boolean isGameEnded() { return gameEnded; }
}
