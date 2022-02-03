package project;

public class Coordinate {
    private int xc; //x-value
    private int yc; //y-value
    private Coordinate next; //pointer to next coordinate

    public Coordinate(int x, int y){ 
        this.xc = x;
        this.yc = y;
        this.next = null;
    }
    public int getX(){
        return this.xc;
    }
    public int getY(){
        return this.yc;
    }
    public void setCoord(int x, int y){
        this.setX(x);
        this.setY(y);
    }
    private void setX(int x){
        this.xc = x;
    }
    private void setY(int y){
        this.yc = y;
    }

    public void setNext(Coordinate n){
        this.next =n;
    }
    public Coordinate getNext(){
        return this.next;
    }
}
