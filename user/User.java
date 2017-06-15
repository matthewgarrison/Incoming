package com.matthewgarrison.user;

public class User {

	private String playerName;
    private Skin currentSkin;
	private int currentDifficulty;

	public User(Skin defaultSkin) {
		this.playerName = "Player";
		this.currentSkin = defaultSkin;
		this.currentDifficulty = 1;
	}

	public void setPlayerName(String name) {
		this.playerName = name;
	}

	public String getPlayerName() {
		return this.playerName;
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
