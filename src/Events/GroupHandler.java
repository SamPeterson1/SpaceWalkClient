package Events;

import java.util.ArrayList;

import GUI.Camera;

public class GroupHandler {
	
	ClickGroup group;
	ArrayList<Clickable> clickable = new ArrayList<Clickable>();
	Clickable selected = null;
	
	public GroupHandler(ClickGroup group) {
		this.group = group;
	}
	
	public Clickable getSelected() {
		return this.selected;
	}
	
	public void addClickable(Clickable clickable) {
		this.clickable.add(clickable);
	}
	
	public void handleClick(int mouseX, int mouseY, MouseClick type) {
		
		if(this.selected != null) {
			if(this.selected.doesReset()) {
				this.selected = null;
			}
		}
		
		for(Clickable c: this.clickable) {
			if(Camera.inBounds(mouseX, mouseY, c.getX(), c.getY(), c.getWidth(), c.getHeight())) {
				if(c.getMouseButton() == type) {
					c.setSelected(true);
					this.selected = c;
					break;
				}
			}
		}
		
	}
}
