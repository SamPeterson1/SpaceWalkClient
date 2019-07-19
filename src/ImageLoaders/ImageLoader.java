package ImageLoaders;

import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class ImageLoader {
	
	public HashMap<Integer, Image> images;
	
	public ImageLoader() {
		this.images = new HashMap<Integer, Image>();
	}
	
	public Image getImage(Integer ID) {
		return this.images.get(ID);			
	}
	
	public void setImage(Integer ID, String image) {
		this.images.put(ID, new ImageIcon(getClass().getResource("/Assets/" + image + ".png")).getImage());
	}
	
}
