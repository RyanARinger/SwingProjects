package gamestatemanager;

import mazetypes.AbstractMaze;
import mazetypes.MazeFactoryIF;
import mazetypes.ProcedureMazeFactory;
import observerstructure.KeyObserver;

import javax.swing.JFrame;
import java.awt.Color;

public class StateManager{

    private final int PANELSIZE = 1000;
    private StateInterface currentState;
    private JFrame window;
    private MazeFactoryIF mf;
    private KeyObserver ko;

    public StateManager(){
        window = new JFrame("A-MAZE-ING");
        currentState = new MainMenuState();
        mf = new ProcedureMazeFactory();
        window.setSize(PANELSIZE, PANELSIZE);
        window.setBackground(Color.LIGHT_GRAY);
//        window.setResizable(false);

        currentState.enter(this);

        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public JFrame getWindow(){
        return this.window;
    }
    public void enterNextState(){
        currentState.enter(this);
    }
    public void setCurrentState(StateInterface s){
        this.currentState = s;
        this.enterNextState();
    }
    public MazeFactoryIF getFactory(){
        return this.mf;
    }
    public void setObserver(AbstractMaze ma){
        this.ko = new KeyObserver(ma);
        window.addKeyListener(ko);
    }
    public void removeObserver(){
        window.removeKeyListener(ko);
    }
}
