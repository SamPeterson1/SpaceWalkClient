package ImageLoaders;

import java.awt.Color;
import java.util.HashMap;

public class ResourceColors {
	
	public HashMap<String, Color> colors;
	
	public ResourceColors() {
		this.colors = new HashMap<String, Color>();
		this.colors.put("Oxygen", Color.CYAN);
		this.colors.put("Power", Color.YELLOW);
	}
	
	public Color getColor(String name) {
		return this.colors.get(name);
	}
	
}
