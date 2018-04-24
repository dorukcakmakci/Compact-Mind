package Solver;

import Parser.Answers;
import UI.PuzzlePanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PuzzleSolverWithTFIDF {

    private PuzzlePanel panel;
    private TFIDF[] tfidf;
    private int slotAmount;
    private State curState;
    private boolean isDataCallDone;
    private StateUpdater updater;

    private ArrayList<String>[] datamuseResults;
    private ArrayList<String>[] googleResults;
    //private ArrayList<String>[] abbreviationResults;
    private ArrayList<String>[] movieResults;
    private ArrayList<ScoredString>[] scores;
    public PuzzleSolverWithTFIDF(String[][] puzzle , PuzzlePanel panel){

        this.panel = panel;

        isDataCallDone = false;
        slotAmount = panel.getAnswers().getAnswers().size();

        curState = new State(panel.getAnswers());
        curState.setOldState(null);
        updater = new StateUpdater(curState);
        datamuseResults = new ArrayList[slotAmount];
        googleResults = new ArrayList[slotAmount];
        movieResults = new ArrayList[slotAmount];
        //abbreviationResults = new ArrayList[curState.getAnswersSize()];
        scores = new ArrayList[slotAmount];

    }


    private void init() throws IOException {

        tfidf = new TFIDF[slotAmount]; // scorer for d1, d2, ..... , a4, a5
        Datamuse datamuse = new Datamuse();

        for(int i  = 0; i < slotAmount; i++){
            scores[i] = new ArrayList<ScoredString>();
            String hint = panel.getAnswers().getAnswers().get(i).getHint();
            int size = panel.getAnswers().getAnswers().get(i).getSize();

            datamuse.checkDatamuse(hint, size);

            datamuseResults[i] = new ArrayList<>();
            for(int j = 0; j < Datamuse.results.size(); j++){
                datamuseResults[i].add( Datamuse.results.get(j));
            }

            googleResults[i] = GoogleChecker.getGoogleSearch(hint, size);


            movieResults[i] = MovieSearch.search(hint, size);

            tfidf[i] = new TFIDF(googleResults[i], datamuseResults[i], movieResults[i]);

            for ( String s: googleResults[i]){
                ScoredString scoredString = new ScoredString();
                scoredString.result = s.toLowerCase();
                scoredString.score = (tfidf[i]).tfIdf(s) * 100000;
                scores[i].add( scoredString);
            }

            for ( String s: datamuseResults[i]){
                ScoredString scoredString = new ScoredString();
                scoredString.result = s.toLowerCase();
                System.out.println(scoredString.result);
                scoredString.score = tfidf[i].tfIdf(s) * 100000;
                scores[i].add( scoredString);

            }
            for ( String s: movieResults[i]){
                ScoredString scoredString = new ScoredString();
                scoredString.result = s.toLowerCase();
                scoredString.score = tfidf[i].tfIdf(s) * 100000;
                scores[i].add( scoredString);

            }
            ScoredString min;
            if(scores[i].size() != 0)
                min = Collections.min(scores[i]);
            else {
                min = new ScoredString();
                min.score = 0.0;
                min.result = "";
            }
            double minVal = Math.abs(min.score);
            for ( ScoredString s: scores[i]){
                s.score += minVal;
            }
            Collections.sort(scores[i], Collections.reverseOrder());

            String oldString = "";
            for ( int m = 0; m < scores[i].size(); m++){
                if( scores[i].get(m).result.equalsIgnoreCase(oldString)) {
                    scores[i].remove(m);
                    m--;
                }else{
                   oldString = scores[i].get(m).result;
                }
            }
            for ( ScoredString s: scores[i]) {
                //System.out.println(s.score  + ": " + s.result);
            }
        }
        curState.setFilledSlotCount(0);
    }

    public void solve() throws IOException {
        init();
        findMaxState();
    }
    public void findMaxState(){
        int ansNo = 0;
        int inputNo = 0;
        Answers inputAnswers = panel.getAnswers();
        System.out.println("-------------------------------------------");
        do{
            inputAnswers = panel.getAnswers();
            while((inputNo <= scores[ansNo].size()) || scores[ansNo].isEmpty()){
                inputAnswers = panel.getAnswers();
                if(scores[ansNo].isEmpty()){
                    ansNo++;
                    inputNo = 0;
                    continue;
                }
                if(inputNo == scores[ansNo].size()){
                    ansNo++;
                    inputNo=0;
                    break;
                }
                inputAnswers.updateAnswer(scores[ansNo].get(inputNo).result,ansNo);
                boolean isValid = updater.checkState(inputAnswers);
                System.out.println("Result size : "+ scores[ansNo].size()+ " ~ Result No: "+ inputNo + " ~ " + "Result: " + scores[ansNo].get(inputNo).result + "~~~~ Answer No : "+ansNo + " VALID = " + isValid);
                //System.out.println(isValid);
                if(isValid) {
                    //curState = new State(inputAnswers);
                    panel.printState(inputAnswers);
                    ansNo++;
                    inputNo = 0;
                    break;
                }
                else{
                        inputNo++;

                }
            }
            //System.out.println("tıkandı");
        }while(ansNo < 10);
        System.out.println("çıktım");
        panel.printState(inputAnswers);

        //System.out.println(inputAnswers.getAnswer(0).getAnswer());
        //sendAnswersToPrint(inputAnswers);
    }
    //public void
}

class ScoredString implements Comparable{
    public String result;
    public Double score;

    @Override
    public int compareTo(Object o) {
        if ( this.score > ((ScoredString)o).score)
            return 1;
        if ( this.score < ((ScoredString)o).score)
            return -1;
        return 0;
    }
}
