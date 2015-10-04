package Actor;

import java.awt.Color;
import java.awt.Graphics2D;

import Controller.GamePanel;

public class Player extends Actor {
	
	// Due to Actor dependency on the hp variable, levels does not have the HULL section. Max levels does.
	private int[] levels = {0, 1, 1, 0};
	private int[] maxLevels = {3, 2, 2, 0, 5};
	
	public final int BATTERY = 0;
	public final int ENGINE = 1;
	public final int WEAPON = 2;
	public final int SHIELD = 3;
	public final int HULL = 4;
	
	
	public Player() {
		hp = maxLevels[HULL];
		wi = 32;
		hi = 32;
		moveSpeed = 1.8;
		
	}
	
	public void update() {
		// For engine level impact, update moveSpeed based on engineLevel.
		moveSpeed = 1.3 + (levels[ENGINE] * .5);
		move();
		checkLocation();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.gray);
		g.fill(getRectangle());
	}
	
	public String debug() {
		return dx + " " + dy;
	}
	
	public Bullet createBullet() {
		double bulletSpeed = 2.5 + (.6 * levels[WEAPON]);
		Bullet b = new Bullet(bulletSpeed, UP);
		b.setX(x + wi / 2);
		b.setY(y - hi / 4);
		b.setDamage(levels[WEAPON]);
		return b;
	}
	
	private void checkLocation() {
		// Checking to make sure we don't move out of the play area.
		double nx = x + dx;
		double ny = y + dy;
		if (nx < 0 || nx + wi > GamePanel.WI) {
			nx = x;
		}
		if (ny < 0 || ny + hi > GamePanel.HI) {
			ny = y;
		}
		x = nx;
		y = ny;
	}
	
	public int[] getLevels(boolean max) {
		if (max) return maxLevels;
		else return levels;
	}
	public void setLevels(int[] newLevels, boolean max) {
		if (max) maxLevels = newLevels;
		else levels = newLevels;
	}
}
