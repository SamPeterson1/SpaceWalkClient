package GUI;

import javax.swing.JFrame;

import Events.EventHandler;
import Events.GUIEventQueue;


public class Main {
	
	public static final int LOOP_SPEED_MS = 10;
	public static final int WIDTH = 1020;
	public static final int HEIGHT = 1020;

	public static void main(String args[]) {

		GUIEventQueue queue = new GUIEventQueue();
		JFrame frame = new JFrame("SpaceWalk");
		GUICanvas canvas = new GUICanvas(WIDTH, HEIGHT);
		EventHandler eh = new EventHandler(queue, canvas);
		GUIFrame uiFrame = new GUIFrame(canvas, frame, eh);

        uiFrame.setSize(Main.WIDTH, Main.HEIGHT);
        canvas.addEventQueue(queue);
        
        Client client = new Client();
		client.connect("localhost", 7777);	
		
		String id = client.read();
		DataLoader.loadWorld(client.read());
		uiFrame.setID(id);
		
		uiFrame.start(uiFrame.getWidth(), uiFrame.getHeight(), client);        
        
	}
	
	public static void wait(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
