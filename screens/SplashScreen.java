package com.matthewgarrison.screens;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.tools.PreferencesManager;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {
	private GameHandler game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private float splashTimer;

	public SplashScreen(GameHandler g) {
		this.game = g;
	}

	public void show() {
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, GameHandler.SCREEN_WIDTH, GameHandler.SCREEN_HEIGHT);
		this.batch = new SpriteBatch();
		this.splashTimer = 0;
		PreferencesManager.create(game);
		TextureManager.loadAllGameTextures(game);
		this.loadFiles();
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.textures[TextureManager.splashScreen], 0, 0);
		batch.end();

		if (this.splashTimer >= 1.5) {
			game.setScreen(new MainMenuScreen(game));
		}
		this.splashTimer += delta;
	}

	private void loadFiles() {
		PreferencesManager.loadNonSecureFile("MDGames/Incoming/playerName.in");
		FileHandle file = Gdx.files.external("MDGames/Incoming/playerName.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/playerName.in");
		String playerName = file.readString();
		game.getUser().setPlayerName(playerName);

		PreferencesManager.loadFile("MDGames/Incoming/difficulty.in");
		file = Gdx.files.external("MDGames/Incoming/difficulty.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/difficulty.in");
		int difficulty = Integer.parseInt(file.readString());
		game.getUser().setCurrentDifficulty(difficulty);

		PreferencesManager.loadSecureFile("MDGames/Incoming/whichSkin.in");
		file = Gdx.files.local("MDGames/Incoming/whichSkin.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/whichSkin.in");
		int whichSkin = Integer.parseInt(file.readString());
		if (whichSkin == game.getDefaultSkin().getIdNumber()) game.switchToDefaultSkin();
		else game.switchToSheepSkin();

		game.getUser().setCurrentSkin(game.getSheepSkin());

		PreferencesManager.loadSecureFile("MDGames/Incoming/InAppPurchases/sheepSkin.in");
		file = Gdx.files.local("MDGames/Incoming/InAppPurchases/sheepSkin.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/sheepSkin.in");
		game.getSheepSkin().setIsAvailable(Integer.parseInt(file.readString()) != 0);
	}

	public void resize(int width, int height) {
	}

	public void hide() {
		dispose();
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
		batch.dispose();
	}
}
