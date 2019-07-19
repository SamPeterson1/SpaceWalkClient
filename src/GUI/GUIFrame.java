package GUI;


import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.security.SecureRandom;

import javax.swing.JFrame;

import Events.EventHandler;
import Events.Request;


public class GUIFrame {
	
	private GUICanvas canvas;
	private int width;
	private int height;
	private JFrame frame;
	private EventHandler eh;
	SecureRandom rand;
	int n;
	String id;
	int i = 9;
	
	public GUIFrame(GUICanvas c, JFrame f, EventHandler eh) {
		this.canvas = c;
		this.frame = f;
		this.eh = eh;
        frame.add(c);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rand = new SecureRandom();
        n =	rand.nextInt(1) + 2;
	}
	
	public static void newFrame(int width, int height, String title, GUICanvas c) {
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.setTitle(title);
		frame.add(c);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	public void setSize(int width, int height) {
		frame.setSize(width, height);
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public void start(int WIDTH, int HEIGHT, Client client) {
	    boolean running=true;
	    boolean iterateNext = false;
	    while(running) {
		    BufferStrategy bs = canvas.getBufferStrategy();
		    if(bs==null){
		        canvas.createBufferStrategy(4);
		        iterateNext = false;
		    } else {
		   	 	iterateNext = true;
		    }
		    if(iterateNext) {

		    	//System.out.println("I LIVED");
		    	i ++;
		    	if(i == 100) { 
			    	Request edit = new Request("edit");
			    	edit.addParameter("x", String.valueOf(rand.nextInt(32)));
			    	edit.addParameter("y", String.valueOf(rand.nextInt(32)));
			    	edit.addParameter("id", "2");
			    	client.write(edit.toString());
			    	i = 0;
		    	}
		    	
		    	Request data = new Request("data");
		    	data.addParameter("id", id);
		    	client.write(data.toString());
		    	String line = client.read();
			    if(line.startsWith("{"))
			    	DataLoader.load(line, id);
		    	
			    //System.out.println("I LIVED");
		    	Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		    	
		    	//String msg = client.read();
		    	
				client.write(eh.handleEvents(id));
			    Main.wait(Main.LOOP_SPEED_MS);
			    bs.show();
			    canvas.draw(g);
		    }
	    }
	}
}

