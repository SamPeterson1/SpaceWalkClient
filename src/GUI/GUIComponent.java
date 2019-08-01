package GUI;

import java.awt.Image;

public interface GUIComponent {
	
	Image getImage();
	int getX();
	int getY();
	
	void onCameraMove();
}
