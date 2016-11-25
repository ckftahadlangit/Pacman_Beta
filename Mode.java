package Pacman;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import static Pacman.Main.*;
import static Pacman.Menu.s;

public class Mode extends BasicGameState {
    String mouse = "";
    boolean isSnow = false;
    boolean isNight = false;
    boolean isCity = false;
    boolean isHalloween = false;
    boolean isSummer = false;
    boolean isNature = false;
    boolean isFlagExit = false;
    boolean isDefault = false;

    public Mode(int state) {

    }

    @Override
    public int getID() {
        return 7;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Image Mode = new Image("res/Images/Mode.png");
        Image modeNightButton = new Image("res/Images/modeNightButton.png");
        Image modeCityButton = new Image("res/Images/modeCityButton.png");
        Image modeSummerButton = new Image("res/Images/modeSummerButton.png");
        Image modeHalloweenButton = new Image("res/Images/modeHalloweenButton.png");
        Image modeNatureButton = new Image("res/Images/modeNatureButton.png");
        Image modeSnowButton = new Image("res/Images/modeSnowButton.png");
        Image ExitButtonForHelp = new Image("res/Images/ExitButtonForHelp.png");
        Image modeDefaultButton = new Image("res/Images/defaultMode.png");
        graphics.drawImage(Mode, 0, 0);

        //if (!isDefault) {
         //   graphics.drawImage(modeDefaultButton, 190, 485);
       // }

        if (!isFlagExit) {
            graphics.drawImage(ExitButtonForHelp, 395, 55);
        } else {
            ExitButtonForHelp.setFilter(Image.FILTER_LINEAR);
            ExitButtonForHelp.draw(385, 50, 1.2f);
        }
        graphics.drawString(mouse, 0, 0);

        if (!isSummer) {
            graphics.drawImage(modeSummerButton, 100, 150);
        } else {
            modeSummerButton.setFilter(Image.FILTER_LINEAR);
            modeSummerButton.draw(90, 145, 1.2f);
        }
        if (!isCity) {
            graphics.drawImage(modeCityButton, 100, 270);
        } else {
            modeCityButton.setFilter(Image.FILTER_LINEAR);
            modeCityButton.draw(90, 265, 1.2f);
        }
        if (!isHalloween) {
            graphics.drawImage(modeHalloweenButton, 100, 390);
        } else {
            modeHalloweenButton.setFilter(Image.FILTER_LINEAR);
            modeHalloweenButton.draw(90, 385, 1.2f);
        }
        if (!isNature) {
            graphics.drawImage(modeNatureButton, 270, 145);
        } else {
            modeNatureButton.setFilter(Image.FILTER_LINEAR);
            modeNatureButton.draw(260, 140, 1.2f);
        }
        if (!isSnow) {
            graphics.drawImage(modeSnowButton, 270, 267);
        } else {
            modeSnowButton.setFilter(Image.FILTER_LINEAR);
            modeSnowButton.draw(260, 260, 1.2f);
        }
        if (!isNight) {
            graphics.drawImage(modeNightButton, 270, 385);
        } else {
            modeNightButton.setFilter(Image.FILTER_LINEAR);
            modeNightButton.draw(260, 380, 1.2f);
        }
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
        } else if ((xPos >= 106 && xPos <= 231) && (yPos <= 440 && yPos >= 347)) {
            isSummer = true;
            if (input.isMousePressed(0)) {
                superisSummer = true;
            }
        } else if ((xPos >= 105 && xPos <= 230) && (yPos <= 318 && yPos >= 230)) {
            isCity = true;
            if (input.isMousePressed(0)) {
                superisCity = true;
            }
        } else if ((xPos >= 105 && xPos <= 230) && (yPos <= 205 && yPos >= 115)) {
            isHalloween = true;
            if (input.isMousePressed(0)) {
                superisHalloween = true;
            }
        } else if ((xPos >= 275 && xPos <= 402) && (yPos <= 441 && yPos >= 350)) {
            isNature = true;
            if (input.isMousePressed(0)) {
                superisNature = true;
            }
        } else if ((xPos >= 275 && xPos <= 402) && (yPos <= 320 && yPos >= 231)) {
            isSnow = true;
            if (input.isMousePressed(0)) {
                superisSnow = true;
            }
        } else if ((xPos >= 275 && xPos <= 405) && (yPos <= 205 && yPos >= 111)) {
            isNight = true;
            if (input.isMousePressed(0)) {
                superisNight = true;
            }
        } else {
            isFlagExit = isSummer = isCity = isSnow = isHalloween = isNature = isNight = false;
            superisCity = superisHalloween = superisNature = superisNight = superisSummer = superisSnow = false;
        }
        if(superisSnow || superisCity || superisHalloween || superisNature || superisNight || superisSummer){
            s.backgroundMenu.stop();
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
    }
}
