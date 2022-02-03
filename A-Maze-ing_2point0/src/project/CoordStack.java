package project;

//This is a class that defines a stack of Coordinate objects
//This is primarily designed for use in the procedural generation of
//mazes but is also used by the pathfinding algorithms
//This class operates like a normal stack

public class CoordStack {
    private Coordinate top;
    private int numCoords;

    public CoordStack(){
        this.top = null;
        this.numCoords = 0;
    }
    public void push(int x, int y){
        Coordinate temp;
        if(this.numCoords == 0){
            this.top = new Coordinate(x,y);
            this.numCoords++;
        }
        else{
            temp = new Coordinate(x,y);
            temp.setNext(this.top);
            this.top = temp;
            this.numCoords++;
        }
    }
    public void push(Coordinate c){
        if(this.numCoords == 0){
            this.top = new Coordinate(c.getX(), c.getY());
            this.numCoords++;
        }
        else{
            c.setNext(this.top);
            this.top = c;
            this.numCoords++;
        }
    }
    public boolean isEmpty(){
        if(this.numCoords == 0){
            return true;
        }
        return false;
    }
    public int size(){
        return this.numCoords;
    }
    public Coordinate viewTop(){
        return this.top;
    }

    public Coordinate pop(){
        Coordinate temp;

        if(this.numCoords == 0){
            return null;
        }
        temp = this.top;
        this.top = this.top.getNext();
        this.numCoords--;
        return temp;
    }

}
