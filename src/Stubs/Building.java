package Stubs;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import Events.ClickAttribute;
import Events.ClickGroup;
import Events.Clickable;
import Events.MouseClick;
import Events.MouseManager;
import GUI.Consumable;
import GUI.GUIComponent;
import ImageLoaders.BuildingImageLoader;
import Net.Request;

public class Building implements Clickable{
	
	//private Image img;
	private static BuildingImageLoader loader = new BuildingImageLoader();
	private static int[] connectPos = new int[4];
	private static int connectState = 0;
	private Image img;
	private int width;
	private int height;
	private HashMap <String, Consumable> consumables;
	private ArrayList<Integer[]> connected;
	private HashMap<MouseClick, ClickAttribute> attributes;
	private boolean linked;
	private boolean active = false;
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
		this.attributes = new HashMap<MouseClick, ClickAttribute>();
		this.attributes.put(MouseClick.LEFT, new ClickAttribute(MouseClick.LEFT, true, false));
		this.attributes.put(MouseClick.RIGHT, new ClickAttribute(MouseClick.RIGHT, true, false));
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
	
	private Request selectBuilding(int x, int y) {
		Building.connectPos[Building.connectState*2] = x;
		Building.connectPos[Building.connectState*2+1] = y;
		Building.connectState ++;
		if(Building.connectState == 2) {
			Request link = new Request("link");
			int[] pos = Building.connectPos;
			link.addParameter("x1", pos[0]);
			link.addParameter("y1", pos[1]);
			link.addParameter("x2", pos[2]);
			link.addParameter("y2", pos[3]);
			Building.connectState = 0;
			
			return link;
		}
		return null;
	}
	
	@Override
	public int getX() {return this.x;}
	@Override
	public int getY() {return this.y;}
	@Override	
	public Set<MouseClick> getMouseButton() {return this.attributes.keySet();}
	@Override
	public int getWidth() {return this.width;}
	@Override
	public int getHeight() {return this.height;}
	@Override
	public ClickGroup getGroup() {return ClickGroup.BUILDING;}
	@Override
	public boolean doesReset(MouseClick type) {return this.attributes.get(type).doesReset();}
	@Override
	public void onReset(MouseClick type) {
		System.out.println("HUHUHUHU");
		if(type == MouseClick.RIGHT) {
			Building.connectPos = new int[4];
			Building.connectState = 0;
		}
	}
	@Override
	public Request onClick(MouseClick type, int x, int y) {
		if(type == MouseClick.RIGHT) {
			System.out.println("RIGHT");
			return this.selectBuilding(this.x, this.y);
		} else {
			System.out.println("LEFT");
		}
		return null;
	}
	@Override
	public boolean selected(MouseClick type) {return this.attributes.get(type).doesReset();}
	@Override
	public void setSelected(boolean selected, MouseClick type) {this.attributes.get(type).setSelected(selected);}
}
