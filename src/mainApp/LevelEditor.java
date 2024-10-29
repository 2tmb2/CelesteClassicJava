package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class LevelEditor extends JComponent {
	private BufferedImage image;
	private static final int ATLAS_WIDTH = 128 * 6;
	private static final int ATLAS_HEIGHT = 88 * 6;
	private int selectedX = 768;
	private int selectedY = 0;
	private Point[][] spriteMap = new Point[16][16];
	public LevelEditor() {
		try {
			image = ImageIO.read(new File("src/Sprites/atlas.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.white);
		g2.fillRect(768, 0, 850, 768);
		g.drawImage(image,768,0,ATLAS_WIDTH, ATLAS_HEIGHT,null);
		g2.setColor(Color.black);
		g2.drawRect(selectedX, selectedY, 48, 48);
		
		for (int i = 0; i < spriteMap.length; i++) {
			for (int j = 0; j < spriteMap[0].length; j++) {
				if (spriteMap[i][j] == null) continue;
				g.drawImage(image, i * 48, j * 48, i * 48 + 48, j * 48 + 48, (spriteMap[i][j].getX() - 768) / 6, spriteMap[i][j].getY() / 6, ((spriteMap[i][j].getX() - 768) / 6) + 8, (spriteMap[i][j].getY() / 6) + 8, null);
			}
		}
		//g.drawImage(image, 768, 0, 768 + (8 * 6), 0 + (8 * 6), 0, 16, 8, 24, null);
		
	}
	
	public void doMouseClick(int x, int y) {
		x -= 8;
		y -= 30;
		if (x > 768 && x < 768 + ATLAS_WIDTH && y < ATLAS_HEIGHT) {
			selectedX = x - (x % 48);
			selectedY = y - (y % 48);
		}
		if (x <= 768) {
			System.out.println(x / 48 + " " + y / 48);
			spriteMap[x / 48][y / 48] = new Point(selectedX, selectedY);
		}
	}
}
