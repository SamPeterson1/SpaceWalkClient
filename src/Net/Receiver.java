package Net;

import java.util.ArrayList;

import GUI.DataLoader;
import Stubs.Building;

public class Receiver {
	
	public static int[][] world;
	public static ArrayList<Building> buildings;
	
	public Receiver(String world, String id) {
		DataLoader.load(world, id);
		Receiver.world = DataLoader.world;
		Receiver.buildings = DataLoader.buildings;
	}
	
	public void update(Request message) {
		if(message.getMessage().equals("edit")) {
			int x = message.getInt("x");
			int y = message.getInt("y");
			int id = message.getInt("id");
			Receiver.world[y][x] = id;
		} else if(message.getMessage().equals("build")) {
			//Building b = Building.getBuilding(message);
			//Receiver.buildings.add(b);
		}
	}
	
}
