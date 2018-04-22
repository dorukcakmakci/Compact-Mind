package Solver;

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
