package gamestatemanager;


import javax.swing.JFrame;

public class NullState implements StateInterface{
    private StateManager sm;
    private StateInterface nextState;

    @Override
    public void enter(StateManager man) {
        System.out.println("Waiting in null state");
        this.nextState = this;
    }

    @Override
    public void exit() {

    }

    @Override
    public StateInterface nextState(Condition c) {
        return null;
    }

    @Override
    public void setSelection(Condition c){
    }
}
