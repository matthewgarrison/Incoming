package com.matthewgarrison.objects;

public class Modifier {

	private float timeLeft;
	private float modifyingValue;

	public  Modifier(float mod, int time) {
		this.timeLeft = time;
		this.modifyingValue = mod;
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

	public float getModifyingValue() {
		return this.modifyingValue;
	}

	public float getTimeLeft() {
		return this.timeLeft;
	}
}