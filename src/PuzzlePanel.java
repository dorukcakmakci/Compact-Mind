import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.io.IOException;

public class PuzzlePanel extends JPanel{
	static final int SCREEN_HEIGHT = 900;
	static final int SCREEN_WIDTH = 1200;
	static final int puzzleSize = 5;
	static final int blankRectPixel = 2;
	static final int blankRect = -1;
	static final int puzzleRect = 0;
	static final int rectWidth = 100;
	//
	private HTMLProcessor input;
	//
	
	public PuzzlePanel() throws Exception{
		input = new HTMLProcessor(); //HTML saver
		Dimension screen = new Dimension(SCREEN_HEIGHT,SCREEN_WIDTH);
		setVisible(true);
		this.setPreferredSize(screen);
		//
	}
	
	public void paint(Graphics g){
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 1200, 900);
		g.setColor(Color.black);
		int startX = 45;
		int startY = 40;
		for(int i = 0; i < puzzleSize;i++){
			for(int j = 0; j < puzzleSize;j++){
				if(input.puzzle[i][j].currentLetter == "-1")
					g.fillRect(startX, startY, rectWidth, rectWidth);
				else if(input.puzzle[i][j].currentLetter == ""){
					g.drawRect(startX, startY, rectWidth, rectWidth);
				}
				else{} //do nothing
				if(input.puzzle[i][j].questionNo != ""){
					g.drawRect(startX, startY, rectWidth, rectWidth);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
					g.drawString(input.puzzle[i][j].questionNo, startX + 10, startY + 20);
				}
				startY = startY + rectWidth + blankRectPixel;
			}
			startY = 40;
			startX = startX + rectWidth + blankRectPixel;
		}
		g.drawLine(600, 0, 600, 600);
		//ACROSS HINTS READ FROM HINTS[0] STRING ARRAY AND GUI COMPONENTS//
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
		g.drawString("Across", 620, 50);
		g.drawLine(600, 52, 1200, 52);
		int hintY = 70;
		for(int i = 0; i < input.hints[0].size(); i++){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
			g.drawString(input.hints[0].get(i), 630, hintY);
			hintY = hintY + 30;
		}
		////////////
		//DOWN HINTS READ FROM HINTS[1] STRING ARRAY AND GUI COMPONENTS//
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
		g.drawLine(600, 300, 1200, 300);
		g.drawString("Down", 620, 320);
		g.drawLine(600, 322, 1200, 322);
		hintY = 340;
		for(int i = 0; i < input.hints[1].size(); i++){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
			g.drawString(input.hints[1].get(i), 630, hintY);
			hintY = hintY + 30;
		}
		g.drawLine(0, 570, 1200, 570);
		g.drawLine(0, 600, 1200, 600);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0,570,1200,30);   ///GUI COMPONENTS
		g.fillRect(0, 0, 1200, 30);
		g.fillRect(0, 0, 30, 900);
		g.fillRect(1155, 0, 30, 900);
		g.fillRect(0, 825, 1200, 30);
		g.setColor(Color.BLUE);
		//
		g.setColor(Color.WHITE);  //PRORAM LOG
		g.fillRect(50, 620, 500, 180);
	}
}
