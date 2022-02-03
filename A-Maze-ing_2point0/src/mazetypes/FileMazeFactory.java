package mazetypes;

public class FileMazeFactory implements MazeFactoryIF{
    private String filename;
    public FileMazeFactory(String filename){
        this.filename = filename;
    }
    @Override
    public AbstractMaze createEasyMaze() {
        return null;
    }

    @Override
    public AbstractMaze createMediumMaze() {
        return null;
    }

    @Override
    public AbstractMaze createHardMaze() {
        return null;
    }
}
