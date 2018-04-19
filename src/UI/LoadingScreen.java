package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LoadingScreen extends JPanel{
	private static final int SCREEN_HEIGHT = 900;
	private static final int SCREEN_WIDTH = 1200;
	private BufferedImage loadingImage;
	private Image loadingCircle;
	public LoadingScreen(){
		try {
			loadingImage = ImageIO.read(getClass().getResourceAsStream("/resources/images/loading.png"));
		}	catch(IOException exc) {
				exc.printStackTrace();
		}
		loadingCircle = new ImageIcon(getClass().getResource("/resources/images/loading_circle.gif")).getImage();
		Dimension screen = new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT);
		setVisible(true);
		this.setPreferredSize(screen);
	}
	public void paint(Graphics g){
		g.drawImage(loadingImage, 0, 0, this);
		g.drawImage(loadingCircle, 562, 580, this);
	}
}
