package se370_team3_projectv1;

/*
This is a class that is designed to hold the information gathered from the .csv files
which are used to save the level information
There is a member for the name of the maze
There is size member
There is a two dimensional array for the bit strings

This is also used to save the information from a generated level and the toString method is used to write the
information to a .csv file
 */

public class LevelInfo {
    private String[][] tiles;
    private String name;
    private int size;

    public LevelInfo(){
        this.name = null;
        this.size = 0;
        this.tiles = null;
    }

    public void addString(String s, int x, int y){
        this.tiles[x][y] = s;
    }

    public void setName(String n){
        this.name = n;
    }

    public void setSize(int s) {
        this.size = s;
        this.tiles = new String[this.size][this.size];
    }

    public String getName(){
        return this.name;
    }

    public int getSize(){
        return this.size;
    }

    public String getTile(int x, int y){
        return this.tiles[x][y];
    }

    public String toString(){
        String s = "";

        s += this.name + "," + this.size + "\n";

        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                s += this.tiles[i][j];
                if(j < this.size-1){
                    s += ",";
                }
            }
            if(i < this.size-1){
                s += "\n";
            }
        }
        return s;
    }
}
