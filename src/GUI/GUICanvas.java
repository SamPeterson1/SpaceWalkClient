package GUI;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;

import Events.GUIEventQueue;
import ImageLoaders.BuildingImageLoader;
import ImageLoaders.PlayerImageLoader;
import ImageLoaders.ResourceColors;
import ImageLoaders.TetherImageLoader;
import ImageLoaders.TileImageLoader;

public class GUICanvas extends Canvas {
	//TODO: generate new long for serialVersionUID
	private static final long serialVersionUID = -557652432650828632L;
	private int width;
	private int height;
	private int mouseX = 0;
	private int mouseY = 0;
	private Building selected = null;
	private Image tether;
	private TileImageLoader TIL;
	private PlayerImageLoader PIL;
	private BuildingImageLoader BIL;
	private TetherImageLoader TEIL;
	private ResourceColors RC;
	private Font f1;
	
	public GUICanvas(int width, int height) {
		this.setBackground(Color.WHITE);
		this.setSize(width, height-20);
		this.width = width;
		this.height = height;
		this.TIL = new TileImageLoader();
		this.PIL = new PlayerImageLoader();
		this.BIL = new BuildingImageLoader();
		this.RC = new ResourceColors();
		this.TEIL = new TetherImageLoader();
		tether = new ImageIcon(getClass().getResource("/Assets/TetherO2.png")).getImage();
	}
	
	
	public void draw(Graphics g2) {

		BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		this.clear(g2);	
		int cx = (Camera.x/32);
		int cy = (Camera.y/32);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		for(int i = cx - 17; i < cx + 17; i ++) {
			for(int ii = cy-17; ii < cy + 17; ii ++) {
				int j = i;
				int k = ii;
				int size = DataLoader.world.length;
				if(j < 0) j += size;
				if(j >= size) j -= size;
				if(k < 0) k += size;
				if(k >= size) k -= size;
				int[] screen = Camera.worldToScreen(j, k, 32, 32);
				g.drawImage(this.TIL.getImage(DataLoader.world[j][k]), screen[0], screen[1], null);
			}
		}
		
		for(Building b: DataLoader.buildings) {
			int[] pos = Camera.worldToScreen(b.getX(), b.getY(), 32, 32);
			g.drawImage(this.BIL.getImage(b.getID()), pos[0], pos[1], null);
			for(Integer[] i: b.getConnected()) {
				int[] pos2 = Camera.worldToScreen(i[0], i[1], 32, 32);
				g.drawLine(pos[0], pos[1], pos2[0], pos2[1]);
			}
			for(Consumable c: b.getConsumables()) {
				//System.out.println(c.getName() + ": " + "Amount: " + c.getAmount() + "/" + c.getMax());
			}
		}
		
		HashMap<Integer, Vector<Integer>> tethers = DataLoader.tethers;
		this.TEIL.animate();
		for(Vector<Integer> tether: tethers.values()) {
			
			int[] pos = Camera.worldToScreen(tether.get(0), tether.get(1), 5*32, 1);

			g.drawImage(this.TEIL.getImage(tether.get(2)), pos[0], pos[1], null);
			
			int index = 0;
			for(Integer id: tether) {
				if(index > 2) {
					int[] pos2 = Camera.worldToScreen(tethers.get(id).get(0), tethers.get(id).get(1), 5*32, 1);
					if(Math.abs(pos2[0] - pos[0]) < 5*32 && Math.abs(pos2[1]- pos[1]) < 5*32)
						g.drawLine(pos[0]+16, pos[1]+16, pos2[0]+16, pos2[1]+16);
				}
				index ++;
			}
		}
		
		
		for(int i = 0; i < DataLoader.playersPos.length; i += 2) {
			int[] tether = Camera.worldToScreen(DataLoader.playersTether[i], DataLoader.playersTether[i+1], 32, 1);
			if(DataLoader.playersPos[i] != -1) {
				int[] pos = Camera.worldToScreen(DataLoader.playersPos[i], DataLoader.playersPos[i+1], 32, 1);
				if(DataLoader.playersTether[i] != -1)
					g.drawLine(pos[0], pos[1], tether[0], tether[1]);
				g.drawImage(this.PIL.getImage(DataLoader.playersSprite[i/2]), pos[0], pos[1], null);	
			} else {
				if(DataLoader.playersTether[i] != -1)
					g.drawLine(512, 512, tether[0], tether[1]);
				g.drawImage(this.PIL.getImage(DataLoader.playersSprite[i/2]), 512, 512, null);				
			}
		}
		
		if(this.selected != null) {
			int[] pos = Camera.worldToScreen(this.selected.getX(), this.selected.getY(), 32, 32);
			g.setColor(Color.LIGHT_GRAY);
 			g.fillRect(pos[0] + 95, pos[1] + 95, 260, 160);
 			int width = BIL.getImage(this.selected.getID()).getWidth(null);
 			g.drawLine(pos[0] + 95, pos[1] + 95, width/2 + pos[0], width/2 + pos[1]);
 			g.setColor(Color.BLACK);
			g.fillRect(pos[0] + 100, pos[1] + 100, 250, 150);
			g.setFont(f1);
			String str = this.selected.isActive() ? "Active" : "Inactive";
			Color color = this.selected.isActive() ? Color.GREEN : Color.RED;
			g.setColor(color);
			g.drawString(str, pos[0]+110, pos[1] + 120);
			str = this.selected.linked() ? "Connected" : "Disconnected";
			color = this.selected.linked() ? Color.GREEN : Color.RED;
			g.setColor(color);
			g.drawString(str, pos[0]+180, pos[1] + 120);
			
			int y = 130 + pos[1];
			
			for(Consumable c: this.selected.getConsumables()) {
				int level = (c.getAmount()*130)/c.getMax();
				g.setColor(RC.getColor(c.getName()));
				g.drawString(c.getName(), pos[0] + 250, y + 10);
				g.fillRect(pos[0] + 110, y, level, 12);
				g.setColor(Color.GRAY);
				g.fillRect(pos[0] + 110 + level, y, 130-level, 12);
				y += 17;
			}
		}
		
		g.dispose();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
	}
	
