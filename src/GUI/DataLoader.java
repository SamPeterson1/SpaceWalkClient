package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataLoader {
	
	public static int[][] world = new int[256][256];
	public static int[] playersPos;
	public static int[] playersSprite;
	public static ArrayList<Building> buildings;
	public static int speed;
	
	public static void loadWorld(String str) {
		String[] temp = str.split(",");
		for(int i = 0; i < 256; i ++) {
			for(int ii = 0; ii < 256; ii ++) {
				DataLoader.world[i][ii] = Integer.parseInt(temp[ii*256+i]);
			}
		}
	}
	
	public static void load(String str, String id) {
		
		//System.out.println(str + id);
		JSONObject json = new JSONObject(str);
		JSONObject tiles = json.getJSONObject("tiles");
		
		JSONArray ids = tiles.getJSONArray("ids");
		for(int i = 0; i < ids.length(); i++) {
			JSONArray pos = tiles.getJSONArray(ids.getString(i));
			for(int ii = 0; ii < pos.length(); ii += 2) {
				//System.out.println(pos.getInt(ii) + " " + pos.getInt(ii+1));
				DataLoader.world[pos.getInt(ii)][pos.getInt(ii+1)] = Integer.parseInt(ids.getString(i));
			}
		}
		
		JSONObject players = json.getJSONObject("players");
		JSONArray playerIDs = players.getJSONArray("ids");
		DataLoader.playersPos = new int[playerIDs.length()*2];
		DataLoader.playersSprite = new int[playerIDs.length()];
		for(int i = 0; i < playerIDs.length(); i ++) {
			JSONArray pos = players.getJSONArray(playerIDs.getString(i));
			DataLoader.playersSprite[i] = pos.getInt(2);
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
		
	}
}
