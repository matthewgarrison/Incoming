package com.matthewgarrison;

import com.matthewgarrison.objects.MainGuy;
import com.matthewgarrison.objects.Score;
import com.matthewgarrison.screens.SplashScreen;
import com.matthewgarrison.tools.TextureManager;
import com.matthewgarrison.user.Skin;
import com.matthewgarrison.user.User;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.Arrays;
import java.util.Random;

public class GameHandler extends Game {

	private User user;
	private BitmapFont textNormalSelected, textLargeSelected, textNormal, textLarge;
	private Score[] scores;
	private Skin defaultSkin;
	private Skin sheepSkin;
	public final static int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 480;
	public final static int DEFAULT_SKIN = 0, SHEEP_SKIN = 1, EASY = 1, MEDIUM = 2, HARD = 3;
	public static Random rand;

	public GameHandler() {

	}

	public void create() {
		this.defaultSkin = new Skin("DefaultSkin", DEFAULT_SKIN, true);
		this.sheepSkin = new Skin("SheepSkin", SHEEP_SKIN, true);
		this.user = new User(this.defaultSkin);

		this.textNormalSelected = new BitmapFont(Gdx.files.internal("Fonts/normal2/a.fnt"),
				Gdx.files.internal("Fonts/normal2/a.png"), false);
		this.textLargeSelected = new BitmapFont(Gdx.files.internal("Fonts/normal2/a.fnt"),
				Gdx.files.internal("Fonts/normal2/a.png"), false);
		this.textNormal = new BitmapFont(Gdx.files.internal("Fonts/normal2/b.fnt"),
				Gdx.files.internal("Fonts/normal2/b.png"), false);
		this.textLarge = new BitmapFont(Gdx.files.internal("Fonts/normal2/b.fnt"),
				Gdx.files.internal("Fonts/normal2/b.png"), false);

		this.setScreen(new SplashScreen(this));

		rand = new Random();
	}

	// Used to switch all relevant variables to their settings in the sheep skin.
	public void switchToSheepSkin() {
		user.setCurrentSkin(this.sheepSkin);
		MainGuy.switchToSheepSkin();
		TextureManager.loadSkinTextures(this);
	}

	// Used to switch all relevant variables to their settings in default skin.
	public void switchToDefaultSkin() {
		user.setCurrentSkin(this.defaultSkin);
		MainGuy.switchToDefaultSkin();
		TextureManager.loadSkinTextures(this);
	}

	public User getUser() {
		return user;
	}

	public BitmapFont getTextLarge() {
		return textLarge;
	}

	public BitmapFont getTextNormalSelected() {
		return textNormalSelected;
	}

	public BitmapFont getTextLargeSelected() {
		return textLargeSelected;
	}

	public BitmapFont getTextNormal() {
		return textNormal;
	}

	public Score[] getScores() {
		return scores;
	}

	public void sortScores() {
		Arrays.sort(this.scores);
	}

	public void createScores(int size) {
		this.scores = new Score[size];
	}

	public Skin getDefaultSkin() {
		return defaultSkin;
	}

	public Skin getSheepSkin() {
		return sheepSkin;
	}

}