package ImageLoaders;

public class PlayerImageLoader extends ImageLoader{
	
	private String[] directions = {"North", "East", "South", "West"};
	
	public PlayerImageLoader() {
		for(int i = 0; i < 4; i ++) {
			for(int ii = 0; ii < 3; ii ++) {
				System.out.println(Integer.valueOf(i*3+ii) + "," + (directions[i] + "_0" + (ii+1)));
				super.setImage(Integer.valueOf(i*3+ii), directions[i] + "_0" + (ii+1));
			}
		}
	}
	
}
