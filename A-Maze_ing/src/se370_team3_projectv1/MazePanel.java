package se370_team3_projectv1;

import javax.swing.JPanel;
import javax.swing.JLayeredPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Container;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
This is the meat and cheese of the application

This is a class, inherited from a JPanel, which displays an array of SquarePanel objects.
It is also responsible for the procedural generation of the maze, player movement, bounds testing, and victory condition
testing.
 */

public class MazePanel extends JPanel implements KeyListener{
    private int xc; //x coordinate for player
    private int yc; //y coordinate for player
    private int PANELSIZE; //size of the containing JFrame
    private int fx; //win location x
    private int fy; //win location y

    private int mazeSize; //length; number of tiles on one axis
    private int numTiles; //mazeSize x mazeSize
    private boolean suspended; //if game is paused
    private boolean hintOn; //hints are active
    private boolean pathOn; //path is on

    private boolean pg; //procedurally generate?
    private LevelInfo li; //for saving or generating levels
    private LevelWriter lw; //saving levels
    private SquarePanel[][] grid; //The maze
//    private PlayerPanel pp; // Not used, failed implementation
    private GridBagConstraints gbc; //for GridBagLayout

    public MazePanel(int size, int dim){ //random maze and dimension
        super();
        this.pg = true;
        this.mazeSize = size;
        this.li = new LevelInfo();
        this.setUp(dim);
    }
    public MazePanel(LevelInfo l, int dim){ //saved maze and dimension
        super();
        this.pg = false;
        this.li = l;
        this.mazeSize = l.getSize();
        this.setUp(dim);
    }
    private void setUp(int dim){
        this.xc = 0; //starting position
        this.yc = 0;
        this.PANELSIZE = dim;
        this.grid = new SquarePanel[this.mazeSize][this.mazeSize]; // initialize the maze
        this.gbc = new GridBagConstraints();
        this.numTiles = this.mazeSize * this.mazeSize;
        this.fx = this.mazeSize-1; //win location as bottom right tile
        this.fy = this.mazeSize-1;
        this.suspended = false; //game is not paused
        this.lw = new LevelWriter();

//        this.pp = new PlayerPanel(this.mazeSize, this.PANELSIZE);

        this.setLayout(new GridBagLayout());
        this.addKeyListener(this);

        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j++) {
                this.grid[i][j] = new SquarePanel((this.PANELSIZE / this.mazeSize) - 1);
                if(i == this.fx && j == this.fy){ // if final tile, mark as destination
                    this.grid[i][j].setToGate();
                }
                else { //otherwise make blank
                    this.grid[i][j].setToSpace();
                }
            }
        }

        if(this.pg){ // if maze is meant to be random
            this.createMazeProcedurally(); // see method
        }
        else{
            this.createMazeFromFile(); // see method
        }

        //add all tiles to the JPanel using GridBagLayout
        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j++){
                this.addStuff(this, this.grid[i][j], this.gbc, i, j, 1, 1);
            }
        }

        this.placePlayer(this.xc, this.yc);
