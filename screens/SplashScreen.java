package net.ocps.tchs.MDGame.screens;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import net.ocps.tchs.MDGame.GameHandler;
import net.ocps.tchs.MDGame.objects.Score;
import net.ocps.tchs.MDGame.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class SplashScreen implements Screen {

	GameHandler myGame;
	OrthographicCamera camera;
	SpriteBatch batch;
	public float splashTimer;

	//NOTE: things in the constructor we want created once, like objects. Things in show() we want recreated or reset
		//every time the screen is viewed 
	//constructor (show is then called after this, by setScreen())
	public SplashScreen(GameHandler g) {
		this.myGame = g;
	}
	
	//called by the setScreen() method
	public void show() {
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, 800, 480);
		this.batch = new SpriteBatch();
		TextureManager.load(TextureManager.splashScreen_value, myGame);
		this.splashTimer = 0;
		
		this.loadFiles();
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.splashScreen_texture, 0, 0);
		batch.end();

		//the splash screen displays for 1.5 seconds
		if (this.splashTimer >= 1.5) {
			myGame.setScreen(new MainMenuScreen(myGame));
		}
		this.splashTimer += Gdx.graphics.getDeltaTime();
	}
	
	private void loadFiles() {
		//player name
		LoadNonSecureFile("MDGames/Incoming/playerName.in");
		FileHandle file = Gdx.files.external("MDGames/Incoming/playerName.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/playerName.in");
		String playerName = file.readString();
		
		//difficulty
		LoadNonSecureFile("MDGames/Incoming/difficulty.in");
		file = Gdx.files.external("MDGames/Incoming/difficulty.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/difficulty.in");
		int difficulty = Integer.parseInt(file.readString());

		//which skin
		LoadSecureFile("MDGames/Incoming/whichSkin.in");
		file = Gdx.files.local("MDGames/Incoming/whichSkin.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/whichSkin.in");
		int whichSkin = Integer.parseInt(file.readString());
		switch (myGame.user.currentSkin.idNumber) {
		case 1:
			myGame.SwitchToDefaultSkin();
			break;
		case 2:
			myGame.SwitchToSheepSkin();
			break;
		}

		//high scores
		myGame.scores = null;
		LoadHighscoreFile("MDGames/Incoming/Highscores/normalEasy.in");
		LoadHighscoreFile("MDGames/Incoming/Highscores/normalMedium.in");
		LoadHighscoreFile("MDGames/Incoming/Highscores/normalHard.in");
		LoadHighscoreFile("MDGames/Incoming/Highscores/sheepEasy.in");
		LoadHighscoreFile("MDGames/Incoming/Highscores/sheepMedium.in");
		LoadHighscoreFile("MDGames/Incoming/Highscores/sheepHard.in");

		//in app purchases
		LoadSecureFile("MDGames/Incoming/InAppPurchases/sheepSkin.in"); //checks if the user can switch to the sheep skin
		file = Gdx.files.local("MDGames/Incoming/InAppPurchases/sheepSkin.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/sheepSkin.in");
		myGame.sheepSkin.isAvailable = (Integer.parseInt(file.readString()) == 0 ? false : true);
		LoadSecureFile("MDGames/Incoming/InAppPurchases/areThereAds.in"); //checks if there are ads
		file = Gdx.files.local("MDGames/Incoming/InAppPurchases/areThereAds.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/areThereAds.in");
		myGame.user.adsShown = (Integer.parseInt(file.readString()) == 0 ? false : true);
		
		//purchasing all in app stuff without paying
		LoadSecureFile("MDGames/Incoming/InAppPurchases/didYouCheat.in");
		file = Gdx.files.local("MDGames/Incoming/InAppPurchases/didYouCheat.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/didYouCheat.in");
		myGame.user.didYouCheat = Integer.parseInt(file.readString());
	}
	
	//for in app purchases, etc.
	private void LoadSecureFile(String FileName) {
		FileHandle file = Gdx.files.local(FileName);
		//creates the file if it doesn't exist
		if (!file.exists()) {
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local(FileName).write(false)));
				out.write("1");
				out.close();
			} catch (GdxRuntimeException ex) {

			} catch (IOException e) {
				System.out.println("LoadSecureFile error");
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}	
		}
	}

	//for leaderboards name, difficulty, etc
	private void LoadNonSecureFile(String FileName) {
		FileHandle file = Gdx.files.external(FileName);
		//creates the file if it doesn't exist
		if (!file.exists()) {
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external(FileName).write(false)));
				out.write("1");
				out.close();
			} catch (GdxRuntimeException ex) {

			} catch (IOException e) {
				System.out.println("LoadNonSecureFile error");
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}	
		}
	}

	//high scores
	private void LoadHighscoreFile(String FileName) {
		FileHandle file = Gdx.files.local(FileName);
		//creates the file if it doesn't exist
		if (!file.exists()) {
			file = Gdx.files.internal("SavedVariables/highscoreBase.in");
			StringTokenizer tokens = new StringTokenizer(file.readString());
			myGame.scores = new Score[tokens.countTokens()/2];
			int i = 0;
			while (tokens.hasMoreTokens()) {
				myGame.scores[i] = new Score(tokens.nextToken(), Integer.parseInt(tokens.nextToken()));
				i++;
			}
			myGame.scores = Score.sortScores(myGame.scores);

			//saves the top 3 in the file.
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local(FileName).write(false)));
				for (int j = 0; j < 3; j++) {
					out.write(myGame.scores[j].getName() + " " + myGame.scores[j].getScore() + " ");
				}
				out.close();
			} catch (GdxRuntimeException ex) {

			} catch (IOException e) {
				System.out.println("LoadHighScoreFile error");
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}	
		}
	}

	
	public void resize(int width, int height) {

	}

	//called when you switch to another screen
	public void hide() {
		dispose();
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
		TextureManager.dispose(TextureManager.splashScreen_value);
	}
}
