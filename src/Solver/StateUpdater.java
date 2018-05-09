package Solver;

import Parser.Answers;
import UI.PuzzlePanel;
//CLASS THAT STORES MAX STATE AND CHECKS IF A STATE IS VALID OR NOT
public class StateUpdater {
    Answers currentAnswers;
    private Answers maxAnswers;
    PuzzlePanel panel;
    int score = 0;
    public StateUpdater(State state, Answers answers, Answers maxAnswer, PuzzlePanel panel){
        currentAnswers = answers;
        this.panel = panel;
        maxAnswers = state.getAnswers();
        checkState(state.getAnswers(), maxAnswer);
    }

    //ALGORITHM FOR CONSTRAINT SATISFACTION PROBLEM
    public boolean checkState(Answers answers, Answers maxAnswer){
        int[] found = new int[10];
        for(int i = 0; i < 10; i++) {
            found[i] = 0;
        }
        boolean flag = true;
        String [][] puzzle = new String[5][5];
        //INITIALIZING THE EMPTY PUZZLE
        for(int i = 0; i < puzzle.length; i++){
            for(int j = 0; j < puzzle[0].length; j++)
                puzzle[i][j] = "";
        }
        for(int i = 0; i < answers.getAnswers().size(); i++){
            for(int j = 0; j < answers.getAnswer(i).getSize(); j++){
                //IF THE PUZZLE SLOT IS EMPTY, PLACE THE CORRESPONDING LETTER
                if(answers.getAnswer(i).getDirection() == 0 && puzzle[answers.getAnswer(i).getColNo()+j][answers.getAnswer(i).getRowNo()] == ""){
                    puzzle[answers.getAnswer(i).getColNo()+j][answers.getAnswer(i).getRowNo()] = answers.getAnswer(i).getCharAt(j);
                    continue;
                }
                //IF THE PUZZLE SLOT IS EMPTY, PLACE THE CORRESPONDING LETTER
                if(answers.getAnswer(i).getDirection() == 1 && puzzle[answers.getAnswer(i).getColNo()][answers.getAnswer(i).getRowNo()+j] == ""){
                    puzzle[answers.getAnswer(i).getColNo()][answers.getAnswer(i).getRowNo()+j] = answers.getAnswer(i).getCharAt(j);
                    continue;
                }
                //IF A CONFLICT OCCURS, RETURN INVALID
                if(answers.getAnswer(i).getCharAt(j) != "" && answers.getAnswer(i).getDirection() == 0 && !puzzle[answers.getAnswer(i).getColNo()+j][answers.getAnswer(i).getRowNo()].equals(answers.getAnswer(i).getCharAt(j))) {
                    flag = false;
                }
                //IF A CONFLICT OCCURS, RETURN INVALID
                if(answers.getAnswer(i).getCharAt(j) != "" && answers.getAnswer(i).getDirection() == 1 && !puzzle[answers.getAnswer(i).getColNo()][answers.getAnswer(i).getRowNo()+j].equals(answers.getAnswer(i).getCharAt(j))) {
                    flag = false;
                }
                if(!flag) {
                    break;
                }
            }
            if(!flag) {
                break;
            }
        }
        /*
        for(int i = 0; i < answers.getAnswers().size(); i++){
            found[i] = 0;
            if(!answers.isAnswerEmpty(i)){
                found[i] = 1;
            }
        }
        int acrossFound = 0;
        int downFound = 0;
        for(int i = 0; i < answers.getAnswers().size(); i++){
            if(found[i] == 1 && i < 5){
                acrossFound++;
            }
            if(found[i] == 1 && i >= 5){
                downFound++;
            }
        }
        int currentScore = (downFound * 3) + acrossFound;
        if(currentScore >= score) {
            score = currentScore;
            for(int i = 0; i < answers.getAnswers().size(); i++){
                maxAnswer.updateAnswer(answers.getAnswer(i).getAnswer(),i);
                //maxAnswer.printAnswers();
            }
        }
        */

        for(int i = 0; i < answers.getAnswers().size(); i++){
            if(answers.getAnswer(i).getAnswer().equals(currentAnswers.getAnswer(i).getAnswer()) && found[i] == 0){
                found[i] = 1;
                panel.addLog("Max state is updated");
                maxAnswer.updateAnswer(answers.getAnswer(i).getAnswer(),i);
                //maxAnswer.printAnswers();
            }
        }

        return flag;
    }

    public Answers getMaxAnswers() {
        return maxAnswers;
    }
}