//        this.add(this.pp, JLayeredPane.PALETTE_LAYER);
        this.setVisible(true);
    }

    /*
    This algorithm is a modification of an algorithm I studied called the "Recursive Backtracker"
    It randomly moves in a direction to a tile that has not been visited already until it has touched
    a number of tiles equal to the number of tiles in the maze.
    It utilizes a coordinate stack to backtrack in the event it finds itself in a dead end

    -Ryan
     */

    private void createMazeProcedurally(){ // Recursive Backtracker
        int visitedTiles = 1; //we start on a tile; (0, 0)
        CoordStack s = new CoordStack(); // used to back track
        Coordinate temp;
        int x = 0; //for traversal
        int y = 0;
        DirectionList list = new DirectionList(); //for saving and retrieving random movements
        Direction move = null;
        this.grid[x][y].visit(); //visit the first tile
        s.push(x, y); //push the first tile onto the stack
//        boolean hintFound = false;

        while(visitedTiles < this.numTiles){ //while tiles are unvisited

//            System.out.println("(" + x + " , " + y + ")");
            //if tile to the left is unvisited and within bounds
            if((x-1) >= 0){
                if(!this.grid[x-1][y].visited()) {
                    list.add(Direction.LEFT); //add direction to list as possible movement
                }
            }
            //right
            if((x+1) <= this.mazeSize-1){
                if(!this.grid[x+1][y].visited()) {
                    list.add(Direction.RIGHT);
                }
            }
            //Up
            if((y-1) >= 0){
                if(!this.grid[x][y-1].visited()) {
                    list.add(Direction.UP);
                }
            }
            //down
            if((y+1) <= this.mazeSize-1){
                if(!this.grid[x][y+1].visited()) {
                    list.add(Direction.DOWN);
                }
            }

            if(list.isEmpty()){ //if no directions found (A.K.A. a dead end)
//                System.out.println("Dead End at (" + x + " , " + y + ")");
                s.pop(); //pop off the most recent coordinate
                temp = s.viewTop(); //look at the location last visited
                if(temp == null){ //debugging
                    System.out.println("Err1");
                    return;
                }
                else {
                    x = temp.getX(); //backtrack and return to movement testing
                    y = temp.getY();
                }
            }
            else{
                move = list.getRandomDirection(); //pick a random direction from available directions

                if(move == Direction.UP){ //move up
                    this.grid[x][y].openTopBorder(); //move up out of current tile
                    y--; // move up
                    s.push(x,y); //push new coordinate
                    this.grid[x][y].openBottomBorder(); //move into new tile from below
                    this.grid[x][y].visit(); //visit new tile
                    visitedTiles++; //increment
                }
                else if(move == Direction.DOWN){ //move down
                    this.grid[x][y].openBottomBorder();//exit down
                    y++; //move down
                    s.push(x,y);//push
                    this.grid[x][y].openTopBorder();//enter above
                    this.grid[x][y].visit();//visit
                    visitedTiles++; //increment
                }
                else if(move == Direction.LEFT){ //move left
                    this.grid[x][y].openLeftBorder();//exit left
                    x--;// move left
                    s.push(x,y);//push
                    this.grid[x][y].openRightBorder();//enter right
                    this.grid[x][y].visit();//visit
                    visitedTiles++;//increment
                }
                else if(move == Direction.RIGHT){ //move right
                    this.grid[x][y].openRightBorder();//exit right
                    x++;//move right
                    s.push(x,y);//push
                    this.grid[x][y].openLeftBorder();//enter left
                    this.grid[x][y].visit();//visit
                    visitedTiles++;//increment
                }
                else{
                    System.out.println("Err"); //debug
                }

            }

        }

        this.saveLevel(); //save to file (user prompt planned)
        this.unVisitAll(); //reset all tiles for pathfinding
//        this.makePath(); //
//        if(this.hintOn){
//            makeHint();
//        }



    }

    /*
    The following nine methods are part of what is referred to as the "Pathfinding" algorithms
     */
    public void hints(boolean h){ //toggle hint variable
        this.hintOn = h;
        this.makeHint();
    }

    /*
    This algorithm utilizes the same recursive back tracker algorithm to mark tiles as on the path
    to the final tile as hints
    The only difference is that instead of starting at 0,0 and running until all tiles have been touched
    it runs from the user's current position until the last tile is found
     */
    public void makeHint(){
        int x = this.xc;
        int y = this.yc;
        CoordStack path = new CoordStack();
        Coordinate temp;
        DirectionList list = new DirectionList();
        Direction move = null;
        this.grid[x][y].visit();

        boolean done = false;
        if(x != this.fx || y != this.fy) { //if on the last tile
            if (this.hintOn) {
                path.push(x, y);
                while (!done) {
                    if (this.grid[x][y].getLeft()) {
                        if (!this.grid[x - 1][y].visited()) {
                            list.add(Direction.LEFT);
                        }
                    }
                    if (this.grid[x][y].getRight()) {
                        if (!this.grid[x + 1][y].visited()) {
                            list.add(Direction.RIGHT);
                        }
                    }
                    if (this.grid[x][y].getUp()) {
                        if (!this.grid[x][y - 1].visited()) {
                            list.add(Direction.UP);
                        }
                    }
                    if (this.grid[x][y].getDown()) {
                        if (!this.grid[x][y + 1].visited()) {
                            list.add(Direction.DOWN);
                        }
                    }

                    if (list.isEmpty()) {
                        path.pop();
                        temp = path.viewTop();
                        if (temp == null) {
                            System.out.println("hint_err");
                            return;
                        } else {
                            x = temp.getX();
                            y = temp.getY();
                        }
                    } else {
                        move = list.getRandomDirection();

                        if (move == Direction.UP) {
                            y--;
                            path.push(x, y);
                            this.grid[x][y].visit();
                        } else if (move == Direction.DOWN) {
                            y++;
                            path.push(x, y);
                            this.grid[x][y].visit();
                        } else if (move == Direction.LEFT) {
                            x--;
                            path.push(x, y);
                            this.grid[x][y].visit();
                        } else if (move == Direction.RIGHT) {
                            x++;
                            path.push(x, y);
                            this.grid[x][y].visit();
                        } else {
                            System.out.println("Err");
                        }
                        if (x == this.fx && y == this.fy) {
                            done = true;
                        }
                    }
                }
            }
        }
        this.unVisitAll();
        this.unHintAll();
        this.paintHint(path);//send the found path to the painter

    }
    public void path(boolean p){ //toggle path
        this.pathOn = p;
        this.makePath();
    }
    /*
    This algorithm utilizes the same recursive back tracker algorithm to mark tiles as on the path
    to the starting tile as hints
    The only difference is that instead of starting at 0,0 and running until all tiles have been touched
    it runs from the user's current position until the starting tile is found
     */
    public void makePath(){
        int x = this.xc;
        int y = this.yc;
        CoordStack path = new CoordStack();
        Coordinate temp;
        DirectionList list = new DirectionList();
        Direction move = null;
        this.grid[x][y].visit();

        boolean done = false;
        if(x != 0 || y != 0) {
            if (this.pathOn) {
                path.push(x, y);
                while (!done) {
                    if (this.grid[x][y].getLeft()) {
                        if (!this.grid[x - 1][y].visited()) {
                            list.add(Direction.LEFT);
                        }
                    }
                    if (this.grid[x][y].getRight()) {
                        if (!this.grid[x + 1][y].visited()) {
                            list.add(Direction.RIGHT);
                        }
                    }
                    if (this.grid[x][y].getUp()) {
                        if (!this.grid[x][y - 1].visited()) {
                            list.add(Direction.UP);
                        }
                    }
                    if (this.grid[x][y].getDown()) {
                        if (!this.grid[x][y + 1].visited()) {
                            list.add(Direction.DOWN);
                        }
                    }

                    if (list.isEmpty()) {
                        path.pop();
                        temp = path.viewTop();
                        if (temp == null) {
                            System.out.println("path_err");
                            return;
                        } else {
                            x = temp.getX();
                            y = temp.getY();
                        }
                    } else {
                        move = list.getRandomDirection();

                        if (move == Direction.UP) {
                            y--;
                            path.push(x, y);
                            this.grid[x][y].visit();
                        } else if (move == Direction.DOWN) {
                            y++;
                            path.push(x, y);
                            this.grid[x][y].visit();
                        } else if (move == Direction.LEFT) {
                            x--;
                            path.push(x, y);
                            this.grid[x][y].visit();
                        } else if (move == Direction.RIGHT) {
                            x++;
                            path.push(x, y);
                            this.grid[x][y].visit();
                        } else {
                            System.out.println("Err");
                        }
                        if (x == 0 && y == 0) {
                            done = true;
                        }
                    }
                }
            }
        }
        this.unVisitAll();
        this.unTouchAll();
        this.paintPath(path); //send the found path to the painter

    }
    /*
    These next two methods parse through the passed path stacks and mark each tile as hint or path
    respectively
     */
    private void paintHint(CoordStack s){
        CoordStack t = new CoordStack();
        while(!s.isEmpty()){
            this.grid[s.viewTop().getX()][s.viewTop().getY()].hintTile();
            t.push(s.pop());
        }
        while(!t.isEmpty()){
            s.push(t.pop());
        }
//        this.repaint();
    }
    private void paintPath(CoordStack s){
        CoordStack t = new CoordStack();
        while(!s.isEmpty()){
            this.grid[s.viewTop().getX()][s.viewTop().getY()].touch();
            t.push(s.pop());
        }
        while(!t.isEmpty()){
            s.push(t.pop());
        }
//        this.repaint();
    }
    private void unVisitAll(){ //reset method for generation
        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j ++){
                this.grid[i][j].unVisit();
            }
        }
    }
    private void unHintAll(){//reset method for hints
        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j ++){
                this.grid[i][j].unHint();
            }
        }
    }
    private void unTouchAll(){//reset method for path
        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j ++){
                this.grid[i][j].unTouch();
            }
        }
    }
    /*
    This method saves the current aze to a file
     */
    private void saveLevel(){
        String temp = "";
        this.li = new LevelInfo();
        this.li.setSize(this.mazeSize);
        this.li.setName("NamedFile");

        /*
        This nested loop creates a four-bit string that denotes the shape and orientation
        of each tile in the maze.
        The bit pattern is "Up, Down, Left, Right"
        A one signifies the direction and a zero signifies the direction is closed
         */
        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j++){
                if(this.grid[j][i].getUp()){// if open up
                    temp += "1";
                }
                else{
                    temp += "0";
                }
                if(this.grid[j][i].getDown()){ //if open down
                    temp += "1";
                }
                else{
                    temp += "0";
                }
                if(this.grid[j][i].getLeft()){//if open left
                    temp += "1";
                }
                else{
                    temp += "0";
                }
                if(this.grid[j][i].getRight()){//if open right
                    temp += "1";
                }
                else{
                    temp += "0";
                }

                this.li.addString(temp, i, j); //add string and coordinate to the LevelInfo object
                temp = ""; //reset string
            }
        }


