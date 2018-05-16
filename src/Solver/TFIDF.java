package Solver;

import java.util.ArrayList;

public class TFIDF {

    //DATA MEMBERS
    private ArrayList<String> google;
    private ArrayList<String> datamuse;
    private ArrayList<String> abbreviations;
    private ArrayList<String> movie;
    //private ArrayList<String> reverse;
    ArrayList<ArrayList<String>> docs;



    public TFIDF( /* ArrayList<String> google,*/ ArrayList<String> datamuse /*, ArrayList<String> movie, ArrayList<String> reverse */) {
        //this.google = google;
        //this.datamuse = datamuse;
        //this.movie = movie;
        //this.reverse = reverse;
        docs = new ArrayList<ArrayList<String>>();
        //docs.add(google);
        docs.add(datamuse);
        //docs.add(movie);
        //docs.add(reverse);
    }

    public double tf(String term, int type) {
        double n = 0;
        for (String word : docs.get(type)) {
            if (term.equalsIgnoreCase(word))
                switch(type){
                    //case 0: /*google*/ n += 0.3; break;
                    case 0: /*datamuse*/ n += 0.6; break; //case changed to 0
                    // case 2: /*abbreviation*/ n += 3; break;
                    //case 2: /*movie*/  n += 0.8 ;break;
                    //case 3:/*reverseDictionary*/ n += 0.4; break;
                    default: break;
                }

        }
        if (docs.get(type).size() == 0)
            return 0;
        return n / docs.get(type).size();
    }

    public double idf(String term) {
        double n = 0;
        for (int i = 0; i < docs.size(); i++ ) {
            for (String word : docs.get(i)) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                }
            }
        }
        return Math.log(docs.size() / n);
    }

    public double tfIdf( String term) {

        double freq = 0;
        for(int i = 0; i < 1 /* changed from 3*/; i++){
            freq += tf(term, i) * idf(term);
        }
        return freq;
    }

    public ArrayList<String> getGoogle() {
        return google;
    }

    public void setGoogle(ArrayList<String> google) {
        this.google = google;
    }

    public ArrayList<String> getDatamuse() {
        return datamuse;
    }

    public void setDatamuse(ArrayList<String> datamuse) {
        this.datamuse = datamuse;
    }

    public ArrayList<String> getAbbreviations() {
        return abbreviations;
    }

    public void setAbbreviations(ArrayList<String> abbreviations) {
        this.abbreviations = abbreviations;
    }

    public ArrayList<String> getMovie() {
        return movie;
    }

    public void setMovie(ArrayList<String> movie) {
        this.movie = movie;
    }

}
