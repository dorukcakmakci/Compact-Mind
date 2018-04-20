package backend;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import UI.CellBlock;

public class PuzzleSolver {

	private ArrayList<String>[] hints;
	private String [][] puzzle;
	private CellBlock[][] puzzle2;
	private QuestionPriorityChecker questionChecker;


	public PuzzleSolver(ArrayList<String>[] hints, String [][] puzzle, CellBlock [][] puzzle2) {
		this.hints = hints;
		this.puzzle = puzzle;
		this.puzzle2 = puzzle2;

	}

	public void solvePuzzle()
	{
		ArrayList<String> order = new ArrayList<String>();
		questionChecker = new QuestionPriorityChecker(hints, puzzle, puzzle2);
		order = questionChecker.generateOrder();

		GoogleChecker googleChecker = new GoogleChecker();
		Datamuse datamuseChecker = new Datamuse();
		boolean google = false;
		boolean datamuse = false;
		boolean flag = true;
		int count = 0;
		int start_question = 1;
		int prev_start = 0;
		do
		{
			flag = true;
			ArrayList<String> answers = new ArrayList<String>();
			String start = start_question + "";
			int question_x_index = 0;
			int question_y_index = 0;
			boolean isDown = false;

			for(int i = 0; i < 5; i++)
			{
				for(int j = 0; j < 5; j++)
				{
					if(puzzle2[i][j].questionNo.equals(start))
					{
						question_x_index = i;
						question_y_index = j;
					}

				}

			}

			int hint_index = 0;
			int question_index = 0;
			for(int i = 0; i < 10; i++)
			{
				if(i < 5 )
				{
					if(hints[0].get(i).substring(0, 1).equals(start))
					{
						hint_index = 0;
						question_index = i;
						isDown = false;
						break;
					}
				}
				else
				{
					if(hints[1].get(i%5).substring(0, 1).equals(start))
					{
						hint_index = 1;
						question_index = i%5;
						isDown = true;
						break;
					}	
				}
			}
	
			if(start_question != 4)
			{
				datamuseChecker.checkDatamuse(hints[hint_index].get(question_index), 5);
				if(!(Datamuse.results.isEmpty()))
				{
					for(int i = 0; i < Datamuse.results.size(); i++)
					{
						answers.add(Datamuse.results.get(i));
					}

					for(int k = 0; k < answers.size();k++)
					{
						if(!isDown)
						{
							int answer_count = 0;
							for(int i = question_x_index; i < 5; i++)
							{

								if(!(puzzle[i][question_y_index].equals("black")) && answer_count < answers.get(k).length() && (puzzle[i][question_y_index].equals("")))
								{

									if(puzzle2[i][question_y_index].currentLetter.equals(answers.get(k).substring(answer_count, answer_count+1).toUpperCase()))
									{

										puzzle[i][question_y_index] = puzzle2[i][question_y_index].currentLetter;
										System.out.println(puzzle[i][question_y_index]);
									}

								}
								answer_count++;
							}
						}
						else
						{
							int answer_count = 0;
							for(int i = question_y_index; i < 5; i++)
							{
								if(!(puzzle[question_x_index][i].equals("black")) && answer_count < answers.get(k).length() && (puzzle[question_x_index][i].equals("")))
								{
									if(puzzle2[question_x_index][i].currentLetter.equals(answers.get(k).substring(answer_count, answer_count+1).toUpperCase()))
									{
										puzzle[question_x_index][i] = puzzle2[question_x_index][i].currentLetter;
										System.out.println(puzzle[question_x_index][i]);
									}

								}
								answer_count++;
							}
						}

					}	
				}

			}
			
			prev_start = start_question;
			start_question++;
			System.out.println(start_question);
			count++;
			if(count < 10)
			{
				flag = false;
			}
		}while(!flag);
	}

}
