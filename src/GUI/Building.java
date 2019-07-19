package GUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Building {
	
	//private Image img;
	private HashMap <String, Consumable> consumables;
	private ArrayList<Integer[]> connected;
	private boolean active;
	private boolean linked;
	private int ID;
	private int x;
	private int y;
	
	public Building(int ID) {
		this.ID = ID;
		this.connected = new ArrayList<Integer[]>();
		this.consumables = new HashMap<String, Consumable>();
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
}
