package mazetypes;

import gamestatemanager.Condition;
import gamestatemanager.MazeState;
import gamestatemanager.StateInterface;
import observerstructure.KeyObserver;
import project.*;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;


public abstract class AbstractMaze extends JPanel {
    private boolean hintOn;
    private boolean suspended;
    private boolean pathOn;
    private int yc;
    private int xc;
    private int mazeSize;
    private SquarePanel [][] grid;
    private final int PANELSIZE = 1000;
    private GridBagConstraints gbc;
    private int fc;
    private StateInterface containerState;

    public SquarePanel[][] getGrid(){
        return this.grid;
    }
    public AbstractMaze(){
        super();
        this.xc = 0;
        this.yc = 0;
        this.mazeSize = 0;
        this.containerState = null;
    }
    public AbstractMaze(int mazeSize){
        super();
        hintOn = false;
        pathOn = false;
        suspended = false;
        this.containerState = null;
        this.grid = new SquarePanel[mazeSize][mazeSize];
        this.mazeSize = mazeSize;

        this.fc = this.mazeSize-1; //win location as bottom right tile
        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j++) {

                this.grid[i][j] = new SquarePanel((this.PANELSIZE / this.mazeSize) - 1);
                if(i == this.fc && j == this.fc){ // if final tile, mark as destination
                    this.grid[i][j].setToGate();
                }
                else { //otherwise make blank
                    this.grid[i][j].setToSpace();
                }
            }
        }

    }
    public void setState(StateInterface si){
        this.containerState = si;
    }
    public void setUp(){
        this.xc = 0; //starting position
        this.yc = 0;
//        this.grid = new SquarePanel[this.mazeSize][this.mazeSize]; // initialize the maze
        this.gbc = new GridBagConstraints();

        this.suspended = false; //game is not paused


//        this.pp = new PlayerPanel(this.mazeSize, this.PANELSIZE);

        this.setLayout(new GridBagLayout());

        //add all tiles to the JPanel using GridBagLayout
        for(int i = 0; i < this.mazeSize; i++){
            for(int j = 0; j < this.mazeSize; j++){
//                System.out.println("( " + i + " " + j + " )");
                this.addStuff(this, this.grid[i][j], this.gbc, i, j, 1, 1);
            }
        }
//        o = new KeyObserver(this);
//        addKeyListener(o);
        this.placePlayer(this.xc, this.yc);
//        this.add(this.pp, JLayeredPane.PALETTE_LAYER);
        this.setVisible(true);
    }
    public void makeHint(int xc, int yc) {
        int x = xc;
        int y = yc;
        int fy = mazeSize - 1;
        int fx = mazeSize - 1;
        CoordStack path = new CoordStack();
        Coordinate temp;
        DirectionList list = new DirectionList();
        Direction move = null;
        grid[x][y].visit();

        boolean done = false;
        if(x != fx || y != fy) { //if on the last tile
            if (hintOn) {
                path.push(x, y);
                while (!done) {
                    if (grid[x][y].getLeft()) {
                        if (!grid[x - 1][y].visited()) {
                            list.add(Direction.LEFT);
                        }
                    }
                    if (grid[x][y].getRight()) {
                        if (!grid[x + 1][y].visited()) {
                            list.add(Direction.RIGHT);
                        }
                    }
                    if (grid[x][y].getUp()) {
                        if (!grid[x][y - 1].visited()) {
                            list.add(Direction.UP);
                        }
                    }
                    if (grid[x][y].getDown()) {
                        if (!grid[x][y + 1].visited()) {
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
                            grid[x][y].visit();
                        } else if (move == Direction.DOWN) {
                            y++;
                            path.push(x, y);
                            grid[x][y].visit();
                        } else if (move == Direction.LEFT) {
                            x--;
                            path.push(x, y);
                            grid[x][y].visit();
                        } else if (move == Direction.RIGHT) {
                            x++;
                            path.push(x, y);
                            grid[x][y].visit();
                        } else {
                            System.out.println("Err");
                        }
                        if (x == fx && y == fy) {
                            done = true;
                        }
                    }
                }
            }
        }
        unVisitAll(grid, mazeSize);
        unHintAll(grid, mazeSize);
        paintHint(path, grid);//send the found path to the painter
    }
    public void toggleHint(){
        if(hintOn){
            hintOn = false;
            repaint();
        }
        else{
            hintOn = true;
            repaint();
        }
    }
    public void toggleSuspended(){
        if(suspended){
            suspended = false;
            this.containerState.setSelection(Condition.EXIT);
            this.containerState.exit();
        }
        else{
            suspended = true;
            this.containerState.setSelection(Condition.PAUSE);
            this.containerState.exit();
        }
    }
    public void togglePath(){
        if(pathOn){
            pathOn = false;
        }
        else{
            pathOn = true;
        }
    }
    public void makePath(int xc, int yc) {

    }

    private void unVisitAll(SquarePanel[][] grid, int mazeSize){ //reset method for generation
        for(int i = 0; i < mazeSize; i++){
            for(int j = 0; j < mazeSize; j ++){
                grid[i][j].unVisit();
            }
        }
    }
    private void unHintAll(SquarePanel[][] grid, int mazeSize){//reset method for hints
        for(int i = 0; i < mazeSize; i++){
            for(int j = 0; j < mazeSize; j ++){
                grid[i][j].unHint();
            }
        }
    }
    private void unTouchAll(SquarePanel[][] grid, int mazeSize){//reset method for path
        for(int i = 0; i < mazeSize; i++){
            for(int j = 0; j < mazeSize; j ++){
                grid[i][j].unTouch();
            }
        }
    }

    private void paintHint(CoordStack s, SquarePanel[][] grid){
        CoordStack t = new CoordStack();
        while(!s.isEmpty()){
            grid[s.viewTop().getX()][s.viewTop().getY()].hintTile();
            t.push(s.pop());
        }
        while(!t.isEmpty()){
            s.push(t.pop());
        }
//        this.repaint();
    }

    public abstract void createMaze();

    public void setKeyListener(KeyListener kl){
        this.addKeyListener(kl);
    }
    public void placePlayer(int x, int y){
        grid[x][y].setToPlayer();
//        System.out.println("(" + xc + ", " + yc + ")");
        this.makePath( x, y);
        if(this.hintOn){
            this.makeHint(x, y);
        }
    }
    //removes player from a tile. Usually used before placePlayer()
    public void removePlayer(int x, int y){
        grid[x][y].setToSpace();
    }
    //moves the player coordinates up and repaints the player
    public void moveUp(){
        if(!this.suspended){
            if(this.yc-1 >= 0 && grid[xc][yc].getUp()){
                this.removePlayer(xc, yc);
                this.yc--;
                this.placePlayer(xc, yc);

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
    private void addStuff(Container c, Component l, GridBagConstraints gbc, int x, int y, int wx, int wy){
        gbc.gridx = x;
        gbc.gridy = y;

        gbc.weightx = wx;
        gbc.weighty = wy;
        gbc.fill = GridBagConstraints.BOTH;

        c.add(l, gbc);
    }
    public int getMazeSize(){
        return this.mazeSize;
    }

    public void winTest(){
        if(xc == fc && yc == fc){
            if(containerState != null){
                containerState.setSelection(Condition.COMPLETE);
                containerState.exit();
            }
        }
    }
//    @Override
//    public void keyTyped(KeyEvent e) {
//
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        // WASD
//        if(e.getKeyCode() == KeyEvent.VK_W){
//            this.moveUp();
//            this.winTest();
//        }
//        if(e.getKeyCode() == KeyEvent.VK_A){
//            this.moveLeft();
//            this.winTest();
//        }
//        if(e.getKeyCode() == KeyEvent.VK_S){
//            this.moveDown();
//            this.winTest();
//        }
//        if(e.getKeyCode() == KeyEvent.VK_D){
//            this.moveRight();
//            this.winTest();
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//
//    }

    public boolean getHint(){
        return hintOn;
    }
    public boolean getSuspended(){
        return suspended;
    }
    public boolean getPath(){
        return pathOn;
    }
}
