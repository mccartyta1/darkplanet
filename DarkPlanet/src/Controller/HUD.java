package Controller;

import java.awt.Color;
import java.awt.Graphics2D;

import Actor.Player;

// Handles health and such displays

public class HUD {
	
	private Player player;
	private QuestController qc;
	
	public HUD(Player player, QuestController qc) {
		this.player = player;
		this.qc = qc;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.drawString("HP: " + player.getHP(), 10, 30);
		g.drawString("Quest: " + qc.getQuestDescription(), 10, 70);
	}

}
