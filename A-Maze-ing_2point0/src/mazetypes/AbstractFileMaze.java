package mazetypes;


import project.*;


public abstract class AbstractFileMaze extends AbstractMaze{
    private String filename;
    private final LevelReader LR = new LevelReader();

    public AbstractFileMaze(String filename, int mazeSize){
        super(mazeSize);
        this.filename = filename;
        createMaze();
        setUp();

    }

    @Override
    public void createMaze() {
        String t;
        LevelInfo li = LR.nextFile(filename);
        //for each coordinate
        for(int i = 0; i < getMazeSize(); i++){
            for(int j = 0; j < getMazeSize(); j++){
                t = li.getTile(i, j); //get bit string
//                System.out.println(t);
                checkTileString(t, i, j);
            }
        }
    }

    private void checkTileString(String t, int x, int y){
        if(t.charAt(0) == '1'){
            this.getGrid()[x][y].openTopBorder();
        }
        if(t.charAt(1) == '1'){
            this.getGrid()[x][y].openBottomBorder();
        }
        if(t.charAt(2) == '1'){
            this.getGrid()[x][y].openLeftBorder();
        }
        if(t.charAt(3) == '1'){
            this.getGrid()[x][y].openRightBorder();
        }
    }
}
