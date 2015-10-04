package Segment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import Actor.*;
import Controller.*;
import Enemy.*;

public class LevelSegment extends Segment {

	private Player player;
	private PauseMenu pm;
	private QuestController qc;
	private ArrayList<Bullet> bullets;
	private ArrayList<EnemyBullet> enemyBullets;
	private ArrayList<Enemy> enemies;
	private ArrayList<Planet> planets;
	private ArrayList<Asteroid> asteroids;
	private Random r = new Random();
	private HUD hud;
	private boolean paused;
	private int cr;
	
	public LevelSegment(Manager m) {
		this.m = m;
		
		
		player = new Player();
		player.setX(300);
		player.setY(300);
		
		pm = new PauseMenu(player.getLevels(true), player.getLevels(false));
		qc = new QuestController();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		enemyBullets = new ArrayList<EnemyBullet>();
		planets = new ArrayList<Planet>();
		asteroids = new ArrayList<Asteroid>();
		
		hud = new HUD(player, qc);
	}
	public void init() {
		// I have no idea what to put here.
	}

	
	public void update() {
		if (!paused) updateNotPaused();
		if (paused) updatePaused();
		
	}
	private void updatePaused() {
		// Do something here if needed.
	}
	private void updateNotPaused() {
		
		// Planets
		for (int i = 0; i < planets.size(); i++) {
			Planet planet = planets.get(i);
			planet.update();
		}
		
		// Asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			Asteroid asteroid = asteroids.get(i);
			asteroid.update();
			if (asteroid.getY() > GamePanel.HI) {
				asteroids.remove(i);
				i--;
			}
		}
				
		// Player
		player.update();
		
		// Collision Checking (Should this go after or before the rest?)
		collisionChecks();
		
