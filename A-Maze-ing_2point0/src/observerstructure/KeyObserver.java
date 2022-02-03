package observerstructure;

import mazetypes.AbstractMaze;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyObserver implements KeyListener, ObserverIF{
    private AbstractMaze dependentMaze;
    
    public KeyObserver(AbstractMaze am){
        dependentMaze = am;
    }
    @Override
    public void update(){
        dependentMaze.repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // WASD
        if(e.getKeyCode() == KeyEvent.VK_W){
            dependentMaze.moveUp();
            dependentMaze.winTest();
            update();
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            dependentMaze.moveLeft();
            dependentMaze.winTest();
            update();
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            dependentMaze.moveDown();
            dependentMaze.winTest();
            update();
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            dependentMaze.moveRight();
            dependentMaze.winTest();
            update();
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            dependentMaze.toggleSuspended();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
