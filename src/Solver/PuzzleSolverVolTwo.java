package Solver;

import Parser.PuzzleWord;
import UI.PuzzlePanel;

import java.io.IOException;
import java.util.ArrayList;

public class PuzzleSolverVolTwo
{
    private String [][] puzzle;
    private PuzzlePanel panel;
    private int numBacktracks;
    private int slot_amount;
    private boolean isDataCallDone;
    private ArrayList<Word>[] results;

    public PuzzleSolverVolTwo(String[][] puzzle, PuzzlePanel panel)
    {
        this.panel = panel;
        this.puzzle = puzzle;

        isDataCallDone = false;
        slot_amount = 0;
        for(PuzzleWord wrd : panel.getAnswers().getAnswers())
        {
            slot_amount++;
        }
        results = new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            results[i] = new ArrayList<Word>();
        }

    }

    private void reinit()
    {

        numBacktracks = 0;
        for (int i = 0; i < 10; i++) {
            results[i].clear();
        }
        isDataCallDone = false;
    }

    public void solve() {

        reinit();
        for(PuzzleWord wd : panel.getAnswers().getAnswers())
        {
            System.out.println(wd.getHint());
        }
        if (fillPuzzle(0, 0)) {
            System.out.println("Solution found!");
            System.out.println("Backtracks: " + numBacktracks);
        } else {
            System.out.println("No solution found!");
        }


    }

    private boolean fillPuzzle(int slot, int q_index)
    {

        if(slot ==  slot_amount )
        {
            //Solving finished, we have filled every slot in the puzzle
            return true;
        }

        int question_x_index = (panel.getAnswers().getAnswers().get(q_index)).getStart();
        int question_y_index = (panel.getAnswers().getAnswers().get(q_index)).getEnd();
        boolean isDown;

        ArrayList<Word> words = new ArrayList<Word>();
        try {
            words = getWords(q_index);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isDown = panel.getAnswers().getAnswers().get(q_index).getDirection() != 0;

        for(Word word : words)
        {

            if(isPuttable(word, question_x_index, question_y_index, isDown ))
            {
                putWord(word,question_x_index, question_y_index, isDown);
                if(fillPuzzle(slot + 1, q_index+1))
                    return true;
                removeWord(word,question_x_index, question_y_index, isDown);
            }
        }
        numBacktracks++;
        return false;
    }

    private ArrayList<Word> getWords(int start_question) throws IOException
    {
        if(!isDataCallDone)
        {
            //TODO: Datamuse ve Google gibi abbr ve imdb eklencek.
            //TODO: isterseniz scoring, datamuse'un scoring'i commentli bug veriyordu.
            GoogleChecker googleChecker = new GoogleChecker();
            Datamuse datamuseChecker = new Datamuse();
            for(int j = 0; j < 10; j++)
            {
                String hint = panel.getAnswers().getAnswers().get(j).getHint();
                int size = panel.getAnswers().getAnswers().get(j).getSize();
                int q_no = panel.getAnswers().getAnswers().get(j).getQuestionNo();

                datamuseChecker.checkDatamuse(hint, size);
                panel.addLog("Checking google for hint : "+hint);
                ArrayList<String> googleAnswers = GoogleChecker.getGoogleSearch(hint, size);
                ArrayList<String> datamuseAnswers = new ArrayList<String>();
                if (!(Datamuse.results.isEmpty())) {
                    for (int i = 0; i < Datamuse.results.size(); i++) {
                        datamuseAnswers.add(Datamuse.results.get(i));
                    }

                }
                for (String dAns : datamuseAnswers) {
                    dAns = dAns.toUpperCase();
                    Word curr = new Word(dAns, false);
                    if (!results[j].contains(curr))
                        results[j].add(curr);
                }

                for (String gAns : googleAnswers) {
                    gAns = gAns.toUpperCase();
                    Word curr = new Word(gAns, false);
                    if (!results[j].contains(curr))
                        results[j].add(curr);
                }

            }
            isDataCallDone = true;
        }

        return results[start_question];
}

    private void putWord(Word w, int question_x_index, int question_y_index, boolean isDown)
    {
        if(!isDown) //across
        {
            int letter_counter = 0;
            for(int i = question_x_index; i < w.getWord().length()+question_x_index; i++)
            {
                puzzle[i][question_y_index] = w.getWord().charAt(letter_counter) + "";
                //panel.addLog(puzzle[i][question_y_index]);
                letter_counter++;
            }
        }
        else
        {
            int letter_counter = 0;
            for(int i = question_y_index; i < w.getWord().length()+question_y_index; i++)
            {
                puzzle[question_x_index][i] = w.getWord().charAt(letter_counter) + "";
                //panel.addLog(puzzle[question_x_index][i]);
                letter_counter++;
            }
        }

        w.setUsed(true);

    }

    private void removeWord(Word w, int question_x_index, int question_y_index, boolean isDown)
    {
        if(!isDown)
        {
            for(int i = question_x_index; i < w.getWord().length()+question_x_index; i++)
            {
                puzzle[i][question_y_index] = "";
                //panel.addLog(puzzle[i][question_y_index]);
            }
        }
        else
        {
            for(int i = question_y_index; i < w.getWord().length() + question_y_index; i++)
            {
                puzzle[question_x_index][i] =  "";
                //panel.addLog(puzzle[question_x_index][i]);
            }
        }
        w.setUsed(false);
    }

    private boolean isPuttable(Word w, int question_x_index, int question_y_index, boolean isDown)
    {
        if(w.getUsed())
            return false;

        if(!isDown) //across
        {
            int letter_counter = 0;
            for(int i = question_x_index; i < w.getWord().length()+question_x_index; i++)
            {
                String currLetter = w.getWord().charAt(letter_counter) + "";
                if(!puzzle[i][question_y_index].equals("") && !puzzle[i][question_y_index].equals(currLetter) )
                {
                    return false;
                }
                letter_counter++;
            }
        }
        else
        {
            int letter_counter = 0;
            for(int i = question_y_index; i < w.getWord().length() + question_y_index; i++)
            {
                String currLetter = w.getWord().charAt(letter_counter) + "";
                if(!puzzle[question_x_index][i].equals("") && !puzzle[question_x_index][i].equals(currLetter)  )
                {
                    return false;
                }
                letter_counter++;
            }

        }
        return true;
    }

    public void printPuzzle() {
        printPuzzleBorder();

        for ( int row = 0; row < 5; row++ ) {
            System.out.print("|");
            for ( int col = 0; col < 5; col++ ) {
                System.out.print(puzzle[row][col] + "|");
            }
            System.out.println();
        }

        printPuzzleBorder();

        System.out.println();
    }

    private void printPuzzleBorder() {
        for ( int i = 0; i < 5 * 2 + 1; i++ ) {
            System.out.print("-");
        }
        System.out.println();
    }

}
