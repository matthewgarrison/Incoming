package com.matthewgarrison.screens;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.enums.Difficulty;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameOverScreen implements Screen {
	private GameHandler game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private float canActOnThisScreenTimer;
	private int score;

	public GameOverScreen (GameHandler g, int score) {
		this.game = g;
		this.score = score;
	}

	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameHandler.SCREEN_WIDTH, GameHandler.SCREEN_HEIGHT);
		batch = new SpriteBatch();
		canActOnThisScreenTimer = 0;
		if (score != 0) game.addNewScore(game.getUser().getCurrentDifficulty(), game.getUser().getName(), score);
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.textures[TextureManager.gameOverScreen], 0, 0);
		if (game.getUser().getCurrentDifficulty() == Difficulty.EASY) {
			batch.draw(TextureManager.textures[TextureManager.easy], 300, 280);
		} else if (game.getUser().getCurrentDifficulty() == Difficulty.MEDIUM) {
			batch.draw(TextureManager.textures[TextureManager.medium], 309, 280);
		} else {
			batch.draw(TextureManager.textures[TextureManager.hard], 300, 280);
		}
		game.getTextNormal().draw(batch, "Score: " + score, 300, 250);
		batch.end();

		if (canActOnThisScreenTimer <= 0.15) {
			canActOnThisScreenTimer += delta;
		} else {
			for (int i = 0; i < 5; i++) {
				if(Gdx.input.isTouched(i)){
					Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touchPos);
					if(touchPos.x > 6 && touchPos.x < 310 && touchPos.y > 140 && touchPos.y < 200){
						game.setScreen(new MainGameScreen(game));
					}
					if(touchPos.x > 440 && touchPos.x < 795 && touchPos.y > 140 && touchPos.y < 200){
						game.setScreen(new MainMenuScreen(game));
					}
				}
			}

			if(Gdx.input.isKeyJustPressed(Keys.R)){
				game.setScreen(new MainGameScreen(game));
			}
			if(Gdx.input.isKeyJustPressed(Keys.BACKSPACE)){
				game.setScreen(new MainMenuScreen(game));
			}
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
