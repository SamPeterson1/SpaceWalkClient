package Events;

public interface Clickable {
	
	MouseClick getMouseButton();
	
	int getWidth();
	int getHeight();
	
	ClickGroup getGroup();
	
	boolean doesReset();
	
	Request onClick();
	
	boolean selected();
	
	void setSelected(boolean selected);
	
	int getX();
	int getY();
}
