package Solver;

import Parser.Answers;
import UI.PuzzlePanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

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
    private ArrayList<String>[] reverseDictionaryResults;
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
        reverseDictionaryResults = new ArrayList[slotAmount];
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
            reverseDictionaryResults[i] = ReverseDictionary.getReverseDict( hint, size);

            tfidf[i] = new TFIDF(googleResults[i], datamuseResults[i], movieResults[i], reverseDictionaryResults[i]);

            double scorePriority = 0;

            scorePriority = 0;
            for ( String s: movieResults[i]){
                ScoredString scoredString = new ScoredString();
                scoredString.result = s.toLowerCase();
                scoredString.score = ((tfidf[i]).tfIdf(s) + scorePriority) * 10;
                scorePriority -= 0.001;
                if (!scores[i].contains( scoredString))
                    scores[i].add( scoredString);

            }

            scorePriority = 0;
            int in = 0;

            for ( String s: datamuseResults[i]){
                System.out.println( "index " + in + ": " + s);
                ScoredString scoredString = new ScoredString();
                scoredString.result = s.toLowerCase();
                scoredString.score = ((tfidf[i]).tfIdf(s) + scorePriority) * 10;
                scorePriority -= 0.002;
                if (!scores[i].contains( scoredString))
                 scores[i].add( scoredString);
                else{
                    int index = scores[i].indexOf(scoredString);
                    ScoredString ss = scores[i].get(index);
                    ss.score += 10;
                }
                in++;
            }

            scorePriority = 0;
            for ( String s: reverseDictionaryResults[i]){
                ScoredString scoredString = new ScoredString();
                scoredString.result = s.toLowerCase();
                scoredString.score = ((tfidf[i]).tfIdf(s) + scorePriority) * 10;
                scorePriority -= 0.003;
                if (!scores[i].contains( scoredString))
                 scores[i].add( scoredString);
                else{
                    int index = scores[i].indexOf(scoredString);
                    ScoredString ss = scores[i].get(index);
                    ss.score += 0.1;
                }
            }


            scorePriority = 0;
            in = 0;
            for ( String s: googleResults[i]){
                ScoredString scoredString = new ScoredString();
                scoredString.result = s.toLowerCase();
                scoredString.score = ((tfidf[i]).tfIdf(s) + scorePriority) * 10;
                scorePriority -= 0.005;
                if (!scores[i].contains( scoredString))
                    scores[i].add( scoredString);
                else{
                    int index = scores[i].indexOf(scoredString);
                    ScoredString ss = scores[i].get(index);
                    ss.score += 0.25;
                }
            }

            ScoredString min = null;
            double minVal = 0;
            try {
                if (scores[i] != null)
                    min = Collections.min(scores[i]);
                minVal = Math.abs(min.score);
            }catch ( NoSuchElementException e){
                minVal = 0;
            }

            for ( ScoredString s: scores[i]){
                s.score += minVal;
            }
            Collections.sort(scores[i], Collections.reverseOrder());

            String oldString = "";
            for ( int m = 0; m < scores[i].size(); m++){
                if (  scores[i].get(m).result.equalsIgnoreCase(oldString) || !(scores[i].get(m).result.matches("[a-zA-Z0-9]*"))){
                    scores[i].remove(m);
                    m--;
                }else{
                   oldString = scores[i].get(m).result;
                }
            }
            for ( ScoredString s: scores[i]) {
                System.out.println(s.score  + ": " + s.result);
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

    @Override
    public boolean equals(Object obj) {
        if( this.result.equalsIgnoreCase(((ScoredString)obj).result))
            return true;
        return false;
    }
}
