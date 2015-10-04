package Controller;

import java.util.ArrayList;

import Segment.*;

public class Manager {
	
	private ArrayList<Segment> segments;
	private int currentState;
	
	public static final int MENU = 0;
	public static final int LEVEL = 1;
	
	public Manager() {
		
		segments = new ArrayList<Segment>();
		// Development Mode. Replace LEVEL with MENU.
		currentState = LEVEL;
		segments.add(new MenuSegment(this));
		segments.add(new LevelSegment(this));
		
	}
	
	public void setState(int state) {
		currentState = state;
		segments.get(currentState).init();
	}
	
	public void update() {
		segments.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		segments.get(currentState).draw(g);
	}
	
	public void keyPressed(int k) {
		segments.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		segments.get(currentState).keyReleased(k);
	}
	
}









