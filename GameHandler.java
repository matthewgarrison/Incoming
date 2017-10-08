package com.matthewgarrison;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.StringBuilder;
import com.matthewgarrison.enums.DifficultyEnum;
import com.matthewgarrison.enums.SkinEnum;
import com.matthewgarrison.objects.MainGuy;
import com.matthewgarrison.objects.Score;
import com.matthewgarrison.screens.SplashScreen;
import com.matthewgarrison.tools.TextureManager;
import com.matthewgarrison.user.User;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameHandler extends Game {

	private User user;
	private BitmapFont textNormalSelected, textLargeSelected, textNormal, textLarge;
	private ArrayList<Score> scores;
	public final static int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 480;
	public final static String DEFAULT_NAME = "Player";
	public final static String DEFAULT_SCORES = "---: -1 ---: -1 ---: -1 ";
	public static Random rand;
	private static Preferences prefs;

	public GameHandler() {
	}

	public void create() {
		this.user = new User();

		this.textNormalSelected = new BitmapFont(Gdx.files.internal("Fonts/normal2/a.fnt"),
				Gdx.files.internal("Fonts/normal2/a.png"), false);
		this.textLargeSelected = new BitmapFont(Gdx.files.internal("Fonts/normal2/a.fnt"),
				Gdx.files.internal("Fonts/normal2/a.png"), false);
		this.textNormal = new BitmapFont(Gdx.files.internal("Fonts/normal2/b.fnt"),
				Gdx.files.internal("Fonts/normal2/b.png"), false);
		this.textLarge = new BitmapFont(Gdx.files.internal("Fonts/normal2/b.fnt"),
				Gdx.files.internal("Fonts/normal2/b.png"), false);

		prefs = Gdx.app.getPreferences("Incoming");
		rand = new Random();
		scores = new ArrayList<Score>();

		this.setScreen(new SplashScreen(this));
	}

	// Used to switch all relevant variables to their settings in the sheep skin.
	public void switchToSheepSkin() {
		user.setCurrentSkin(SkinEnum.SHEEP);
		MainGuy.switchToSheepSkin();
		TextureManager.loadSkinTextures(this);
	}

	// Used to switch all relevant variables to their settings in default skin.
	public void switchToDefaultSkin() {
		user.setCurrentSkin(SkinEnum.NORMAL);
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

	public ArrayList<Score> getScores() {
		return scores;
	}

	public void loadScores(DifficultyEnum difficulty) {
		String[] parts = prefs.getString(difficulty.name(), DEFAULT_SCORES).split("[: ]+");

		scores.clear();
		for (int i = 0; i < parts.length; i += 2) {
			scores.add(new Score(parts[i], Integer.parseInt(parts[i+1])));
		}
	}

	public void addNewScore(DifficultyEnum difficulty, String name, int score) {
		loadScores(difficulty);
		scores.add(new Score(name, score));
		Collections.sort(scores);
		scores.remove(3); // Remove the lowest score from the list.

		StringBuilder sb = new StringBuilder();
		for (Score s : scores) sb.append(s).append(" ");
		prefs.putString(difficulty.name(), sb.toString());
		prefs.flush();
	}

	public String scoresToString() {
		String out = "";
		for (Score s : scores) out += s + " ";
		return out;
	}

	public Preferences getPrefs() {
		return prefs;
	}
}