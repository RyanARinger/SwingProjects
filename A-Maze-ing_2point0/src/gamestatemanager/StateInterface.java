package gamestatemanager;

import javax.swing.JFrame;
public interface StateInterface {
    public void enter(StateManager man);
    public void exit();
    public StateInterface nextState(Condition c);
    public void setSelection(Condition c);
}
