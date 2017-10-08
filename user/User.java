package com.matthewgarrison.user;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.enums.SkinEnum;
import com.matthewgarrison.enums.DifficultyEnum;

public class User {

	private String name;
    private SkinEnum currentSkin;
	private DifficultyEnum currentDifficulty;

	public User() {
		this.name = GameHandler.DEFAULT_NAME;
		this.currentSkin = SkinEnum.NORMAL;
		this.currentDifficulty = DifficultyEnum.EASY;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCurrentSkin(SkinEnum newSkin) {
		this.currentSkin = newSkin;
	}

	public SkinEnum getCurrentSkin() {
		return this.currentSkin;
	}

	public void setCurrentDifficulty(DifficultyEnum newDiff) {
		this.currentDifficulty = newDiff;
	}

	public DifficultyEnum getCurrentDifficulty() {
		return this.currentDifficulty;
	}
}
