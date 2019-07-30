package Stubs;

public class Refinery {
	
	public static int selectedX = 0;
	public static int selectedY = 0;
	
	public int x;
	public int y;
	public int power;
	public int rate;
	public int input;
	public String producing;
	
	public Refinery(int x, int y, int power, int rate, String producing, int input) {
		this.x = x;
		this.y = y;
		this.power = power;
		this.rate = rate;
		this.producing = producing;
		this.input = input;
	}
	
	
}
