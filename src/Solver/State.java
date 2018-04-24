package Solver;

import Parser.Answers;
import Parser.PuzzleWord;

public class State implements Comparable{

    private State oldState = null;
    public static final int RECT_WIDTH =5;
    private int filledSlotCount;
    private Answers answers;
    private double score;


    public State(Answers answers){
        this.answers = answers;
        this.score = 0;
    }
    public Answers getAnswers(){
        return answers;
    }

    public int getFilledSlotCount() {
        return filledSlotCount;
    }

    public void setFilledSlotCount(int filledSlotCount) {
        this.filledSlotCount = filledSlotCount;
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

    public void setOldState(State oldState){
        this.oldState = oldState;
    }
    public State getOldState(){
        return oldState;
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