package net.ocps.tchs.MDGame.user;

public class User {

	public String playerName;
	public Skin currentSkin;
	public boolean adsShown;
	public int currentDifficulty;
	public int didYouCheat; //scans in what the file says, used in any if statements
	
	public User() {
		//currently blank constructor
	}
	
	public void setInfo(String playerName, Skin currentSkin, boolean adsShown, int currentDifficulty, int didYouCheat) {
		this.playerName = playerName;
		this.currentSkin = currentSkin;
		this.adsShown = adsShown;
		this.currentDifficulty = currentDifficulty;
		this.didYouCheat = didYouCheat;
	}
}
