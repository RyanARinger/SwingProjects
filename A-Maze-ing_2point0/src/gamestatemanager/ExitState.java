package gamestatemanager;


import javax.swing.JFrame;

public class ExitState implements StateInterface{
    private StateManager sm;


    @Override
    public void enter(StateManager man) {
        man.getWindow().dispose();
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
