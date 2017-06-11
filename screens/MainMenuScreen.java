package net.ocps.tchs.MDGame.screens;

import net.ocps.tchs.MDGame.GameHandler;
import net.ocps.tchs.MDGame.tools.TextureManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {

	GameHandler myGame;
	OrthographicCamera camera;
	Vector3 touchPos;
	SpriteBatch batch;
		
	public float canNowActOnThisScreenTimer;

	//NOTE: things in the constructor we want created once, like objects. Things in show() we want recreated or reset
		//every time the screen is viewed
	//constructor (show is then called after this, by setScreen())
	public MainMenuScreen(GameHandler g) {
		this.myGame = g;
	}

	//called by the setScreen() method
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		TextureManager.load(TextureManager.mainMenuScreen_value, myGame);
		TextureManager.load(TextureManager.labels_value, myGame);
			    
		canNowActOnThisScreenTimer = 0;
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.mainMenuScreen_texture, 0, 0);
		switch (myGame.user.currentDifficulty) {
		case 1:
			batch.draw(TextureManager.Easy, 300, 280);
			break;
		case 2:
			batch.draw(TextureManager.Medium, 309, 280);
			break;
		case 3:
			batch.draw(TextureManager.Hard, 300, 280);
			break;
		}
		batch.end();

		if (canNowActOnThisScreenTimer > 0.15) {
			for (int i = 0; i < 5; i++) {
				if(Gdx.input.isTouched(i)){
					touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touchPos);
					//new game
					if(touchPos.x > 313 && touchPos.x < 461 && touchPos.y > 155 && touchPos.y < 235){
						myGame.setScreen(new MainGameScreen(myGame));
					}
					//options menu
					if(touchPos.x > 40 && touchPos.x < 216 && touchPos.y > 31 && touchPos.y < 84){
						myGame.setScreen(new OptionsScreen(myGame));
					}
					//leaderboards
					if(touchPos.x > 447 && touchPos.x < 776 && touchPos.y > 36 && touchPos.y < 80){
						myGame.setScreen(new LeaderboardsScreen(myGame));
					}
				}
			}
			//new game
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
				myGame.setScreen(new MainGameScreen(myGame));
			}
		} else {
			canNowActOnThisScreenTimer += Gdx.graphics.getDeltaTime();
		}
		
	}

	public void resize(int width, int height) {
	}

	//called when you switch to another screen
	public void hide() {
		this.dispose();
	}

	//called when you open another app or go to the homescreen
	public void pause() {

	}

	//return from the app from pause
	public void resume() {

	}

	//IS NOT automatically called when the screen switches
	public void dispose() {
		batch.dispose();
		TextureManager.dispose(TextureManager.mainMenuScreen_value);
	}
}