		// Bullets
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			bullet.update();
			if (bullet.shouldRemove()) {
				bullets.remove(i);
				i--;
			}
		}
		for (int i = 0; i < enemyBullets.size(); i++) {
			EnemyBullet bullet = enemyBullets.get(i);
			bullet.update();
			if (bullet.shouldRemove()) {
				enemyBullets.remove(i);
				i--;
			}
		}
		
		// Enemy Management
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			enemy.update();
			if (enemy.getY() > GamePanel.HI) {
				if (enemy.isDead()) {
					cr += enemy.getCargo();
					if (qc.getQuest() == qc.DESTROY_SCA) {
						qc.setQuestGoal(qc.getQuestGoal() + 1);
					}
				}
				enemies.remove(i);
				i--;
			}
			if (enemy.isFiring()) {
				enemy.setFiring(false);
				createBullet(enemy);
			}
		}
		
		// Quest Management
		if (qc.isFinished()) {
			cr += qc.getReward();
			qc.setQuest(0);
		}
		
		
		// ** Spawn Management ** 
		// Update runs 60 times a second
		// So for every second you want, multiply by 60.
		// It is random, but statistically it should happen at the predicted time.
		if (r.nextInt(400) == 5) {
			// Every 8.3 Seconds
			createEnemy(1);
		}
		if (r.nextInt(1600) == 5) {
			// Every 26.6 Seconds
			createEnemy(2);
		}
		if (r.nextInt(1800) == 5) {
			// Every 30 seconds
			createPlanet();
		}
		if (r.nextInt(3600) == 5) {
			// Every 60 seconds.
			// We get a random asteroid belt every time.
			createAsteroids(r.nextInt(39) + 1);
		}
	}

	public void draw(Graphics2D g) {
		if (!paused) drawNotPaused(g);
		if (paused) drawPaused(g);
	}
	private void drawNotPaused(Graphics2D g) {
		// background draw
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WI, GamePanel.HI);
		
		// planets
		for (int i = 0; i < planets.size(); i++) {
			Planet planet = planets.get(i);
			planet.draw(g);
		}
		// asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			Asteroid asteroid = asteroids.get(i);
			asteroid.draw(g);
		}
		// debug stuff
		g.setColor(Color.WHITE);
		g.drawString(player.debug(), 10, 10);
		g.drawString("CR: " + cr, 10, 50);
		
		// bullets
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			bullet.draw(g);
		}
		for (int i = 0; i < enemyBullets.size(); i++) {
			EnemyBullet bullet = enemyBullets.get(i);
			bullet.draw(g);
		}
		
		// enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			enemy.draw(g);
		}
		// player
		player.draw(g);
		
		// HUD
		hud.draw(g);
	}
	private void drawPaused(Graphics2D g) {
		pm.draw(g);
	}
	public void keyPressed(int key) {
		if (!paused) setPlayerDirection(key, true);
		if (paused) {
			// If selection = 0, right arrow takes it to 1. Otherwise, modify player levels.
			
		}
	}

	public void keyReleased(int key) {
		if (!paused) {
			setPlayerDirection(key, false);
			if (key == KeyEvent.VK_SPACE && !paused && player.getLevels(false)[player.WEAPON] > 0) {
				createBullet();
			}
		} else {
			// Paused
			if (pm.getSelection() < 2) {
				// If we are at a planet upgrade menu.
				if (key == KeyEvent.VK_RIGHT) {
					pm.setSelection(1);
				}
				if (key == KeyEvent.VK_LEFT) {
					pm.setSelection(0);
				}
				if (key == KeyEvent.VK_SPACE) {
					if (pm.getPlanetType() == 5 && pm.getSelection() == pm.YES) {
						if (pm.getQuest() > 0) qc.setQuest(pm.getQuest());
					} else if (pm.getPlanetType() == 6 || pm.getPlanetType() == 0) {
						// In case we need anything here. Dave and Find Planet quests handled in collisions.
					} else if (pm.getSelection() == pm.YES && (cr + 1) > pm.getCost()) {
						cr -= pm.getCost();
						upgradePlayerLevels(pm.getPlanetTypeAsPlayer());
					}
					pm.setActivePlanet(false);
				}
			}
			else {
				// We're at the energy management menu
				// Left and Right get the selection, and subtract 1 because that matches with Player levels.
				// Up and Down shift the selection, while PauseMenu makes sure they don't go out of bounds.
				if (key == KeyEvent.VK_RIGHT) {
					setPlayerLevels(pm.getSelection() - 1,true);
				}
				if (key == KeyEvent.VK_LEFT) {
					setPlayerLevels(pm.getSelection() - 1, false);
				}
				if (key == KeyEvent.VK_UP) {
					pm.setSelection(pm.getSelection() - 1);
				}
				if (key == KeyEvent.VK_DOWN) {
					pm.setSelection(pm.getSelection() + 1);
				}
			}
		}
		
		if (key == KeyEvent.VK_P) {
			paused = !paused;
			pm.setCR(cr);
		}
	}

	private void setPlayerDirection(int key, boolean b) {
		if (key == KeyEvent.VK_LEFT) {
			player.setDirection(player.LEFT, b);
		}
		if (key == KeyEvent.VK_RIGHT) {
			player.setDirection(player.RIGHT, b);
		}
		if (key == KeyEvent.VK_UP) {
			player.setDirection(player.UP, b);
		}
		if (key == KeyEvent.VK_DOWN) {
			player.setDirection(player.DOWN, b);
		}
		
	}
	private void setPlayerLevels(int level, boolean adding) {
		int[] levels = player.getLevels(false);
		int[] maxLevels = player.getLevels(true);
		if (adding) {
			if (levels[player.BATTERY] > 0 && levels[level] < maxLevels[level]) {
				levels[level] = levels[level] + 1;
				levels[player.BATTERY] = levels[player.BATTERY] - 1;
			}
		} else if (!adding) {
			if (levels[level] > 0) {
				levels[level] = levels[level] - 1;
				levels[player.BATTERY] = levels[player.BATTERY] + 1;
			}
		}
		player.setLevels(levels, false);
		player.setLevels(maxLevels, true);
	}
	private void upgradePlayerLevels(int level) {
		int[] maxLevels = player.getLevels(true);
		int[] levels = player.getLevels(false);
		if (level == player.BATTERY) {
			levels[level] = levels[level] + 1;
			player.setLevels(levels, false);
			pm.setPlayerLevels(maxLevels, levels);
		} else {
			maxLevels[level] = maxLevels[level] + 1;
			player.setLevels(maxLevels, true);
			pm.setPlayerLevels(maxLevels, levels);
		}
		if (level == player.HULL) {
			player.setHP(maxLevels[level]);
		}
	}
	private void createEnemy(int type) {
		if (type == 1) { 
			Enemy enemy = new EnemyScavenger(); 
			enemies.add(enemy);
		} else if (type == 2) {
			Enemy enemy = new NeutralTransport();
			enemies.add(enemy);
		}
	}
	private void createBullet() {
		Bullet bullet = player.createBullet();
		bullets.add(bullet);
	}
	private void createBullet(Enemy enemy) {
		EnemyBullet bullet = enemy.createBullet();
		enemyBullets.add(bullet);
	}
	private void createPlanet() {
		Planet planet = new Planet();
		planets.add(planet);
	}
	private void createAsteroids(int amount) {
		for (int i = 0; i < amount; i++) {
			Asteroid ast = new Asteroid();
			asteroids.add(ast);
		}
	}
	private void collisionChecks() {
		// Enemy Collisions
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			// Check For Bullet Collision
			for (int j = 0; j < bullets.size(); j++) {
				Bullet bullet = bullets.get(j);
				if (enemy.getRectangle().intersects(bullet.getRectangle())) {
					enemy.hit(bullet.getDamage());
					bullets.remove(j);
					j--;
				}
			}
			// Check for EnemyBullet Collision
			for (int j = 0; j < enemyBullets.size(); j++) {
				EnemyBullet bullet = enemyBullets.get(j);
				if (enemy.getRectangle().intersects(bullet.getRectangle())) {
					enemy.hit(bullet.getDamage());
					enemyBullets.remove(j);
					j--;
				}
			}
			// Check for Asteroid Collision
			for (int j = 0; j < asteroids.size(); j++) {
				Asteroid asteroid = asteroids.get(j);
				if (enemy.getRectangle().intersects(asteroid.getRectangle())) {
					asteroid.hit(1);
					enemy.hit(asteroid.getHP());
				}
			}
			
			// Possibly check for Enemy Collision in the future?
		}
		// Player Collision
		
		for (int j = 0; j < enemyBullets.size(); j++) {
			EnemyBullet bullet = enemyBullets.get(j);
			
			if (player.getRectangle().intersects(bullet.getRectangle())) {
				player.hit(bullet.getDamage());
				enemyBullets.remove(j);
				j--;
			}
			else {
				for (int k = 0; k < bullets.size(); k++) {
					Bullet otherBullet = bullets.get(k);
					if (bullet.getRectangle().intersects(otherBullet.getRectangle())) {
						enemyBullets.remove(j);
						bullets.remove(k);
						k--;
						j--;
						k = bullets.size();
					}
				}
			}
		}
		pm.setAtPlanet(false);
		for (int j = 0; j < planets.size(); j++) {
			Planet planet = planets.get(j);
			if (planet.getRectangle().intersects(player.getRectangle())) {
				if (qc.getQuest() == qc.REACH_GOAL_PLANET && planet.getType() == 0) {
					qc.setQuestProgress(1);
				}
				if (qc.getQuest() == qc.FIND_DAVE && planet.getType() == 6) {
					qc.setQuestProgress(1);
				}
				pm.setType(planet.getType());
				pm.setAtPlanet(true);
				pm.setPlayerLevels(player.getLevels(true), player.getLevels(false));
				pm.setQuest(planet.getQuest());
			}
		}
		
		// Asteroid Collisions
		for (int j = 0; j < asteroids.size(); j++) {
			// Asteroids become smaller after every hit. 
			Asteroid asteroid = asteroids.get(j);
			if (asteroid.getRectangle().intersects(player.getRectangle())) {
				player.hit(asteroid.getHP());
				asteroid.hit(1);
			}
			for (int k = 0; k < bullets.size(); k++) {
				Bullet b = bullets.get(k);
				if (asteroid.getRectangle().intersects(b.getRectangle())) {
					asteroid.hit(1);
					bullets.remove(b);
					k--;
				}
			}
			for (int k = 0; k < enemyBullets.size(); k++) {
				EnemyBullet b = enemyBullets.get(k);
				if (asteroid.getRectangle().intersects(b.getRectangle())) {
					asteroid.hit(1);
					enemyBullets.remove(b);
					k--;
				}
			}
		}
	}

}

