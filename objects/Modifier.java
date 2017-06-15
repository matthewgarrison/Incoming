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
		return (timeLeft <= 0);
	}

	public float getModifyingValue() {
		return this.modifyingValue;
	}

	public float getTimeLeft() {
		return this.timeLeft;
	}
}