package ImageLoaders;

import java.awt.Image;

public class TetherImageLoader extends ImageLoader{

	int animateState = 0;
	long timer = 0;
	int timerReset = 500;
	
	public TetherImageLoader() {
		super.setImage(0, "TetherDisable");
		super.setImage(1, "TetherO2");
		super.setImage(2, "TetherPow_01");
		super.setImage(3, "TetherPow_02");
		this.timer = System.currentTimeMillis();
	}
	
	@Override
	public Image getImage(Integer id) {
		if(id == 0) return super.images.get(id);
		if(id > 1) {		
			System.out.println(this.animateState);
			return super.images.get(id+this.animateState);
		} else {
			return super.images.get(id);
		}
	}
	
	public void animate() {
		if(System.currentTimeMillis() - this.timer >= this.timerReset) {
			this.animateState = this.animateState == 1 ? 0 : 1;
			this.timer = System.currentTimeMillis();
		}
	}
	
}
