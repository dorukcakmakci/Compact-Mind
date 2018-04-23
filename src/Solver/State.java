package Solver;

import Parser.Answers;
import Parser.PuzzleWord;

public class State implements Comparable{

    private State prevState;
    public static final int RECT_WIDTH =5;
    private char[][] puzzle;
    private int filledSlotCount;
    private Answers answers;
    private double score;


    public State(double score){
        filledSlotCount = 0;
        this.score = score;
        this.puzzle = new char[RECT_WIDTH][RECT_WIDTH];
        for(int i = 0; i < RECT_WIDTH; i++){
            for(int j = 0; j < RECT_WIDTH; j++){
                puzzle[i][j] = '?';
            }
        }
    }



    /*public State(State other){
        statePath = new ArrayList<State>();
        for(int i = 0; i < other.getStatePath().size(); i++){
            statePath.add(other.getStatePath().get(i));
        }
        this.puzzle = new char[RECT_WIDTH][RECT_WIDTH];
        for(int i = 0; i < RECT_WIDTH; i++){
            for(int j = 0; j < RECT_WIDTH; j++){
                puzzle[i][j] = other.;
            }
        }
        score = other.score;
        filledSlotCount = other.filledSlotCount;
        backtrackCount = other.backtrackCount;

    }*/

    public State(Answers answers){
        this.answers = answers;
        setScore(0);
    }


    public int getFilledSlotCount() {
        return filledSlotCount;
    }

    public void setFilledSlotCount(int filledSlotCount) {
        this.filledSlotCount = filledSlotCount;
    }


    public char getIndex(int i, int j){
        return puzzle[i][j];
    }

    public char[][] getState() {
        return puzzle;
    }

    public PuzzleWord getAnswer(int i) {
        return answers.getAnswer(i);
    }

    public int getAnswersSize(){
        return answers.getSize();
    }

    public double getScore(){
        return score;
    }

    public void setScore(double score){
        this.score = score;
    }

    @Override
    public int compareTo(Object o1){
        State s1 = (State)o1;
        if(this.getScore() > s1.getScore())
            return 1;
        if(this.getScore() == s1.getScore())
            return 0;
        return -1;
    }
}