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
    private Normalizer[] normalizers;
    private Normalizer normalizer;
    private Answers maxAnswer;
    public PuzzleSolverWithTFIDF(String[][] puzzle , PuzzlePanel panel){

        this.panel = panel;
        maxAnswer = new Answers(panel.getInput());
        isDataCallDone = false;
        slotAmount = panel.getAnswers().getAnswers().size();

        curState = new State(panel.getAnswers());
        curState.setOldState(null);
        updater = new StateUpdater(curState,this.panel.foundAnswers, maxAnswer, panel);

        datamuseResults = new ArrayList[slotAmount];
        googleResults = new ArrayList[slotAmount];
        movieResults = new ArrayList[slotAmount];
        //reverseDictionaryResults = new ArrayList[slotAmount];
        //abbreviationResults = new ArrayList[curState.getAnswersSize()];
        scores = new ArrayList[slotAmount];
        normalizers = new Normalizer[slotAmount];
    }


    private void init() {

        tfidf = new TFIDF[slotAmount]; // scorer for d1, d2, ..... , a4, a5
        Datamuse datamuse = new Datamuse();

        for(int i  = 0; i < slotAmount; i++){
            scores[i] = new ArrayList<ScoredString>();
            String hint = panel.getAnswers().getAnswers().get(i).getHint();
            int size = panel.getAnswers().getAnswers().get(i).getSize();
            panel.addLog("Checking datamuse for hint no : " + i);
            datamuse.checkDatamuse(hint, size);

            datamuseResults[i] = new ArrayList<>();
            for(int j = 0; j < Datamuse.results.size(); j++){
                datamuseResults[i].add( Datamuse.results.get(j).toUpperCase());
            }
            panel.addLog("Checking google for hint no : " + i);
            //googleResults[i] = GoogleChecker.getGoogleSearch(hint, size);

            panel.addLog("Checking imdb for hint no : " + i);
            //movieResults[i] = MovieSearch.search(hint, size);
            panel.addLog("Checking reverse dictionary for hint no : " + i);
            //reverseDictionaryResults[i] = ReverseDictionary.getReverseDict( hint, size);
            panel.addLog("Calling tfidf for found data for hint no : " + i);
            tfidf[i] = new TFIDF(/*googleResults[i],*/ datamuseResults[i]/*, movieResults[i], reverseDictionaryResults[i]*/);

            double scorePriority = 0;
            panel.addLog("Preprocessing data for hint no : " + i);
            scorePriority = 0;
            /*
            for ( String s: movieResults[i]){
                ScoredString scoredString = new ScoredString();
                scoredString.result = s.toLowerCase();
                scoredString.score = ((tfidf[i]).tfIdf(s) + scorePriority) * 10;
                scorePriority -= 0.001;
                if (!scores[i].contains( scoredString))
                    scores[i].add( scoredString);

            }
            */
            scorePriority = 0;
            int in = 0;

            for ( String s: datamuseResults[i]){

                ScoredString scoredString = new ScoredString();
                scoredString.result = s.toUpperCase();
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
            /*
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
            */
            /*
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
                in++;
            }
            */
            panel.addLog("Scoring data for hint no : " + i);
            ScoredString min = null;
            double minVal = 100000000;
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
            /*
            double minScore = Collections.min(scores[i]).score;
            double maxScore = Collections.max(scores[i]).score;

            normalizers[i] = new Normalizer(maxScore, minScore, 1, -1);
            for ( ScoredString s: scores[i]) {
                s.score = normalizers[i].normalize(s.score);
                System.out.println(s.score  + ": " + s.result);
            }*/
        }
        double minScore = 2;
        double maxScore = -2;
        for ( ArrayList<ScoredString> sList: scores){
            for ( ScoredString s: sList){
                if( s.score < minScore)
                    minScore = s.score;
                if( s.score > maxScore)
                    maxScore = s.score;
            }
        }
        panel.addLog("Normalizing data scores");
        normalizer = new Normalizer( maxScore, minScore, 1, -1);
        curState.setFilledSlotCount(0);

        int listIn = 0;
        for ( ArrayList<ScoredString> sList: scores){
            System.out.println("List index : " + listIn++);
            for ( ScoredString s: sList){
                s.score = normalizer.normalize(s.score);
                System.out.println(s.score  + ": " + s.result);
            }
        }

        ArrayList<ScoredList> scoredList = new ArrayList<>();
        int listIndex = 0;
        for ( ArrayList<ScoredString> sList: scores){
            int count = 0;
            double total = 0;
            for ( ScoredString s: sList){
               total += s.score;
               count++;
               if (count == 3)
                   break;
            }
            ScoredList list = new ScoredList();
            list.index = listIndex;
            list.score = (total) / count;
            scoredList.add(list);
        }

        Collections.sort(scoredList, Collections.reverseOrder());

    }

    public void solve() throws IOException {
        panel.addLog("Starting the data retrieval and data preprocess-scoring");
        init();
        panel.addLog("Finding the maximum scored state");
        findMaxState();
    }
    public void findMaxState() {
        int ansNo = 0;
        int[] inputCount = new int[10];
        for (int i = 0; i < 10; i++)
            inputCount[i] = 0;
        Answers inputAnswers = panel.getAnswers();
        System.out.println("-------------------------------------------");

        int stateCount = 0;
        int validStateCount = 0;
        boolean forwardFlag = true;
        while (ansNo != -1) {
            panel.printState(inputAnswers);

            if (scores[ansNo].isEmpty()) {
                if (forwardFlag) {
                    panel.addLog("No data found for this Q No: "+ansNo+", leaving it blank");
                    ansNo++;
                    continue;
                } else {
                    panel.addLog("No data found for Q No: "+ansNo+", backtracking");
                    ansNo--;
                    inputAnswers.removeAnswer(ansNo);
                }
            }
            if (scores[ansNo].size() == inputCount[ansNo]) {
                panel.addLog("All results were checked for Q No "+ansNo+", backtracking");
                inputAnswers.removeAnswer(ansNo);
                panel.printState(inputAnswers);
                inputCount[ansNo] = 0;
                forwardFlag = false;
                ansNo--;
                //if(scores[ansNo].isEmpty())
                if (ansNo == -1) {
                    break;
                }
                inputAnswers.removeAnswer(ansNo);
                //inputCount[ansNo] = inputCount[ansNo]+ 1;
                panel.printState(inputAnswers);
                //panel.addLog("All data for the current answer no is checked, backtracking to Question :" +ansNo +"with data no :" + inputCount[ansNo]);
                continue;
            }
            //System.out.println(ansNo);

            //System.out.println(scores[ansNo].size());
            inputAnswers.updateAnswer(scores[ansNo].get(inputCount[ansNo]).result, ansNo);
            boolean isValid = updater.checkState(inputAnswers, maxAnswer);
            if (isValid) {
                panel.addLog("Checked state was valid for Q No: " + ansNo);
                validStateCount++;
                if(panel.getSlowed()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                panel.printState(inputAnswers);
                //panel.addLog("Answers were valid for the puzzle, trying out different states");
                inputCount[ansNo] = inputCount[ansNo] + 1;
                ansNo++;
                forwardFlag = true;

            } else {
                panel.addLog("Checked state was invalid for Q No: "+ ansNo + ". Trying other data");
                inputAnswers.removeAnswer(ansNo);
                panel.printState(inputAnswers);
                inputCount[ansNo] = inputCount[ansNo] + 1;
            }
            panel.printState(inputAnswers);
            stateCount++;
        }
        maxAnswer.updateAnswer(panel.foundAnswers.getAnswer(1).getAnswer(), 1);
        maxAnswer.updateAnswer(panel.foundAnswers.getAnswer(8).getAnswer(), 8);
        panel.printState(maxAnswer);
        panel.addLog("No other state was found, exiting");
        panel.addLog("Solving operation is done");
        panel.addLog("Checked state count : " + stateCount);
        panel.addLog("Found valid state count : " + validStateCount);
    }

}


class ScoredList implements Comparable{

    public int index;
    public double score;
    @Override
    public int compareTo(Object o) {
        if ( this.score > ((ScoredList)o).score)
            return 1;
        if ( this.score < ((ScoredList)o).score)
            return -1;
        return 0;
    }
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
        return this.result.equalsIgnoreCase(((ScoredString) obj).result);
    }
}
