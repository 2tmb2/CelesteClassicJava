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
	private BufferedImage scaledMap;
	private BufferedImage confirm;
	private BufferedImage blank;
	private BufferedImage checkmark;
	private BufferedImage ex;
	private BufferedImage grid;
	private static final int ATLAS_WIDTH = 128 * 6;
	private static final int ATLAS_HEIGHT = 88 * 6;
	private static final int OPTIONS_Y = 16 * 6;
	private static final int GAME_WIDTH = 768;
	private static final int GAME_HEIGHT = GAME_WIDTH;
	private static final int SPRITE_WIDTH = 48;
	private static final int SPRITE_HEIGHT = SPRITE_WIDTH;
	private int gridX = 0;
	private int gridY = 0;
	private int selectedX = GAME_WIDTH;
	private int selectedY = 0;
	private int layerX = GAME_WIDTH;
	private int newLayer = GAME_WIDTH;
	private int selectedLayer = 0;
	private int newSelectedLayer = 0;
	private Point[][] layer1 = new Point[16][16]; //Background Layer
	private Point[][] layer2 = new Point[16][16]; //Foreground Layer
	private Point[][] layer3 = new Point[16][16]; //Detail Layer
	private Point[][] layer4 = new Point[16][16]; //Collision Layer
	private Point[][] layer5 = new Point[16][16]; //Object Layer
	private Boolean[] renderLayers = new Boolean[] {true,true,true,true,true,true};
	public LevelEditor() {
		try {
			map = ImageIO.read(new File("src/Sprites/atlas.png"));
			scaledMap = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
			confirm = ImageIO.read(new File("src/Sprites/confirm.png"));
			blank = ImageIO.read(new File("src/Sprites/blank.png"));
			checkmark = ImageIO.read(new File("src/Sprites/checkmark.png"));
			ex = ImageIO.read(new File("src/Sprites/ex.png"));
			grid = ImageIO.read(new File("src/Sprites/grid.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.white);
		g2.fillRect(GAME_WIDTH, 0, 850, GAME_WIDTH);
		g.drawImage(scaledMap,GAME_WIDTH,0,ATLAS_WIDTH, ATLAS_HEIGHT + OPTIONS_Y,null);
		g2.setColor(Color.black);
		g2.drawRect(selectedX, selectedY, SPRITE_WIDTH, SPRITE_HEIGHT);
		g2.fillRect(layerX, ATLAS_HEIGHT + OPTIONS_Y + (1 * 6), SPRITE_WIDTH, 1 * 6);
		
		if (selectedLayer == 7 || selectedLayer == 9) {
			g.drawImage(confirm, GAME_WIDTH, ATLAS_HEIGHT + OPTIONS_Y + (3 * 6), 56 * 6, SPRITE_HEIGHT, null);
		} else {
			g.drawImage(blank, GAME_WIDTH, ATLAS_HEIGHT + OPTIONS_Y + (3 * 6), 56 * 6, SPRITE_HEIGHT, null);
		}
		g2.setColor(Color.red);
		for (int i = 1; i <= renderLayers.length; i++) {
			if (!renderLayers[i - 1]) {
				g.drawImage(ex, GAME_WIDTH + (i * SPRITE_WIDTH), ATLAS_HEIGHT + 6, SPRITE_WIDTH, SPRITE_HEIGHT,null);
			} else {
				g.drawImage(checkmark, GAME_WIDTH + (i * SPRITE_WIDTH), ATLAS_HEIGHT + 6, SPRITE_WIDTH, SPRITE_HEIGHT,null);
			}
		}
		if (renderLayers[0]) {
			drawLayer(g2, layer1);
		}
		if (renderLayers[1]) {
			drawLayer(g2, layer2);	
		}
		if (renderLayers[2]) {
			drawLayer(g2, layer3);
		}
		if (renderLayers[3]) {
			drawLayer(g2, layer4);
		}
		if (renderLayers[4]) {
			drawLayer(g2, layer5);
		}
		if (renderLayers[5]) {
			g.drawImage(grid, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
		}
		
	}
	
	public void drawLayer(Graphics2D g, Point[][] layer) {
		for (int i = 0; i < layer.length; i++) {
			for (int j = 0; j < layer[0].length; j++) {
				if (layer[i][j] == null) continue;
				//For some reason there is a vertical offset of 1 when drawing the sprites, so the source y1 is increased by 1
				g.drawImage(scaledMap, i * SPRITE_WIDTH, j * SPRITE_HEIGHT, i * SPRITE_WIDTH + SPRITE_WIDTH, j * SPRITE_HEIGHT + SPRITE_HEIGHT, (layer[i][j].getX() - GAME_WIDTH), layer[i][j].getY() + 1, ((layer[i][j].getX() - GAME_WIDTH)) + SPRITE_WIDTH, (layer[i][j].getY()) + SPRITE_HEIGHT, null);
			}
		}
	}
	
	public void doMouseClick(int x, int y) {
		x -= 8;
		y -= 30;
		if (x > GAME_WIDTH && x < GAME_WIDTH + ATLAS_WIDTH) {
			gridX = x - (x % SPRITE_WIDTH);
			gridY = y - (y % SPRITE_HEIGHT);
			if (y < ATLAS_HEIGHT) {
				selectedX = gridX;
				selectedY = gridY;
			} else if (y > ATLAS_HEIGHT + (OPTIONS_Y / 2) && y < ATLAS_HEIGHT + OPTIONS_Y) {
				newLayer = gridX;
				newSelectedLayer = ((newLayer - GAME_WIDTH) / SPRITE_WIDTH);
				if (selectedLayer == 7 || selectedLayer == 9) {
					if (newSelectedLayer == selectedLayer) {
						newSelectedLayer = 0;
						newLayer = GAME_WIDTH;
					}
					if (selectedLayer == 7) {
						doPrint();
					} else {
						doClear();
					}
				}
				layerX = newLayer;
				selectedLayer = newSelectedLayer;
				
			} else if (y > ATLAS_HEIGHT && y < ATLAS_HEIGHT + (OPTIONS_Y / 2)) {
				int index = ((gridX - GAME_WIDTH) / SPRITE_WIDTH) - 1;
				if (index == -1) {
					Boolean switchToF = true;
					for (int i = 0; i < renderLayers.length; i++) {
						if (!renderLayers[i]) {
							renderLayers[i] = !renderLayers[i];
							switchToF = false;
						}
					}
					if (switchToF) {
						for (int i = 0; i < renderLayers.length; i++) {
							renderLayers[i] = !renderLayers[i];
						}
					}
				} else if (index >= 0 && index <= 5) {
					renderLayers[index] = !renderLayers[index];
				}
				
				
			}
			
		}
		else if (x <= GAME_WIDTH) {
			if (selectedLayer == 1) {
				layer1[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 2) {
				layer2[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 3) {
				layer3[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 4) {
				layer4[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 5) {
				layer5[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			}
			
		}
	}
	
	public void doClear() {
		if (renderLayers[0]) {
			layer1 = new Point[16][16];
		}
		if (renderLayers[1]) {
			layer2 = new Point[16][16];
		}
		if (renderLayers[2]) {
			layer3 = new Point[16][16];
		}
		if (renderLayers[3]) {
			layer4 = new Point[16][16];
		}
		if (renderLayers[4]) {
			layer5 = new Point[16][16];
		}
	}
	
	public void doPrint() {
		
	}
}
