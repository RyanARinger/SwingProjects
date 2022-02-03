package se370_team3_projectv1;

public class ProcedureMazeFactory implements AbstractMazeFactory{

    @Override
    public SquarePanel[][] createMaze(int size){
        SquarePanel[][] maze = new SquarePanel[size][size];

        return maze;
    }
}
