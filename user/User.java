package com.matthewgarrison.user;

import com.matthewgarrison.GameHandler;

public class User {

	private String name;
    private Skin currentSkin;
	private int currentDifficulty;

	public User(Skin defaultSkin) {
		this.name = GameHandler.DEFAULT_NAME;
		this.currentSkin = defaultSkin;
		this.currentDifficulty = 1;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCurrentSkin(Skin newSkin) {
		this.currentSkin = newSkin;
	}

	public Skin getCurrentSkin() {
		return this.currentSkin;
	}

	public void setCurrentDifficulty(int newDiff) {
		this.currentDifficulty = newDiff;
	}

	public int getCurrentDifficulty() {
		return this.currentDifficulty;
	}
}
