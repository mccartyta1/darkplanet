package Actor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import Controller.GamePanel;

public class Planet {
	
	Random r;
	private int type, quest, width, height;
	private boolean active;
	private double x, y;
	private Color color;
	
	private final int DULL = 0; // Nothing.
	private final int BLUE = 1; // Hull
	private final int RED = 2; // Weapons
	private final int YELLOW = 3; // Engines
	private final int GREEN = 4; // Shields
	private final int GOLD = 5; // Mission Giver
	private final int SILVER = 6; // Possible Goal
	private final int PINK = 7; // Battery
	
	public Planet() {
		r = new Random();
		type = r.nextInt(8);
		y = -100 + r.nextInt(35);
		x = r.nextInt(GamePanel.HI);
		width = 100;
		height = 100;
		active = true;
		if (type == 0) {
			color = Color.WHITE;
		} else if (type == 1) {
			color = Color.BLUE;
		} else if (type == 2) {
			color = Color.RED;
		} else if (type == 3) {
			color = Color.YELLOW;
		} else if (type == 4) {
			color = Color.GREEN;
		} else if (type == 5) {
			// No Default Gold Color
			color = Color.GRAY;
			quest = r.nextInt(4);
		} else if (type == 6) {
			// No Default Silver Color
			color = Color.GRAY;
		} else {
			color = Color.PINK;
		}
	}
	
	public void update() {
		y += 1;
	}
	
	public int getType() { return type; }
	public boolean isActive() { return active; }
	public int getQuest() { return quest; }
	public void setActive(boolean b) { active = b; }
	
	public Rectangle getRectangle() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fill(getRectangle());
	}

}
