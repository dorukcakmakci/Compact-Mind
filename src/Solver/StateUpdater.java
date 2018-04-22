package Solver;

import Parser.Answers;

import java.util.PriorityQueue;

public class StateUpdater {
    PriorityQueue stateQueue;
    State maxState;
    public StateUpdater(){
       stateQueue = new PriorityQueue();
    }


    public boolean checkState(Answers answers){
        boolean flag = true;
        State stateToAdd = new State(answers); //
        char [][] puzzle = new char[5][5];
        for(int i = 0; i < puzzle.length; i++){
            for(int j = 0; j < puzzle[0].length; j++)
                puzzle[i][j] = '?';
        }
        for(int i = 0; i < answers.getAnswers().size(); i++){
            for(int j = 0; j < answers.getAnswer(i).getSize(); j++){
                if(answers.getAnswer(i).getDirection() == 0 && puzzle[answers.getAnswer(i).getColNo()+j][answers.getAnswer(i).getRowNo()] == '?'){
                    puzzle[answers.getAnswer(i).getColNo()+j][answers.getAnswer(i).getRowNo()] = answers.getAnswer(i).getAnswerLetter(j);
                }
                else if(answers.getAnswer(i).getDirection() == 1 && puzzle[answers.getAnswer(i).getColNo()][answers.getAnswer(i).getRowNo()+j] == '?'){
                    puzzle[answers.getAnswer(i).getColNo()+j][answers.getAnswer(i).getRowNo()] = answers.getAnswer(i).getAnswerLetter(j);
                }
                else if(answers.getAnswer(i).getDirection() == 0 && puzzle[answers.getAnswer(i).getColNo()+j][answers.getAnswer(i).getRowNo()] != answers.getAnswer(i).getAnswerLetter(j)) {
                    flag = false;
                }
                else if(answers.getAnswer(i).getDirection() == 1 && puzzle[answers.getAnswer(i).getColNo()][answers.getAnswer(i).getRowNo()+j] != answers.getAnswer(i).getAnswerLetter(j)) {
                    flag = false;
                }
                if(!flag) {
                    break;
                }
            }
        }
        //stateQueue.add(stateToAdd);
        if(maxState == null)
            maxState = stateToAdd;
        else{
            if(maxState.getScore() < stateToAdd.getScore())
                maxState = stateToAdd;
        }
        return flag;
    }
}

