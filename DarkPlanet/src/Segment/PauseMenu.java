package Segment;

import java.awt.Color;
import java.awt.Graphics2D;

import Controller.GamePanel;

// Pause Menu
// Considering Moving this to the Controllers Package
// Mainly handles Planet options, and displays the Menu.

public class PauseMenu {

	private int selection, planetType, cost, cr, quest;
	private int[] playerMaxLevels, playerLevels;
	private boolean atPlanet, activePlanet, haveEnough;
	public final int YES = 0;
	public final int NO = 1;
	public PauseMenu(int[] playerMaxLevels, int[] playerLevels) {
		this.playerMaxLevels = playerMaxLevels;
		this.playerLevels = playerLevels;
		activePlanet = false;
	}
	
	public String getPlanetDescription() {
		String s;
		if (planetType == 0) {
			s = "This is a barren plannet. No life detected.";
		} else if (planetType == 1) {
			s = "The people here are experts at repairing ships.";
		} else if (planetType == 2) {
			s = "The people here are weapon experts.";
		} else if (planetType == 3) {
			s = "This humanoid, raccoon-like species is experienced with engines.";
		} else if (planetType == 4) {
			s = "The merchants offer you turtle shells imbued with repelling power.";
		} else if (planetType == 5) {
			s = "Please leave. We are not accepting of foreigners.";
			if (quest == 1) s = "The Space Coloniziation Corporation is offering rewards to those who aid in destroying those blasted Scavengers.";
			if (quest == 2) s = "We're looking for a barren planet to move to. Please inform us if you find one.";
			if (quest == 3) s = "You must find Dave! You're my only hope!";
		} else if (planetType == 6) {
			s = "Dave here.";
		} else if (planetType == 7) {
			s = "These battery-worshipping people are collectively the insipriation/nfor Charlie and the Chocolate Battery.";
		} else {
				s = "Strange planet here...";
		}
		
		return s;
	}

	public String getPlanetOption() {
		String s;
		haveEnough = true;
		if (planetType == 0) {
			if (quest != 2) s = "Nothing here for us.";
			else s = "I think this planet is likely what they're looking for.";
		} else if (planetType == 1) {
			s = "Repair the ship for " + cost + "?";
		} else if (planetType == 2) {
			s = "Upgrade our weapons for " + cost + "?";
		} else if (planetType == 3) {
			s = "Upgrade our engines for " + cost + "?";
		} else if (planetType == 4) {
			s = "Upgrade our shields for " + cost + "?";
		} else if (planetType == 5) {
			if (quest != 0) s = "Help them? (This will override any current quests.)";
			else s = "";
		} else if (planetType == 6) {
			s = "This is fortunate.";
		} else if (planetType == 7) {
			s = "Upgrade our energy storage for " + cost + "?";
		} else {
			s = "What should we do?";
		}
		if (planetType > 0 && (planetType < 5 || planetType == 7) && cost > cr) {
			s = "We don't have enough to offer them.";
			haveEnough = false;
		}
		
		return s;
	}
	public void setType(int type) {
		planetType = type;
	}
	public void setSelection(int newInt) {
		if (selection > 1 && newInt < 3) {
			newInt = 2;
		}
		if (newInt > 4) {
			newInt = 4;
		}
		this.selection = newInt;
	}
	public void setPlayerLevels(int[] maxArray, int[] array) {
		playerMaxLevels = maxArray;
		playerLevels = array;
		if (planetType > 0 &&  (planetType < 5 || planetType == 7)) cost = playerMaxLevels[getPlanetTypeAsPlayer()] * 10 + 10;
		else cost = 0;
	}
	public void setActivePlanet(boolean b) { 
		activePlanet = b; 
		if (!b) {
			selection = 2;
		}
	}
	public int getSelection() { return selection; }

	public int getPlanetTypeAsPlayer() {
		int t = planetType;
		if (planetType == 7) t = 0;
		if (planetType == 3) t = 1;
		if (planetType == 2) t = 2;
		if (planetType == 4) t = 3;
		if (planetType == 1) t = 4;
		return t;
	}
	public int getPlanetType() {
		return planetType;
	}
	public int getCost() {
		return cost;
	}
	public int getQuest() { return quest; }
	public void setQuest(int q) { quest = q; }
	public void setCR(int cr) { this.cr = cr; }
	public void draw(Graphics2D g) {
		// Draw Menu coupled with the selection
		g.setColor(Color.WHITE);
		g.fillRect(100, 100, GamePanel.WI - 200, GamePanel.HI - 200);
		g.setColor(Color.BLACK);
		g.drawString("Paused", 120, 120);
		
		if (atPlanet && activePlanet) {
			// 0, 5, 6 Planet Types don't have upgrades. 
			g.drawString(getPlanetDescription(), 120, 140);
			g.drawString(getPlanetOption(), 120, 180);
			// If the planet is a barren planet, there isn't really an option to choose.
			if (planetType != 0 && planetType != 6 && haveEnough) {
				g.drawString("Yes", 250, 225);
				g.drawString("No", 300, 225);
				// Selection Rectangle.
				g.drawRect(247 + (selection * 50), 215, 40, 25);
			}
		} else {
			// draw battery handling.
			g.drawString("Battery Management", 120, 140);
			g.drawString("Current Battery Levels:  ", 120, 180);
			g.drawString("Engine Levels:  ", 120, 200);
			g.drawString("Weapon Levels:  ", 120, 220);
			g.drawString("Shield Levels:  ", 120, 240);
			for (int i = 0; i < 4; i++) {
				g.drawString(playerLevels[i] + " / " + playerMaxLevels[i], 260, 180 + (i * 20));
			}
			// Lame selection thingy:
			g.drawString(">", 250, 200 + ((selection - 2) * 20));
		}
	}

	public void setAtPlanet(boolean b) {
		atPlanet = b;
		if (b) selection = 0;
		else selection = 2;
		activePlanet = true;
		// 0 is Yes, 1 is No for selection
	}

}
