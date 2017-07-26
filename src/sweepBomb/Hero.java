package sweepBomb;

import java.awt.image.BufferedImage;

public class Hero extends Cell {

	public Hero(int x, int y, BufferedImage image) {
		super(x, y, image);
	}
	public void moveTo(int x,int y){
		this.x=x;
		this.y=y;
	}

}
