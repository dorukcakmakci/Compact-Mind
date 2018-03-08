import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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
	private Control mouse;
	private KeyInput keyboard;
	//
	public BufferedImage revealImage;
	public BufferedImage startImage;
	public BufferedImage readFileImage;
	//
	private boolean isRevealed;
	private boolean isRead;
	private String[][] puzzle;
	//
	public int selectedRectX = -1;
	public int selectedRectY = -1;
	//
	public String fileToRead;

	public PuzzlePanel() throws Exception{
		fileToRead = "";
		isRevealed = false;
		isRead = false;
		try {
			revealImage = ImageIO.read(getClass().getResourceAsStream("/resources/images/reveal_image.png"));
		}	catch(IOException exc) {
			exc.printStackTrace();
		}
		try {
			startImage = ImageIO.read(getClass().getResourceAsStream("/resources/images/start_image.png"));
		}	catch(IOException exc) {
			exc.printStackTrace();
		}
		try {
			readFileImage = ImageIO.read(getClass().getResourceAsStream("/resources/images/file_read.png"));
		}	catch(IOException exc) {
			exc.printStackTrace();
		}
		puzzle = new String[5][5];
		for(int i = 0;i<puzzleSize;i++){
			for(int j = 0;j<puzzleSize;j++){
				puzzle[i][j] = "";
			}
		}

		mouse = new Control(); // mouse adapter
		keyboard = new KeyInput();
		addMouseListener(mouse);
		addKeyListener(keyboard);
		this.setFocusable(true);
		this.requestFocus();
		input = new HTMLProcessor(); //HTML saver
		repaint();
		Dimension screen = new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT);
		setVisible(true);
		this.setPreferredSize(screen);
		this.setFocusable(true);
		this.requestFocus();
		repaint();
		//
	}

	public void paint(Graphics g){
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 1200, 900);
		g.setColor(Color.black);
		int startX = 45;
		int startY = 40;

		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		for(int i = 0; i < puzzleSize;i++){
			for(int j = 0; j < puzzleSize;j++){
				if(input.puzzle[i][j].currentLetter == "-1"){
					g.setColor(Color.black);
					g.fillRect(startX, startY, rectWidth, rectWidth); // -1 for blank
				}
				else if(input.puzzle[i][j].currentLetter != ""){
					g.setColor(Color.black);
					g.drawRect(startX, startY, rectWidth, rectWidth);
					g.drawString(puzzle[i][j],startX+50,startY+60);
				}
				else{
					g.setColor(Color.black);
					g.drawRect(startX, startY, rectWidth, rectWidth);
				}
				if(input.puzzle[i][j].questionNo != ""){
					g.setColor(Color.black);
					g.drawRect(startX, startY, rectWidth, rectWidth);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString(input.puzzle[i][j].questionNo, startX + 10, startY + 20);
				}
				startY = startY + rectWidth + blankRectPixel;
			}
			startY = 40;
			startX = startX + rectWidth + blankRectPixel;
		}
		g.setColor(Color.black);
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
		//GRAY RECTANGLE GUI COMPONENTS
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0,570,1200,30);
		g.fillRect(0, 0, 1200, 30);
		g.fillRect(0, 0, 30, 900);
		g.fillRect(1155, 0, 45, 900);
		g.fillRect(0, 870, 1200, 30);
		/////////////////////////////
		g.drawLine(0, 570, 1200, 570);
		g.drawLine(0, 600, 1200, 600);

		g.setColor(Color.BLUE);
		// REVEAL/START/READ IMAGES
		g.drawImage(startImage, 600, 620, this);
		g.drawImage(revealImage, 680, 620, this);
		g.drawImage(readFileImage,760,620,this);
		//READ TEXT
		g.setColor(Color.WHITE);
		g.fillRect(600, 700, 235,30);
		g.setColor(Color.black);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		g.drawString(fileToRead,610,720);
		//
		g.setColor(Color.WHITE);  //PRORAM LOG
		g.fillRect(50, 620, 520, 230);
		repaint();
		///DRAWING REVEALED PUZZLE
		if(isRevealed == true){
			startX = 880;
			startY = 620;
			int revealPuzzleSize = 45;
			g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			for(int i = 0; i < puzzleSize;i++){
				for(int j = 0; j < puzzleSize;j++){
					if(input.puzzle[i][j].currentLetter == "-1"){
						g.setColor(Color.black);
						g.fillRect(startX, startY, revealPuzzleSize, revealPuzzleSize); // -1 for blank
					}
					else if(input.puzzle[i][j].currentLetter != ""){
						g.setColor(Color.black);
						g.drawRect(startX, startY, revealPuzzleSize, revealPuzzleSize);
						g.drawString(input.puzzle[i][j].currentLetter,startX+20,startY+25);
					}
					else{
						g.setColor(Color.black);
						g.drawRect(startX, startY, revealPuzzleSize, revealPuzzleSize);
					}
					if(input.puzzle[i][j].questionNo != ""){
						g.setColor(Color.black);
						g.drawRect(startX, startY, revealPuzzleSize, revealPuzzleSize);
						g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
						g.drawString(input.puzzle[i][j].questionNo, startX + 6, startY + 15);
					}
					startY = startY + revealPuzzleSize + blankRectPixel;
				}
				startY = 620;
				startX = startX + revealPuzzleSize + blankRectPixel;
			}
		}
	}
	public void reveal(){
		isRevealed = isRevealed == false;
		repaint();

	}
	public void start(){
		System.out.println("Start called");
	}
	public void read(){System.out.println("Read called");
		//public htmli fileToRead stringi ile çağırabiliriz
		input.readPuzzle(fileToRead);
		isRead = false;
		fileToRead = "";
		for(int i = 0; i < puzzleSize;i++)
			for(int j = 0; j < puzzleSize;j++)
				puzzle[i][j] = "";
	}
	public void fileInput(){System.out.println("File Input Called");}
	public void selectRect(int x,int y){
		isRead = false;
		selectedRectX = x;
		selectedRectY = y;
		System.out.println(x +","+ y);
	}
	//Mouse adapter
	public class Control extends MouseAdapter{

		private int mouseX;
		private int mouseY;

		public void mousePressed(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			if(mouseX >= 600 && mouseY >= 620 && mouseX <= 675 && mouseY <= 685){
				start();
				isRead = false;
				mouseX = 0;
				mouseY = 0;
			}
			if(mouseX >= 680 && mouseY >= 620 && mouseX <= 755 && mouseY <= 685){
				reveal();
				isRead = false;
				mouseX = 0;
				mouseY = 0;
			}
			if(mouseX >= 600 && mouseY >= 700 && mouseX <= 825 && mouseY <= 730){
				isRead = true;
				fileInput();
			}
			if(mouseX >= 760 && mouseY >= 620 && mouseX <= 835 && mouseY <= 685){
				isRead = false;
				read();
			}
			/////// 0 Y
			if(mouseX >= 45 && mouseY >= 40 && mouseX <= 145 && mouseY <= 140){
				selectRect(0,0);
			}
			if(mouseX >= 147 && mouseY >= 40 && mouseX <= 245 && mouseY <= 140){
				selectRect(0,1);
			}
			if(mouseX >= 247 && mouseY >= 40 && mouseX <= 345 && mouseY <= 140){
				selectRect(0,2);
			}
			if(mouseX >= 347 && mouseY >= 40 && mouseX <= 445 && mouseY <= 140){
				selectRect(0,3);
			}
			if(mouseX >= 447 && mouseY >= 40 && mouseX <= 545 && mouseY <= 140){
				selectRect(0,4);
			}
			////////////// 1 Y
			if(mouseX >= 45 && mouseY >= 142 && mouseX <= 145 && mouseY <= 240){
				selectRect(1,0);
			}
			if(mouseX >= 147 && mouseY >= 142 && mouseX <= 245 && mouseY <= 240){
				selectRect(1,1);
			}
			if(mouseX >= 247 && mouseY >= 142 && mouseX <= 345 && mouseY <= 240){
				selectRect(1,2);
			}
			if(mouseX >= 347 && mouseY >= 142 && mouseX <= 445 && mouseY <= 240){
				selectRect(1,3);
			}
			if(mouseX >= 447 && mouseY >= 142 && mouseX <= 545 && mouseY <= 240){
				selectRect(1,4);
			}
			////////// 2 Y
			if(mouseX >= 45 && mouseY >= 242 && mouseX <= 145 && mouseY <= 340){
				selectRect(2,0);
			}
			if(mouseX >= 147 && mouseY >= 242 && mouseX <= 245 && mouseY <= 340){
				selectRect(2,1);
			}
			if(mouseX >= 247 && mouseY >= 242 && mouseX <= 345 && mouseY <= 340){
				selectRect(2,2);
			}
			if(mouseX >= 347 && mouseY >= 242 && mouseX <= 445 && mouseY <= 340){
				selectRect(2,3);
			}
			if(mouseX >= 447 && mouseY >= 242 && mouseX <= 545 && mouseY <= 340){
				selectRect(2,4);
			}
			///// 3 Y
			if(mouseX >= 45 && mouseY >= 342 && mouseX <= 145 && mouseY <= 440){
				selectRect(3,0);
			}
			if(mouseX >= 147 && mouseY >= 342 && mouseX <= 245 && mouseY <= 440){
				selectRect(3,1);
			}
			if(mouseX >= 247 && mouseY >= 342 && mouseX <= 345 && mouseY <= 440){
				selectRect(3,2);
			}
			if(mouseX >= 347 && mouseY >= 342 && mouseX <= 445 && mouseY <= 440){
				selectRect(3,3);
			}
			if(mouseX >= 447 && mouseY >= 342 && mouseX <= 545 && mouseY <= 440){
				selectRect(3,4);
			}
			////// 4 Y
			if(mouseX >= 45 && mouseY >= 442 && mouseX <= 145 && mouseY <= 540){
				selectRect(4,0);
			}
			if(mouseX >= 147 && mouseY >= 442 && mouseX <= 245 && mouseY <= 540){
				selectRect(4,1);
			}
			if(mouseX >= 247 && mouseY >= 442 && mouseX <= 345 && mouseY <= 540){
				selectRect(4,2);
			}
			if(mouseX >= 347 && mouseY >= 442 && mouseX <= 445 && mouseY <= 540){
				selectRect(4,3);
			}
			if(mouseX >= 447 && mouseY >= 442 && mouseX <= 545 && mouseY <= 540){
				selectRect(4,4);
			}
		}
	}
	/// MOUSE LISTENER END
	public class KeyInput implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			char c = e.getKeyChar();
			if(isRead == true){
				if((int) c == 8)
					if(fileToRead.length() == 0)
						fileToRead = "";
					else
						fileToRead = fileToRead.substring(0,fileToRead.length()-1);
				else
					fileToRead = fileToRead + c;

			}
			else {
				if ((int) c == 8) {
					puzzle[selectedRectY][selectedRectX] = "";
					return;
				}
				String inp = "" + c;
				inp = inp.toUpperCase();
				if (selectedRectX == -1 && selectedRectY == -1)
					return;
				puzzle[selectedRectY][selectedRectX] = inp;
			}
			repaint();
		}
		@Override
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
}
