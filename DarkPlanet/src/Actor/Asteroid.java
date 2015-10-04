package Actor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import Controller.GamePanel;

public class Asteroid extends Actor {

	public Asteroid() {
		
		Random r = new Random();
		hp = r.nextInt(3) + 2;
		wi = hi = hp * 7;
		x = r.nextInt(GamePanel.HI);
		y = -250 + r.nextInt(150);
	}
	
	public void update() {
		x += 0;
		y += 2;
		// Use health as a base for size. 
		wi = hi = hp * 7;
	}
	public boolean isDead() { return dead; }
	public void draw(Graphics2D g) {
		// Does not have brown :(
		g.setColor(Color.YELLOW);
		g.fill(getRectangle());
	}

}
