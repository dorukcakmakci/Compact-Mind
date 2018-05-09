package Parser;

import java.util.ArrayList;
//CLASS THAT STORES CURRENT ANSWER STATE
public class Answers {
    private static final int RECT_SIZE = 5;
    private HTMLProcessor processor;
    private ArrayList<PuzzleWord> answers;

    public Answers(HTMLProcessor processor){
        this.processor = processor;
        this.answers = new ArrayList<PuzzleWord>();
        initAnswers();
    }
    public Answers(HTMLProcessor processor, boolean isTrue){
        this.processor = processor;
        this.answers = new ArrayList<PuzzleWord>();
        initAnswersTrue();
    }
    public void updateAnswers(ArrayList<PuzzleWord> input){
        answers = input;
    }
    public void removeAnswer(int ansNo){
        answers.get(ansNo).removeAll();
    }
    public void updateAnswer(String input, int qNo){
        this.getAnswer(qNo).updateAnswer(input);
    }
    public boolean isAnswerEmpty(int k){
        boolean isEmpty = false;
        for(int i = 0; i < answers.get(k).getSize(); i++){
                isEmpty = answers.get(k).getCharAt(i).equals("");
        }
        return isEmpty;
    }
    private void initAnswers(){
        //ACROSS
        for(int i = 0; i < processor.hints[0].size();i++){
            String questionNo = processor.hints[0].get(i).substring(0,1);
            String hint = processor.hints[0].get(i).substring(2,processor.hints[0].get(i).length());
            int startX = 0;
            int startY = 0;
            int size = 0;
            while(!processor.puzzle[startX][startY].getQuestionNo().equals(questionNo)) {
                if (startX == 4) {
                    startX = 0;
                    startY++;
                }
                else
                    startX++;

            }
            int tempX = startX;
            while(tempX < 5){
                if(processor.puzzle[tempX][startY].getCurrentLetter().equals("-1"))
                    break;
                size++;
                tempX++;
            }
            PuzzleWord temp = new PuzzleWord(0,startX,startY,size,Integer.parseInt(questionNo),hint);
            answers.add(temp);
            System.out.println("Direction across : " + startX + " ~ " + startY + " Size : "+ size + " Hint: "+hint);
        }
        //DOWN
        for(int i = 0; i < processor.hints[1].size();i++){
            String questionNo = processor.hints[1].get(i).substring(0,1);
            String hint = processor.hints[1].get(i).substring(2,processor.hints[1].get(i).length());
            int startX = 0;
            int startY = 0;
            int size = 0;
            while(!processor.puzzle[startX][startY].getQuestionNo().equals(questionNo)){
                if(startX == 4){
                    startX = 0;
                    startY++;
                }
                else
                    startX++;
            }
            int tempY = startY;
            while(tempY < 5){
                if(processor.puzzle[startX][tempY].getCurrentLetter().equals("-1"))
                    break;
                size++;
                tempY++;
            }
            PuzzleWord temp = new PuzzleWord(1,startX,startY,size,Integer.parseInt(questionNo),hint);
            answers.add(temp);
            System.out.println("Direction down : " + startX + " ~ " + startY + " Size : "+ size + " Hint: "+hint);
        }
    }
    private void initAnswersTrue(){
        //ACROSS
        for(int i = 0; i < processor.hints[0].size();i++){
            String questionNo = processor.hints[0].get(i).substring(0,1);
            String hint = processor.hints[0].get(i).substring(2,processor.hints[0].get(i).length());
            int startX = 0;
            int startY = 0;
            int size = 0;
            String text = "";
            while(!processor.puzzle[startX][startY].getQuestionNo().equals(questionNo)) {
                if (startX == 4) {
                    startX = 0;
                    startY++;
                }
                else
                    startX++;

            }
            int tempX = startX;
            while(tempX < 5){
                if(processor.puzzle[tempX][startY].getCurrentLetter().equals("-1"))
                    break;
                text = text + processor.puzzle[tempX][startY].getCurrentLetter();
                size++;
                tempX++;
            }
            PuzzleWord temp = new PuzzleWord(0,startX,startY,size,Integer.parseInt(questionNo),hint);
            temp.updateAnswer(text);
            answers.add(temp);
            System.out.println(temp.getAnswer());
        }
        //DOWN
        for(int i = 0; i < processor.hints[1].size();i++){
            String questionNo = processor.hints[1].get(i).substring(0,1);
            String hint = processor.hints[1].get(i).substring(2,processor.hints[1].get(i).length());
            int startX = 0;
            int startY = 0;
            int size = 0;
            String text = "";
            while(!processor.puzzle[startX][startY].getQuestionNo().equals(questionNo)){
                if(startX == 4){
                    startX = 0;
                    startY++;
                }
                else
                    startX++;
            }
            int tempY = startY;
            while(tempY < 5){
                if(processor.puzzle[startX][tempY].getCurrentLetter().equals("-1"))
                    break;
                text = text + processor.puzzle[startX][tempY].getCurrentLetter();
                size++;
                tempY++;
            }
            PuzzleWord temp = new PuzzleWord(0,startX,startY,size,Integer.parseInt(questionNo),hint);
            temp.updateAnswer(text);
            answers.add(temp);
            System.out.println(temp.getAnswer());
        }
    }

    public ArrayList<PuzzleWord> getAnswers() {
        return answers;
    }
    public PuzzleWord getAnswer(int answerNo){
        return answers.get(answerNo);
    }
    public int getSize(){
        return answers.size();
    }
    public void printAnswers(){
        for(int i = 0; i < answers.size(); i++){
            System.out.print(answers.get(i).getAnswer() + ",");
        }
        System.out.println();
    }
}
