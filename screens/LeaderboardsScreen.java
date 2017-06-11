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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class LeaderboardsScreen implements Screen {

	GameHandler myGame;
	OrthographicCamera camera;
	Vector3 touchPos;
	SpriteBatch batch;
	public float canNowActOnThisScreenTimer;
	public boolean loadedScores;
	public int whichSkinScoreIsShown;
	public int whichDifficultyScoreIsShown;

	//NOTE: things in the constructor we want created once, like objects. Things in show() we want recreated or reset
	//every time the screen is viewed
	//constructor (show is then called after this, by setScreen())
	public LeaderboardsScreen(GameHandler g) {
		this.myGame = g;
	}

	//called by the setScreen() method
	public void show() {
		this.whichSkinScoreIsShown = myGame.user.currentSkin.idNumber;
		this.whichDifficultyScoreIsShown = myGame.user.currentDifficulty;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		TextureManager.load(TextureManager.leadersboardsScreen_value, myGame);
		canNowActOnThisScreenTimer = 0;
		loadedScores = false;
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
		batch.draw(TextureManager.leadersboardsScreen_texture, 0, 0);
		switch (whichSkinScoreIsShown) {
		case 1:
			batch.draw(TextureManager.DarkNormal, 200, 200);
			batch.draw(TextureManager.Sheep, 195, 140);
			break;
		case 2:
			batch.draw(TextureManager.DarkSheep, 195, 140);
			batch.draw(TextureManager.Normal, 200, 200);
			break;
		}
		switch (whichDifficultyScoreIsShown) {
		case 1:
			batch.draw(TextureManager.DarkEasy, 5, 320);
			batch.draw(TextureManager.Medium, 175, 320);
			batch.draw(TextureManager.Hard, 345, 320);
			break;
		case 2:
			batch.draw(TextureManager.Easy, 5, 320);
			batch.draw(TextureManager.DarkMedium, 175, 320);
			batch.draw(TextureManager.Hard, 345, 320);			
			break;
		case 3:
			batch.draw(TextureManager.Easy, 5, 320);
			batch.draw(TextureManager.Medium, 175, 320);
			batch.draw(TextureManager.DarkHard, 345, 320);			
			break;
		}

		//draws the three scores
		//use the following once the display is perfected:
		//if (scores[0, 1, or 2] >= 1) textLarge.draw();
		myGame.textLarge.draw(batch, "1. " + myGame.scores[0].getName() + ": " + myGame.scores[0].getScore(), 500, 350);
		myGame.textLarge.draw(batch, "2. " + myGame.scores[1].getName() + ": " + myGame.scores[1].getScore(), 500, 275);
		myGame.textLarge.draw(batch, "3. " + myGame.scores[2].getName() + ": " + myGame.scores[2].getScore(), 500, 200);	
		batch.end();
		

		//displays ads
		if (myGame.user.adsShown) {
			//put ads
		}
		
		//loads the leaderboards
		if (loadedScores == false) {
			loadScores();
		}

		if (canNowActOnThisScreenTimer > 0.15) {
			for(int i = 0; i < 5; i++){
				if(Gdx.input.isTouched(i)){
					touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touchPos);

					//returns to the menu
					if(touchPos.x < 136 && touchPos.x > 26 && touchPos.y > 144 && touchPos.y < 190){
						myGame.setScreen(new MainMenuScreen(myGame));
					}
					//displays sheep scores
					if(touchPos.x < 370 && touchPos.x > 200 && touchPos.y > 200 && touchPos.y < 240 && whichSkinScoreIsShown != 1) {
						whichSkinScoreIsShown = 1;
						loadedScores = false;
					}
					//displays normal scores
					if(touchPos.x < 370 && touchPos.x > 200 && touchPos.y > 140 && touchPos.y < 180 && whichSkinScoreIsShown != 2) {
						whichSkinScoreIsShown = 2;
						loadedScores = false;
					}
					//displays easy scores
					if(touchPos.x < 170 && touchPos.x > 5 && touchPos.y > 320 && touchPos.y < 360 && whichDifficultyScoreIsShown != 1) {
						whichDifficultyScoreIsShown = 1;
						loadedScores = false;
					}
					//displays medium scores
					if(touchPos.x < 340 && touchPos.x > 175 && touchPos.y > 320 && touchPos.y < 360 && whichDifficultyScoreIsShown != 2) {
						whichDifficultyScoreIsShown = 2;
						loadedScores = false;
					}
					//displays hard scores
					if(touchPos.x < 510 && touchPos.x > 345 && touchPos.y > 320 && touchPos.y < 360 && whichDifficultyScoreIsShown != 3) {
						whichDifficultyScoreIsShown = 3;
						loadedScores = false;
					}
				}
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
		TextureManager.dispose(TextureManager.leadersboardsScreen_value);
	}

	public void loadScores() {
		String FileName = null;
		if (whichDifficultyScoreIsShown == 1 && whichSkinScoreIsShown == 1) FileName = "MDGames/Incoming/Highscores/normalEasy.in";
		else if (whichDifficultyScoreIsShown == 2 && whichSkinScoreIsShown == 1) FileName = "MDGames/Incoming/Highscores/normalMedium.in";
		else if (whichDifficultyScoreIsShown == 3 && whichSkinScoreIsShown == 1) FileName = "MDGames/Incoming/Highscores/normalHard.in";
		else if (whichDifficultyScoreIsShown == 1 && whichSkinScoreIsShown == 2) FileName = "MDGames/Incoming/Highscores/sheepEasy.in";
		else if (whichDifficultyScoreIsShown == 2 && whichSkinScoreIsShown == 2) FileName = "MDGames/Incoming/Highscores/sheepMedium.in";
		else if (whichDifficultyScoreIsShown == 3 && whichSkinScoreIsShown == 2) FileName = "MDGames/Incoming/Highscores/sheepHard.in";

		//grabs the scores from the file
		FileHandle file = Gdx.files.local(FileName);
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/highscoreBase.in");
		StringTokenizer tokens = new StringTokenizer(file.readString());
		myGame.scores = new Score[tokens.countTokens()/2];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			myGame.scores[i] = new Score(tokens.nextToken(), Integer.parseInt(tokens.nextToken()));
			i++;
		}
		myGame.scores = Score.sortScores(myGame.scores);

		//overwrites the file with the top 3 scores 
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local(FileName).write(false)));
			for (int j = 0; j < 3; j++) {
				out.write(myGame.scores[j].getName() + " " + myGame.scores[j].getScore() + " ");
			}
			out.close();
		} catch (GdxRuntimeException ex) {

		} catch (IOException e) {
			System.out.println("leaderboards broke");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		loadedScores = true;
	}
}
