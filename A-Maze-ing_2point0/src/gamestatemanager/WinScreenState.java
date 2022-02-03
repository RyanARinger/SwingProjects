package gamestatemanager;

import javax.swing.JFrame;

import project.MenuPanel;
import project.WordButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WinScreenState implements StateInterface {
    private StateManager sm;
    private Condition selection;
    private MenuPanel menu;

    public WinScreenState(){
        menu = new MenuPanel();
    }


    @Override
    public void enter(StateManager man) {
        sm = man;
        sm.getWindow().getContentPane().removeAll();

        String message = "YOU WIN!";
        WordButton again = new WordButton("AGAIN?");
        WordButton exit = new WordButton("MENU");

        again.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selection = Condition.MENU;
                exit();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selection = Condition.EXIT;
                exit();
            }
        });

        this.menu.add(message);
        this.menu.add(again);
        this.menu.add(exit);

        man.getWindow().add(menu);

        man.getWindow().getContentPane().revalidate();
        man.getWindow().getContentPane().repaint();
    }

    @Override
    public void exit() {
        sm.setCurrentState(nextState(selection));
        menu = null;
    }

    @Override
    public StateInterface nextState(Condition c) {
        switch (c) {
            case MENU:
                return new DifficultyMenuState();
            case EXIT:
                return new MainMenuState();
            default:
                return new NullState();
        }


    }
    @Override
    public void setSelection(Condition c){

    }
}