	public void setSelected(Building selected) {
		this.selected = selected;
	}
	
	public int[] screenToWorld(int x, int y) {
		
		//a = width/2+(x*32-camY) --> a - width/2 = x*32-camY  --> a - width/2 + camY = x*32 --> x = (a - (width/2) + camY)/32
		
		x = (x - (this.getWidth()/2) + Camera.x)/32;
		y = (y - (this.getWidth()/2) + Camera.y)/32;
		
		int testX = x - 64;
		int testY = y - 64;
		
		if(Math.abs(testX*32-Camera.x) > 16*32 + 100 && testX >= 0) {
			x = testX;
		}
		
		if(Math.abs(testY*32-Camera.y) > 16*32 + 100 && testY >= 0) {
			y = testY;
		}
		
		int[] retVal = {x, y};
		
		return retVal;
	}
	
	public void clear(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.width, this.height);
	}
	
	public BufferedImage scaleToFrameSize(BufferedImage img) {
		
		int[] targetRes = new int[2];
		targetRes[0] = this.getWidth();
		targetRes[1] = this.getHeight();
		
		return scale(targetRes, img);
		
	}
	
	public BufferedImage scale(int[] targetRes, BufferedImage img) {
		if(targetRes[1] != Main.WIDTH && targetRes[0] != Main.HEIGHT - 20) {
			Image image = img.getScaledInstance(targetRes[0], targetRes[1], Image.SCALE_SMOOTH);
			BufferedImage resizedImage = new BufferedImage(targetRes[0], targetRes[1], BufferedImage.TYPE_INT_ARGB); 
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(image, 0, 0, targetRes[0], targetRes[1], null);
			g.dispose();
		    return resizedImage;
		}
		return img;
	}
	
	public void addEventQueue(GUIEventQueue queue)  {

		this.addKeyListener(queue);
		this.addMouseListener(queue);
		this.addMouseMotionListener(queue);

		return;
	}

}
