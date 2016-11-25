package Pacman;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.Font;
import org.newdawn.slick.UnicodeFont;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScore extends BasicGameState {

    private static final String FILE_NAME = "FILE.ser";
    List<Player> players = new ArrayList<>();
    boolean serialize = false;
    Font font;
    boolean isFlagExit = false;
    TrueTypeFont TrueTypeFont;

    public HighScore(int state) {

    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        font = new Font("Verdana", Font.BOLD, 20);
        UnicodeFont uFont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Image ExitButtonForHelp = new Image("res/Images/ExitButtonForHelp.png");
        int X = 0;
        int Y = 0;
        if (serialize) {
            Image HighScore = new Image("res/Images/HighScore.png");
            graphics.drawImage(HighScore, 0, 0);
        }
        if (!isFlagExit) {
            graphics.drawImage(ExitButtonForHelp, 395, 55);
        } else {
            ExitButtonForHelp.setFilter(Image.FILTER_LINEAR);
            ExitButtonForHelp.draw(385, 50, 1.2f);
        }
        for (int i = 0; i < players.size(); i++) {
            graphics.drawString(players.get(i).toString(), X, Y);
            // increment
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        File fin = new File(FILE_NAME);
        FileInputStream fileIn;
        ObjectInputStream in_;
        FileOutputStream fileOut;
        ObjectOutputStream out;
        try {
            if (fin.length() != 0) {
                fileIn = new FileInputStream(FILE_NAME);
                in_ = new ObjectInputStream(fileIn);
                players = (List<Player>) in_.readObject();
                in_.close();
                fileIn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Student class not found");
            c.printStackTrace();
        }
        serialize = true;

        Image SoundOffButton = new Image("res/Images/SoundOffButton.png");
        Image SoundOnButton = new Image("res/Images/SoundOnButton.png");

        Input input = gameContainer.getInput();
        int xPos = Mouse.getX();
        int yPos = Mouse.getY();

        if ((xPos >= 392 && xPos <= 456) && (yPos <= 520 && yPos >= 458)) {
            isFlagExit = true;
            if (input.isMousePressed(0)) {
                stateBasedGame.enterState(0);
            }
        } else {
            isFlagExit = false;
        }
    }
}
