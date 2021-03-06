package com.matthewgarrison.screens;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.enums.DifficultyEnum;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.matthewgarrison.enums.TextureEnum;

public class LeaderboardsScreen implements Screen {
	private GameHandler game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private float canActOnThisScreenTimer;
	private DifficultyEnum currentDifficultyShown;

	public LeaderboardsScreen(GameHandler g) {
		this.game = g;
	}

	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameHandler.SCREEN_WIDTH, GameHandler.SCREEN_HEIGHT);
		batch = new SpriteBatch();
		this.currentDifficultyShown = game.getUser().getCurrentDifficulty();
		canActOnThisScreenTimer = 0;
		game.loadScores(currentDifficultyShown);
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.textures[TextureEnum.LEADERBOARDS_SCREEN.ordinal()], 0, 0);
		if (currentDifficultyShown == DifficultyEnum.EASY) {
			batch.draw(TextureManager.textures[TextureEnum.EASY_SELECTED.ordinal()], -10, 260);
		} else {
			batch.draw(TextureManager.textures[TextureEnum.EASY.ordinal()], -10, 260);
		}
		if (currentDifficultyShown == DifficultyEnum.MEDIUM) {
			batch.draw(TextureManager.textures[TextureEnum.MEDIUM_SELECTED.ordinal()], 160, 260);
		} else {
			batch.draw(TextureManager.textures[TextureEnum.MEDIUM.ordinal()], 160, 260);
		}
		if (currentDifficultyShown == DifficultyEnum.HARD) {
			batch.draw(TextureManager.textures[TextureEnum.HARD_SELECTED.ordinal()], 330, 260);
		} else {
			batch.draw(TextureManager.textures[TextureEnum.HARD.ordinal()], 330, 260);
		}

		game.getTextLarge().draw(batch, "1. " + (game.getScores().get(0).getValue() != -1 ?
				game.getScores().get(0) : ""), 500, 350);
		game.getTextLarge().draw(batch, "2. " + (game.getScores().get(1).getValue() != -1 ?
				game.getScores().get(1) : ""), 500, 275);
		game.getTextLarge().draw(batch, "3. " + (game.getScores().get(2).getValue() != -1 ?
				game.getScores().get(2) : ""), 500, 200);
		batch.end();

		if (canActOnThisScreenTimer <= 0.15) {
			canActOnThisScreenTimer += delta;
		} else {
			boolean reloadScores = false;
			for (int i = 0; i < 5; i++) {
				if (Gdx.input.isTouched(i)) {
					Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touchPos);

					if (touchPos.x < 136 && touchPos.x > 26 && touchPos.y > 144 && touchPos.y < 190){
						game.setScreen(new MainMenuScreen(game));
					}

					if (touchPos.x < 155 && touchPos.x > -10 && touchPos.y > 260 && touchPos.y < 320
							&& currentDifficultyShown != DifficultyEnum.EASY) {
						currentDifficultyShown = DifficultyEnum.EASY;
						reloadScores = true;
					}
					if (touchPos.x < 325 && touchPos.x > 160 && touchPos.y > 260 && touchPos.y < 320
							&& currentDifficultyShown != DifficultyEnum.MEDIUM) {
						currentDifficultyShown = DifficultyEnum.MEDIUM;
						reloadScores = true;
					}
					if (touchPos.x < 495 && touchPos.x > 330 && touchPos.y > 260 && touchPos.y < 320
							&& currentDifficultyShown != DifficultyEnum.HARD) {
						currentDifficultyShown = DifficultyEnum.HARD;
						reloadScores = true;
					}
				}
			}
			if (reloadScores) game.loadScores(currentDifficultyShown);
		}
	}

	public void resize(int width, int height) {
	}

	public void hide() {
		this.dispose();
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
		batch.dispose();
	}
}
