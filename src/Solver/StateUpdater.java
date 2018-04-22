package Solver;
import Parser.Answers;

public class StateUpdater {
    StateHeap maxHeap;
    public StateUpdater(int maxSize){
        maxHeap = new StateHeap(1000);
    }

    public boolean checkState(Answers answers){
        boolean flag = true;
        State stateToAdd = new State(0); //
        for(int i = 0; i < answers.getAnswers().size(); i++){
            for(int j = 0; j < answers.getAnswer(i).getSize(); j++){
                if(answers.getAnswer(i).getDirection() == 0 && stateToAdd.getIndex(answers.getAnswer(i).getColNo()+j,answers.getAnswer(i).getRowNo()) == '?'){
                    stateToAdd.setIndex(answers.getAnswer(i).getColNo()+j, answers.getAnswer(i).getRowNo(),answers.getAnswer(i).getAnswerLetter(j));
                }
                else if(answers.getAnswer(i).getDirection() == 1 && stateToAdd.getIndex(answers.getAnswer(i).getColNo(),answers.getAnswer(i).getRowNo()+j) == '?'){
                    stateToAdd.setIndex(answers.getAnswer(i).getColNo()+j,answers.getAnswer(i).getRowNo(),answers.getAnswer(i).getAnswerLetter(j));
                }
                else if(answers.getAnswer(i).getDirection() == 0 && stateToAdd.getIndex(answers.getAnswer(i).getColNo()+j,answers.getAnswer(i).getRowNo()) != answers.getAnswer(i).getAnswerLetter(j)) {
                    flag = false;
                }
                else if(answers.getAnswer(i).getDirection() == 1 && stateToAdd.getIndex(answers.getAnswer(i).getColNo(),answers.getAnswer(i).getRowNo()+j) != answers.getAnswer(i).getAnswerLetter(j)) {
                    flag = false;
                }
                if(!flag)
                    break;
                maxHeap.insert(stateToAdd);
                maxHeap.maxHeap();
            }
        }

        return flag;
    }

    public void addState(State input){

    }
}

