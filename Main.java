package Pacman;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Main extends StateBasedGame {
    public static final String gameName = "Pacman Got Size Issue";
    public static final int menu = 0;
    public static final int play = 1;
    public static final int help = 2;
    public static final int highScore = 3;
    public static final int exit = 4;
    public static final int lose = 5;
    public static final int win = 6;
    public static final int mode = 7;
    public static final int timed = 8;
    public static final int revenge = 9;
    //public static final int prev;

    public static Image modeNight ;
    public static Image modeCity;
    public static Image modeSnow;
    public static Image modeHalloween;
    public static Image modeSummer;
    public static Image modeNature;
    public static Image Play;
    public static Image defaultMode;

    public static boolean superdefaultMode;
    public static boolean superisNight;
    public static boolean superisCity;
    public static boolean superisSnow;
    public static boolean superisHalloween;
    public static boolean superisSummer;
    public static boolean superisNature;

    public Main(String gameName) throws  SlickException {
        super(gameName);
        this.addState(new Menu(menu));
        this.addState(new Play(play));
        this.addState(new Help(help));
        this.addState(new HighScore(highScore));
        this.addState(new ExitScreen(exit));
        this.addState(new loseScreen(lose));
        this.addState(new winScreen(win));
        this.addState(new Mode(mode));
        this.addState(new Timed(timed));
        this.addState(new Revenge(revenge));
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        modeNight = new Image("res/Images/modeNight.png");
        modeCity = new Image("res/Images/modeCity.png");
        modeSnow = new Image("res/Images/modeSnow.png");
        modeHalloween = new Image("res/Images/modeHalloween.png");
        modeSummer = new Image("res/Images/modeSummer.png");
        modeNature = new Image("res/Images/modeNature.png");
        defaultMode = new Image("res/Images/defaultMode.png");

        this.getState(menu).init(gameContainer, this);
//        this.getState(help).init(gameContainer, this);
//        this.getState(highScore).init(gameContainer, this);
//        this.getState(exit).init(gameContainer, this);
//        this.getState(lose).init(gameContainer, this);
//        this.getState(win).init(gameContainer, this);
//        this.getState(play).init(gameContainer, this);
        this.getState(timed).init(gameContainer, this);
        //this.getState(revenge).init(gameContainer, this);
        //this.enterState(revenge);
        this.enterState(timed);
    }


    public static void main(String[] args) {
        AppGameContainer appGameContainer;
        try {
            appGameContainer = new AppGameContainer(new Main(gameName));
            appGameContainer.setDisplayMode(500, 600, false);
            appGameContainer.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}