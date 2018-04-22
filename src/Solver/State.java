package Solver;

import java.util.ArrayList;

public class State {

    private ArrayList<Word>[] results;
    public static final int RECT_WIDTH =5;
    private char[][] puzzle;
    private int backtrackCount;
    private int filledSlotCount;
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

    public ArrayList<Word>[] getResults() {
        return results;
    }

    public void setResults(ArrayList<Word>[] results) {
        this.results = results;
    }

    public int getBacktrackCount() {
        return backtrackCount;
    }

    public void setBacktrackCount(int backtrackCount) {
        this.backtrackCount = backtrackCount;
    }

    public int getFilledSlotCount() {
        return filledSlotCount;
    }

    public void setFilledSlotCount(int filledSlotCount) {
        this.filledSlotCount = filledSlotCount;
    }

    public State(){
        score = 0;
        filledSlotCount = 0;
        backtrackCount = 0;
        puzzle = new char[RECT_WIDTH][RECT_WIDTH];
        results = new ArrayList[10];
    }
    public char getIndex(int i, int j){
        return puzzle[i][j];
    }

    public char[][] getState(){
        return puzzle;
import Parser.Answers;
import Parser.PuzzleWord;

public class State implements Comparable{
    public static final int RECT_WIDTH = 5;
    private Answers answers;
    private double score;
    public State(Answers answers){
        this.answers = answers;
        setScore();
    }

    public PuzzleWord getAnswer(int i) {
        return answers.getAnswer(i);
    }
    public double getScore(){
        return score;
    }
    public void setScore(){
        ///TO-DO
        this.score = 0;
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
