package net.ocps.tchs.MDGame.screens;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import net.ocps.tchs.MDGame.GameHandler;
import net.ocps.tchs.MDGame.tools.TextureManager;
import net.ocps.tchs.MDGame.user.User;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class GameOverScreen implements Screen {

	GameHandler myGame;
	OrthographicCamera camera;
	Vector3 touchPos;
	SpriteBatch batch;
	public float canNowActOnThisScreenTimer;
	public int cheatThePurchasesCount;
	public float cheatThePurchasesTimer; //checks to make sure the presses on the button are quick enough
	int score;

	//NOTE: things in the constructor we want created once, like objects. Things in show() we want recreated or reset
	//every time the screen is viewed
	//constructor (show is then called after this, by setScreen())
	public GameOverScreen (GameHandler g, int score) {
		this.myGame = g;
		this.score = score;
	}

	//called by the setScreen() method
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		TextureManager.load(TextureManager.gameOverScreen_value, myGame);
		TextureManager.load(TextureManager.buttons_value, myGame);
		TextureManager.load(TextureManager.cheatIcon_value, myGame);
		canNowActOnThisScreenTimer = 0;
		cheatThePurchasesCount = 0;
		cheatThePurchasesTimer = 0; //checks to make sure the presses on the button are quick enough

		//adds the score to the high score file
		if (score != 0) {
			addHighScoreToFile();
		}
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.gameOverScreen_texture, 0, 0);
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
		myGame.textNormal.draw(batch, "Score: " + score, 300, 250);

		if (myGame.user.didYouCheat == 2) batch.draw(TextureManager.cheatIcon, 790, 470);
		batch.end();

		//displays ads
		if (myGame.user.adsShown) {
			myGame.showAds();
		}
		if (canNowActOnThisScreenTimer > 0.15) {
			//starts the timer once you've started pressing the button
			if (cheatThePurchasesCount > 0 && cheatThePurchasesCount < 13 && cheatThePurchasesTimer < 1) {
				cheatThePurchasesTimer += Gdx.graphics.getDeltaTime();
			} else if (cheatThePurchasesCount > 0 && cheatThePurchasesTimer > 1) { //zeroes the values if you wait too long
				cheatThePurchasesCount = 0;
				cheatThePurchasesTimer = 0;
			}
			for (int i = 0; i < 5; i++) {
				if(Gdx.input.isTouched(i)){
					touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touchPos);
					//starting a new game
					if(touchPos.x > 6 && touchPos.x < 310 && touchPos.y > 140 && touchPos.y < 200){
						myGame.setScreen(new MainGameScreen(myGame));

					}
					//returning to the menu
					if(touchPos.x > 440 && touchPos.x < 795 && touchPos.y > 140 && touchPos.y < 200){
						myGame.setScreen(new MainMenuScreen(myGame));
					}
					//if you press the button 15 times, you bypass all in-app purchases
					if (touchPos.x > 780 && touchPos.y > 460 && cheatThePurchasesTimer <= 0.15) {
						cheatThePurchasesCount++;
						cheatThePurchasesTimer = 0;
						if (cheatThePurchasesCount >= 15) CheatThePurchases();
					}
				}
			}
			//starting a new game
			if(Gdx.input.isKeyJustPressed(Keys.R)){
				myGame.setScreen(new MainGameScreen(myGame));
			}
			//returning to the menu
			if(Gdx.input.isKeyJustPressed(Keys.BACKSPACE)){
				myGame.setScreen(new MainMenuScreen(myGame));
			}
		} else {
			canNowActOnThisScreenTimer += Gdx.graphics.getDeltaTime();
		}
	}

	private void addHighScoreToFile() {
		FileHandle file = Gdx.files.external("MDGames/Incoming/playerName.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/playerName.in");
		String playerName = myGame.user.playerName;
		//add option to edit name before submitting
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local("MDGames/Incoming/Highscores/" + myGame.user.currentSkin.name + "_" + myGame.user.currentDifficulty + ".in").write(true)));
			out.write(myGame.user.playerName + " ");
			out.write(score + " ");
			out.close();
		} catch (GdxRuntimeException ex) {
			Gdx.app.log("ERROR", ex.getMessage());
		} catch (IOException e) {
			Gdx.app.log("ERROR", e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void CheatThePurchases() {
		System.out.println("yup");
		myGame.user.didYouCheat = 2;
		myGame.user.adsShown = false;
		myGame.sheepSkin.isAvailable = true;
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local("MDGames/Incoming/InAppPurchases/didYouCheat.in").write(false)));
			out.write("2");
			out.close();
		} catch (GdxRuntimeException ex) {
			Gdx.app.log("ERROR", ex.getMessage());
		} catch (IOException e) {
			Gdx.app.log("ERROR", e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		try {
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local("MDGames/Incoming/InAppPurchases/sheepSkin.in").write(false)));
			out.write("2");
			out.close();
		} catch (GdxRuntimeException ex) {
			Gdx.app.log("ERROR", ex.getMessage());
		} catch (IOException e) {
			Gdx.app.log("ERROR", e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		try {
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local("MDGames/Incoming/InAppPurchases/areThereAds.in").write(false)));
			out.write("2");
			out.close();
		} catch (GdxRuntimeException ex) {
			Gdx.app.log("ERROR", ex.getMessage());
		} catch (IOException e) {
			Gdx.app.log("ERROR", e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
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
		TextureManager.dispose(TextureManager.gameOverScreen_value);
		TextureManager.dispose(TextureManager.buttons_value);
		TextureManager.dispose(TextureManager.cheatIcon_value);
	}
}
