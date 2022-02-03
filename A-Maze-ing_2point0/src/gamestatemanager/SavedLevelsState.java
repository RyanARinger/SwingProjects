package gamestatemanager;

import javax.swing.JFrame;

public class SavedLevelsState implements StateInterface{
    private StateManager sm;

    @Override
    public void enter(StateManager man) {

    }

    @Override
    public void exit() {

    }

    @Override
    public StateInterface nextState(Condition c) {
        switch(c){

        }
        return null;
    }

    @Override
    public void setSelection(Condition c){
    }
}
