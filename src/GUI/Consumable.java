package GUI;

import java.awt.Color;

public class Consumable {
	
	int amount;
	int max;
	//int production;
	String name;
	
	public Consumable(String name, int amount, int max) {
		this.name = name;
		this.amount = amount;
		this.max = max;
	}
	
	public int getAmount() {
		return this.amount;
	}

	public int getMax() {
		return this.max;
	}
	
	public String getName() {
		return this.name;
	}
	
}
