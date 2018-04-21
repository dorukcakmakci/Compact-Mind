package Parser;

import UI.CellBlock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HTMLProcessor{

    // 0 ACROSS // 1 DOWN
    public ArrayList<String>[] hints;
    public CellBlock[][] puzzle;

    public HTMLProcessor() {
        hints = new ArrayList[2];
        hints[0] = new ArrayList<String>();
        hints[1] = new ArrayList<String>();
        puzzle = new CellBlock[5][5];
        for(int i = 0;i < 5;i++)
            for(int j = 0; j<5;j++)
                puzzle[i][j] = new CellBlock();
    }

    public boolean isBlank(int x,int y){
        return puzzle[x][y].getCurrentLetter().equals("-1");
    }
    public void readPuzzle( String puzzleName){
        String content;
        StringBuilder contentBuilder = new StringBuilder();
        String revealPuzzlePath = "./ph/reveal-" + puzzleName + ".txt";
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(revealPuzzlePath));
            String str;
            while ((str = fileReader.readLine()) != null) {
                contentBuilder.append(str);
            }
            fileReader.close();
        } catch (IOException e) {
            System.out.print("");
            return;
        }
        content = contentBuilder.toString();
        parsePuzzle( content);
    }


    private void parsePuzzle(String content){
        hints = new ArrayList[2];
        hints[0] = new ArrayList<String>();
        hints[1] = new ArrayList<String>();
        puzzle = new CellBlock[5][5];
        for(int i = 0;i < 5;i++)
            for(int j = 0; j<5;j++)
                puzzle[i][j] = new CellBlock();
        String hintNoPattern = "\"Clue-label--";
        String hintPattern = "\"Clue-text--";
        int acrossDown = 0; // 0 for across, 1 for down
        String tempContent = content;
        tempContent = tempContent.substring(tempContent.indexOf(">Across<")+8);
        while(true){
            String hintString;
            int downIndex = 99999999;
            if(tempContent.contains(">Down<")){
                downIndex = tempContent.indexOf(">Down<");
            }
            int hintNoIndex = tempContent.indexOf(hintNoPattern); //finding index
            if(downIndex < hintNoIndex){
                acrossDown = 1;
                tempContent = tempContent.substring(tempContent.indexOf(">Down<")+6);
                hintNoIndex = tempContent.indexOf(hintNoPattern); //finding index after update
                //System.out.println("Down");
            }
            if(hintNoIndex == -1)
                break;
            hintNoIndex = hintNoIndex+20;
            String hintNo = ""+ tempContent.charAt(hintNoIndex);
            if(tempContent.charAt(hintNoIndex+1) != '<'){
                hintNo = hintNo +tempContent.charAt(hintNoIndex+1);
            }
            hintString = hintNo + "-";
            tempContent = tempContent.substring(hintNoIndex);

            int hintStart = tempContent.indexOf(hintPattern); //finding hint
            if(hintStart == -1)
                break;
            hintStart = hintStart + 19;
            String hint = "";
            while(tempContent.charAt(hintStart) != '<'){
                hint = hint + tempContent.charAt(hintStart);
                hintStart++;
            }
            hintString = hintString + hint;
            if(acrossDown == 0)
                hints[0].add(hintString);
            if(acrossDown == 1)
                hints[1].add(hintString);
            tempContent = tempContent.substring(hintStart);
        }
        ///PARSING HINTS END
        //PARSING PUZZLE
        tempContent = content;
        int i = 0;
        int j = 0;
        String cellEmptyPattern = "\"Cell-block";
        String cellLetterPattern = "text-anchor=\"middle\" font-size=\"66.67\">"; // 39
        String cellLetterNo = "text-anchor=\"start\" font-size=\"33.33\">";
        while(true){
            //
            int cellEmptyIndex = tempContent.indexOf(cellEmptyPattern);
            //
            int cellLetterNoIndex = tempContent.indexOf(cellLetterNo);
            //
            int cellLetterIndex = tempContent.indexOf(cellLetterPattern);

            if(cellEmptyIndex == -1){ //cell empty parse

            }
            else if(cellEmptyIndex < cellLetterIndex && cellEmptyIndex < cellLetterNoIndex && cellEmptyIndex != -1){
                puzzle[i][j].setQuestionNo("");
                puzzle[i][j].setCurrentLetter("-1"); //-1 for blank
                tempContent = tempContent.substring(cellEmptyIndex + 11);
                if(i == 4){
                    j++;
                    i = 0;
                }
                else
                    i++;

                continue;
            }
            else{}
            if(cellLetterNoIndex == -1)
                cellLetterNoIndex = 9999999;
            if((cellLetterNoIndex < cellLetterIndex)){
                String letterNo = "";
                cellLetterNoIndex = cellLetterNoIndex + 38; // 38
                while(tempContent.charAt(cellLetterNoIndex) != '<'){
                    letterNo = letterNo + tempContent.charAt(cellLetterNoIndex);
                    cellLetterNoIndex++;
                }
                tempContent = tempContent.substring(cellLetterNoIndex);
                puzzle[i][j].setQuestionNo(letterNo);
                //System.out.print(letterNo + "-");
                //System.out.println(i+"-"+j+"false");
            }

            cellLetterIndex = tempContent.indexOf(cellLetterPattern);
            if(cellLetterIndex == -1)
                break;
            cellLetterIndex = cellLetterIndex + 39;
            String letter = "";
            while(tempContent.charAt(cellLetterIndex) != '<'){
                letter = letter + tempContent.charAt(cellLetterIndex);
                cellLetterIndex++;
            }
            tempContent = tempContent.substring(cellLetterIndex);
            puzzle[i][j].setCurrentLetter(letter);
            //System.out.println(letter);
            if(i == 4){
                j++;
                i = 0;
            }
            else
                i++;
        }
    }
}