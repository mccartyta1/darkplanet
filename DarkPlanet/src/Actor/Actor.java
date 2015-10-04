package Actor;

import java.awt.Rectangle;

// Our basic object. But I didn't call it Object.
public abstract class Actor {
	
	protected double x, y, dx, dy, moveSpeed;
	protected int hp, type, wi, hi;
	protected boolean left, right, up, down, dead;
	public final int LEFT = 0;
	public final int RIGHT = 1;
	public final int UP = 2;
	public final int DOWN = 3;
	// Development Color
	
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public double getX() { return x; }
	public double getY() { return y; }
	public int getHP() { return hp; }
	public void setHP(int nhp) { hp = nhp; }
	public void setX(double nx) { x = nx; }
	public void setY(double ny) { y = ny; }
	public void setWidth(int nwi) { wi = nwi; }
	public void setHeight (int nhi) { hi = nhi; }
	public void move() {
		// Deciding dx and dy values based on key presses.
				if (left) {
					dx = -moveSpeed;
				} else if (right) {
					dx = moveSpeed;
				} else dx = 0;
				if (up) {
					dy = -moveSpeed;
				} else if (down) {
					dy = moveSpeed;
				} else dy = 0;
	}
	public void setDirection(int i, boolean b) {
		if (i == LEFT) {
			left = b;
		}
		if (i == RIGHT) {
			right = b;
		}
		if (i == UP) {
			up = b;
		}
		if (i == DOWN) {
			down = b;
		}
	}
	
	public void hit(int damage) {
		hp -= damage;
		
		if (hp < 1) {
			hp = 0;
			dead = true;
		}
	}
	
	public Rectangle getRectangle() { return new Rectangle((int)x, (int)y, wi, hi); }

}
