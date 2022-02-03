package mazetypes;

public class ProcedureMazeFactory implements MazeFactoryIF{
    @Override
    public AbstractMaze createEasyMaze() {
        return new EasyProcedureMaze();
    }

    @Override
    public AbstractMaze createMediumMaze() {
        return new MediumProcedureMaze();
    }

    @Override
    public AbstractMaze createHardMaze() {
        return new HardProcedureMaze();
    }
}
