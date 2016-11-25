package Pacman;
import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private int score;

    Player() {
        name = "";
        score = 0;
    }
    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
    String getName() { return name; }
    int getScore() { return score; }
    void setName(String name) { this.name = name; }
    void setScore(int score) { this.score = score; }

    public String toString() {
        return String.format("%s        %d meters", name, score);
    }
}
