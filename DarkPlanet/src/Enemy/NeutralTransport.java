package Enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import Controller.GamePanel;
// Neutral Transport
// Does not fire. Thinks less often, and is running already.
// Debatable whether it should be an Actor or Enemy. Enemy allows the think(), which is why it was chosen.

public class NeutralTransport extends Enemy {

	public NeutralTransport() {
		dead = false;
		// Random
		r = new Random();
		
		// Stats
		state = RUNNING;
		mhp = hp = 8;
		engineLevel = r.nextInt(1) + 2;
		weaponLevel = 0;
		wi = 35;
		hi = 20;
		cargo = 15;
		
		// Move Speed
		moveSpeed = 1.5 + (.3 * engineLevel);
		down = true;
		x = r.nextInt(GamePanel.WI);
		y = -100 + r.nextInt(35);
		
		// "AI"
		thinkTime = 0;
		thinkTimeNeeded = 150;
		chanceToRun = 40;
	}
	public void think() {
		if (!dead) {
			// The Brain of our guy. "AI" if you wanna call it that.
			// The 1000 gives us room for multiple options.
			int chance = r.nextInt(1000);
			
			// There is a 1.5% in the beginning, which goes up as they lose health. 
			if (chance > 700 && chance < 900) {
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
			moveSpeed = 4.5;
		}
	}

	@Override
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

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.CYAN);
		g.fill(getRectangle());
		g.drawString("HP:" + hp, (int)x + wi / 2, (int)y - 5);
	}

	@Override
	public void setFiring(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFiring() {
		// Never fires. He's cool.
		return false;
	}

	@Override
	public EnemyBullet createBullet() {
		// Shouldn't be firing at all.
		return null;
	}

}
