package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import javax.swing.JComponent;

public class LevelComponent extends JComponent{
	private Madeline m;
	private MainApp main;
	private ArrayList<CollisionObject> collisionObjects;
	private int madelineSpawnX;
	private int madelineSpawnY;
	private int numberOfDashesTotal;
	private CollisionObject strawberry;
	private PointText pt;
	public LevelComponent(MainApp main, int level, boolean strawberryAlreadyCollected)
	{
		this.main = main;
		if (!strawberryAlreadyCollected)
		{
			strawberry = new CollisionObject(0,0,0,0);
		}
		
		collisionObjects = new ArrayList<CollisionObject>();
		//createLevelFromText();
		createLevel(level);
	}
	
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)(g);
		for (CollisionObject c : collisionObjects)
		{
			c.drawOn(g2);
		}
		m.drawOn(g2);
		if (pt != null)
		{
			pt.drawOn(g2);
		}
		
	}
	public void resetLevel()
	{
		main.resetLevel();
	}
	public void nextLevel()
	{
		main.nextLevel();
	}
	public void moveMadelineRight()
	{
		m.increaseX();
	}
	public void moveMadelineLeft()
	{
		m.decreaseX();
	}
	public void moveMadelineVertically()
	{
		m.setVerticalPosition();
	}
	public void moveMadelineHorizontally()
	{
		m.setHorizontalPosition();
	}
	public boolean madelineIsCollidingWithFloor()
	{
		return m.isCollidingWithFloor();
	}
	public void madelineJump()
	{
		m.jump();
	}
	public void dash(String dir)
	{
		m.dash(dir);
	}
	public void checkIfDashing()
	{
		m.checkIfDashing();
	}
	public void setMadelineJumpPressed(boolean b)
	{
		m.setJumpPressed(b);
	}
	public void addNewStrawberry(int x, int y)
	{
		strawberry = new Strawberry(x, y, 36, 48, m);
		collisionObjects.add(strawberry);
	}
	public void collectStrawberry()
	{
		pt = new PointText(strawberry.getX(), strawberry.getY());
		main.collectStrawberry();
		collisionObjects.remove(strawberry);
		strawberry = null;
	}
	public void updateAnimations()
	{
		m.updateAnimations();
		if (pt != null)
		{
			if (pt.updateAnimation())
			{
				pt = null;
			}
		}
	}
	// temporary, will replace with reading level data from files
	public void createLevel(int level)
	{
		if (level == 1)
		{
			madelineSpawnX = 60;
			madelineSpawnY = 768-144-42;
			numberOfDashesTotal = 1;
			m = new Madeline(madelineSpawnX, madelineSpawnY, numberOfDashesTotal, collisionObjects, this);
			CollisionObject c1 = new EnvironmentObject(0,768-144,240,144,new String[] {"","","",""});
			collisionObjects.add(c1);
			CollisionObject c2 = new EnvironmentObject(150,768-144-48,240-150,48,new String[] {"bottom","","",""});
			collisionObjects.add(c2);
//			CollisionObject c3 = new EnvironmentObject(240, 768-48, 96, 48,new String[] {"left","right","",""});
//			collisionObjects.add(c3);
			CollisionObject c4 = new EnvironmentObject(240+96,768-246,96,96,new String[] {"","","",""});
			collisionObjects.add(c4);
			CollisionObject c23 = new EnvironmentObject(240+96,768-246+96,96,246-96,new String[] {"","","",""});
			collisionObjects.add(c23);
//			CollisionObject c5 = new EnvironmentObject(240+96+96, 768-96, 96, 96,new String[] {"left","right","",""});
//			collisionObjects.add(c5);
//			CollisionObject c6 = new EnvironmentObject(240+96+96+96,768-144,144,144,new String[] {"right","","",""});
//			collisionObjects.add(c6);
			CollisionObject c8 = new EnvironmentObject(768-144, 768-336, 144, 48,new String[] {"","","",""});
			collisionObjects.add(c8);
			
			CollisionObject c10 = new EnvironmentObject(768-96, 768 - 336 - 144 - 96, 96, 96,new String[] {"","","",""});
			collisionObjects.add(c10);
			CollisionObject c11 = new EnvironmentObject(768-96, 768-336-144-96-48, 48, 48,new String[] {"","","",""});
			collisionObjects.add(c11);
			CollisionObject c12 = new EnvironmentObject(768-96-48, 768-336-144-96-48, 48, 48,new String[] {"","","",""});
			collisionObjects.add(c12);
			CollisionObject c13 = new EnvironmentObject(768-96-96,768-336-144-96,96,48,new String[] {"right","","",""});
			collisionObjects.add(c13);
			CollisionObject c14 = new EnvironmentObject(768-48,0,48,192,new String[] {"bottom","","",""});
			collisionObjects.add(c14);
			CollisionObject c15 = new EnvironmentObject(0,0,48,768-288,new String[] {"","","",""});
			collisionObjects.add(c15);
			CollisionObject c16 = new EnvironmentObject(48,768-288-96-96,48,96,new String[] {"left","","",""});
			collisionObjects.add(c16);
			CollisionObject c17 = new EnvironmentObject(48+48,768-288-96-96,144,48,new String[] {"","","",""});
			collisionObjects.add(c17);
			
			CollisionObject c19 = new EnvironmentObject(48,0,96,96+96,new String[] {"left","","",""});
			collisionObjects.add(c19);
			CollisionObject c20 = new EnvironmentObject(48+96,0,48*4,48*2,new String[] {"left","","",""});
			collisionObjects.add(c20);
			CollisionObject c21 = new EnvironmentObject(48+96,96,48,48,new String[] {"","","",""});
			collisionObjects.add(c21);
			CollisionObject c22 = new InvisibleWall(-100,-100,100,868);
			collisionObjects.add(c22);
//			CollisionObject test = new EnvironmentObject(200,200,300,400);
//			collisionObjects.add(test);
			
			CollisionObject c18 =  new EnvironmentObject(48+96+96+96,0,48*6,48,new String[] {"left","","",""});
			collisionObjects.add(c18);
			CollisionObject c9 = new EnvironmentObject(768-48, 768 - 336-144, 48, 144,new String[] {"bottom","top","",""});
			collisionObjects.add(c9);
			CollisionObject c7 = new EnvironmentObject(768-96,768-288,96,288,new String[] {"top","","",""});
			collisionObjects.add(c7);
			CollisionObject c6 = new EnvironmentObject(240+96+96+96,768-144,144,144,new String[] {"right","","",""});
			collisionObjects.add(c6);
			CollisionObject c5 = new EnvironmentObject(240+96+96, 768-96, 96, 96,new String[] {"left","right","",""});
			collisionObjects.add(c5);
			CollisionObject c3 = new EnvironmentObject(240, 768-48, 96, 48,new String[] {"left","right","",""});
			collisionObjects.add(c3);
			
			CollisionObject s1 = new Spike(150+(240-150),768-36-48,4, true, false, m);
			collisionObjects.add(s1);
			CollisionObject s2 = new Spike(240+96+96,768-96-36,4,true, false, m);
			collisionObjects.add(s2);
			CollisionObject s3 = new Spike(240+96+96+96, 768-144-36, 6, true, false, m);
			collisionObjects.add(s3);
			
			if (strawberry != null)
			{
				CollisionObject b1 = new BreakableBlock(48, 768-288-96-96-96,96,96, m);
				collisionObjects.add(b1);
			}
			
			CollisionObject lvlFinish = new LevelFinishZone(0,-10,786,10, m);
			collisionObjects.add(lvlFinish);
		}
		else
		{
			madelineSpawnX = 50;
			madelineSpawnY = 50;
			numberOfDashesTotal = 1;
			m = new Madeline(madelineSpawnX, madelineSpawnY, numberOfDashesTotal, collisionObjects, this);
		}
		
	}
	public void printLevel()
	{
		int i = 0;
		for (CollisionObject c : collisionObjects)
		{
			i++;
			System.out.println(c.toString());
		}
	}
	public void createLevelFromText()
	{
		String t = "mainApp.EnvironmentObject, 0 624 240 144 ,    \r\n"
				+ "mainApp.EnvironmentObject, 150 576 90 48 , bottom   \r\n"
				+ "mainApp.EnvironmentObject, 336 522 96 96 ,    \r\n"
				+ "mainApp.EnvironmentObject, 336 618 96 150 ,    \r\n"
				+ "mainApp.EnvironmentObject, 624 432 144 48 ,    \r\n"
				+ "mainApp.EnvironmentObject, 672 192 96 96 ,    \r\n"
				+ "mainApp.EnvironmentObject, 672 144 48 48 ,    \r\n"
				+ "mainApp.EnvironmentObject, 624 144 48 48 ,    \r\n"
				+ "mainApp.EnvironmentObject, 576 192 96 48 , right   \r\n"
				+ "mainApp.EnvironmentObject, 720 0 48 192 , bottom   \r\n"
				+ "mainApp.EnvironmentObject, 0 0 48 480 ,    \r\n"
				+ "mainApp.EnvironmentObject, 48 288 48 96 , left   \r\n"
				+ "mainApp.EnvironmentObject, 96 288 144 48 ,    \r\n"
				+ "mainApp.EnvironmentObject, 48 0 96 192 , left   \r\n"
				+ "mainApp.EnvironmentObject, 144 0 192 96 , left   \r\n"
				+ "mainApp.EnvironmentObject, 144 96 48 48 ,    \r\n"
				+ "mainApp.InvisibleWall, -100 -100 100 868 \r\n"
				+ "mainApp.EnvironmentObject, 336 0 288 48 , left   \r\n"
				+ "mainApp.EnvironmentObject, 720 288 48 144 , bottom top  \r\n"
				+ "mainApp.EnvironmentObject, 672 480 96 288 , top   \r\n"
				+ "mainApp.EnvironmentObject, 528 624 144 144 , right   \r\n"
				+ "mainApp.EnvironmentObject, 432 672 96 96 , left right  \r\n"
				+ "mainApp.EnvironmentObject, 240 720 96 48 , left right  \r\n"
				+ "mainApp.Spike, 240, 684, 4, true, false\r\n"
				+ "mainApp.Spike, 432, 636, 4, true, false\r\n"
				+ "mainApp.Spike, 528, 588, 6, true, false\r\n"
				+ "mainApp.BreakableBlock, 48 192 96 96 \r\n"
				+ "mainApp.LevelFinishZone, 0 -10 786 10 ";
		
		String[] tList = t.split("\n");
		for (int i = 0; i < tList.length; i++)
		{
			String[] options = tList[i].split(",");
			String clsName = options[0];
			String[] positions = options[1].split(" ");
			Object collisionObject;
			try {
				Class<?> cls = Class.forName(clsName);
				if (options.length == 6)
				{
					Constructor<?> constructor = cls.getConstructor(int.class, int.class, int.class, boolean.class, boolean.class);
					collisionObject = constructor.newInstance(Integer.parseInt(options[1].substring(1)), Integer.parseInt(options[2].substring(1)), Integer.parseInt(options[3].substring(1)), Boolean.parseBoolean(options[4].substring(1)), Boolean.parseBoolean(options[5].substring(1)));
				}
				else if (options.length == 3)
				{
					String[] touchingDirections = options[2].split(" ");
					Constructor<?> constructor = cls.getConstructor(int.class, int.class, int.class, int.class, String[].class);
					collisionObject = constructor.newInstance(Integer.parseInt(positions[1]), Integer.parseInt(positions[2]), Integer.parseInt(positions[3]), Integer.parseInt(positions[4]), touchingDirections);
				}
				else
				{
					Constructor<?> constructor = cls.getConstructor(int.class, int.class, int.class, int.class);
					collisionObject = constructor.newInstance(Integer.parseInt(positions[1]), Integer.parseInt(positions[2]), Integer.parseInt(positions[3]), Integer.parseInt(positions[4]));
				}
				
				CollisionObject c = (CollisionObject)collisionObject;
				collisionObjects.add(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
