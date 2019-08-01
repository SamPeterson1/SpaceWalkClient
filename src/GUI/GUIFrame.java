package GUI;


import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.security.SecureRandom;

import javax.swing.JFrame;

import Events.EventHandler;
import Net.Client;
import Net.Receiver;
import Net.Request;


public class GUIFrame {
	
	private GUICanvas canvas;
	private int width;
	private int height;
	private JFrame frame;
	private EventHandler eh;
	private Receiver receiver;
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
	    this.receiver = new Receiver(client.read(), id);
	    client.write("edit x:" + rand.nextInt(16) + " y:" + rand.nextInt(16) + " id:2");
	    while(running) {
		    BufferStrategy bs = canvas.getBufferStrategy();
		    if(bs==null){
		        canvas.createBufferStrategy(4);
		        iterateNext = false;
		    } else {
		   	 	iterateNext = true;
		    }
		    if(iterateNext) {
		    	
		    	String line;
		    	while ((line = client.nonBlockRead()) != "") {
				    if(line.startsWith("{")) {
				    	DataLoader.load(line, id);
				    } else {
				    	this.receiver.update(Request.parseString(line));
				    }
		    	}
		    	
			    //System.out.println("I LIVED");
		    	Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		    	
		    	//String msg = client.read();
		    	
		    	String event = eh.handleEvents(id);
		    	if(!event.equals("")) {
		    		System.out.println("ree" + event);
		    		client.write(event);
					
		    	}
			    Main.wait(Main.LOOP_SPEED_MS);
			    bs.show();
			    canvas.draw(g);
		    }
	    }
	}
}

