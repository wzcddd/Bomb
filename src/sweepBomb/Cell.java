package sweepBomb;

import java.awt.image.BufferedImage;

public class Cell {
	protected int x;
	protected int y;
	protected int xspeed;
	protected int yspeed;
	protected BufferedImage image;
	public Cell(int x,int y,BufferedImage image){
		this.x=x;
		this.y=y;
		this.image=image;
	}
	public int getx(){
		return this.x;
	}
	public int gety(){
		return this.y;
	}
	public BufferedImage getImage(){
		return this.image;
	}
	public void setImage(BufferedImage image){
		this.image=image;
	}
	public void moveUp(){
		this.y-=2;
	}
	public void setSpeed(int xspeed,int yspeed){
		this.xspeed=xspeed;
		this.yspeed=yspeed;
	}
	public void bang(){
		x+=xspeed;
		y+=yspeed;
	}
}
