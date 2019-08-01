package Events;

import java.util.Set;

import Net.Request;

public interface Clickable {
	
	Set<MouseClick> getMouseButton();
	
	int getWidth();
	int getHeight();
	
	ClickGroup getGroup();

	boolean doesReset(MouseClick type);
	
	Request onClick(MouseClick type, int x, int y);
	
	boolean selected(MouseClick type);
	
	void setSelected(boolean selected, MouseClick type);
	
	void onReset(MouseClick type);
	
	int getX();
	int getY();
}
