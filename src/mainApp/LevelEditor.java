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

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import TextElements.FontLocs;

/**
 * This class is the entirety of the LevelEditor, including the ability to place
 * textures, collision, and objects, as well as saving the information to a
 * file.
 */
public class LevelEditor extends JComponent {
	private static final long serialVersionUID = 1L;
	private BufferedImage confirm;
	private BufferedImage blank;
	private BufferedImage checkmark;
	private BufferedImage ex;
	private BufferedImage grid;
	private BufferedImage font;

	private static final int ATLAS_WIDTH = 128 * Constants.PIXEL_DIM;
	private static final int ATLAS_HEIGHT = 88 * Constants.PIXEL_DIM;
	private static final int PALETTE_WIDTH = 850;
	private static final int OPTIONS_Y = 16 * Constants.PIXEL_DIM;
	private static final int GAME_HEIGHT = Constants.GAME_WIDTH;
	private static final int FONT_WIDTH = 3;
	private static final int FONT_HEIGHT = 5;

	private static final Color BLUE_COLOR = new Color(63, 73, 204);
	private static final Color GREEN_COLOR = new Color(14, 209, 69);
	private static final int BLUE_COLLIDER_Y = 9 * Constants.SPRITE_HEIGHT;
	private static final int BLUE_COLLIDER_X = Constants.GAME_WIDTH + (15 * Constants.SPRITE_WIDTH);
	private static final int MOUSE_Y_OFFSET = 30;
	private static final int MOUSE_X_OFFSET = 8;
	private static final int CONFIRMATION_WIDTH = 56 * Constants.PIXEL_DIM;
	private static final int CONFIRMATION_Y_OFFSET = 3 * Constants.PIXEL_DIM;
	private static final int COLLIDER_THICKNESS = 4;
	private static final BasicStroke RECT_STROKE = new BasicStroke(COLLIDER_THICKNESS);

	private int gridX = 0;
	private int gridY = 0;
	private int selectedX = Constants.GAME_WIDTH;
	private int selectedY = 0;
	private int layerX = Constants.GAME_WIDTH;
	private int newLayer = Constants.GAME_WIDTH;
	private int selectedLayer = 0;
	private int newSelectedLayer = 0;

	private Point topLeft = null;
	private Point botRight = null;
	private ColoredRectangle tempRect;
	private ArrayList<ColoredRectangle> colliders = new ArrayList<ColoredRectangle>(); // Collision Layer

	private Point[][] environmentLayer = new Point[16][16]; // Background Layer
	private Point[][] backgroundLayer = new Point[16][16]; // Foreground Layer
	private Point[][] layer3 = new Point[16][16]; // Detail Layer

	private HashMap<List<Integer>, String> data;
	// private Point[][] layer4 = new Point[16][16]; //Collision Layer
	private Point[][] objectLayer = new Point[16][16]; // Object Layer
	private Boolean[] renderLayers = new Boolean[] { true, true, true, true, true, false };

