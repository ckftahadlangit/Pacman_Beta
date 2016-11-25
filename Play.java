package Pacman;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import static Pacman.Help.*;
import static Pacman.Main.*;
import static Pacman.Menu.*;
import java.util.*;

public class Play extends BasicGameState {

    private List<Ghosts> listOfGhosts;
    private List<Pellet> listOfPellets;
    private int spawnTimeGhost = 500;
    private int spawnTimePellet = 1000;
    private int powerDuration;
    private int timerPellet;
    private int timerGhost;
    public Pacman pacman;
    public String mouse;
    public Image Play;
    public static int distance;
    public int temp;
    int ghostFrequency;
    public float spriteDivider = 5.f;

    public Play() {

    }
    public Play(int state) {

    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        listOfGhosts = new ArrayList<Ghosts>();
        listOfPellets = new ArrayList<Pellet>();
        Play = new Image("res/Images/Play.png");
        pacman = new Pacman();
        mouse = "";
        timerPellet = 0;
        timerGhost = 0;
        distance = 0;
        ghostFrequency = 10;
        temp = 2;
        powerDuration = 0;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        if (superdefaultMode) {
            graphics.drawImage(Play, 0, 0);
        } else if (superisHalloween) {
            graphics.drawImage(modeHalloween, 0, 0);
        } else if (superisNature) {
            graphics.drawImage(modeNature, 0, 0);
        } else if (superisSnow) {
            graphics.drawImage(modeSnow, 0, 0);
        } else if (superisSummer) {
            graphics.drawImage(modeSummer, 0, 0);
        }else if (superisNight) {
            graphics.drawImage(modeNight, 0, 0);
        }else if (superisCity) {
            graphics.drawImage(modeCity, 0, 0);
        }

        graphics.setColor(Color.yellow);
        graphics.fillOval(pacman.pacmanX, pacman.pacmanY, pacman.radius,pacman.radius,200);
        if(pacman.powerUP){
            for (Ghosts g : listOfGhosts) {
                g.invisible.draw(g.ghostX, g.ghostY);
            }
        }else {
            for (Ghosts g : listOfGhosts) {
                g.move.draw(g.ghostX, g.ghostY);
            }
        }
        for (Pellet p : listOfPellets) {
            p.move.draw(p.pelletX, p.pelletY);
        }
        graphics.drawString("Distance: " + (distance/100) + " meters", 20, 550);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException{
        int xPos = Mouse.getX();
        int yPos = Mouse.getY();
        mouse = "Position X: " + xPos + "| Position Y: " + yPos;
        if(distance % 20000 == 0 && distance >= 20000 ){
            listOfPellets.add(new Pellet(1));
            distance += 100;
        }
        //Movement for Pacman
        Input input = gameContainer.getInput();
        if (input.isKeyDown(Input.KEY_LEFT)) {
            if (pacman.pacmanX >= 20.5) {
                pacman.pacmanX -= delta/3.f;
            }
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            if ((pacman.pacmanX + pacman.radius) <= 480) {
                pacman.pacmanX += delta/3.f;
            }
        }
        if (input.isKeyPressed(Input.KEY_SPACE)) {
            System.out.println("PAUSE!");
            if (gameContainer.isPaused()){
                gameContainer.resume();
            } else {
                gameContainer.pause();
            }
        }
        distance += delta;
        timerGhost += delta;
        timerPellet += delta;

        //movement for pellet
        movePellet(delta);
        //movement for ghost
        moveGhost(stateBasedGame, gameContainer,delta);
        //Add Ghost
        addGhost();
        //Add Pellet
        addPellet();
    }

    public void moveGhost(StateBasedGame SBG, GameContainer GC,int delta) throws SlickException{
        for (Iterator<Ghosts> iterator = listOfGhosts.iterator(); iterator.hasNext();) {
            Ghosts ghost = iterator.next();
            ghost.ghostY += delta/ spriteDivider;
            if (ghost.ghostY >= 550) {
                ghost.changeLocation();
            }
            if (ghost.bounds().intersects(pacman.bounds()) || pacman.radius <= 4) {
                if(!pacman.powerUP) {
                    SBG.getState(1).init(GC, SBG);
                    SBG.getState(6).init(GC, SBG);
                    listOfGhosts.clear();
                    listOfGhosts.clear();
                    s.gameOver.play();
                    if(soundState){
                        s.backgroundMenu.play();
                    }
                    SBG.enterState(6, new FadeOutTransition(Color.black, 2000), new FadeInTransition());
                }else{
                    ghost.ghostY = -100;
                    distance += 1000;
                }
            }
            for (Pellet p: listOfPellets) {
                if(p.bounds().intersects(ghost.bounds())){
                    ghost.changeLocation();
                }
            }
            for (Ghosts g: listOfGhosts) {
                if(g.ghostY != ghost.ghostY && g.ghostX != ghost.ghostX) {
                    if (g.bounds().intersects(ghost.bounds())) {
                        ghost.changeLocation();
                    }
                }
            }

        }
    }

    public void movePellet(int delta) {

        for (Iterator<Pellet> iterator = listOfPellets.iterator(); iterator.hasNext();) {
            Pellet pellet = iterator.next();
            pellet.pelletY += delta / spriteDivider;
            if (pellet.pelletY >= 500 || pellet.pelletY >= pacman.pacmanY) {
                pellet.changeLocation();
            }
            if(pacman.powerUP){
                powerDuration += delta;
                if(powerDuration >= 40000){
                    powerDuration = 0;
                    pacman.powerUP = false;
                    spriteDivider = 5.f;
                }
            }
            if(!pacman.powerUP){
                pacman.radius -= .001f;
            }
            if (pellet.bounds().intersects(pacman.bounds()) && !pacman.powerUP) {//Check if pacman intersects with pellet
                s.feed.play();
                if (pacman.pacmanY + pacman.radius <= 570) {
                    pacman.radius += 0.3f;
                }
                if (pellet.powerUp) {
                    pacman.powerUP = true;
                    iterator.remove();
                    spriteDivider = 2.f;
                }
            }
            for (Ghosts g: listOfGhosts) {
                if(pellet.bounds().intersects(g.bounds())){
                    pellet.changeLocation();
                }
            }
            for (Pellet p: listOfPellets) {
                if(pellet.pelletY != p.pelletY && pellet.pelletX != p.pelletX) {
                    if (pellet.bounds().intersects(p.bounds())) {
                        pellet.changeLocation();
                    }
                }
            }
        }
    }

    public void addPellet() throws SlickException{
        if(timerPellet >= spawnTimePellet) {
            Pellet pellet;
            pellet = new Pellet(2);
            listOfPellets.add(pellet);
            for (Ghosts ghost : listOfGhosts) {
                if (pellet.bounds().intersects(ghost.bounds())) {
                    pellet.changeLocation();
                }
            }
            timerPellet = 0;
            spawnTimePellet += 310;
        }
    }

    public void addGhost() throws SlickException {
        if (timerGhost >= spawnTimeGhost) {
            if (listOfGhosts.size() <= 15) {
                Random rand = new Random();
                Ghosts g = new Ghosts(rand.nextInt(4) + 1);
                listOfGhosts.add(g);
                for (Ghosts ghost : listOfGhosts) {
                    if (g.bounds().intersects(ghost.bounds())) {
                        g.changeLocation();
                    }
                }
                timerGhost = 0;
                spawnTimeGhost += 325;
            }
        }
    }
}
