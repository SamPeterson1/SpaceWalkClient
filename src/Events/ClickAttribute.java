package Events;

import java.util.ArrayList;
import java.util.HashMap;

public class ClickAttribute {

	private MouseClick clickType;
	private boolean doesReset;
	private boolean selected;
	
	public ClickAttribute(MouseClick clickType, boolean doesReset, boolean selected) {
		this.clickType = clickType;
		this.doesReset = doesReset;
		this.selected = selected;
	}

	public MouseClick getClickType() {
		return clickType;
	}

	public void setClickType(MouseClick clickType) {
		this.clickType = clickType;
	}

	public boolean doesReset() {
		return doesReset;
	}

	public void setReset(boolean doesReset) {
		this.doesReset = doesReset;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
