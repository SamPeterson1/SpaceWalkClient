package Stubs;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import Events.ClickGroup;
import Events.Clickable;
import Events.MouseClick;
import Events.MouseManager;
import Events.Request;
import GUI.Consumable;
import ImageLoaders.BuildingImageLoader;

public class Building implements Clickable{
	
	//private Image img;
	private static BuildingImageLoader loader = new BuildingImageLoader();
	private Image img;
	private int width;
	private int height;
	private HashMap <String, Consumable> consumables;
	private ArrayList<Integer[]> connected;
	private boolean active;
	private boolean linked;
	private boolean selected;
	private int ID;
	private int x;
	private int y;
	
	public Building(int ID) {
		
		this.ID = ID;
		this.connected = new ArrayList<Integer[]>();
		this.consumables = new HashMap<String, Consumable>();
		this.img = Building.loader.getImage(ID);
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		MouseManager.addClickable(this);
		
	}
	
	public void setConsumables(HashMap<String, Consumable> consumables) {
		this.consumables = consumables;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setLinked(boolean linked) {
		this.linked = linked;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public boolean linked() {
		return this.linked;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void connectTo(int x, int y) {
		Integer[] pos = {Integer.valueOf(x), Integer.valueOf(y)};
		this.connected.add(pos);
	}
	
	public ArrayList<Integer[]> getConnected() {
		return this.connected;
	}
	
	public Consumable getConsumable(String name) {
		return this.consumables.get(name);
	}
	
	public Collection<Consumable> getConsumables() {
		return this.consumables.values();
	}
	
	@Override	
	public MouseClick getMouseButton() {return MouseClick.LEFT;}
	@Override
	public int getWidth() {return this.width;}
	@Override
	public int getHeight() {return this.height;}
	@Override
	public ClickGroup getGroup() {return ClickGroup.BUILDING;}
	@Override
	public boolean doesReset() {return true;}
	@Override
	public Request onClick() {return null;}
	@Override
	public boolean selected() {return this.selected;}
	@Override
	public void setSelected(boolean selected) {this.selected = selected;}
}
