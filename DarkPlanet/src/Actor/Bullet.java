package Actor;

import java.awt.Color;
import java.awt.Graphics2D;

import Controller.GamePanel;

public class Bullet extends Actor {
	
	private boolean remove;
	protected Color color;
	private int damage;

	public Bullet(double speed, int direction) {
		wi = hi = 8;
		left = direction == LEFT;
		right = direction == RIGHT;
		up = direction == UP;
		down = direction == DOWN;
		moveSpeed = speed;
		color = Color.ORANGE;
	}
	public void update() {
		move();
		x += dx;
		y += dy;
		
		if (x >  GamePanel.WI || x < 0 || y > GamePanel.HI) {
			remove = true;
		}
	}

	public boolean shouldRemove() {
		return remove;
	}
	public void setDamage(int d) {
		damage = d;
	}
	public int getDamage() {
		return damage;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fill(getRectangle());
	}

}
