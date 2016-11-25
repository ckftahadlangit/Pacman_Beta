package Pacman;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import static Pacman.Menu.*;

public class Help extends BasicGameState {

    public boolean isFlagExit = false;
    public static boolean soundState = true;
    public boolean hover  = false;
    public String mouse = "";

    public Help(int state) {

    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Image Help = new Image("res/Images/Help2.png");
        Image SoundOff = new Image("res/Images/SoundOffButton.png");
        Image SoundOn = new Image("res/Images/SoundOnButton.png");
        Image ExitButtonForHelp = new Image("res/Images/ExitButtonForHelp.png");
        graphics.drawImage(Help, 0, 0);

        if (soundState) {
            if (hover) {
                SoundOn.setFilter(Image.FILTER_LINEAR);
                SoundOn.draw(90, 175, 1.2f);
            } else {
                graphics.drawImage(SoundOn, 95, 180);
            }
        } else {
            if (hover) {
                SoundOff.setFilter(Image.FILTER_LINEAR);
                SoundOff.draw(90, 175, 1.2f);
            } else {
                graphics.drawImage(SoundOff, 95, 180);
            }
        }

        if (!isFlagExit) {
            graphics.drawImage(ExitButtonForHelp, 395, 55);
        } else {
            ExitButtonForHelp.setFilter(Image.FILTER_LINEAR);
            ExitButtonForHelp.draw(385, 50, 1.2f);
        }
        graphics.drawString(mouse, 0, 0);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();
        int xPos = Mouse.getX();
        int yPos = Mouse.getY();
        mouse = xPos + ":::::" + yPos;
        if ((xPos >= 392 && xPos <= 456) && (yPos <= 520 && yPos >= 458)) {
            isFlagExit = true;
            if (input.isMousePressed(0)) {
                stateBasedGame.enterState(0);
            }
        } else if ((xPos >= 100 && xPos <= 150) && (yPos <= 410 && yPos >= 365)) {
            hover = true;
            if (input.isMousePressed(0)) {
                if(soundState == true){
                    s.backgroundMenu.pause();
                }
                else{
                    s.backgroundMenu.play();
                }
                soundState = !soundState;

            }
        } else {
            isFlagExit = false;
            hover = false;
        }
    }
}
