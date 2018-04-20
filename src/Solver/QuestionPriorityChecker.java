package backend;

import java.util.ArrayList;
import UI.*;

public class QuestionPriorityChecker
{
	private boolean [] down;
	private boolean [] across;
	private boolean [] posarr;
	public static int count = 0;
	private ArrayList<String> priorityOrder;
	private ArrayList<String>[] hints;
	private String [][] puzzle;
	private CellBlock[][] puzzle2;

	public QuestionPriorityChecker(ArrayList<String>[] hints, String [][] puzzle, CellBlock[][] puzzle2)
	{
		this.hints = hints;
		this.puzzle = puzzle;
		this.puzzle2 = puzzle2;
		down = new boolean[5];
		across = new boolean[5];
		posarr = new boolean[10];
		priorityOrder = new ArrayList<String>();
		int acrossIndex = 0;
		for(int i = 0; i < 10; i++)
		{
			if(i < 5)
			{
				down[i] = true;
			}
			else
			{
				across[i%5] = true;
			}
			posarr[i] = true;
		}

	}

	public ArrayList<String> generateOrder()
	{
		double [] ratio = new double[10];
		int [] wordlettercount = new int[10];

		for(int i = 0; i < 10; i++)
		{

			double totalCount = 0;
			double letterCount = 0;
			if(i < 5)
			{
				for(int j = 0; j < 5; j++)
				{
					if(!puzzle[i][j].equals("black"))
					{
						if(!puzzle[i][j].equals(""))
						{
							letterCount++;
						}
						totalCount++;
					}
				}
			}
			else
			{
				for(int j = 0; j < 5; j++)
				{
					int index = (i % 5);
					if(!puzzle[j][index].equals("black"))
					{
						if(!puzzle[j][index].equals(""))
						{
							letterCount++;
						}
						totalCount++;
					}
				}	
			}
			ratio[i] = letterCount/totalCount;
			wordlettercount[i] = (int) totalCount;
		}
		while(count < 10)
		{
			int pos_index = -1;
			double largest_ratio = 0;
			for(int j = 0; j < 10; j ++)
			{
				if(ratio[j] > largest_ratio && ratio[j] != 1 && posarr[j])
				{
					largest_ratio = ratio[j];
					pos_index = j;
				}
				else if(ratio[j] == largest_ratio && ratio[j] != 1 && posarr[j])
				{
					if(pos_index == -1)
					{
						largest_ratio = ratio[j];
						pos_index = j;
					}
					else
					{
						if(wordlettercount[j] <= wordlettercount[pos_index])
						{
							largest_ratio = ratio[j];
							pos_index = j;
						}
					}
				}
			}

			posarr[pos_index] = false;
			boolean flag = true;
			String question_number = "";
			boolean isDown = false;
			if(pos_index < 5)
			{
				for(int j = 0; j < 5 && flag; j++)
				{
					if(!puzzle2[pos_index][j].questionNo.equals(""))
					{
						question_number = puzzle2[pos_index][j].questionNo;
						flag = false;
					}
				}
			}
			else
			{
				for(int j = 0; j < 5 && flag; j++)
				{
					if(!puzzle2[j][pos_index%5].questionNo.equals(""))
					{
						question_number = puzzle2[j][pos_index%5].questionNo;
						flag = false;
					}
				}
			}
			int index = Integer.parseInt(question_number);	
			index = index % 5;
			String temp = "";
			String temp1 = "";
			if(pos_index < 5) //across
			{
				temp = hints[0].get(index);
				System.out.println(temp);

			}
			else  //down
			{
				temp = hints[1].get(index);
				System.out.println( temp);

			}
			priorityOrder.add(temp);
			count++;
		}
		return priorityOrder;
	}

}
