package gamestatemanager;

import mazetypes.AbstractMaze;
import project.MenuPanel;
import project.WordButton;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

public class PauseState implements StateInterface{

    private StateManager sm;
    private AbstractMaze pausedMaze;
    private StateInterface pausedState;
    private MenuPanel menu;
    private Condition selection;

    public PauseState(AbstractMaze am, MazeState ps){
        menu = new MenuPanel();
        pausedMaze = am;
        pausedState = ps;
    }

    @Override
    public void enter(StateManager man) {
        sm = man;
        sm.getWindow().getContentPane().removeAll();

        String hintMessage = "HINTS";
//        if (pausedMaze.getHint()) {
//            hintMessage += "ON";
//        } else {
//            hintMessage += "OFF";
//        }
        String pathMessage = "PATH";
//        if (pausedMaze.getPath()) {
//            pathMessage += "ON";
//        } else {
//            pathMessage += "OFF";
//        }

        String message = "GAME PAUSED";
        WordButton resume = new WordButton("RESUME");
        WordButton hintButton = new WordButton(hintMessage);
        WordButton pathButton = new WordButton(pathMessage);
        WordButton menu = new WordButton("MENU");

        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selection = Condition.EXIT;
                exit();
            }
        });
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selection = Condition.MENU;
                exit();
            }
        });
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausedMaze.toggleHint();
            }
        });
        pathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausedMaze.togglePath();
            }
        });

        pausedMaze.setState(this);

        this.menu.add(message);
        this.menu.add(resume);
        this.menu.add(hintButton);
        this.menu.add(pathButton);
        this.menu.add(menu);

        sm.getWindow().add(this.menu);

        sm.getWindow().getContentPane().revalidate();
        sm.getWindow().getContentPane().repaint();

    }

    @Override
    public void exit() {
        sm.setCurrentState(nextState(selection));

        if(selection == Condition.MENU){
            pausedMaze = null;
            pausedState = null;
        }
        else if(selection == Condition.EXIT){
            pausedMaze.setState(pausedState);
        }
    }

    @Override
    public StateInterface nextState(Condition c) {
        switch (c){
            case EXIT:
                return pausedState;
            case MENU:
                return new MainMenuState();
            default:
                return new NullState();
        }
    }
    @Override
    public void setSelection(Condition c){
        this.selection = c;
    }
}
