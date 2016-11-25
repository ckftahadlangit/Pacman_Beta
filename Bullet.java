package Pacman;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.awt.Rectangle;

public class Bullet{
    public Image[] pellet;
    public int[] duration = {200,200};
    public Animation move;
    public float bulletX;
    public float bulletY;

    public Bullet(float bulletX) throws SlickException {
        pellet = new Image[] {new Image("res/Images/ghost/pellet.png"), new Image("res/Images/ghost/pellet.png")};
        move = new Animation(pellet, duration, true);
        this.bulletX = bulletX + 10;
        bulletY = 490;
    }

    public Bullet(float bulletX, float bulletY) throws SlickException {
        pellet = new Image[] {new Image("res/Images/ghost/pellet.png"), new Image("res/Images/ghost/pellet.png")};
        move = new Animation(pellet, duration, true);
        this.bulletX = bulletX + 10;
        this.bulletY = bulletY;
    }

    public Rectangle bounds(){
        return new Rectangle((int)bulletX, (int)bulletY, pellet[0].getWidth(), pellet[0].getHeight());
    }
}
