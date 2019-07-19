package Events;

import java.util.Vector;

import GUI.Building;
import GUI.Camera;
import GUI.DataLoader;
import GUI.GUICanvas;
import ImageLoaders.BuildingImageLoader;

public class EventHandler {
	
	GUIEventQueue queue;
	GUICanvas canvas;
	BuildingImageLoader BIL;
	int mouseX = 0;
	int mouseY = 0;
	
	public EventHandler(GUIEventQueue queue, GUICanvas canvas) {
		this.queue = queue;
		this.canvas = canvas;
		this.BIL = new BuildingImageLoader();
	}
	
	public String handleEvents(String id) {
		Request r = new Request("");
		if(queue.isEventToProcess()) {
			GUIEvent event = queue.getEvent();
			if(event.getType() == GUIEvent.EVENT_KEY_PRESS) {
				char key = event.getKeyChar();
				if(key == 'w') {
					System.out.println("HI");
					Camera.up(DataLoader.speed);
					r = new Request("move");
					r.addParameter("direction", "up");
					r.addParameter("id", id);
				} else if(key == 'a') {
					Camera.left(DataLoader.speed);
					r = new Request("move");
					r.addParameter("direction", "left");
					r.addParameter("id", id);
				} else if(key == 's') {
					Camera.down(DataLoader.speed);
					r = new Request("move");
					r.addParameter("direction", "down");
					r.addParameter("id", id);
				} else if(key == 'd') {
					Camera.right(DataLoader.speed);
					r = new Request("move");
					r.addParameter("direction", "right");
					r.addParameter("id", id);
				} else if(key == 'g') {
					r = new Request("build");
					r.addParameter("buildID", "2");
					r.addParameter("playerID", id);
				} else if(key == 'h') {
					r = new Request("build");
					r.addParameter("buildID", "1");
					r.addParameter("playerID", id);
				}
				if(event.getKeyCode() == 16) {
					r = new Request("sprint");
					r.addParameter("id", id);
				}
			} else if(event.getType() == GUIEvent.EVENT_KEY_RELEASE) {
				char key = event.getKeyChar();
				if(key == 'w' || key == 'a' || key == 's' || key == 'd') {
					r = new Request("halt");
					r.addParameter("id", id);
				}
			} else if(event.getType() == GUIEvent.EVENT_MOUSE_BUTTON_PRESS) {
				if(event.isMouseRightButton()) {
					this.mouseX = event.getMouseX();
					this.mouseY = event.getMouseY();
				} else if(event.isMouseLeftButton()) {
					int[] pos = Camera.screenToWorld(event.getMouseX(), event.getMouseY());
					for(Building b: DataLoader.buildings) {
						if(Math.abs(pos[0] - b.getX()) < this.BIL.getImage(b.getID()).getWidth(null)/32 && Math.abs(pos[1] - b.getY()) < this.BIL.getImage(b.getID()).getHeight(null)/32) {
							r = new Request("link");
							r.addParameter("x", String.valueOf(b.getX()));
							r.addParameter("y", String.valueOf(b.getY()));
							System.out.println("YEETTTTTT");
						}
					}
				}
			}
		}
		
		canvas.setSelected(null);
		int[] pos = Camera.screenToWorld(this.mouseX, this.mouseY);
		for(Building b: DataLoader.buildings) {
			if(Math.abs(pos[0] - b.getX()) < this.BIL.getImage(b.getID()).getWidth(null)/32 && Math.abs(pos[1] - b.getY()) < this.BIL.getImage(b.getID()).getHeight(null)/32) {
				canvas.setSelected(b);
				break;
			}
		}
		
		return r.toString();
	}
	
}
