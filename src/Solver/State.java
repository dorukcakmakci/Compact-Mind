package Solver;

public class State {
    public static final int RECT_WIDTH = 5;
    private char[][] puzzle;
    private double score;
    public State(int score){
        this.score = score;
        this.puzzle = new char[RECT_WIDTH][RECT_WIDTH];
        for(int i = 0; i < RECT_WIDTH;i++)
            for(int j = 0; j < RECT_WIDTH;j++)
                puzzle[i][j] = '?';
    }
    public char getIndex(int i, int j){
        return puzzle[i][j];
    }
    public void setIndex(int i, int j, char input){
        puzzle[i][j] = input;
    }
    public char[][] getState(){
        return puzzle;
    }
    public void setState(char[][] state){
        this.puzzle = state;
    }
    public double getScore(){
        return score;
    }
    public void setScore(double score){
        this.score = score;
    }
}
