package gamestatemanager;

import project.MenuPanel;
import project.WordButton;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainMenuState implements StateInterface{

    private MenuPanel menu;
    private Condition selected;
    private StateManager sm;
    public MainMenuState(){
        menu = new MenuPanel();
    }
    @Override
    public void enter(StateManager man) {
        sm = man;
        sm.getWindow().getContentPane().removeAll();

        String message = "Welcome To";
        String message1 = "    A-MAZE-ING    ";
        WordButton start = new WordButton("QUICKPLAY"); //quickplay
        WordButton help = new WordButton("INSTRUCTIONS"); //tutorial
        WordButton exit = new WordButton("EXIT"); //exit
//        WordButton custom = new WordButton("SAVED LEVELS"); //saved levels

        //now we add action listeners to the WordButtons that operate their respective tasks
        start.addActionListener(new ActionListener() { //switch to difficulty menu
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = Condition.QUICKPLAY;
                exit();
            }
        });
        help.addActionListener(new ActionListener() {// switch to tutorial menu
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = Condition.INSTRUCTIONS;
                exit();
            }
        });
        exit.addActionListener(new ActionListener() {//close the game
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = Condition.EXIT;
                exit();
            }
        });

//        custom.addActionListener(new ActionListener() { // switch to saved levels list
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                makeLevelListMenu();
//            }
//        });
        //add each item
        this.menu.add(message);
        this.menu.add(message1);
//        this.menu.add(custom);
        this.menu.add(start);
        this.menu.add(help);
        this.menu.add(exit);

        man.getWindow().add(menu);

        man.getWindow().getContentPane().revalidate();
        man.getWindow().getContentPane().repaint();
    }

    @Override
    public void exit() {
        sm.setCurrentState(nextState(selected));
        menu = null;
//        sm.getWindow().remove(menu);
    }

    @Override
    public StateInterface nextState(Condition c){
        return switch (c) {
            case QUICKPLAY -> new DifficultyMenuState();
            case SAVED_LEVELS -> new SavedLevelsState();
            case INSTRUCTIONS -> new InstructionsState();
            case EXIT -> new ExitState();
            default -> new NullState();
        };
    }

    @Override
    public void setSelection(Condition c){

    }

}
