package Lab0;


public class Animator extends Thread{
    private AnimatedLiningPanel pan;
    private boolean paused = true;

    public Animator(AnimatedLiningPanel p){
        this.pan = p;
    }

    @Override
    public void run(){
        while(true){
            if(!paused){
                this.pan.repaint();
                this.pan.increaseCounter();
            }
            try{
                Thread.sleep(this.pan.getTimer());
            }catch(InterruptedException ie){
                System.out.println("Error");
            }
        }
    }

    public void pauseToggle(){
        if(this.paused){
            this.paused = false;
        }
        else{
            this.paused = true;
        }
    }
    
}
