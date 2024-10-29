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
	private BufferedImage map;
	private BufferedImage confirm;
	private BufferedImage blank;
	private static final int ATLAS_WIDTH = 128 * 6;
	private static final int ATLAS_HEIGHT = 88 * 6;
	private static final int OPTIONS_Y = 16 * 6;
	private int selectedX = 768;
	private int selectedY = 0;
	private int layerX = 768;
	private int newLayer = 768;
	private int selectedLayer = 0;
	private int newSelectedLayer = 0;
	private Point[][] layer1 = new Point[16][16]; //Background Layer
	private Point[][] layer2 = new Point[16][16]; //Foreground Layer
	private Point[][] layer3 = new Point[16][16]; //Detail Layer
	private Point[][] layer4 = new Point[16][16]; //Collision Layer
	private Point[][] layer5 = new Point[16][16]; //Object Layer
	private Boolean[] renderLayers = new Boolean[5];
	public LevelEditor() {
		try {
			map = ImageIO.read(new File("src/Sprites/atlas.png"));
			confirm = ImageIO.read(new File("src/Sprites/confirm.png"));
			blank = ImageIO.read(new File("src/Sprites/blank.png"));
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
		g.drawImage(map,768,0,ATLAS_WIDTH, ATLAS_HEIGHT + OPTIONS_Y,null);
		g2.setColor(Color.black);
		g2.drawRect(selectedX, selectedY, 48, 48);
		g2.fillRect(layerX, ATLAS_HEIGHT + OPTIONS_Y + (1 * 6), 8 * 6, 1 * 6);
		
		if (selectedLayer == 7 || selectedLayer == 9) {
			g.drawImage(confirm, 768, ATLAS_HEIGHT + OPTIONS_Y + (3 * 6), 56 * 6, 8 * 6, null);
		} else {
			g.drawImage(blank, 768, ATLAS_HEIGHT + OPTIONS_Y + (3 * 6), 56 * 6, 8 * 6, null);
		}
		drawLayer(g2, layer1);
		
	}
	
	public void drawLayer(Graphics2D g, Point[][] layer) {
		for (int i = 0; i < layer.length; i++) {
			for (int j = 0; j < layer[0].length; j++) {
				if (layer[i][j] == null) continue;
				g.drawImage(map, i * 48, j * 48, i * 48 + 48, j * 48 + 48, (layer[i][j].getX() - 768) / 6, layer[i][j].getY() / 6, ((layer[i][j].getX() - 768) / 6) + 8, (layer[i][j].getY() / 6) + 8, null);
			}
		}
	}
	
	public void doMouseClick(int x, int y) {
		x -= 8;
		y -= 30;
		if (x > 768 && x < 768 + ATLAS_WIDTH) {
			if (y < ATLAS_HEIGHT) {
				selectedX = x - (x % 48);
				selectedY = y - (y % 48);
			} else if (y > ATLAS_HEIGHT + (OPTIONS_Y / 2) && y < ATLAS_HEIGHT + OPTIONS_Y) {
				newLayer = x - (x % 48);
				newSelectedLayer = ((newLayer - 768) / 48);
				if (selectedLayer == 7 || selectedLayer == 9) {
					if (newSelectedLayer == selectedLayer) {
						newSelectedLayer = 0;
						newLayer = 768;
					}
					if (selectedLayer == 7) {
						doPrint();
					} else {
						doClear();
					}
				}
				layerX = newLayer;
				selectedLayer = newSelectedLayer;
				System.out.println(selectedLayer);
				
			}
			
		}
		else if (x <= 768) {
			layer1[x / 48][y / 48] = new Point(selectedX, selectedY);
		}
	}
	
	public void doClear() {
		layer1 = new Point[16][16];
	}
	
	public void doPrint() {
		
	}
}
