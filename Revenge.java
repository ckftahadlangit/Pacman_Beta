package Pacman;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.*;
import java.util.Random;
import static Pacman.Menu.*;

public class Revenge extends BasicGameState{

    public Ghosts ghost;
    public int countScore;
    public float ghostSpeed;
    public int life;
    public Random random;
    public Pacman pacman;
    public List<Bullet> bullets;
    public List<Ghosts> listOfGhosts;
    public Image background;
    public String pause;
    public int countDown;
    public Random rand;

    public Revenge(int state) {

    }

    @Override
    public int getID() {
        return 9;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        rand = new Random();
        life = 3;
        ghostSpeed = 5.0f;
        countDown = 0;
        pause = "Press Enter to play.";
        s.backgroundMenu.stop();
        background = new Image("res/Images/modeSpace.png");
        random = new Random();
        ghost = new Ghosts(random.nextInt(4) + 1);
        countScore = 0;
        pacman = new Pacman();
        pacman.pacmanX = 200;
        bullets = new ArrayList<Bullet>();
        listOfGhosts = new ArrayList<Ghosts>();
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
        for(Ghosts g: listOfGhosts){
            g.move.draw(g.ghostX, g.ghostY);
        }
        graphics.setColor(Color.white);
        graphics.drawString(pause,150,200);
        graphics.drawString("Lives: " + life + "     Kill Count: " + countScore, 20, 550);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();
        //checkStatus(gameContainer, delta, stateBasedGame);
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
        if (input.isKeyDown(Input.KEY_UP)) {
            if (pacman.pacmanY > 10) {
                pacman.pacmanY -= delta/3.f;
            }
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            if (pacman.pacmanY < 490){
                pacman.pacmanY += delta/3.f;
            }
        }
        if (input.isKeyPressed(Input.KEY_SPACE) && !gameContainer.isPaused()) {
            bullets.add(new Bullet(pacman.pacmanX, pacman.pacmanY));
            s.shot.play();
        }

        moveBullets(delta);
        moveGhost(delta);
    }
    public void checkStatus(GameContainer gameContainer, int delta, StateBasedGame SBG){
        countDown -= delta;
        if(life == 0){
            pause = "G A M E  O V E R : Out of bullets";
            s.gameOver.play();
            SBG.enterState(6, new FadeOutTransition(Color.black, 2000), new FadeInTransition());
        }
    }

    public void moveGhost(int delta) throws SlickException{
        countDown += delta;
        System.out.println(countDown);
        if(countDown > 400){
            listOfGhosts.add(new Ghosts(rand.nextInt(4) + 1));
            countDown = 0;
        }
        for(Iterator<Ghosts> iterator = listOfGhosts.iterator(); iterator.hasNext(); ) {
            Ghosts ghost = iterator.next();
            ghost.ghostY += delta /4.0f;
            if(bullets.size() != 0) {
                for (Iterator<Bullet> iter = bullets.iterator(); iter.hasNext(); ) {
                    Bullet b = iter.next();
                    if (ghost.bounds().intersects(b.bounds())) {
                        countScore++;
                        iter.remove();
                        iterator.remove();
                    }
                }
            }
            if(ghost.ghostY > 490){
                iterator.remove();
            }
        }
    }

    public void moveBullets(int delta) throws SlickException {
        for(Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
            Bullet b = iterator.next();
            b.bulletY -= delta / 2.0f;
            if(b.bulletY <= 0){
                iterator.remove();
            }
        }
    }
}