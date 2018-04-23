package Solver;

import UI.PuzzlePanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class PuzzleSolverWithTFIDF {

    private ArrayList<State> path;
    private State currentState;
    private PuzzlePanel panel;
    private TFIDF[] tfidf;
    private String[][] puzzle;
    private int slotAmount;
    private State curState;
    private boolean isDataCallDone;

    private ArrayList<String>[] datamuseResults;
    private ArrayList<String>[] googleResults;
    //private ArrayList<String>[] abbreviationResults;
    private ArrayList<String>[] movieResults;
    private ArrayList<ScoredString>[] scores;

    public PuzzleSolverWithTFIDF(String[][] puzzle, PuzzlePanel panel){

        this.panel = panel;
        this.puzzle = puzzle;

        isDataCallDone = false;
        slotAmount = panel.getAnswers().getAnswers().size();

        curState = new State(0);

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
                scoredString.score = tfidf[i].tfIdf(s) * 100000;
                scores[i].add( scoredString);

            }
            for ( String s: movieResults[i]){
                ScoredString scoredString = new ScoredString();
                scoredString.result = s.toLowerCase();
                scoredString.score = tfidf[i].tfIdf(s) * 100000;
                scores[i].add( scoredString);

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
                if( scores[i].get(m).result.equalsIgnoreCase(oldString) || scores[i].get(m).result.contains("\\n")
                        || scores[i].get(m).result.contains("(") || scores[i].get(m).result.contains(")") ||
                        scores[i].get(m).result.contains("?") || scores[i].get(m).result.contains("+") ||
                        scores[i].get(m).result.contains("-") || scores[i].get(m).result.contains("\\") ||
                        scores[i].get(m).result.contains(":") || scores[i].get(m).result.contains("!") ||
                        scores[i].get(m).result.contains("\"") || scores[i].get(m).result.contains(";") ||
                        scores[i].get(m).result.contains("\\s+") || scores[i].get(m).result.contains("&") ||
                        scores[i].get(m).result.contains("”") || scores[i].get(m).result.contains("“")
                        || scores[i].get(m).result.contains(" ") || scores[i].get(m).result.contains(" ")
                        ) {
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
        init();/*
        for(PuzzleWord wd : panel.getAnswers().getAnswers())
        {
            System.out.println(wd.getHint());
        }
        if (fillPuzzle()) {
            System.out.println("Solution found!");
            System.out.println("Backtracks: " + curState.getFilledSlotCount());
        } else {
            System.out.println("Not the best solution");
        }*/
    }

    /*private boolean fillPuzzle(){
        ArrayList<String>[]
    }
    private*/
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
