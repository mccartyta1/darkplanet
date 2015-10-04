package Enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import Controller.GamePanel;

public class EnemyScavenger extends Enemy{
	
	public EnemyScavenger() {
		dead = false;
		// Random
		r = new Random();
		
		// Stats
		mhp = hp = 3;
		engineLevel = r.nextInt(1) + 1;
		weaponLevel = r.nextInt(1) + 1;
		wi = hi = 15;
		cargo = 5;
		
		// Move Speed
		moveSpeed = 1.5 * engineLevel;
		down = true;
		x = r.nextInt(GamePanel.WI);
		y = -100 + r.nextInt(35);
		
		// "AI"
		thinkTime = 0;
		thinkTimeNeeded = 70;
		chanceToRun = 10;
	}
	public void think() {
		if (!dead) {
			// The Brain of our guy. "AI" if you wanna call it that.
			// The 1000 gives us room for multiple options.
			int chance = r.nextInt(1000);
			
			// There is a 1.5% in the beginning, which goes up as they lose health. 
			if (chance < 15 + (-hp + mhp) * 20 + chanceToRun) {
				state = RUNNING;
			}
			// 70% chance to fire when it thinks.
			if (chance > 100 && chance < 800 && y > 0) {
				firing = true;
			}
			if (chance > 800 && chance < 900) {
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
			if (!(state == RUNNING) && (y > (GamePanel.HI / 4) * 3)) up = true; // 3/4ths of the screen down.
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
		bullet.setDamage(1);
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
