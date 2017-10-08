package com.matthewgarrison.screens;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.enums.DifficultyEnum;
import com.matthewgarrison.enums.SkinEnum;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.matthewgarrison.enums.TextureEnum;

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

		if (game.getUser().getCurrentDifficulty() == DifficultyEnum.EASY) {
			batch.draw(TextureManager.textures[TextureEnum.OPTIONS_SCREEN_EASY.ordinal()], 0, 0);
		} else if (game.getUser().getCurrentDifficulty() == DifficultyEnum.MEDIUM) {
			batch.draw(TextureManager.textures[TextureEnum.OPTIONS_SCREEN_MEDIUM.ordinal()], 0, 0);
		} else {
			batch.draw(TextureManager.textures[TextureEnum.OPTIONS_SCREEN_HARD.ordinal()], 0, 0);
		}
		if (game.getUser().getCurrentSkin() == SkinEnum.NORMAL) {
			batch.draw(TextureManager.textures[TextureEnum.SHEEP.ordinal()], 450, 210);
			batch.draw(TextureManager.textures[TextureEnum.NORMAL_SELECTED.ordinal()], 186, 210);
		} else {
			batch.draw(TextureManager.textures[TextureEnum.SHEEP_SELECTED.ordinal()], 450, 210);
			batch.draw(TextureManager.textures[TextureEnum.NORMAL.ordinal()], 186, 210);
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
						game.getPrefs().putString("difficulty", DifficultyEnum.EASY.name());
						game.getPrefs().flush();
						game.getUser().setCurrentDifficulty(DifficultyEnum.EASY);
					}
					if(touchPos.x < 490 && touchPos.x > 321 && touchPos.y > 276 && touchPos.y < 330) {
						game.getPrefs().putString("difficulty", DifficultyEnum.MEDIUM.name());
						game.getPrefs().flush();
						game.getUser().setCurrentDifficulty(DifficultyEnum.MEDIUM);
					}
					if(touchPos.x < 629 && touchPos.x > 515 && touchPos.y > 276 && touchPos.y < 330) {
						game.getPrefs().putString("difficulty", DifficultyEnum.HARD.name());						game.getPrefs().flush();
						game.getPrefs().flush();
						game.getUser().setCurrentDifficulty(DifficultyEnum.HARD);
					}

					if (touchPos.x < 350 && touchPos.x > 186 && touchPos.y < 270 && touchPos.y > 210) {
						game.getPrefs().putString("skin", SkinEnum.NORMAL.name());
						game.getPrefs().flush();
						game.switchToDefaultSkin();
					}
					if (touchPos.x < 614 && touchPos.x > 450 && touchPos.y < 270 && touchPos.y > 210) {
						game.getPrefs().putString("skin", SkinEnum.SHEEP.name());
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
