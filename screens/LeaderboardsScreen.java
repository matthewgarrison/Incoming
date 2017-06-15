package com.matthewgarrison.screens;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.tools.FileManager;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class LeaderboardsScreen implements Screen {
	private GameHandler game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private float canActOnThisScreenTimer;
	private int currentSkin, currentDifficulty;

	public LeaderboardsScreen(GameHandler g) {
		this.game = g;
	}

	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameHandler.getScreenWidth(), GameHandler.getScreenHeight());
		batch = new SpriteBatch();
		TextureManager.loadAllGameTextures(game);
		this.currentSkin = game.getUser().getCurrentSkin().getIdNumber();
		this.currentDifficulty = game.getUser().getCurrentDifficulty();
		canActOnThisScreenTimer = 0;
		loadScores();
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.textures[TextureManager.leaderboardsScreen], 0, 0);
		switch (currentSkin) {
			case 1:
				batch.draw(TextureManager.textures[TextureManager.darkNormal], 200, 200);
				batch.draw(TextureManager.textures[TextureManager.sheep], 195, 140);
				break;
			case 2:
				batch.draw(TextureManager.textures[TextureManager.darkSheep], 195, 140);
				batch.draw(TextureManager.textures[TextureManager.normal], 200, 200);
				break;
		}
		switch (currentDifficulty) {
			case 1:
				batch.draw(TextureManager.textures[TextureManager.darkEasy], 5, 320);
				batch.draw(TextureManager.textures[TextureManager.medium], 175, 320);
				batch.draw(TextureManager.textures[TextureManager.hard], 345, 320);
				break;
			case 2:
				batch.draw(TextureManager.textures[TextureManager.easy], 5, 320);
				batch.draw(TextureManager.textures[TextureManager.darkMedium], 175, 320);
				batch.draw(TextureManager.textures[TextureManager.hard], 345, 320);
				break;
			case 3:
				batch.draw(TextureManager.textures[TextureManager.easy], 5, 320);
				batch.draw(TextureManager.textures[TextureManager.medium], 175, 320);
				batch.draw(TextureManager.textures[TextureManager.darkHard], 345, 320);
				break;
		}

		game.getTextLarge().draw(batch, "1. " + game.getScores()[0].toString(), 500, 350);
		game.getTextLarge().draw(batch, "2. " + game.getScores()[1].toString(), 500, 275);
		game.getTextLarge().draw(batch, "3. " + game.getScores()[2].toString(), 500, 200);
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

					if (touchPos.x < 370 && touchPos.x > 200 && touchPos.y > 200 && touchPos.y < 240
							&& currentSkin != 1) {
						currentSkin = 1;
						reloadScores = true;
					}
					if (touchPos.x < 370 && touchPos.x > 200 && touchPos.y > 140 && touchPos.y < 180
							&& currentSkin != 2) {
						currentSkin = 2;
						reloadScores = true;
					}
					if (touchPos.x < 170 && touchPos.x > 5 && touchPos.y > 320 && touchPos.y < 360
							&& currentDifficulty != 1) {
						currentDifficulty = 1;
						reloadScores = true;
					}
					if (touchPos.x < 340 && touchPos.x > 175 && touchPos.y > 320 && touchPos.y < 360
							&& currentDifficulty != 2) {
						currentDifficulty = 2;
						reloadScores = true;
					}
					if (touchPos.x < 510 && touchPos.x > 345 && touchPos.y > 320 && touchPos.y < 360
							&& currentDifficulty != 3) {
						currentDifficulty = 3;
						reloadScores = true;
					}
				}
			}
			if (reloadScores) loadScores();
		}
	}

	private void loadScores() {
		String fileName = null;
		if (currentDifficulty == 1 && currentSkin == 1) fileName = "MDGames/Incoming/Highscores/normalEasy.in";
		else if (currentDifficulty == 2 && currentSkin == 1) fileName = "MDGames/Incoming/Highscores/normalMedium.in";
		else if (currentDifficulty == 3 && currentSkin == 1) fileName = "MDGames/Incoming/Highscores/normalHard.in";
		else if (currentDifficulty == 1 && currentSkin == 2) fileName = "MDGames/Incoming/Highscores/sheepEasy.in";
		else if (currentDifficulty == 2 && currentSkin == 2) fileName = "MDGames/Incoming/Highscores/sheepMedium.in";
		else if (currentDifficulty == 3 && currentSkin == 2) fileName = "MDGames/Incoming/Highscores/sheepHard.in";

		FileManager.loadHighscoreFile(fileName);
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
