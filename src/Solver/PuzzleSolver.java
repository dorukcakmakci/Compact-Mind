package Solver;


import UI.CellBlock;
import UI.PuzzlePanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PuzzleSolver implements Runnable{

    private ArrayList<String>[] hints;
    private String [][] puzzle;
    private CellBlock[][] puzzle2;
    private QuestionPriorityChecker questionChecker;
    private PuzzlePanel panel;
    private HashMap<Integer,Boolean> isDoneMap;

    public PuzzleSolver(CellBlock [][] puzzle2, String[][] puzzle, PuzzlePanel panel) {
        this.panel = panel;
        this.puzzle2 = puzzle2;
        this.puzzle = puzzle;
        isDoneMap = new HashMap<Integer, Boolean>();
        for(int i = 1; i < 10; i++)
        {
            isDoneMap.put(i, false);
        }
    }
    public void run(){
        try {
            solvePuzzle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void solvePuzzle() throws IOException {
        ArrayList<String> order = new ArrayList<String>();

        GoogleChecker googleChecker = new GoogleChecker();
        Datamuse datamuseChecker = new Datamuse();
        boolean google = false;
        boolean datamuse = false;
        boolean flag = true;
        int count = 0;
        int start_question = 0;
        int prev_start = 0;
        do
        {
            flag = true;
            String start = start_question + "";
            int question_x_index = (panel.getAnswers().getAnswers().get(start_question)).getStart();
            int question_y_index = (panel.getAnswers().getAnswers().get(start_question)).getEnd();
            String hint = panel.getAnswers().getAnswers().get(start_question).getHint();
            int size = panel.getAnswers().getAnswers().get(start_question).getSize();
            int q_no = panel.getAnswers().getAnswers().get(start_question).getQuestionNo();
            System.out.println("------------------------------");
            System.out.println("q_no: " + q_no);
            System.out.println("hint: "+ hint);
            System.out.println("x: " + question_x_index);
            System.out.println("y: " + question_y_index);
            System.out.println("size: " + size);
            System.out.println("------------------------------");
            boolean isDown;

            isDown = panel.getAnswers().getAnswers().get(start_question).getDirection() != 0;


            datamuseChecker.checkDatamuse(hint,size);
            ArrayList<String> googleAnswers = GoogleChecker.getGoogleSearch(hint, size);
            ArrayList<String> answers = new ArrayList<String>();
            if(!(Datamuse.results.isEmpty())) {
                for (int i = 0; i < Datamuse.results.size(); i++) {
                    answers.add(Datamuse.results.get(i));
                }

            }
            for (String gAns: googleAnswers)
            {
                answers.add(gAns);

            }

            for(int k = 0; k < answers.size();k++)
            {
                if(!isDown)
                {
                    int answer_count = 0;
                    for(int i = question_x_index; i < size; i++)
                    {

                        if(!(puzzle2[i][question_y_index].equals("-1")) && answer_count < answers.get(k).length() && (puzzle[i][question_y_index].equals("")))
                        {

                            if(puzzle2[i][question_y_index].getCurrentLetter().equals(answers.get(k).substring(answer_count, answer_count+1).toUpperCase()))
                            {

                                puzzle[i][question_y_index] = puzzle2[i][question_y_index].getCurrentLetter();
                                panel.addLogInLine(puzzle[i][question_y_index]);
                            }

                        }
                        answer_count++;
                    }
                }
                else
                {
                    int answer_count = 0;
                    for(int i = question_y_index; i < size; i++)
                    {
                        if(!(puzzle2[question_x_index][i].equals("-1")) && answer_count < answers.get(k).length() && (puzzle[question_x_index][i].equals("")))
                        {
                            if(puzzle2[question_x_index][i].getCurrentLetter().equals(answers.get(k).substring(answer_count, answer_count+1).toUpperCase()))
                            {
                                puzzle[question_x_index][i] = puzzle2[question_x_index][i].getCurrentLetter();
                                panel.addLogInLine(puzzle[question_x_index][i]);
                            }

                        }
                        answer_count++;
                    }
                }

            }


            boolean flag2 = true;
            if(!isDown)
            {
                for(int i = question_x_index; i < size; i++)
                {
                    if(puzzle[i][question_y_index].equals(""))
                    {
                        flag2 = false;
                    }
                }
            }
            else
            {
                for(int i = question_y_index; i < size; i++)
                {
                    if(puzzle[question_x_index][i].equals(""))
                    {
                        flag2 = false;
                    }
                }
            }
            if(flag2)
            {
                isDoneMap.replace(q_no, true);
            }

            start_question++;
            if(start_question == 10)
            {
                for(int i = 1 ; i < 10 ; i++)
                {
                    if(!isDoneMap.get(i))
                    {
                        String currStr = "";
                        int q_start = panel.getAnswers().getAnswers().get(i-1).getStart();
                        int q_end = panel.getAnswers().getAnswers().get(i-1).getEnd();
                        int dir = panel.getAnswers().getAnswers().get(i-1).getDirection();
                        for(int j = 0; j < panel.getAnswers().getAnswers().get(i-1).getSize(); j++)
                        {
                            if(dir == 0) //across
                            {
                                if(!puzzle[j][q_end].equals(""))
                                {
                                    currStr = currStr + puzzle[j][q_end];
                                }
                                else
                                {
                                    currStr = currStr + "?";
                                }

                            }
                            else
                            {
                                if(!puzzle[q_start][j].equals(""))
                                {
                                    currStr = currStr + puzzle[j][q_start];
                                }
                                else
                                {
                                    currStr = currStr + "?";
                                }
                            }
                        }
                        answers.clear();
                        datamuseChecker.checkStartEndMissing(currStr, panel.getAnswers().getAnswers().get(i-1).getSize());
                        if(!(Datamuse.results.isEmpty())) {
                            for (int t = 0; t < Datamuse.results.size(); t++) {
                                answers.add(Datamuse.results.get(t));
                            }

                        }
                        for(int k = 0; k < answers.size();k++)
                        {
                            if(dir == 0) //across
                            {
                                int answer_count = 0;
                                for(int t = q_start; t < size; t++)
                                {

                                    if(!(puzzle2[t][q_end].equals("-1")) && answer_count < answers.get(k).length() && (puzzle[t][q_end].equals("")))
                                    {

                                        if(puzzle2[t][q_end].getCurrentLetter().equals(answers.get(k).substring(answer_count, answer_count+1).toUpperCase()))
                                        {

                                            puzzle[t][q_end] = puzzle2[t][q_end].getCurrentLetter();
                                            panel.addLogInLine(puzzle[t][q_end]);
                                        }

                                    }
                                    answer_count++;
                                }
                            }
                            else
                            {
                                int answer_count = 0;
                                for(int t = q_end; t < size; t++)
                                {
                                    if(!(puzzle2[q_start][t].equals("-1")) && answer_count < answers.get(k).length() && (puzzle[q_start][t].equals("")))
                                    {
                                        if(puzzle2[q_start][t].getCurrentLetter().equals(answers.get(k).substring(answer_count, answer_count+1).toUpperCase()))
                                        {
                                            puzzle[q_start][t] = puzzle2[q_start][t].getCurrentLetter();
                                            panel.addLogInLine(puzzle[q_start][t]);
                                        }

                                    }
                                    answer_count++;
                                }
                            }

                        }
                    }
                }
            }

            panel.addLog("" + start_question);
            count++;
            if(count < 10)
            {
                flag = false;
            }
        }while(!flag);
        System.out.println("Exiting");

    }
}

