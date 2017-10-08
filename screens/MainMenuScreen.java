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
import com.matthewgarrison.enums.TextureEnum;

public class MainMenuScreen implements Screen {
	private GameHandler game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private float canActOnThisScreenTimer;

	public MainMenuScreen(GameHandler g) {
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.textures[TextureEnum.MAIN_MENU_SCREEN.ordinal()], 0, 0);
		if (game.getUser().getCurrentDifficulty() == Difficulty.EASY) {
			batch.draw(TextureManager.textures[TextureEnum.EASY.ordinal()], 300, 280);
		} else if (game.getUser().getCurrentDifficulty() == Difficulty.MEDIUM) {
			batch.draw(TextureManager.textures[TextureEnum.MEDIUM.ordinal()], 309, 280);
		} else {
			batch.draw(TextureManager.textures[TextureEnum.HARD.ordinal()], 300, 280);
		}

		batch.end();

		if (canActOnThisScreenTimer <= 0.15) {
			canActOnThisScreenTimer += delta;
		} else {
			for (int i = 0; i < 5; i++) {
				if(Gdx.input.isTouched(i)){
					Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touchPos);
					if(touchPos.x > 313 && touchPos.x < 461 && touchPos.y > 155 && touchPos.y < 235){
						game.setScreen(new MainGameScreen(game));
					}
					if(touchPos.x > 40 && touchPos.x < 216 && touchPos.y > 31 && touchPos.y < 84){
						game.setScreen(new OptionsScreen(game));
					}
					if(touchPos.x > 447 && touchPos.x < 776 && touchPos.y > 36 && touchPos.y < 80){
						game.setScreen(new LeaderboardsScreen(game));
					}
				}
			}
			if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				game.setScreen(new MainGameScreen(game));
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
