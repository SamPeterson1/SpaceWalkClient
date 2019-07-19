package GUI;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Sprite {
	
	ArrayList<Image> sprites;
	
	public Sprite(String name, int amount) {
		
		for(int i = 1; i <= amount; i ++) {
			String imgName = "/Assets/" + name + "_0" + i + ".png";
			this.sprites.add(new ImageIcon(getClass().getResource(imgName)).getImage());
		}
		
	}
	
	public ArrayList<Image> getSprites() {		
		return this.sprites;
	}
	
}
