package net.ocps.tchs.MDGame.objects;

public class Modifier {
	
	//currently only used to modify how many points are earned
	//could later be used to modify speed, gravity, etc.

	public float timeLeft = 0; //how long the modifier lasts
	public float mod = 0; //the modifying value
	
	public  Modifier(float mod, int time) { //takes in how much it modifies by and how long it lasts
		this.timeLeft = time;
		this.mod = mod;
	}
	
	public void update(float deltaTime) {
		this.timeLeft -= deltaTime;
	}
	
	public boolean isDone() {
		if (timeLeft <= 0 ) {
			return true;
		}
		return false;
	}
	
	public float getModify() {
		return this.mod;
	}
}