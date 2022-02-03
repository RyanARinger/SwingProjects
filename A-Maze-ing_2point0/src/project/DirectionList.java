package project;

import java.util.ArrayList;
import java.util.Random;

//this "list" holds an arraylist that can be loaded with direction instances
//the main use of this list is to return a random direction for use by
//maze generation and pathfinding

public class DirectionList {
    ArrayList<Direction> al;

    public DirectionList(){
        this.al = new ArrayList<Direction>();
    }

    public void add(Direction d){
        this.al.add(d);
    }

    public boolean isEmpty(){
        return this.al.isEmpty();
    }
    public Direction getRandomDirection(){
        if(this.al.size() < 1){ // if list is empty
            return null;
        }
        Random rand = new Random(); //random number generator
        Direction temp;
        int index = rand.nextInt()%this.al.size(); //mod the random number with the number of elements in al
        if(index < 0){ // if number is negative, make positive
            index *= -1;
        }

        temp = al.get(index); //get direction at random index
        al.clear(); //clear the arraylist
        return temp; //return the randomly chosen direction
    }
}
