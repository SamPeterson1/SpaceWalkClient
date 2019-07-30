package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import Events.MouseManager;
import Stubs.Building;
import Stubs.Refinery;

public class DataLoader {
	
	public static int[][] world = new int[256][256];
	public static int[] bag = new int[8];
	public static int[] playersPos;
	public static int[] playersSprite;
	public static int[] playersTether;
	public static ArrayList<Building> buildings;
	public static HashMap<String, Consumable> playerResources;
	public static HashMap<Integer, Vector<Integer>> tethers;
	public static ArrayList<Refinery> refineries;
	public static int speed;
	public static int selectedRefineryX;
	public static int selectedRefineryY;
	public static boolean inBag;
	public static boolean ready = false;
	
	public static void loadWorld(String str) {
		String[] temp = str.split(",");
		for(int i = 0; i < 256; i ++) {
			for(int ii = 0; ii < 256; ii ++) {
				DataLoader.world[i][ii] = Integer.parseInt(temp[ii*256+i]);
			}
		}
	}
	
	public static void load(String str, String id) {
		
		MouseManager.clear();
		DataLoader.ready = true;
		
		JSONObject json = new JSONObject(str);
		JSONObject tiles = json.getJSONObject("tiles");
		
		JSONArray ids = tiles.getJSONArray("ids");
		for(int i = 0; i < ids.length(); i++) {
			JSONArray pos = tiles.getJSONArray(ids.getString(i));
			for(int ii = 0; ii < pos.length(); ii += 2) {
				DataLoader.world[pos.getInt(ii)][pos.getInt(ii+1)] = Integer.parseInt(ids.getString(i));
			}
		}
		
		JSONObject players = json.getJSONObject("players");
		JSONArray playerIDs = players.getJSONArray("ids");
		DataLoader.playersPos = new int[playerIDs.length()*2];
		DataLoader.playersTether = new int[playerIDs.length()*2];
		DataLoader.playersSprite = new int[playerIDs.length()];
		DataLoader.playerResources = new HashMap<String, Consumable>();
		JSONArray names = players.getJSONArray("names");
		JSONArray values = players.getJSONArray("vals");
		JSONArray maximums = players.getJSONArray("max");
		JSONArray bag = players.getJSONArray("bag");
		
		DataLoader.inBag = players.getBoolean("inBag");
		
		for(int i = 0; i < names.length(); i ++) {
			DataLoader.playerResources.put(names.getString(i), new Consumable(names.getString(i), values.getInt(i), maximums.getInt(i)));
		}
		
		for(int i = 0; i < 8; i ++) {
			DataLoader.bag[i] = bag.getInt(i);
		}
		
		for(int i = 0; i < playerIDs.length(); i ++) {
			JSONArray pos = players.getJSONArray(playerIDs.getString(i));
			DataLoader.playersSprite[i] = pos.getInt(2);
			DataLoader.playersTether[i*2] = pos.getInt(4);
			DataLoader.playersTether[i*2+1] = pos.getInt(5);
			if(!(playerIDs.getString(i).equals(id))) {
				DataLoader.playersPos[i*2] = pos.getInt(0);
				DataLoader.playersPos[i*2+1] = pos.getInt(1);
			} else {
				DataLoader.playersPos[i*2] = -1;
				DataLoader.playersPos[i*2+1] = -1;
				DataLoader.speed = pos.getInt(3);
			}
		}
		
		DataLoader.buildings = new ArrayList<Building>();
		
		for(int i = 1; i < 3; i ++) {
			JSONObject building = json.getJSONObject("Building " + i);
			JSONArray pos = building.getJSONArray("pos");
			JSONArray connectedPos = building.getJSONArray("connectedPos");
			JSONArray connectedNum = building.getJSONArray("connectedNum");
			JSONArray resources = building.getJSONArray("resources");
			JSONArray production = building.getJSONArray("production");
			JSONArray linked = building.getJSONArray("linked");
			JSONArray active = building.getJSONArray("active");
			JSONArray max = building.getJSONArray("max");
			int index = 0;
			for(int ii = 0; ii < pos.length()/2; ii ++) {
				Building b = new Building(i);
				HashMap<String, Consumable> consumables = new HashMap<String, Consumable>();
				for(int j = 0; j < resources.length(); j ++) {
					JSONArray amount = building.getJSONArray(resources.getString(j));
					consumables.put(resources.getString(j), new Consumable(resources.getString(j), amount.getInt(ii), max.getInt(j)));
				}
				b.setConsumables(consumables);
				b.setX(pos.getInt(ii*2));
				b.setY(pos.getInt(ii*2+1));
				b.setActive(active.getBoolean(ii));
				b.setLinked(linked.getBoolean(ii));
				for(int j = 0; j < connectedNum.getInt(ii); j ++) {
					b.connectTo(connectedPos.getInt(index), connectedPos.getInt(index + 1));
					index +=2;
				}
				DataLoader.buildings.add(b);
				Vector<Integer> v = new Vector<Integer>();
				v.add(b.getX());
				v.add(b.getY());
			}
		}
		
		
		DataLoader.tethers = new HashMap<Integer, Vector<Integer>>();
		JSONObject tethers = json.getJSONObject("tethers");
		for(int i = 0; i < tethers.length(); i ++) {
			JSONArray tether = tethers.getJSONArray(String.valueOf(i));
			Vector<Integer> pos = new Vector<Integer>();
			for(int ii = 0; ii < tether.length(); ii ++) {
				pos.add(tether.getInt(ii));
			}
			DataLoader.tethers.put(Integer.valueOf(i), pos);
		}
	
		DataLoader.refineries = new ArrayList<Refinery>();
		JSONObject refineries = json.getJSONObject("refineries");
		for(int i = 0; i < refineries.length(); i ++) {
			JSONArray dat = refineries.getJSONArray(String.valueOf(i));
			DataLoader.refineries.add(new Refinery(dat.getInt(0), dat.getInt(1), dat.getInt(4), dat.getInt(3), dat.getString(2), dat.getInt(5)));
		}
	}
}
