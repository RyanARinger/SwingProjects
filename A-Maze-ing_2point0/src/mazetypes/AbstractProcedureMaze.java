package mazetypes;

import project.*;

public abstract class AbstractProcedureMaze extends AbstractMaze{

    public AbstractProcedureMaze(int mazeSize){
        super(mazeSize);
        createMaze();
        setUp();
    }

    @Override
    public void createMaze() {
        int visitedTiles = 1; //we start on a tile; (0, 0)
        CoordStack s = new CoordStack(); // used to back track
        Coordinate temp;
        int x = 0; //for traversal
        int y = 0;
        int numTiles = getMazeSize()*getMazeSize();
        DirectionList list = new DirectionList(); //for saving and retrieving random movements
        Direction move = null;
        this.getGrid()[x][y].visit(); //visit the first tile
        s.push(x, y); //push the first tile onto the stack
//        boolean hintFound = false;

        while (visitedTiles < numTiles) { //while tiles are unvisited

//            System.out.println("(" + x + " , " + y + ")");
            //if tile to the left is unvisited and within bounds
            if ((x - 1) >= 0) {
                if (!this.getGrid()[x - 1][y].visited()) {
                    list.add(Direction.LEFT); //add direction to list as possible movement
                }
            }
            //right
            if ((x + 1) <= getMazeSize() - 1) {
                if (!this.getGrid()[x + 1][y].visited()) {
                    list.add(Direction.RIGHT);
                }
            }
            //Up
            if ((y - 1) >= 0) {
                if (!this.getGrid()[x][y - 1].visited()) {
                    list.add(Direction.UP);
                }
            }
            //down
            if ((y + 1) <= getMazeSize() - 1) {
                if (!this.getGrid()[x][y + 1].visited()) {
                    list.add(Direction.DOWN);
                }
            }

            if (list.isEmpty()) { //if no directions found (A.K.A. a dead end)
//                System.out.println("Dead End at (" + x + " , " + y + ")");
                s.pop(); //pop off the most recent coordinate
                temp = s.viewTop(); //look at the location last visited
                if (temp == null) { //debugging
                    System.out.println("Err1");
                    return;
                } else {
                    x = temp.getX(); //backtrack and return to movement testing
                    y = temp.getY();
                }
            } else {
                move = list.getRandomDirection(); //pick a random direction from available directions

                if (move == Direction.UP) { //move up
                    this.getGrid()[x][y].openTopBorder(); //move up out of current tile
                    y--; // move up
                    s.push(x, y); //push new coordinate
                    this.getGrid()[x][y].openBottomBorder(); //move into new tile from below
                    this.getGrid()[x][y].visit(); //visit new tile
                    visitedTiles++; //increment
                } else if (move == Direction.DOWN) { //move down
                    this.getGrid()[x][y].openBottomBorder();//exit down
                    y++; //move down
                    s.push(x, y);//push
                    this.getGrid()[x][y].openTopBorder();//enter above
                    this.getGrid()[x][y].visit();//visit
                    visitedTiles++; //increment
                } else if (move == Direction.LEFT) { //move left
                    this.getGrid()[x][y].openLeftBorder();//exit left
                    x--;// move left
                    s.push(x, y);//push
                    this.getGrid()[x][y].openRightBorder();//enter right
                    this.getGrid()[x][y].visit();//visit
                    visitedTiles++;//increment
                } else if (move == Direction.RIGHT) { //move right
                    this.getGrid()[x][y].openRightBorder();//exit right
                    x++;//move right
                    s.push(x, y);//push
                    this.getGrid()[x][y].openLeftBorder();//enter left
                    this.getGrid()[x][y].visit();//visit
                    visitedTiles++;//increment
                } else {
                    System.out.println("Err"); //debug
                }

            }
        }
    }
}
