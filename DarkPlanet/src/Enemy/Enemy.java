package Enemy;

import java.awt.Graphics2D;
import java.util.Random;

import Actor.Actor;

public abstract class Enemy extends Actor {
	
	protected boolean firing, dead;
	protected int hp, mhp, weaponLevel,maxWeaponLevel, engineLevel, maxEngineLevel, shieldLevel, 
	maxShieldLevel, batteryLevel, maxBatteryLevel;
	protected int thinkTime, thinkTimeNeeded, state, chanceToRun, cargo;
	protected Random r;
	
	protected final int NORMAL = 0;
	protected final int RUNNING = 1;
	protected final int ANGRY = 2;
	
	public abstract void think();
	
	public void hit(int damage) {
		hp -= damage;
		
		if (hp < 1) {
			hp = 0;
			dead = true;
		}
	}
	
	public boolean isDead() {
		return dead;
	}
	public int getCargo() { return cargo; }
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void setFiring(boolean b);
	public abstract boolean isFiring();
	public abstract EnemyBullet createBullet();

}
