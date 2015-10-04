package Enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import Controller.GamePanel;

public class BossRogue extends Enemy{
	
	public BossRogue() {
		dead = false;
		// Random
		r = new Random();
		
		// Stats
		mhp = hp = 25;
		engineLevel = 3;
		weaponLevel = 3;
		wi = hi = 32;
		cargo = 250;
		
		// Move Speed
		moveSpeed = 1.5 * engineLevel;
		down = true;
		x = GamePanel.WI / 2;
		y = -135;
		
		// "AI"
		thinkTime = 10;
		thinkTimeNeeded = 30;
		chanceToRun = 0;
	}
	public void think() {
		if (!dead) {
			// The 1000 gives us room for multiple options.
			int chance = r.nextInt(1000);
			
			// There is no chance to run for a boss. 
			
			// 70% chance to fire when it thinks.
			if (chance > 100 && chance < 600 && y > 0) {
				firing = true;
			}
			if (chance > 600 && chance < 900) {
				if (left) {
					right = true;
					left = false;
				} else {
					left = true;
					right = false;
				}
			}
			// Adjusts only when it thinks, gives a bit of unpredictability as a whole, but
			// only this enemy will behave how it does, due to the timer and y-position.
			if (x < 0) right = true;
			if (x > GamePanel.WI) left = true;
			if (y < 0 || (state == RUNNING)) down = true;
			if (!(state == RUNNING) && (y > (GamePanel.HI / 8))) up = true; // 3/4ths of the screen down.
		} else {
			down = true;
			up = false;
			moveSpeed = 5;
		}
		
	}
	
	public void setFiring(boolean b) {
		firing = b;
	}
	public boolean isFiring() {
		return firing;
	}
	
	public EnemyBullet createBullet() {
		int direction;
		if (up) {
			direction = UP;
		} else {
			direction = DOWN;
		}
		EnemyBullet bullet = new EnemyBullet(2.0 * weaponLevel, DOWN);
		bullet.setX(x + wi / 2);
		bullet.setY(y + hi + hi / 2);
		// Shooting up is pretty unneeded. Possibly include in higher level enemies.
		//if (direction == DOWN) bullet.setY(y + hi + hi / 2);
		//if (direction == UP) bullet.setY(y - hi);
		bullet.setDamage(2);
		return bullet;
	}

	public void update() {
		move();
		x += dx;
		y += dy;
		thinkTime++;
		if (thinkTime == thinkTimeNeeded) {
			thinkTime = 0;
			think();
		}
		
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fill(getRectangle());
		g.drawString("HP:" + hp, (int)x + wi / 2, (int)y - 5);
	}

}
