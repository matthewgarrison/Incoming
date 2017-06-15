package com.matthewgarrison.screens;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.tools.FileManager;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameOverScreen implements Screen {
	private GameHandler myGame;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private float canActOnThisScreenTimer;
	private int score;

	public GameOverScreen (GameHandler g, int score) {
		this.myGame = g;
		this.score = score;
	}

	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameHandler.getScreenWidth(), GameHandler.getScreenHeight());
		batch = new SpriteBatch();
		canActOnThisScreenTimer = 0;
		if (score != 0) addHighScoreToFile();
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.textures[TextureManager.gameOverScreen], 0, 0);
		switch (myGame.getUser().getCurrentDifficulty()) {
			case 1:
				batch.draw(TextureManager.textures[TextureManager.easy], 300, 280);
				break;
			case 2:
				batch.draw(TextureManager.textures[TextureManager.easy], 309, 280);
				break;
			case 3:
				batch.draw(TextureManager.textures[TextureManager.easy], 300, 280);
				break;
		}
		myGame.getTextNormal().draw(batch, "Score: " + score, 300, 250);
		batch.end();

		if (canActOnThisScreenTimer <= 0.15) {
			canActOnThisScreenTimer += delta;
		} else {
			for (int i = 0; i < 5; i++) {
				if(Gdx.input.isTouched(i)){
					Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touchPos);
					if(touchPos.x > 6 && touchPos.x < 310 && touchPos.y > 140 && touchPos.y < 200){
						myGame.setScreen(new MainGameScreen(myGame));
					}
					if(touchPos.x > 440 && touchPos.x < 795 && touchPos.y > 140 && touchPos.y < 200){
						myGame.setScreen(new MainMenuScreen(myGame));
					}
				}
			}

			if(Gdx.input.isKeyJustPressed(Keys.R)){
				myGame.setScreen(new MainGameScreen(myGame));
			}
			if(Gdx.input.isKeyJustPressed(Keys.BACKSPACE)){
				myGame.setScreen(new MainMenuScreen(myGame));
			}
		}
	}

	private void addHighScoreToFile() {
		FileManager.writeToFile("MDGames/Incoming/Highscores/" +
				myGame.getUser().getCurrentSkin().getName() + "_" +
				myGame.getUser().getCurrentDifficulty() + ".in", myGame.getUser().getPlayerName() +
				" " + score + " ", true);
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
