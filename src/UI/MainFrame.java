package UI;

import Parser.SeleniumConnection;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame{

	private static final String SCREEN_TITLE = "Compact Mind";
	private static final int SCREEN_WIDTH = 1200;
	private static final int SCREEN_HEIGHT = 900;
	private PuzzlePanel puzzle;
	private SeleniumConnection connection;

	private MainFrame() {
		init();
	}
	private void init(){
		LoadingScreen loading = new LoadingScreen();
		this.add(loading); //ADDING LOADING SCREEN WHILE SELENIUM IS DOWNLOADING THE TODAY'S PUZZLE
		this.setFocusable(true);
		Dimension size = new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT);
		setSize(size);
		setLayout(new GridLayout(1,1,0,0));
		setTitle(SCREEN_TITLE);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		//connection = new SeleniumConnection();
		try {
			puzzle = new PuzzlePanel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		loading.setVisible(false);
		this.remove(loading); //REMOVING THE LOADING SCREEN AND ADDING THE PUZZLE LAYOUT
		this.add(puzzle);
		puzzle.requestFocus();
		pack();
		repaint();
	}

	public static void main(String[] args) {
		MainFrame f = new MainFrame();
	}
}