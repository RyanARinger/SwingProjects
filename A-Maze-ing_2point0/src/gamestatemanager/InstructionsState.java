package gamestatemanager;

import project.MenuPanel;
import project.WordButton;

import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class InstructionsState implements StateInterface{
    private StateManager sm;
    private Condition selected;
    private MenuPanel menu;

    public InstructionsState(){
        menu = new MenuPanel();
    }

    @Override
    public void enter(StateManager man) {
        sm = man;
        sm.getWindow().getContentPane().removeAll();
//        this.clearMenu();
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
                selected = Condition.MENU;
                exit();
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

        sm.getWindow().add(menu);

        sm.getWindow().getContentPane().revalidate();
        sm.getWindow().getContentPane().repaint();

    }

    @Override
    public void exit() {
        sm.setCurrentState(nextState(selected));
        menu = null;
    }

    @Override
    public StateInterface nextState(Condition c) {
        switch (c){
            case MENU:
                return new MainMenuState();
            default:
                return new NullState();
        }
    }

    @Override
    public void setSelection(Condition c){
    }
}
