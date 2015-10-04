package Controller;

public class QuestController {
	
	private int currentQuest;
	private int questProgress;
	private int questGoal;
	
	// Quest Constants as Follows:
	public final int NONE = 0;
	public final int DESTROY_SCA = 1;
	public final int REACH_GOAL_PLANET = 2;
	public final int FIND_DAVE = 3;
	
	// Add More Here
	public final int FOUR = 4;
	public final int FIVE = 5;

	
	
	public QuestController() {
		currentQuest = 0;
		questProgress = 0;
		questGoal = 1;
	}
	
	public boolean isFinished() {
		boolean b = false;
		if (questProgress >= questGoal) {
			b = true;
			questProgress = 0;
		}
		return b;
		
	}
	public void setQuest(int q) { 
		currentQuest = q; 
		if (q == DESTROY_SCA) {
			setQuestGoal(10);
		}
		if (q == REACH_GOAL_PLANET || q == FIND_DAVE) {
			setQuestGoal(1);
		}
	}
	public void setQuestProgress(int p) { questProgress = p; }
	public void setQuestGoal(int g) { questGoal = g; }
	
	public int getQuest() { return currentQuest; }
	public int getQuestProgress() { return questProgress; }
	public int getQuestGoal() { return questGoal; }
	public int getReward() {
		int r = 15;
		if (currentQuest == FIND_DAVE) {
			r = 60;
		} else if (currentQuest == REACH_GOAL_PLANET) {
			r = 50;
		} else {
			r = 30;
		}
		return r;
	}
	public String getQuestDescription() { 
		String s = "No current quest.";
		if (currentQuest == 1) {
			s = "Annihilate " + (questGoal - questProgress) + " scavenger" + (questProgress > 1 ? "s." : ".");
		} else if (currentQuest == 2) {
			s = "Find the sought planet.";
		} else if (currentQuest == 3) {
			s = "Find Dave. At any cost.";
		}
		
		return s;
	}
	
	
	

}
