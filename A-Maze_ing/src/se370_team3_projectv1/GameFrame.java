package se370_team3_projectv1;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

/*
This is a class that extends the JFrame object and is used to switch between the menus and the mazes
within the application
 */

public class GameFrame extends JFrame implements KeyListener{
    private JPanel disp; //panel for using CartdLayout for switching between panels
    private MenuPanel menu; //menu panel
    private MazePanel maze; //maze panel

    private final int DIM; //dimension of window
    private CardLayout cl; //card layout for flipping between the maze and the menus

    private boolean pausable; // for testing whether the player is in-game
    private boolean hintsOn; // pathfinder booleans
    private boolean pathOn;

    // these are inoperable and thus, antiquated
//    private LevelLister ll;
//    private LevelListerV2 llv2;
//    private LevelReader lr;

    public GameFrame(){
        super();
        this.menu = new MenuPanel();
        this.cl = new CardLayout();
        this.disp = new JPanel();
        this.DIM = 1000;
//        this.llv2 = new LevelListerV2();
//        this.lr = new LevelReader();

        this.setTitle("A-MAZE-ING");
        this.setSize(this.DIM, this.DIM);
        this.disp.setLayout(this.cl);
        this.setResizable(false); //if this is left true, errors can be created by users
        this.makeMainMenu(); //open to main menu

        this.disp.add(this.menu, "Menu"); //the format for carlayout is (component, String)
        this.addKeyListener(this); //for reading key inputs
        this.add(this.disp); //add the cardlayout panel to the base jframe

        this.pausable = false;
//        this.onMenu = true;
        this.hintsOn = false;
        this.pathOn = false;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
    private void clearMenu(){ //this is to wipe out the menuPanel's content before a new menu is made
        this.menu.wipePanel();
        this.pausable = false;
    }
    private void makeMainMenu(){
        this.clearMenu();
        String message = "Welcome To";
        String message1 = "    A-MAZE-ING    ";
        WordButton start = new WordButton("QUICKPLAY"); //quickplay
        WordButton help = new WordButton("INSTRUCTIONS"); //tutorial
        WordButton exit = new WordButton("EXIT"); //exit
        WordButton custom = new WordButton("SAVED LEVELS"); //saved levels

        //now we add action listeners to the WordButtons that operate their respective tasks
        start.addActionListener(new ActionListener() { //switch to difficulty menu
            @Override
            public void actionPerformed(ActionEvent e) {
                makeDiffMenu();
            }
        });
        help.addActionListener(new ActionListener() {// switch to tutorial menu
            @Override
            public void actionPerformed(ActionEvent e) {
                makeInsctrcMenu();
            }
        });
        exit.addActionListener(new ActionListener() {//close the game
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        custom.addActionListener(new ActionListener() { // switch to saved levels list
            @Override
            public void actionPerformed(ActionEvent e) {
                makeLevelListMenu();
            }
        });
        //add each item
        this.menu.add(message);
        this.menu.add(message1);
        this.menu.add(custom);
        this.menu.add(start);
        this.menu.add(help);
        this.menu.add(exit);

    }
    //this is for creating the difficulty menu. its creation follows the same procedure as above
    private void makeDiffMenu(){
        this.clearMenu();
        String message = "Select A Difficulty";
        WordButton e = new WordButton("EASY");
        WordButton m = new WordButton("MEDIUM");
        WordButton h = new WordButton("HARD");
        WordButton b = new WordButton("RETURN");
        
        e.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //generate an easy maze with size 10
                makeMazePanel(10);
            }
        });
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//generate an easy maze with size 20
                makeMazePanel(20);
            }
        });
        h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//generate an easy maze with size 30
                makeMazePanel(30);
            }
        });
        b.addActionListener(new ActionListener() { // reload the main menu
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMainMenu();
            }
        });
        this.menu.add(message);
        this.menu.add(e);
        this.menu.add(m);
        this.menu.add(h);
        this.menu.add(b);

    }
    //this is for creating the tutorial menu. its creation follows the same procedure as above
    private void makeInsctrcMenu() {
        this.clearMenu();
        String message = "How To Play: ";
        String up = "Press \"w\" or Up to move up";
        String down = "Press \"s\" or Down to move down";
        String left = "Press \"a\" or Left to move left";
        String right = "Press \"d\" or Right to move right";
        String redo = "Press \"r\" to restart the maze";
        String pause = "Press Escape to pause the maze";

        WordButton back = new WordButton("Return");

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //reload the main menu
                makeMainMenu();
            }
        });

        this.menu.add(message);
        this.menu.add(up);
        this.menu.add(down);
        this.menu.add(left);
        this.menu.add(right);
        this.menu.add(redo);
        this.menu.add(pause);
        this.menu.add(back);

    }

    //temp-ish idk ryan will figure something out
    //update: this method uses hard-coded buttons for the saved levels.
    //there exists a class in this project responsible for listing the
    //possible levels, but we were unable to achieve a funcitonal list
    private void makeLevelListMenu(){
        this.clearMenu();
        String message = "    Select A Level    ";
        WordButton e = new WordButton("Easy   : Level 1");
        WordButton m = new WordButton("Medium : Level 2");
        WordButton h = new WordButton("Hard   : Level 3");
        WordButton h2 = new WordButton("Hard   : Custom1");
        WordButton m2 = new WordButton("Medium : Custom2");
        WordButton b = new WordButton("RETURN");
        LevelReader lr = new LevelReader(); //for reading tile values from secified file

        e.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelInfo li = lr.nextFile("Easy.csv"); //load this filename
                makeMazePanel(li); //ganerate the saved maze
            }
        });
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelInfo li = lr.nextFile("Medium.csv");
                makeMazePanel(li);
            }
        });
        h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelInfo li = lr.nextFile("Hard.csv");
                makeMazePanel(li);
            }
        });
        h2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelInfo li = lr.nextFile("HardCustom1.csv");
                makeMazePanel(li);
            }
        });
        m2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelInfo li = lr.nextFile("MediumCustom1.csv");
                makeMazePanel(li);
            }
        });
        b.addActionListener(new ActionListener() {// return to the main menu
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMainMenu();
            }
        });

        this.menu.add(message);
        this.menu.add(e);
        this.menu.add(m);
        this.menu.add(h);
        this.menu.add(h2);
        this.menu.add(m2);
        this.menu.add(b);

    }
    //this is for creating the exit menu. its creation follows the same procedure as above
    private void makeExitMenu() {
        this.clearMenu();
        String message = "YOU WIN!";
        WordButton again = new WordButton("AGAIN?");
        WordButton exit = new WordButton("MENU");

        again.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maze.getPg()){ //if maze was procedurally generated
                    makeDiffMenu(); //return to the difficulty menu
                }
                else{ //was loaded from a file
                    makeLevelListMenu(); //return to level list
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMainMenu();
            }
        });

        this.menu.add(message);
        this.menu.add(again);
        this.menu.add(exit);

    }
    //this is for creating the pause menu. its creation follows the same procedure as above
    private void makePauseMenu(){
        this.clearMenu();
        String hintMessage = "HINTS: ";
        if (this.hintsOn) {
            hintMessage += "ON";
        } else {
            hintMessage += "OFF";
        }
        String pathMessage = "PATH: ";
        if (this.pathOn) {
            pathMessage += "ON";
        } else {
            pathMessage += "OFF";
        }

        String message = "GAME PAUSED";
        WordButton resume = new WordButton("RESUME");
        WordButton hintButton = new WordButton(hintMessage);
        WordButton pathButton = new WordButton(pathMessage);
        WordButton menu = new WordButton("MENU");

        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maze.toggleSuspended();
                cl.show(disp, "Maze");
            }
        });
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMainMenu();
            }
        });
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleHints(); //makes the button toggle while the menu is open
            }
        });
        pathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePath(); //makes the button toggle while the menu is open
            }
        });

        this.menu.add(message);
        this.menu.add(resume);
        this.menu.add(hintButton);
        this.menu.add(pathButton);
        this.menu.add(menu);
        this.cl.show(disp, "Menu");
        this.pausable = true;
    }
    private void makeMazePanel(int s){ // this randomly generates a maze
        this.hintsOn = false; // this is in the constructor but these values may be changed between mazes
        this.pathOn = false;
        this.maze = new MazePanel(s, this.DIM);
        this.disp.add(maze, "Maze");// adds the maze panel to the display panel
        this.cl.show(disp, "Maze");// switches to the maze panel
        this.addMazeListener(); //adds the maze as a key listener to the JFrame
        this.pausable = true;
    }
    private void makeMazePanel(LevelInfo li){ // this loads a maze from a file, otherwise it is the same as above
        this.hintsOn = false;
        this.pathOn = false;
        this.maze = new MazePanel(li, this.DIM); // this is different: li is a LevelInfo object rather than an integer
        this.disp.add(maze, "Maze");
        this.cl.show(disp, "Maze");
        this.addMazeListener();
        this.pausable = true;
    }

    private void toggleHints(){
        if(this.hintsOn){ // if hints on
            this.hintsOn = false; // turn off
        }
        else{ //else hints off
            this.hintsOn = true; // turn on
        }

        this.maze.hints(this.hintsOn); //tell the maze to turn on hints
        makePauseMenu(); //repaint the pause menu
    }
    private void togglePath(){
        if(this.pathOn){//if path on
            this.pathOn = false; //turn off
        }
        else{ //else path off
            this.pathOn = true;//turn on
        }

        this.maze.path(this.pathOn); //tell the maze to turn on the path
        makePauseMenu(); //repaint the pause menu
    }

    private void addMazeListener(){
        this.addKeyListener(this.maze);//adds the maze as a key listener to the JFrame
    }
    //if the maze is complete, the game loads the exit menu
    private void testWin(){
        if(this.maze.winTest()) {
            makeExitMenu();
            cl.show(disp, "Menu");
            this.removeKeyListener(this.maze);
            this.maze.reset();
            this.cl.removeLayoutComponent(this.maze);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { // not needed

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        //this fixes the glitch where u could walk past the goal
//        //WASD
//        if(e.getKeyCode() == KeyEvent.VK_W){
//            this.testWin();
//        }
//        if(e.getKeyCode() == KeyEvent.VK_A){
//            this.testWin();
//        }
//        if(e.getKeyCode() == KeyEvent.VK_S){
//            this.testWin();
//        }
//        if(e.getKeyCode() == KeyEvent.VK_D){
//            this.testWin();
//        }
//
//        // Arrow keys
//        if(e.getKeyCode() == KeyEvent.VK_UP){
//            this.testWin();
//        }
//        if(e.getKeyCode() == KeyEvent.VK_LEFT){
//            this.testWin();
//        }
//        if(e.getKeyCode() == KeyEvent.VK_DOWN){
//            this.testWin();
//        }
//        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
//            this.testWin();
//        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){ //user presses escape
            if(this.pausable) {
                if (this.maze.isSuspended()) { // if maze is already paused
                    this.cl.show(this.disp, "Maze"); // return to maze
                } else { //otherwise
                    this.makePauseMenu(); // show pause menu
                }
                this.maze.toggleSuspended();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        //this let the player walk past the goal by holding two buttons
        //WASD
        if(e.getKeyCode() == KeyEvent.VK_W){
            this.testWin();
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            this.testWin();
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            this.testWin();
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            this.testWin();
        }

        // Arrow keys
        if(e.getKeyCode() == KeyEvent.VK_UP){
            this.testWin();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            this.testWin();
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            this.testWin();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            this.testWin();
        }

    }
}
