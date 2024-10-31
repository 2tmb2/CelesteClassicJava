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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class LevelEditor extends JComponent {
	
	private BufferedImage map;
	private BufferedImage scaledMap;
	private BufferedImage confirm;
	private BufferedImage blank;
	private BufferedImage checkmark;
	private BufferedImage ex;
	private BufferedImage grid;
	private BufferedImage font;
	
	private static final int ATLAS_WIDTH = 128 * MainApp.PIXEL_DIM;
	private static final int ATLAS_HEIGHT = 88 * MainApp.PIXEL_DIM;
	private static final int OPTIONS_Y = 16 * MainApp.PIXEL_DIM;
	private static final int GAME_WIDTH = 768;
	private static final int GAME_HEIGHT = GAME_WIDTH;
	private static final int SPRITE_WIDTH = 48;
	private static final int SPRITE_HEIGHT = SPRITE_WIDTH;
	private static final int FONT_WIDTH = 3;
	private static final int FONT_HEIGHT = 5;
	
	private static final Color BLUE_COLOR = new Color(63, 73, 204);
	private static final Color GREEN_COLOR = new Color(14,209, 69);
	private static final int COLLIDER_THICKNESS = 4;
	private static final BasicStroke RECT_STROKE = new BasicStroke(COLLIDER_THICKNESS);
	
	private MainApp mainApp;
	
	private boolean drawDialog = false;
	
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
	
	private Point[][] environmentLayer = new Point[16][16]; //Background Layer
	private Point[][] backgroundLayer = new Point[16][16]; //Foreground Layer
	private Point[][] layer3 = new Point[16][16]; //Detail Layer
	
	private HashMap<List<Integer>, String> data;
	//private Point[][] layer4 = new Point[16][16]; //Collision Layer
	private Point[][] objectLayer = new Point[16][16]; //Object Layer
	private Boolean[] renderLayers = new Boolean[] {true,true,true,true,true,false};
	public LevelEditor(MainApp mainApp) {
		try {
			map = ImageIO.read(new File("src/Sprites/atlas.png"));
			scaledMap = ImageIO.read(new File("src/Sprites/atlasScaled.png"));
			confirm = ImageIO.read(new File("src/Sprites/confirm.png"));
			blank = ImageIO.read(new File("src/Sprites/blank.png"));
			checkmark = ImageIO.read(new File("src/Sprites/checkmark.png"));
			ex = ImageIO.read(new File("src/Sprites/ex.png"));
			grid = ImageIO.read(new File("src/Sprites/grid.png"));
			font = ImageIO.read(new File("src/Sprites/font.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mainApp = mainApp;
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
		
		if (drawDialog) {
			drawText(g2, new Point(768, 650), "ESC TO CANCEL");
			drawText(g2, new Point(768, 700), "LVL: ");
		} else {
			g2.setColor(Color.white);
			g2.fillRect(780, 650, 500, 200);
		}
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
			drawLayer(g2, environmentLayer);
		}
		if (renderLayers[1]) {
			drawLayer(g2, backgroundLayer);	
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
			drawLayer(g2, objectLayer);
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
	
	public ColoredRectangle getColliderAtPoint(int x, int y)
	{
		for (ColoredRectangle r : colliders) {
			if ((r.getX())/SPRITE_WIDTH == x && (r.getY()/SPRITE_WIDTH) == y)
			{
				return r;
			}
		}
		return null;
	}
	
	public String getColliderSize(ColoredRectangle c)
	{
		return (int)c.getWidth()/48 + "" + (int)c.getHeight()/48;
	}
	
	public void drawRectangle(Graphics2D g, ColoredRectangle r) {
		Stroke oldStroke = g.getStroke();
		g.setColor(r.getColor());
		g.setStroke(RECT_STROKE);
		g.drawRect((int)r.getX() + (COLLIDER_THICKNESS / 2), (int)r.getY() + (COLLIDER_THICKNESS / 4), (int)r.getWidth() - (COLLIDER_THICKNESS ), (int)r.getHeight() - (COLLIDER_THICKNESS));
		g.setStroke(oldStroke);
	}
	
	public void drawText(Graphics2D g, Point location, String text) {
		text = text.toUpperCase();
		Point fPoint;
		for (int i = 0; i < text.length(); i++) {
			fPoint = FontLocs.getLoc(text.charAt(i));
			g.drawImage(font, (int)location.getX(), (int)location.getY(), (int)location.getX() + (FONT_WIDTH * MainApp.PIXEL_DIM), (int)location.getY() + (FONT_HEIGHT * MainApp.PIXEL_DIM), (int)fPoint.getX(), (int)fPoint.getY(), (int)fPoint.getX() + FONT_WIDTH, (int)fPoint.getY() + FONT_HEIGHT, null);
			location.setLocation(location.getX() + (4 * MainApp.PIXEL_DIM), location.getY());
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
		else if (x <= GAME_WIDTH && selectedLayer != 4) {
			if (selectedLayer == 1) {
				environmentLayer[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 2) {
				backgroundLayer[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 3) {
				layer3[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 5) {
				objectLayer[x / SPRITE_WIDTH][y / SPRITE_WIDTH] = new Point(selectedX, selectedY);
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
			environmentLayer = new Point[16][16];
		}
		if (renderLayers[1]) {
			backgroundLayer = new Point[16][16];
		}
		if (renderLayers[2]) {
			layer3 = new Point[16][16];
		}
		if (renderLayers[3]) {
			colliders.clear();
		}
		if (renderLayers[4]) {
			objectLayer = new Point[16][16];
		}
	}
	
	public void doPrint() {
		
		data = new HashMap<List<Integer>, String>();
		data.put(Arrays.asList(2,1), "pp");
		data.put(Arrays.asList(1,1), "^1");
		data.put(Arrays.asList(6,1), "rr");
		data.put(Arrays.asList(7,1), "dd");
		data.put(Arrays.asList(11,1), "v1");
		data.put(Arrays.asList(11,2), ">1");
		data.put(Arrays.asList(11,3), "<1");
		data.put(Arrays.asList(10,1), "ss");
		data.put(Arrays.asList(8,0), "kk");
		data.put(Arrays.asList(4,1), "cc");
		data.put(Arrays.asList(1,0), "mm");
		data.put(Arrays.asList(0,4), "bb");
		String levelDataString = "";
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (objectLayer[j][i] != null)
				{
					List<Integer> point = Arrays.asList((int)(objectLayer[j][i].getX()-(768))/SPRITE_WIDTH, (int)objectLayer[j][i].getY()/SPRITE_WIDTH);
					if (data.get(point) != null)
					{
						levelDataString += data.get(point);
					}
					else
					{
						levelDataString += "--";
					}
				}
				else if (getColliderAtPoint(j,i) != null)
				{
					levelDataString += getColliderSize(getColliderAtPoint(j,i));
				}
				else
				{
					levelDataString += "--";
				}
				if (j != 15)
				{
					levelDataString += " ";
				}
			}
			levelDataString += "\n";
		}
		for (int i = 0; i < environmentLayer.length; i++)
		{
			levelDataString += "\n";
			for (int j = 0; j < environmentLayer[0].length; j++)
			{
				if (environmentLayer[j][i] != null)
					levelDataString += environmentLayer[j][i].getX() + "," + environmentLayer[j][i].getY();
				else if (backgroundLayer[j][i] != null)
				{
					levelDataString += backgroundLayer[j][i].getX() + "," + backgroundLayer[j][i].getY();
				}
				else
					levelDataString += "-";
				if (j != 15)
				{
					levelDataString += " ";
				}
			}
		}
		System.out.println(levelDataString);
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("src/LevelData/"));
		chooser.showSaveDialog(chooser);
		File f = chooser.getSelectedFile();
		try (FileWriter writer = new FileWriter(f)) {
			writer.write(levelDataString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (NullPointerException e) {
			//e.printStackTrace();
		}
	}
	
	
	public void clearPrint() {
		drawDialog = false;
	}
}
