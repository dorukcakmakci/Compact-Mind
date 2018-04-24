package Parser;

import java.util.ArrayList;

public class Answers {
    private static final int RECT_SIZE = 5;
    private HTMLProcessor processor;
    private ArrayList<PuzzleWord> answers;

    public Answers(HTMLProcessor processor){
        this.processor = processor;
        this.answers = new ArrayList<PuzzleWord>();
        initAnswers();
    }
    public void updateAnswers(String s0, String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8,String s9){
        this.getAnswer(0).updateAnswer(s0);
        this.getAnswer(1).updateAnswer(s1);
        this.getAnswer(2).updateAnswer(s2);
        this.getAnswer(3).updateAnswer(s3);
        this.getAnswer(4).updateAnswer(s4);
        this.getAnswer(5).updateAnswer(s5);
        this.getAnswer(6).updateAnswer(s6);
        this.getAnswer(7).updateAnswer(s7);
        this.getAnswer(8).updateAnswer(s8);
        this.getAnswer(9).updateAnswer(s9);
    }
    public void updateAnswer(String input, int qNo){
        this.getAnswer(qNo).updateAnswer(input);
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
    public ArrayList<PuzzleWord> getAnswers() {
        return answers;
    }
    public PuzzleWord getAnswer(int answerNo){
        return answers.get(answerNo);
    }
    public int getSize(){
        return answers.size();
    }
}
