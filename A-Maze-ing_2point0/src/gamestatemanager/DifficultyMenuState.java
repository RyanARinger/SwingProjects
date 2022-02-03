package gamestatemanager;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import project.*;

public class DifficultyMenuState implements StateInterface{
    private StateManager sm;
    private Condition selected;
    private MenuPanel menu;

    public DifficultyMenuState(){
        menu = new MenuPanel();
    }

    @Override
    public void enter(StateManager man) {
        sm = man;
        sm.getWindow().getContentPane().removeAll();

        String message = "Select A Difficulty";
        WordButton e = new WordButton("EASY");
        WordButton m = new WordButton("MEDIUM");
        WordButton h = new WordButton("HARD");
        WordButton b = new WordButton("RETURN");

        e.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //generate an easy maze with size 10
                selected = Condition.EASY;
                exit();
            }
        });
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//generate an easy maze with size 20
                selected = Condition.MEDIUM;
                exit();
            }
        });
        h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//generate an easy maze with size 30
                selected = Condition.HARD;
                exit();
            }
        });
        b.addActionListener(new ActionListener() { // reload the main menu
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = Condition.EXIT;
                exit();
            }
        });
        this.menu.add(message);
        this.menu.add(e);
        this.menu.add(m);
        this.menu.add(h);
        this.menu.add(b);

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
    public StateInterface nextState(Condition c){
        return switch (c) {
            case EXIT -> new MainMenuState();
            case EASY -> new MazeState(Difficulty.EASY);
            case MEDIUM -> new MazeState(Difficulty.MEDIUM);
            case HARD -> new MazeState(Difficulty.HARD);
            default -> new NullState();
        };
    }

    @Override
    public void setSelection(Condition c){
    }
}
