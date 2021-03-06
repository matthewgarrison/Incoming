package com.matthewgarrison.screens;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.enums.DifficultyEnum;
import com.matthewgarrison.enums.SkinEnum;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.matthewgarrison.enums.TextureEnum;

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
		TextureManager.loadAllGameTextures(game);
		this.loadPrefs();
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.textures[TextureEnum.SPLASH_SCREEN.ordinal()], 0, 0);
		batch.end();

		if (this.splashTimer >= 1.5) {
			game.setScreen(new MainMenuScreen(game));
		}
		this.splashTimer += delta;
	}

	private void loadPrefs() {
		game.getUser().setName(game.getPrefs().getString("name", "Player"));
		DifficultyEnum diff = DifficultyEnum.valueOf(game.getPrefs().getString("difficulty",
				DifficultyEnum.EASY.name()));
		game.getUser().setCurrentDifficulty(diff);

		SkinEnum whichSkin = SkinEnum.valueOf(game.getPrefs().getString("skin",
				SkinEnum.NORMAL.name()));
		if (whichSkin == SkinEnum.NORMAL) game.switchToDefaultSkin();
		else game.switchToSheepSkin();
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
