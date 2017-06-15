package com.matthewgarrison.screens;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class OptionsScreen implements Screen {
	private GameHandler game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private float canActOnThisScreenTimer;

	public OptionsScreen (GameHandler g) {
		this.game = g;
	}

	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameHandler.SCREEN_WIDTH, GameHandler.SCREEN_HEIGHT);
		batch = new SpriteBatch();
		canActOnThisScreenTimer = 0;
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		if (game.getUser().getCurrentDifficulty() == GameHandler.EASY) {
			batch.draw(TextureManager.textures[TextureManager.optionsScreenEasy], 0, 0);
		} else if (game.getUser().getCurrentDifficulty() == GameHandler.MEDIUM) {
			batch.draw(TextureManager.textures[TextureManager.optionsScreenMedium], 0, 0);
		} else {
			batch.draw(TextureManager.textures[TextureManager.optionsScreenHard], 0, 0);
		}
		if (game.getUser().getCurrentSkin().getIdNumber() == GameHandler.DEFAULT_SKIN) {
			batch.draw(TextureManager.textures[TextureManager.sheep], 450, 210);
			batch.draw(TextureManager.textures[TextureManager.darkNormal], 186, 210);
		} else {
			batch.draw(TextureManager.textures[TextureManager.darkSheep], 450, 210);
			batch.draw(TextureManager.textures[TextureManager.normal], 186, 210);
		}

		batch.end();

		if (canActOnThisScreenTimer <= 0.15) {
			canActOnThisScreenTimer += delta;
		} else {
			for (int i = 0; i < 5; i++) {
				if(Gdx.input.isTouched(i)){
					Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touchPos);

					if (touchPos.x < 136 && touchPos.x > 26 && touchPos.y > 144 && touchPos.y < 190){
						game.setScreen(new MainMenuScreen(game));
					}

					if(touchPos.x < 291 && touchPos.x > 181 && touchPos.y > 276 && touchPos.y < 330) {
						game.getPrefs().putInteger("difficulty", GameHandler.EASY);
						game.getPrefs().flush();
						game.getUser().setCurrentDifficulty(GameHandler.EASY);
					}
					if(touchPos.x < 490 && touchPos.x > 321 && touchPos.y > 276 && touchPos.y < 330) {
						game.getPrefs().putInteger("difficulty", GameHandler.MEDIUM);
						game.getPrefs().flush();
						game.getUser().setCurrentDifficulty(GameHandler.MEDIUM);
					}
					if(touchPos.x < 629 && touchPos.x > 515 && touchPos.y > 276 && touchPos.y < 330) {
						game.getPrefs().putInteger("difficulty", GameHandler.HARD);
						game.getUser().setCurrentDifficulty(GameHandler.HARD);
						game.getPrefs().flush();
					}

					if (touchPos.x < 350 && touchPos.x > 186 && touchPos.y < 270 && touchPos.y > 210) {
						game.getPrefs().putInteger("skin", GameHandler.DEFAULT_SKIN);
						game.getPrefs().flush();
						game.switchToDefaultSkin();
					}
					if (touchPos.x < 614 && touchPos.x > 450 && touchPos.y < 270 && touchPos.y > 210) {
						game.getPrefs().putInteger("skin", GameHandler.SHEEP_SKIN);
						game.getPrefs().flush();
						game.switchToSheepSkin();
					}
				}
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
