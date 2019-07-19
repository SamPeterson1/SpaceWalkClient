package GUI;

public class Camera {
	
	static int x = 512;
	static int y = 512;
	static int WORLD_SIZE = 256;
	
	public static void left(int amount) {
		Camera.x -= amount;
		if(Camera.x <= 0) Camera.x += Camera.WORLD_SIZE*32;
	}
	
	public static void right(int amount) {
		Camera.x += amount;
		if(Camera.x >= Camera.WORLD_SIZE*32) Camera.x -= Camera.WORLD_SIZE*32;
	}
	
	public static void up(int amount) {
		Camera.y -= amount;
		if(Camera.y <= 0) Camera.y += Camera.WORLD_SIZE*32;
	}
	
	public static void down(int amount) {
		Camera.y += amount;
		if(Camera.y >= Camera.WORLD_SIZE*32) Camera.y -= Camera.WORLD_SIZE*32;
	}
	
	public static int[] worldToScreen(int x, int y, int thresh, int gridSize) {
		
		if(Math.abs(x*gridSize-Camera.x) >= 48*32)
			if(x < 512/gridSize) {
				x += Camera.WORLD_SIZE*32/gridSize;
			} else {
				x -= Camera.WORLD_SIZE*32/gridSize;
			}
		
		if(Math.abs(y*gridSize-Camera.y) >= 48*32)
			if(y < 512/gridSize) {
				y += Camera.WORLD_SIZE*32/gridSize;
			} else {
				y -= Camera.WORLD_SIZE*32/gridSize;
			}
		
		int dx = x*gridSize-Camera.x;
		int dy = y*gridSize-Camera.y;
		
		int[] retVal = {512+dx, 512+dy};

		return retVal;
	}
	
	public static int[] screenToWorld(int x, int y) {

		int cx = Camera.x;
		int cy = Camera.y;
		
		int dx = (x+cx - 512)/32;
		int dy = (y+cy - 512)/32;
		
		if(x + cx < 512) dx --;
		if(y + cy < 512) dy --;

		if(dx < 0) dx += Camera.WORLD_SIZE;
		if(dy < 0) dy += Camera.WORLD_SIZE;
		
		if(dx >= Camera.WORLD_SIZE) dx -= Camera.WORLD_SIZE;
		if(dy >= Camera.WORLD_SIZE) dy -= Camera.WORLD_SIZE;
		
		int[] retVal = {dx, dy};
		return retVal;
	}
}
