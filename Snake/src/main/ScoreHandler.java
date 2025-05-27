package main;

public class ScoreHandler {
    private int score;

    public ScoreHandler() {
        this.score = 0;
    };

    public int getScore() {
        return this.score;
    };

    public int incrementScore() {
        this.score += 1;
        return this.score;
    };
}