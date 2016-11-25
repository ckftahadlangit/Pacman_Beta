package Pacman;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.*;
import java.util.Random;
import static Pacman.Menu.*;

public class Timed extends BasicGameState{

    public Ghosts ghost;
    public int countScore;
    public int ghostSpeed;
    public int ammo;
    public Random random;
    public Pacman pacman;
    public List<Bullet> bullets;
    public Image background;
    public String pause;
    public int countDown;

    public Timed(int state) {

    }

    @Override
    public int getID() {
        return 8;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        ammo = 5;
        ghostSpeed = 0;
        countDown = 15000;
        pause = "Press Enter to play.";
        s.backgroundMenu.stop();
        background = new Image("res/Images/modeSpace.png");
        random = new Random();
        ghost = new Ghosts(random.nextInt(4) + 1);
        countScore = 0;
        pacman = new Pacman();
        pacman.pacmanX = 200;
        bullets = new ArrayList<Bullet>();
                                                                                                  gameContainer.pause();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0,0);
        graphics.setColor(Color.yellow);
        graphics.fillOval(pacman.pacmanX, pacman.pacmanY, pacman.radius,pacman.radius,200);
        for(Bullet b: bullets){
            b.move.draw(b.bulletX, b.bulletY);
        }
        graphics.setColor(Color.white);
        graphics.drawString(pause,150,200);
        graphics.drawString("Bullets left: " + ammo + "     Kill Count: " + countScore, 20, 550);
        graphics.drawString("Countdown: " + countDown/1000 , 20, 50);
        ghost.move.draw(ghost.ghostX, ghost.ghostY);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();
        checkStatus(gameContainer, delta, stateBasedGame);
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            if (gameContainer.isPaused()){
                pause = " ";
                gameContainer.resume();
            } else {
                pause = "Press Enter to play.";
                gameContainer.pause();
            }
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            if (pacman.pacmanX >= 15.5) {
                pacman.pacmanX -= delta/3.f;
            }
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            if (pacman.pacmanX <= 458){
                pacman.pacmanX += delta/3.f;
            }
        }
        if (input.isKeyPressed(Input.KEY_SPACE) && !gameContainer.isPaused() && ammo != 0) {
            bullets.add(new Bullet(pacman.pacmanX));
            s.shot.play();
            ammo--;
        }

        moveBullets(delta);
        moveGhost(delta);
    }
    public void checkStatus(GameContainer gameContainer, int delta, StateBasedGame SBG){
        countDown -= delta;
        if((bullets.size() == 0 && ammo == 0) || countDown/1000 <= 0){
            if(countDown / 1000 <= 0){
                pause = "G A M E  O V E R : Time's up!";
            }
            else{
                pause = "G A M E  O V E R : Out of bullets";
            }
            s.gameOver.play();
            SBG.enterState(6, new FadeOutTransition(Color.black, 2000), new FadeInTransition());
        }
    }

    public void moveGhost(int delta){
        ghostSpeed += delta;
        if(ghostSpeed >= 900){
            ghost.ghostX = random.nextInt(410) + 17;
            ghostSpeed = 0;
        }
        ghost.ghostY += delta / 5.0f;
        if(ghost.ghostY > 430){
            ghost.ghostY = 10;
        }
    }

    public void moveBullets(int delta) throws SlickException {
        for(Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
                Bullet b = iterator.next();
                b.bulletY -= delta / 2.0f;
            if(b.bounds().intersects(ghost.bounds())){
                ghost = new Ghosts(random.nextInt(4) + 1);
                countScore++;
                ammo += 3;
            }
            if(b.bulletY <= 0){
                iterator.remove();
            }
        }
    }
}