package Events;

import java.util.ArrayList;
import java.util.HashMap;

import GUI.Camera;
import Net.Request;

public class GroupHandler {
	
	ClickGroup group;
	ArrayList<Clickable> clickable = new ArrayList<Clickable>();
	HashMap<MouseClick, Clickable> selected;
	HashMap<MouseClick, Integer[]> mousePos;
	
	public GroupHandler(ClickGroup group) {
		this.group = group;
		this.selected = new HashMap<MouseClick, Clickable>();
		this.mousePos = new HashMap<MouseClick, Integer[]>();
	}
	
	public void eraseClickable() {
		this.clickable = new ArrayList<Clickable>();
		this.selected = new HashMap<MouseClick, Clickable>();
	}
	
	public Clickable getSelected(MouseClick type) {
		return this.selected.get(type);
	}
	
	public void addClickable(Clickable clickable) {
		this.clickable.add(clickable);
	}
	
	public void reSelect() {
		for(MouseClick type: mousePos.keySet()) {
			Integer[] pos = mousePos.get(type);
			for(Clickable c: this.clickable) {
				if(Camera.inBounds(pos[0], pos[1], c.getX(), c.getY(), c.getWidth(), c.getHeight())) {
					if(c.getMouseButton().contains(type)) {
						c.setSelected(true, type);
						this.selected.put(type, c);
						break;
					}
				}
			}
		}
	}
	
	public Request handleClick(int mouseX, int mouseY, MouseClick type) {
		
		Integer[] pos = {mouseX, mouseY};
		this.mousePos.put(type, pos);
		
		if(this.selected.get(type) != null) {
			if(this.selected.get(type).doesReset(type)) {
				this.selected.put(type, null);
			}
		}
		
		for(Clickable c: this.clickable) {
			if(Camera.inBounds(mouseX, mouseY, c.getX(), c.getY(), c.getWidth(), c.getHeight())) {
				if(c.getMouseButton().contains(type)) {
					c.setSelected(true, type);
					this.selected.put(type, c);
					return c.onClick(type, mouseX, mouseY);
				}
			}
		}
		
		for(Clickable c: this.clickable) {
			c.onReset(type);
		}
		
		return null;
	}
	
	
}