	/**
	 * Creates a level editor by opening all required images to create the ui
	 */
	public LevelEditor() {
		try {
			confirm = ImageIO.read(new File("src/Sprites/confirm.png"));
			blank = ImageIO.read(new File("src/Sprites/blank.png"));
			checkmark = ImageIO.read(new File("src/Sprites/checkmark.png"));
			ex = ImageIO.read(new File("src/Sprites/ex.png"));
			grid = ImageIO.read(new File("src/Sprites/grid.png"));
			font = ImageIO.read(new File("src/Sprites/font.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fillRect(Constants.GAME_WIDTH, 0, PALETTE_WIDTH, Constants.GAME_WIDTH);
		g.drawImage(MainApp.SCALED_MAP, Constants.GAME_WIDTH, 0, ATLAS_WIDTH, ATLAS_HEIGHT + OPTIONS_Y, null);
		g2.setColor(Color.black);
		g2.drawRect(selectedX, selectedY, Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT);
		g2.fillRect(layerX, ATLAS_HEIGHT + OPTIONS_Y + (1 * Constants.PIXEL_DIM), Constants.SPRITE_WIDTH,
				1 * Constants.PIXEL_DIM);

		if (selectedLayer == 7 || selectedLayer == 9) {
			g.drawImage(confirm, Constants.GAME_WIDTH, ATLAS_HEIGHT + OPTIONS_Y + (CONFIRMATION_Y_OFFSET),
					CONFIRMATION_WIDTH, Constants.SPRITE_HEIGHT, null);
		} else {
			g.drawImage(blank, Constants.GAME_WIDTH, ATLAS_HEIGHT + OPTIONS_Y + (CONFIRMATION_Y_OFFSET),
					CONFIRMATION_WIDTH, Constants.SPRITE_HEIGHT, null);
		}
		g2.setColor(Color.red);
		// Renders the visibility status markers for each layer
		for (int i = 1; i <= renderLayers.length; i++) {
			if (!renderLayers[i - 1]) {
				g.drawImage(ex, Constants.GAME_WIDTH + (i * Constants.SPRITE_WIDTH), ATLAS_HEIGHT + Constants.PIXEL_DIM,
						Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT, null);
			} else {
				g.drawImage(checkmark, Constants.GAME_WIDTH + (i * Constants.SPRITE_WIDTH),
						ATLAS_HEIGHT + Constants.PIXEL_DIM, Constants.SPRITE_WIDTH, Constants.SPRITE_HEIGHT, null);
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
			g.drawImage(grid, 0, 0, Constants.GAME_WIDTH, GAME_HEIGHT, null);
		}

	}

	/**
	 * Draws the provided layer onto the level view layers are arrays of points that
	 * reference the sprite that occupies that position in the array For example
	 * layer[2][3] contains the top left point of the sprite on the sprite map which
	 * the tile 3rd from the right and 4th from the top contains
	 * 
	 * @param g     the graphics object to draw on
	 * @param layer the array of points for the sprite information of each tile
	 */
	public void drawLayer(Graphics2D g, Point[][] layer) {
		for (int i = 0; i < layer.length; i++) {
			for (int j = 0; j < layer[0].length; j++) {
				if (layer[i][j] == null)
					continue;
				// For some reason 1 extra line is drawn from above the sprite on the scaled
				// map, so the sy1 is increased by 1 to compensate
				g.drawImage(MainApp.SCALED_MAP, i * Constants.SPRITE_WIDTH, j * Constants.SPRITE_HEIGHT,
						i * Constants.SPRITE_WIDTH + Constants.SPRITE_WIDTH,
						j * Constants.SPRITE_HEIGHT + Constants.SPRITE_HEIGHT,
						((int) layer[i][j].getX() - Constants.GAME_WIDTH), (int) layer[i][j].getY() + 1,
						(((int) layer[i][j].getX() - Constants.GAME_WIDTH)) + Constants.SPRITE_WIDTH,
						((int) layer[i][j].getY()) + Constants.SPRITE_HEIGHT, null);
			}
		}
	}

	/**
	 * Draws the green and blue boxes which denote the colliders the player can
	 * interact with in game
	 * 
	 * @param g         the graphics object to draw on
	 * @param colliders the list of colored rectangles to draw
	 */
	public void drawColliders(Graphics2D g, ArrayList<ColoredRectangle> colliders) {
		for (ColoredRectangle r : colliders) {
			drawRectangle(g, r);
		}
	}

	/**
	 * Determines if the provided x and y houses the top left of a collider box at
	 * that point. If there is a collider there, returns it
	 * 
	 * @param x the x location to check
	 * @param y the y location to check
	 * @return the colored rectangle at that point. Null if there is no collider
	 *         there
	 */
	public ColoredRectangle getColliderAtPoint(int x, int y) {
		for (ColoredRectangle r : colliders) {
			if ((r.getX()) / Constants.SPRITE_WIDTH == x && (r.getY() / Constants.SPRITE_WIDTH) == y) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Formats the width and height of the provided collider as a pair of 1 digit
	 * numbers representing the tile width and height of the collider
	 * 
	 * @param c the collider to find the size of
	 * @return the string representing the width and height of the collider
	 */
	public String getColliderSize(ColoredRectangle c) {
		return (int) c.getWidth() / Constants.SPRITE_WIDTH + "" + (int) c.getHeight() / Constants.SPRITE_HEIGHT;
	}

	/**
	 * Draws a colored rectangle with the border thickness specified by RECT_STROKE
	 * 
	 * @param g the graphics object to draw on
	 * @param r the colored rectangle to draw
	 */
	public void drawRectangle(Graphics2D g, ColoredRectangle r) {
		Stroke oldStroke = g.getStroke();
		g.setColor(r.getColor());
		g.setStroke(RECT_STROKE);
		g.drawRect((int) r.getX() + (COLLIDER_THICKNESS / 2), (int) r.getY() + (COLLIDER_THICKNESS / 4),
				(int) r.getWidth() - (COLLIDER_THICKNESS), (int) r.getHeight() - (COLLIDER_THICKNESS));
		g.setStroke(oldStroke);
	}

	/**
	 * Draws a string of text with the top left of the first letter at the specified
	 * location
	 * 
	 * @param g        the graphics object to draw on
	 * @param location the top left of the first letter
	 * @param text     the text to draw
	 */
	public void drawText(Graphics2D g, Point location, String text) {
		text = text.toUpperCase();
		Point fPoint;
		for (int i = 0; i < text.length(); i++) {
			fPoint = FontLocs.getLoc(text.charAt(i));
			g.drawImage(font, (int) location.getX(), (int) location.getY(),
					(int) location.getX() + (FONT_WIDTH * Constants.PIXEL_DIM),
					(int) location.getY() + (FONT_HEIGHT * Constants.PIXEL_DIM), (int) fPoint.getX(),
					(int) fPoint.getY(), (int) fPoint.getX() + FONT_WIDTH, (int) fPoint.getY() + FONT_HEIGHT, null);
			location.setLocation(location.getX() + (4 * Constants.PIXEL_DIM), location.getY());
		}
	}

	/**
	 * Performs the actions associated with a mouse click at the specified point
	 * 
	 * @param x the x location of the mouse click
	 * @param y the y location of the mouse click
	 */
	public void doMouseClick(int x, int y) {
		x -= MOUSE_X_OFFSET; // For some reason the actual location of the mouse is offset by these amounts
		y -= MOUSE_Y_OFFSET; // So all mouse math is done with the x and y altered by these amounts
		// Perform this code if the mouse is in the sprite map region
		if (x > Constants.GAME_WIDTH && x < Constants.GAME_WIDTH + ATLAS_WIDTH) {
			gridX = x - (x % Constants.SPRITE_WIDTH);
			gridY = y - (y % Constants.SPRITE_HEIGHT);
			if (y < ATLAS_HEIGHT) { // Perform this code if the mouse is in the image region of the sprite map
				selectedX = gridX;
				selectedY = gridY;
			}
			// Perform this code if the
			// mouse is in the
			// options region of the
			// sprite map - lol i dont know why it formatted like that
			else if (y > ATLAS_HEIGHT + (OPTIONS_Y / 2) && y < ATLAS_HEIGHT + OPTIONS_Y) {
				newLayer = gridX;
				newSelectedLayer = ((newLayer - Constants.GAME_WIDTH) / Constants.SPRITE_WIDTH);
				if (selectedLayer == 7 || selectedLayer == 9) {
					if (newSelectedLayer == selectedLayer) {
						newSelectedLayer = 0;
						newLayer = Constants.GAME_WIDTH;
					}
					if (selectedLayer == 7) {
						doPrint();
					} else {
						doClear();
					}
				}
				layerX = newLayer;
				selectedLayer = newSelectedLayer;

			}
			// Perform this code if the mouse is in the visibility region of the sprite map
			else if (y > ATLAS_HEIGHT && y < ATLAS_HEIGHT + (OPTIONS_Y / 2)) {
				int index = ((gridX - Constants.GAME_WIDTH) / Constants.SPRITE_WIDTH) - 1;
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
		// Perform this code if the mouse is in the level viewer region and the collider
		// layer is not selected
		else if (x <= Constants.GAME_WIDTH && selectedLayer != 4) {
			if (selectedLayer == 1) {
				environmentLayer[x / Constants.SPRITE_WIDTH][y / Constants.SPRITE_WIDTH] = new Point(selectedX,
						selectedY);
			} else if (selectedLayer == 2) {
				backgroundLayer[x / Constants.SPRITE_WIDTH][y / Constants.SPRITE_WIDTH] = new Point(selectedX,
						selectedY);
			} else if (selectedLayer == 3) {
				layer3[x / Constants.SPRITE_WIDTH][y / Constants.SPRITE_WIDTH] = new Point(selectedX, selectedY);
			} else if (selectedLayer == 5) {
				objectLayer[x / Constants.SPRITE_WIDTH][y / Constants.SPRITE_WIDTH] = new Point(selectedX, selectedY);
			}
		}
		// Perform this code if the mouse is in the level viewer region and the collider
		// layer is selected
		else if (x <= Constants.GAME_WIDTH && selectedLayer == 4) {
			Color rectColor;
			if (selectedX == BLUE_COLLIDER_X && selectedY == BLUE_COLLIDER_Y) {
				rectColor = BLUE_COLOR;
			} else
				rectColor = GREEN_COLOR;
			topLeft = new Point(x - (x % Constants.SPRITE_WIDTH), y - (y % Constants.SPRITE_HEIGHT));
			botRight = new Point();
			tempRect = new ColoredRectangle(topLeft,
					new Dimension((int) (botRight.getX() - topLeft.getX()), (int) (botRight.getX() - topLeft.getX())),
					rectColor);
		}
	}

	/**
	 * Perform this method when the mouse is released
	 * 
	 * @param x the x location of the mouse
	 * @param y the y location of the mouse
	 */
	public void doMouseRelease(int x, int y) {
		x -= MOUSE_X_OFFSET; // See the comment at the top of doMouseClick
		y -= MOUSE_Y_OFFSET;
		if (tempRect != null) {
			colliders.add(new ColoredRectangle(tempRect));
			tempRect = null;
		}

	}

	/**
	 * Perform this method continuously when the mouse is held down
	 * 
	 * @param x the x location of the mouse
	 * @param y the y location of the mouse
	 */
	public void doMouseHold(int x, int y) {
		x -= MOUSE_X_OFFSET; // See the comment at the top of doMouseClick
		y -= MOUSE_Y_OFFSET;
		int botRightX;
		int botRightY;
		if (topLeft != null) {
			botRightX = x + (Constants.SPRITE_WIDTH - (x % Constants.SPRITE_WIDTH));
			botRightY = y + (Constants.SPRITE_HEIGHT - (y % Constants.SPRITE_HEIGHT));
			botRight.setLocation(botRightX, botRightY);
			if (tempRect != null) {
				tempRect.setSize((int) (botRight.getX() - topLeft.getX()), (int) (botRight.getY() - topLeft.getY()));
			}

		}
	}

	/**
	 * Perform this method when then clear button is pressed twice
	 */
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

	/**
	 * Perform this method when the print button is pressed twice
	 */
	public void doPrint() {
		// Initializes a hashmap that relates the location on the sprite map to the
		// object that should be placed in the level
		data = new HashMap<List<Integer>, String>();
		data.put(Arrays.asList(2, 1), "pp");
		data.put(Arrays.asList(1, 1), "^1");
		data.put(Arrays.asList(6, 1), "rr");
		data.put(Arrays.asList(7, 1), "dd");
		data.put(Arrays.asList(11, 1), "v1");
		data.put(Arrays.asList(11, 2), ">1");
		data.put(Arrays.asList(11, 3), "<1");
		data.put(Arrays.asList(10, 1), "ss");
		data.put(Arrays.asList(8, 0), "kk");
		data.put(Arrays.asList(4, 1), "cc");
		data.put(Arrays.asList(1, 0), "m1");
		data.put(Arrays.asList(0, 4), "bb");
		data.put(Arrays.asList(12, 1), "ww");
		data.put(Arrays.asList(5, 1), "dp");
		data.put(Arrays.asList(1, 9), "m2");
		data.put(Arrays.asList(11, 0), "lr");
		data.put(Arrays.asList(12, 0), "ll");
		data.put(Arrays.asList(6, 6), "nn");
		data.put(Arrays.asList(0, 6), "CC");
		String levelDataString = "";
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (objectLayer[j][i] != null) {
					List<Integer> point = Arrays.asList(
							(int) (objectLayer[j][i].getX() - (128 * Constants.PIXEL_DIM)) / Constants.SPRITE_WIDTH,
							(int) objectLayer[j][i].getY() / Constants.SPRITE_WIDTH);
					if (data.get(point) != null) {
						levelDataString += data.get(point);
					} else {
						levelDataString += "--";
					}
				} else if (getColliderAtPoint(j, i) != null) {
					levelDataString += getColliderAtPoint(j, i).getIsIce() + getColliderSize(getColliderAtPoint(j, i));
				} else {
					levelDataString += "--";
				}
				if (j != 15) {
					levelDataString += " ";
				}
			}
			levelDataString += "\n";
		}
		for (int i = 0; i < environmentLayer.length; i++) {
			levelDataString += "\n";
			for (int j = 0; j < environmentLayer[0].length; j++) {
				if (environmentLayer[j][i] != null)
					levelDataString += environmentLayer[j][i].getX() + "," + environmentLayer[j][i].getY();
				else if (backgroundLayer[j][i] != null) {
					levelDataString += backgroundLayer[j][i].getX() + "," + backgroundLayer[j][i].getY();
				} else
					levelDataString += "-";
				if (j != 15) {
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
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
