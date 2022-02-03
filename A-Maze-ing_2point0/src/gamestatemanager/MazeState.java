package gamestatemanager;

import mazetypes.AbstractMaze;
import mazetypes.EasyProcedureMaze;

import javax.swing.JFrame;

public class MazeState implements StateInterface{
    private StateManager sm;
    private AbstractMaze am;
    private Difficulty difficulty;
    private Condition selection;
    private boolean created;

    public MazeState(Difficulty d){
        difficulty = d;
        created = false;
    }
    @Override
    public void enter(StateManager man) {
        sm = man;
        sm.getWindow().getContentPane().removeAll();

        if(!created) {
            switch (difficulty) {
                case EASY:
                    am = sm.getFactory().createEasyMaze();
                    break;
                case MEDIUM:
                    am = sm.getFactory().createMediumMaze();
                    break;
                case HARD:
                    am = sm.getFactory().createHardMaze();
                    break;
                default:
                    System.out.println("You Suck");
            }
            sm.setObserver(am);
            am.setState(this);

            sm.getWindow().add(am);
            sm.getWindow().getContentPane().revalidate();
            sm.getWindow().getContentPane().repaint();
            created = true;
        }
        else{
            paintMaze();
        }
    }

    public void paintMaze(){
        sm.getWindow().getContentPane().removeAll();
        sm.getWindow().add(am);
        sm.getWindow().getContentPane().revalidate();
        sm.getWindow().getContentPane().repaint();
    }
    @Override
    public void exit() {
        sm.setCurrentState(nextState(selection));

        if(selection == Condition.COMPLETE){
            am = null;
            sm.removeObserver();
        }
    }

    @Override
    public StateInterface nextState(Condition c) {
        switch(c){
            case COMPLETE:
                return new WinScreenState();
            case PAUSE:
                return new PauseState(am, this);
            default:
                return new NullState();
        }
    }

    @Override
    public void setSelection(Condition c){
        this.selection = c;
    }
}
