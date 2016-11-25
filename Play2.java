package Pacman;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.*;

import static Pacman.Main.*;

public class Play2 extends BasicGameState {

    private List<Ghosts> listOfGhosts;
    private List<Pellet> listOfPellets;
    private int spawnTimeGhost = 1000;
    private int spawnTimePellet = 1000;
    private int timerPellet;
    private int timerGhost;
    private Music Background;
    public Pacman pacman;
    public String mouse;
    public Image Play;
    public int distance;
    public int temp;
    int ghostFrequency;

    public Play2() {

    }
    public Play2(int state) {

    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        listOfGhosts = new ArrayList<Ghosts>();
        listOfPellets = new ArrayList<Pellet>();
        Play = new Image("res/Play.png");
        pacman = new Pacman();
        mouse = "";
        timerPellet = 0;
        timerGhost = 0;
        distance = 0;
        ghostFrequency = 10;
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
        for(Ghosts g: listOfGhosts){
            g.move.draw(g.ghostX, g.ghostY);
        }
        for(Pellet p: listOfPellets) {
            p.move.draw(p.pelletX, p.pelletY);
        }
        graphics.drawString("Distance: " + (distance/100) + " meters", 20, 550);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        int xPos = Mouse.getX();
        int yPos = Mouse.getY();
        mouse = "Position X: " + xPos + "| Position Y: " + yPos;

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

        //movement for pellet
        movePellet(delta);
        //mpvement for ghost
        moveGhost(stateBasedGame, gameContainer,delta);
        //Distance covered of Pacman
        distance += delta;
        timerGhost += delta;
        timerPellet += delta;
        //Add Ghost
        addGhost();
        //Add Pellet
        addPellet();
    }

    public void moveGhost(StateBasedGame SBG, GameContainer GC,int delta)throws SlickException{
        for (Ghosts ghost: listOfGhosts) {
            ghost.ghostY += delta/5.f;
            if (ghost.ghostY >= 550) {
                ghost.changeLocation();
            }
            if (ghost.bounds().intersects(pacman.bounds())) {
                SBG.getState(1).init(GC,SBG);
                SBG.getState(6).init(GC,SBG);
                SBG.enterState(6,new FadeOutTransition(), new FadeInTransition());
            }
            for (Pellet p: listOfPellets) {
                if(p.bounds().intersects(ghost.bounds())){
                    ghost.changeLocation();
                }
            }

        }
    }

    public void movePellet(int delta) {
        for (Pellet pellet : listOfPellets) {
            pellet.pelletY += delta / 5.f;
            if (pellet.pelletY >= 500 || pellet.pelletY >= pacman.pacmanY) {
                pellet.changeLocation();
            }
            if (pellet.bounds().intersects(pacman.bounds())) {//Check if pacman intersects with pellet
                if (pacman.pacmanY + pacman.radius <= 570) {
                    pacman.radius += 0.3f;
                }
                if (pellet.powerUp) {
                    //Power Stuff
                } else {
                    //Normal Pellet
                }
            }
            for (Ghosts g: listOfGhosts) {
                if(pellet.bounds().intersects(g.bounds())){
                    pellet.changeLocation();
                }
            }
        }
    }

    public void addPellet() throws SlickException{
        if(timerPellet >= spawnTimePellet) {
            Random rand = new Random();
            Pellet pellet = new Pellet(rand.nextInt(30) + 1);
            listOfPellets.add(pellet);
            for (Ghosts ghost : listOfGhosts) {
                if (pellet.bounds().intersects(ghost.bounds())) {
                    pellet.changeLocation();
                }
            }
            timerPellet = 0;
            spawnTimePellet += 300;
        }
    }

    public void addGhost() throws SlickException {
        if (timerGhost >= spawnTimeGhost) {
            Random rand = new Random();
            Ghosts g = new Ghosts(rand.nextInt(4) + 1);
            listOfGhosts.add(g);
            for (Ghosts ghost : listOfGhosts) {
                if (g.bounds().intersects(ghost.bounds())) {
                    g.changeLocation();
                }
            }
            timerGhost = 0;
            spawnTimeGhost += 300;
        }
    }
}
