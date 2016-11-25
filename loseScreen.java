package Pacman;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class loseScreen extends BasicGameState {

    boolean b1flag = false;
    boolean b2flag = false;
    boolean b3flag = false;
    String mouse = "";

    public loseScreen(int state) {

    }

    @Override
    public int getID() {
        return 5;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Image lose = new Image("res/looser.png");
        graphics.drawImage(lose, 0, 0);
        Image b1 = new Image("res/one.png");
        Image b2 = new Image("res/two.png");
        Image b3 = new Image("res/three.png");

        if (!b1flag && !b2flag && !b3flag) {
            graphics.drawImage(b1, 130, 390);
            graphics.drawImage(b2, 230, 390);
            graphics.drawImage(b3, 320, 390);
        } else {
            if (b1flag) {
                graphics.drawImage(b2, 230, 390);
                graphics.drawImage(b3, 320, 390);
                b1.setFilter(Image.FILTER_LINEAR);
                b1.draw(120, 385, 1.2f);
            } else if (b2flag) {
                graphics.drawImage(b1, 130, 390);
                graphics.drawImage(b3, 320, 390);
                b2.setFilter(Image.FILTER_LINEAR);
                b2.draw(220, 385, 1.2f);
            } else if (b3flag) {
                graphics.drawImage(b2, 230, 390);
                graphics.drawImage(b1, 130, 390);
                b3.setFilter(Image.FILTER_LINEAR);
                b3.draw(310, 385, 1.2f);
//            } else if (typeName) {
//                char in = Keyboard.getEventCharacter();
//                graphics.drawString(in+"", 200, 200);
            }
        }
            graphics.drawString(mouse, 0, 0);

    }
    public List<String> pollInput() {
        List<String> keys = new ArrayList<>();
        while (Keyboard.next()) {
            keys.add((char)Keyboard.getEventKey()+"");
        }
        return keys;
    }

        @Override
        public void update (GameContainer gameContainer, StateBasedGame stateBasedGame,int i) throws SlickException {
            Input input = gameContainer.getInput();
            int xPos = Mouse.getX();
            int yPos = Mouse.getY();
            mouse = "Position X: " + xPos + "| Position Y: " + yPos;
            if ((xPos >= 130 && xPos <= 170) && (yPos <= 205 && yPos >= 160)) {
                b1flag = true;
                if (input.isMousePressed(0)) {
                    stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
                }
            } else if ((xPos >= 235 && xPos <= 280) && (yPos <= 210 && yPos >= 160)) {
                b2flag = true;
                if (input.isMousePressed(0)) {
                    stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
                }
            } else if ((xPos >= 320 && xPos <= 370) && (yPos <= 205 && yPos >= 160)) {
                b3flag = true;
                if (input.isMousePressed(0)) {
                    System.exit(0);
                }
            } else {
                b1flag = b2flag = b3flag = false;
            }
        }
    }