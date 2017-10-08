package com.matthewgarrison.user;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.enums.Skin;
import com.matthewgarrison.enums.Difficulty;

public class User {

	private String name;
    private Skin currentSkin;
	private Difficulty currentDifficulty;

	public User() {
		this.name = GameHandler.DEFAULT_NAME;
		this.currentSkin = Skin.NORMAL;
		this.currentDifficulty = Difficulty.EASY;
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

	public void setCurrentDifficulty(Difficulty newDiff) {
		this.currentDifficulty = newDiff;
	}

	public Difficulty getCurrentDifficulty() {
		return this.currentDifficulty;
	}
}