//        System.out.println(this.li.getName());
//        System.out.println(this.li.getSize());
//        for(int i = 0; i < this.mazeSize; i++) {
//            for (int j = 0; j < this.mazeSize; j++) {
//                System.out.print(this.li.getTile(i, j));
//                System.out.print(", ");
//            }
//            System.out.println();
//        }
        lw.nextSave(this.li); //call save method from LevelWriter object
    }

    /*
    The other way that a maze can be generated is by using a saved file
    This uses the LevelInfo "li" member that was initialized in the second overloaded constructor
     */
    public void createMazeFromFile(){
        String t;
        //for each coordinate
        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j++){
                t = this.li.getTile(i, j); //get bit string
//                System.out.println(t);
                checkTileString(t, i, j);
            }
        }
    }
    /*
    This method moves trough a bit string and assigns boolean values to tiles
     */
    private void checkTileString(String t, int x, int y){
        if(t.charAt(0) == '1'){
            this.grid[x][y].openTopBorder();
        }
        if(t.charAt(1) == '1'){
            this.grid[x][y].openBottomBorder();
        }
        if(t.charAt(2) == '1'){
            this.grid[x][y].openLeftBorder();
        }
        if(t.charAt(3) == '1'){
            this.grid[x][y].openRightBorder();
        }
    }

    public void setMazeSize(int m){
        this.mazeSize = m;
    }

    public int getMazeSize(){
        return this.mazeSize;
    }

    //places player at given coordinates
    public void placePlayer(int x, int y){
        this.grid[x][y].setToPlayer();
//        System.out.println("(" + xc + ", " + yc + ")");
        this.makePath();
        if(this.hintOn){
            this.makeHint();
        }
    }
    //removes player from a tile. Usually used before placePlayer()
    public void removePlayer(int x, int y){
        this.grid[x][y].setToSpace();
    }
    //moves the player coordinates up and repaints the player
    public void moveUp(){
        if(!this.suspended){
            if(this.yc-1 >= 0 && this.grid[xc][yc].getUp()){
                this.removePlayer(this.xc, this.yc);
                this.yc--;
                this.placePlayer(this.xc, this.yc);

            }
            else{
//            System.out.println("Out of Bounds");
            }
        }
    }
    //moves the player coordinates down and repaints the player
    public void moveDown(){
        if(!this.suspended) {
            if (this.yc + 1 < this.mazeSize && this.grid[xc][yc].getDown()) {
                this.removePlayer(this.xc, this.yc);
                this.yc++;
                this.placePlayer(this.xc, this.yc);

            } else {
//            System.out.println("Out of Bounds");
            }
        }
    }
    //moves the player coordinates left and repaints the player
    public void moveLeft(){
        if(!this.suspended) {
            if (this.xc - 1 >= 0 && this.grid[xc][yc].getLeft()) {
                this.removePlayer(this.xc, this.yc);
                this.xc--;
                this.placePlayer(this.xc, this.yc);
            }
        }
    }
    //moves the player coordinates right and repaints the player
    public void moveRight(){
        if(!this.suspended) {
            if (this.xc + 1 < this.mazeSize && this.grid[xc][yc].getRight()) {
                this.removePlayer(this.xc, this.yc);
                this.xc++;
                this.placePlayer(this.xc, this.yc);
            }
        }
    }
    //add method for GridBagLayout
    private void addStuff(Container c, Component l, GridBagConstraints gbc, int x, int y, int wx, int wy){
        gbc.gridx = x;
        gbc.gridy = y;

        gbc.weightx = wx;
        gbc.weighty = wy;
        gbc.fill = GridBagConstraints.BOTH;

        c.add(l, gbc);
    }
    //victory condition
    public boolean winTest(){
        if(this.xc == this.fx && this.yc == this.fy){
            return true;
        }
        return false;
    }
    //Paused toggle
    public void toggleSuspended(){
        if(this.suspended){
            this.suspended = false;
        }
        else{
            this.suspended = true;
        }
    }
    //returns isSuspended
    public boolean isSuspended(){
        return this.suspended;
    }
    //moves player to 0,0
    public void reset(){
        this.removePlayer(this.xc, this.yc);
        this.xc = 0;
        this.yc = 0;
        this.placePlayer(this.xc, this.yc);
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {

        // WASD
        if(e.getKeyCode() == KeyEvent.VK_W){
            this.moveUp();
            //this.winTest();
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            this.moveLeft();
            //this.winTest();
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            this.moveDown();
            //this.winTest();
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            this.moveRight();
            //this.winTest();
        }

        // Arrow keys
        if(e.getKeyCode() == KeyEvent.VK_UP){
            this.moveUp();
           // this.winTest();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            this.moveLeft();
            //this.winTest();
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            this.moveDown();
            //this.winTest();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            this.moveRight();
            //this.winTest();
        }

        //reset
        if(e.getKeyCode() == KeyEvent.VK_R){
            this.reset();
        }

        //pause
//        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
//            this.toggleSuspended();
//        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public boolean getPg() {
        return this.pg;
    }
}
