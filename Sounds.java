package Pacman;

import org.newdawn.slick.*;

public class Sounds{
    public  Music backgroundMenu;
    public Sound gameOver;
    public Sound feed;
    public Sound invisibleMode;
    public Sound click;
    public Sound shot;

    public Sounds() throws  SlickException{
        backgroundMenu = new Music("res/Sounds/bg.wav");
        gameOver = new Sound("res/Sounds/gameOver.wav");
        feed = new Sound("res/Sounds/ball.wav");
        click = new Sound("res/Sounds/click effect.wav");
        shot = new Sound("res/Sounds/gun.wav");
    }
}
