package Events;

import java.util.ArrayList;
import java.util.HashMap;

import Net.Request;

public class MouseManager {

	public static HashMap<ClickGroup, GroupHandler> groupHandlers = new HashMap<ClickGroup, GroupHandler>();
	
	public static void clear() {
		for(GroupHandler gh: MouseManager.groupHandlers.values()) {
			gh.eraseClickable();
		}
	}
	
	public static String handleClick(int x, int y, MouseClick type) {
		ArrayList<Request> retVal = new ArrayList<Request>();
		StringBuilder sb = new StringBuilder("");
		for(GroupHandler gh: MouseManager.groupHandlers.values()) {
			retVal.add(gh.handleClick(x, y, type));
		}
		
		for(Request r: retVal) {
			if(r != null)
				sb.append(r.toString()).append("\n");
		}
		
		return sb.toString();
	}
	
	public static Clickable getSelected(ClickGroup group, MouseClick type) {
		return MouseManager.groupHandlers.get(group).getSelected(type);
	}
	
	public static void reSelect() {
		for(GroupHandler gh: MouseManager.groupHandlers.values()) {
			gh.reSelect();
		}
	}
	
	public static void addClickable(Clickable c) {
		ClickGroup group = c.getGroup();
		if(MouseManager.groupHandlers.containsKey(group)) {
			MouseManager.groupHandlers.get(group).addClickable(c);
		} else {
			GroupHandler gh = new GroupHandler(group);
			gh.addClickable(c);
			MouseManager.groupHandlers.put(group, gh);
		}
	}

}
