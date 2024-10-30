package mainApp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	
	private static final int ATLAS_WIDTH = 128 * MainApp.PIXEL_DIM;
	private static final int ATLAS_HEIGHT = 88 * MainApp.PIXEL_DIM;
	private static final int OPTIONS_Y = 16 * MainApp.PIXEL_DIM;
	private static final int GAME_WIDTH = 768;
	private static final int GAME_HEIGHT = GAME_WIDTH;
	private static final int SPRITE_WIDTH = 48;
	private static final int SPRITE_HEIGHT = SPRITE_WIDTH;
	
	private static final Color BLUE_COLOR = new Color(63, 73, 204);
	private static final Color GREEN_COLOR = new Color(14,209, 69);
	private static final int COLLIDER_THICKNESS = 4;
	private static final BasicStroke RECT_STROKE = new BasicStroke(COLLIDER_THICKNESS);
	
	private int gridX = 0;
	private int gridY = 0;
	private int selectedX = GAME_WIDTH;
	private int selectedY = 0;
	private int layerX = GAME_WIDTH;
	private int newLayer = GAME_WIDTH;
	private int selectedLayer = 0;
	private int newSelectedLayer = 0;
	
	private Point topLeft = null;
	private Point botRight = null;
	private ColoredRectangle tempRect;
	private ArrayList<ColoredRectangle> colliders = new ArrayList<ColoredRectangle>(); //Collision Layer
	
	private Point[][] layer1 = new Point[16][16]; //Background Layer
	private Point[][] layer2 = new Point[16][16]; //Foreground Layer
	private Point[][] layer3 = new Point[16][16]; //Detail Layer
	//private Point[][] layer4 = new Point[16][16]; //Collision Layer
	private Point[][] layer5 = new Point[16][16]; //Object Layer
	private Boolean[] renderLayers = new Boolean[] {true,true,true,true,true,false};
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
		if (tempRect != null) {
			drawRectangle(g2, tempRect);
		}
		if (renderLayers[3]) {
			drawColliders(g2, colliders);
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
				g.drawImage(scaledMap, i * SPRITE_WIDTH, j * SPRITE_HEIGHT, i * SPRITE_WIDTH + SPRITE_WIDTH, j * SPRITE_HEIGHT + SPRITE_HEIGHT, ((int)layer[i][j].getX() - GAME_WIDTH), (int)layer[i][j].getY() + 1, (((int)layer[i][j].getX() - GAME_WIDTH)) + SPRITE_WIDTH, ((int)layer[i][j].getY()) + SPRITE_HEIGHT, null);
			}
		}
	}
	
	public void drawColliders(Graphics2D g, ArrayList<ColoredRectangle> colliders) {
		for (ColoredRectangle r : colliders) {
			drawRectangle(g, r);
		}
	}
	
	public void drawRectangle(Graphics2D g2, ColoredRectangle r) {
		Stroke oldStroke = g2.getStroke();
		g2.setColor(r.getColor());
		g2.setStroke(RECT_STROKE);
		g2.drawRect((int)r.getX() + (COLLIDER_THICKNESS / 2), (int)r.getY() + (COLLIDER_THICKNESS / 4), (int)r.getWidth() - (COLLIDER_THICKNESS ), (int)r.getHeight() - (COLLIDER_THICKNESS));
		g2.setStroke(oldStroke);
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
		else if (x <= GAME_WIDTH && selectedLayer != 4) {
			if (selectedLayer == 1) {
				layer1[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 2) {
				layer2[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 3) {
				layer3[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 5) {
				layer5[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			}
		} else if (x <= GAME_WIDTH && selectedLayer == 4) {
			topLeft = new Point(x - (x % SPRITE_WIDTH), y - (y % SPRITE_HEIGHT));
			botRight = new Point();
			tempRect = new ColoredRectangle(topLeft, new Dimension((int)(botRight.getX() - topLeft.getX()),(int)(botRight.getX() - topLeft.getX())), GREEN_COLOR);
		}
	}
	
	public void doMouseRelease(int x, int y) {
		x -= 8;
		y -= 30;
		if (tempRect != null) {
			colliders.add(new ColoredRectangle(tempRect));
			tempRect = null;
		}
		
	}
	
	public void doMouseHold(int x, int y) {
		x -= 8;
		y -= 30;
		int botRightX;
		int botRightY;
		if (topLeft != null) {
			botRightX = x + (SPRITE_WIDTH - (x % SPRITE_WIDTH));
			botRightY = y + (SPRITE_HEIGHT - (y % SPRITE_HEIGHT));
			botRight.setLocation(botRightX, botRightY);
			if (tempRect != null) {
				tempRect.setSize((int)(botRight.getX() - topLeft.getX()),(int)(botRight.getY() - topLeft.getY()));
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
			colliders.clear();
		}
		if (renderLayers[4]) {
			layer5 = new Point[16][16];
		}
	}
	
	public void doPrint() {
		
	}
}
