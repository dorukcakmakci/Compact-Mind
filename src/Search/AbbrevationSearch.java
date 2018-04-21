package Search;

import Parser.PuzzleWord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;
import org.jsoup.nodes.*;
import org.jsoup.Jsoup;


public class AbbrevationSearch {

    public ArrayList<String> getAbbrevation(PuzzleWord puzzleWord, String[][] constraints) throws IOException{

        ArrayList<String> answers = new ArrayList();

        String hint = puzzleWord.getHint();

        //Match Regex
        String abb = "([A-Z]\\.)*[A-Z]$";
        Pattern pattern = Pattern.compile(abb);
        Matcher match = pattern.matcher(hint);

        String keyword;
        if(match.find()){

            keyword = match.group(0);
            keyword = keyword.replaceAll("\\.",  "");
            keyword = keyword.toLowerCase();
        }

        else
            return answers;

        String url = "https://acronyms.thefreedictionary.com/" + keyword;

        Document doc;// = null;
        doc = Jsoup.connect(url).get();

        int startIndex = hint.indexOf('\'');
        char startChar = hint.charAt(startIndex+1);

        String takenRow;
        Element table = doc.getElementById("AcrFinder");

        for(Element row : table.select("tr")){

            takenRow =(row.text()).replaceAll("\\(", "")
                        .replaceAll("\\)","")
                        .replaceAll("\\?","");

            String[] currentAnswer = takenRow.split("\\s+");

            for(int i = 0; i < currentAnswer.length;i++){
                if(currentAnswer[i].length() == puzzleWord.getLength()
                        && currentAnswer[i].charAt(0) == startChar){
                    boolean flag =false;
                    for(int k = 0; !flag && (k < constraints.length); k++){
                        String check = currentAnswer[i].charAt(Integer.parseInt(constraints[k][0])) + "";
                        if(  check.equalsIgnoreCase(constraints[k][1]))
                            flag = true;

                    }

                    if(flag)
                        answers.add(currentAnswer[i]);

                }

            }
        }

        return answers;
    }
}
