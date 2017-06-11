package net.ocps.tchs.MDGame;

import net.ocps.tchs.MDGame.objects.MainGuy;
import net.ocps.tchs.MDGame.objects.Score;
import net.ocps.tchs.MDGame.screens.SplashScreen;
import net.ocps.tchs.MDGame.tools.TextureManager;
import net.ocps.tchs.MDGame.user.Skin;
import net.ocps.tchs.MDGame.user.User;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kyledencker.gravitywell.tools.URLFetch;

public class GameHandler extends Game {
	
	/*
	 *for player name: default is "Player", you can edit it in options, and you can change it from the 
	 *current player name when the score is added to the leader-boards
	 */

	public User user;
	public BitmapFont textNormalSelected, textLargeSelected, textNormal, textLarge;
	public Score[] scores;
	
	public Skin defaultSkin;
	public Skin sheepSkin;

	public URLFetch googleServices; //used for google play
	public GameHandler(URLFetch g) {
		this.googleServices = g;
	}

	//this is called after the constructor
	public void create() {
		this.user = new User();
		this.defaultSkin = new Skin("DefaultSkin", 0, true);
		this.sheepSkin = new Skin("SheepSkin", 1, true);

		this.textNormalSelected = new BitmapFont(Gdx.files.internal("Fonts/normal2/a.fnt"), Gdx.files.internal("Fonts/normal2/a.png"), false);
		this.textLargeSelected = new BitmapFont(Gdx.files.internal("Fonts/normal2/a.fnt"), Gdx.files.internal("Fonts/normal2/a.png"), false);
		this.textNormal = new BitmapFont(Gdx.files.internal("Fonts/normal2/b.fnt"), Gdx.files.internal("Fonts/normal2/b.png"), false);
		this.textLarge = new BitmapFont(Gdx.files.internal("Fonts/normal2/b.fnt"), Gdx.files.internal("Fonts/normal2/b.png"), false);
	
		this.setScreen(new SplashScreen(this));
	}

	
	//used to switch all relevant variables to their settings in sheep mode
	public void SwitchToSheepSkin() {
		//mainguy values
		MainGuy.standingHitbox = 56;
		MainGuy.crouchingHitbox = 44;
		MainGuy.width = 80;

		TextureManager.load(TextureManager.optionsScreens_value, this);
	}

	//used to switch all relevant variables to their settings in normal mode
	public void SwitchToDefaultSkin() {
		//mainguy values
		MainGuy.standingHitbox = 77;
		MainGuy.crouchingHitbox = 65;
		MainGuy.width = 42;

		TextureManager.load(TextureManager.optionsScreens_value, this);
	}
	
	public void showAds() {
		
	}
}