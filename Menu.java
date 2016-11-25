package Pacman;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import static Pacman.Main.*;

public class Menu extends BasicGameState {

    public String mouse = "";
    boolean isFlagPlay = false;
    boolean isFlagHelp = false;
    boolean openHelp = false;
    boolean isFlagHighScore = false;
    boolean isFlagExit = false;

    public Image Menu;
    public Image PlayButton;
    public Image HelpButton;
    public Image HighScoreButton;
    public Image QuitButton;
    public static Sounds s;

    public Menu(int state) {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        s = new Sounds();
        s.backgroundMenu.stop();
        s.backgroundMenu.play();
        Menu = new Image("res/Images/Menu.png");
        PlayButton = new Image("res/Images/PlayButton.png");
        HelpButton = new Image("res/Images/HelpButton.png");
        HighScoreButton = new Image("res/Images/HighScoreButton.png");
        QuitButton = new Image("res/Images/QuitButton.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Image Menu = new Image("res/Images/Menu.png");
        Image PlayButton = new Image("res/Images/PlayButton.png");
        Image HelpButton = new Image("res/Images/HelpButton.png");
        Image HighScoreButton = new Image("res/Images/HighScoreButton.png");
        Image QuitButton = new Image("res/Images/QuitButton.png");
        graphics.drawImage(Menu, 0, 0);

        if (!isFlagPlay) {
            graphics.drawImage(PlayButton, 200, 260);
        } else {
            PlayButton.setFilter(Image.FILTER_NEAREST);
            PlayButton.draw(190, 255, 1.3f);
        }

        if (!isFlagHelp) {
            graphics.drawImage(HelpButton, 195, 310);
        } else {
            HelpButton.setFilter(Image.FILTER_NEAREST);
            HelpButton.draw(180, 305, 1.3f);
        }

        if (!isFlagHighScore) {
            graphics.drawImage(HighScoreButton, 120, 360);
        } else {
            HighScoreButton.setFilter(Image.FILTER_NEAREST);
            HighScoreButton.draw(80, 355, 1.3f);
        }

        if (!isFlagExit) {
            graphics.drawImage(QuitButton, 200, 410);
        } else {
            QuitButton.setFilter(Image.FILTER_NEAREST);
            QuitButton.draw(185, 405, 1.3f);
        }
    }


    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        int xPos = Mouse.getX();
        int yPos = Mouse.getY();

        mouse = "Position X: " + xPos + "| Position Y: " + yPos;


        if ((xPos >= 200 && xPos <= 300) && (yPos <= 337 && yPos >= 320)) {
            isFlagPlay = true;
            if (input.isMousePressed(0)) {
                stateBasedGame.enterState(7);
            }
        }
        else if ((xPos >= 200 && xPos <= 300) && (yPos <= 286 && yPos >= 263)) {
            isFlagHelp = true;
            if (input.isMousePressed(0)) {
                stateBasedGame.enterState(6, new FadeOutTransition(Color.black, 2000), new FadeInTransition());
                stateBasedGame.enterState(2);
            }
        } else if ((xPos >= 123 && xPos <= 381) && (yPos <= 237 && yPos >= 214)) {
            isFlagHighScore = true;
            if (input.isMousePressed(0)) {
                stateBasedGame.enterState(3);
            }
        } else if ((xPos >= 200 && xPos <= 300) && (yPos <= 188 && yPos >= 168)) {
            isFlagExit = true;
            if (input.isMousePressed(0)) {
                stateBasedGame.enterState(4);
            }
        } else {
            isFlagPlay = isFlagHelp = isFlagHighScore = isFlagExit = false;
        }
    }
}