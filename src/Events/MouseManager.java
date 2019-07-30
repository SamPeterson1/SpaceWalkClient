package Events;

import java.util.HashMap;

public class MouseManager {

	public static HashMap<ClickGroup, GroupHandler> groupHandlers = new HashMap<ClickGroup, GroupHandler>();
	
	public static void clear() {
		//MouseManager.groupHandlers = new HashMap<ClickGroup, GroupHandler>();
	}
	
	public static void handleClick(int x, int y, MouseClick type) {
		for(GroupHandler gh: MouseManager.groupHandlers.values()) {
			gh.handleClick(x, y, type);
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
