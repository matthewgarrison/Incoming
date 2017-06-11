package net.ocps.tchs.MDGame.screens;

import java.util.ArrayList;
import java.util.Random;

import net.ocps.tchs.MDGame.GameHandler;
import net.ocps.tchs.MDGame.tools.TextureManager;
import net.ocps.tchs.MDGame.objects.Bottle;
import net.ocps.tchs.MDGame.objects.MainGuy;
import net.ocps.tchs.MDGame.objects.Modifier;
import net.ocps.tchs.MDGame.objects.PowerUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class MainGameScreen implements Screen {

	GameHandler myGame;
	OrthographicCamera camera;
	Vector3 touchPos;
	SpriteBatch batch;
	
	public float canNowActOnThisScreenTimer;
	public float unpauseTimer;
	public boolean isPaused;
	public float gameStartAndUnpauseTimer;
	public float pauseClickTimer;
	public Random randNumGenerator;
	
	Rectangle floor;
	Rectangle bottleResetPoint;
	Rectangle leftWall;
	Rectangle rightWall;
	
	public int score;
	public int lives;

	MainGuy player1;
	
	public ArrayList<Bottle> bottleArray;
	public ArrayList<Bottle> bottlesToBeDeleted;
	public BottlePool bottlePool;

	PowerUp extraLife1; //one extra life
	PowerUp jumpBoost5; //
 	PowerUp scoreModifier2; //2x score modifier
	
	public int scoreModifier;
	public boolean isScoreBeingModified;
	ArrayList<Modifier> scoreModifiers = new ArrayList<Modifier>(); //list of all the current score modifiers

	public int cheatThePurchasesCount; //how many times you've pressed the invisible "cheat all purchases" button
	public float cheatThePurchasesTimer; //checks to make sure the presses on the button are quick enough
	public int didYouCheat; //scans in what the file says, used in any if statements

	//NOTE: things in the constructor we want created once, like objects. Things in show() we want recreated or reset
	//every time the screen is viewed
	//constructor (show is then called after this, by setScreen())
	public MainGameScreen(GameHandler g) {
		this.myGame = g;
	}

	//called by the setScreen() method
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		TextureManager.loadAllGameTextures(myGame);
		randNumGenerator = new Random();

		isPaused = false;
		pauseClickTimer = 0;
		gameStartAndUnpauseTimer = 4;

		FileHandle file = Gdx.files.external("MDGames/Incoming/difficulty.in");
		if (!file.exists()) file = Gdx.files.internal("SavedVariables/difficulty.in");
		myGame.user.currentDifficulty = Integer.parseInt(file.readString());

		player1 = new MainGuy(1, 120); //initializes the dude at (0, 120)
		this.bottleArray = new ArrayList<Bottle>();
		this.bottlesToBeDeleted = new ArrayList<Bottle>();
		this.bottlePool = new BottlePool();
		this.newBottle(300);

		//extra life power up
		extraLife1 = new PowerUp(randNumGenerator.nextInt(700) + 50, 700); //the x coordinate ranges from 50-750
		extraLife1.sprite.setTexture(TextureManager.powerUp_extraLife_texture);
		//jump boost power up
		jumpBoost5 = new PowerUp(randNumGenerator.nextInt(700) + 50, 700);
		jumpBoost5.sprite.setTexture(TextureManager.powerUp_jumpBoost_texture);
		//2x score multiplier power up
		scoreModifier2 = new PowerUp(randNumGenerator.nextInt(700) + 50, 700);
		scoreModifier2.sprite.setTexture(TextureManager.powerUp_scoreModTwo_texture);

		score = 0;
		//score multiplier
		scoreModifier = 1;
		isScoreBeingModified = false;

		switch (myGame.user.currentDifficulty) {
		case 1:
			Bottle.speedIncrease = 10;
			PowerUp.spawnChance = 3;
			lives = 5;
			break;
		case 2:
			Bottle.speedIncrease = 20;
			PowerUp.spawnChance = 2;
			lives = 3;
			break;
		case 3:
			Bottle.speedIncrease = 30;
			PowerUp.spawnChance = 1;
			lives = 1;
			break;
		}
		
		canNowActOnThisScreenTimer = 0;
		unpauseTimer = 0.16f; //starts at 0.16, because the game starts unpaused
		
		//floor, walls, and the reset point for the bottles (points listed in clockwise order, starting at bottom left)
		floor = new Rectangle(-200, 0, 1200, 120); //(-200, 0), (-200, 120), (1000, 120), (1000, 0)
		bottleResetPoint = new Rectangle(-76, 0, 1, 600); //(-76, 0), (-76, 600), (-75, 600), (-75, 0)
		leftWall = new Rectangle(-1, 0, 1, 10000);  //(-200, 0), (-200, 120), (1000, 120), (1000, 0)
		rightWall = new Rectangle(800, 0, 1, 10000);
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(TextureManager.mainGameScreen_texture, 0, 0);

		myGame.textNormal.draw(batch, "Score: " + score,  20, 460);
		myGame.textNormal.draw(batch, "Lives: " + lives, 655, 460);
		myGame.textNormalSelected.draw(batch, "Jump Power : " + player1.jumpPower + "%", 275, 460); //how much the player can jump
		switch (scoreModifier) {
		case 1:
			break;
		case 2:
			myGame.textNormal.draw(batch, "Double Points! (" + (int)scoreModifiers.get(0).timeLeft + " seconds remaining)", 150, 390);
			break;
		case 4:
			myGame.textNormal.draw(batch, "Quadruple Points! (" + (int)scoreModifiers.get(0).timeLeft + " seconds remaining)", 160, 390);
			break;
		case 8:
			myGame.textNormal.draw(batch, "Octuple Points! (" + (int)scoreModifiers.get(0).timeLeft + " seconds remaining)", 155, 390);
			break;
		default: //used only if scoreModifier =/= 1, 2, 4, or 8
			myGame.textNormal.draw(batch, "Score is being multiplied by " + scoreModifier + " (" + (int)scoreModifiers.get(0).timeLeft 
					+ ")", 100, 390);
			break;
		}

		player1.draw(batch); //draws the player
		//the bottles
		for (int ii = 0; ii < bottleArray.size(); ii++) {
			bottleArray.get(ii).draw(batch);
		}
		//the power ups
		extraLife1.draw(batch);
		jumpBoost5.draw(batch);
		scoreModifier2.draw(batch);

		//draws the buttons
		batch.draw(TextureManager.onScreenLeftButton_texture, 0, 0);
		batch.draw(TextureManager.onScreenRightButton_texture, 100, 0);
		batch.draw(TextureManager.onScreenUpButton_texture, 700, 0);
		batch.draw(TextureManager.onScreenDownButton_texture, 600, 0);
		if (isPaused) {
			batch.draw(TextureManager.onScreenPlayButton_texture, 363, 0);
			batch.draw(TextureManager.pauseMenu_texture, 130, 110);
			//the diffilcuty is displayed on the pause menu
			switch (myGame.user.currentDifficulty) {
			case 1:
				batch.draw(TextureManager.Easy, 300, 230);
				break;
			case 2:
				batch.draw(TextureManager.Medium, 309, 230);
				break;
			case 3:
				batch.draw(TextureManager.Hard, 300, 230);
				break;
			}
		} else {
			batch.draw(TextureManager.onScreenPauseButton_texture, 363, 0);
		}

		//the count-down shown when the game starts and un-pauses
		if (gameStartAndUnpauseTimer >= 0 && isPaused == false){
			switch ((int)gameStartAndUnpauseTimer) {
			case 3:
				batch.draw(TextureManager.gameStartUnPause3, 363, 203);
				break;
			case 2:
				batch.draw(TextureManager.gameStartUnPause2, 363, 203);
				break;
			case 1:
				batch.draw(TextureManager.gameStartUnPause1, 363, 203);
				break;
			case 0:
				batch.draw(TextureManager.gameStartUnPauseGo, 343, 203);
				break;	
			}
		}

		batch.end();

		if (canNowActOnThisScreenTimer > 0.15) {
			if (pauseClickTimer < 2) pauseClickTimer += Gdx.graphics.getDeltaTime();
			if (isPaused) {
				if (pauseClickTimer >= 0.15) { //makes sure to don't accidentally pause and immediately un-pause
					//unpauses
					if(Gdx.input.isKeyJustPressed(Keys.P)){
						this.unPauseGame();
					}
					for (int i = 0; i < 5; i++) {
						if(Gdx.input.isTouched(i)) {
							touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
							camera.unproject(touchPos);
							//returns to the menu
							if(touchPos.x > 176 && touchPos.x < 373 && touchPos. y > 151 && touchPos.y < 201){
								myGame.setScreen(new MainMenuScreen(myGame));
							}
							//unpauses
							if(touchPos.x > 433 && touchPos.x < 633 && touchPos. y > 151 && touchPos.y < 201){
								this.unPauseGame();
							}
							//also unpauses
							if(touchPos.x > 363 && touchPos.x < 437 && touchPos.y < 100) {
								this.unPauseGame();
							}
						}
					}
				}
			} else {
				//pauses
				if (pauseClickTimer >= 0.15) {
					if(Gdx.input.isKeyJustPressed(Keys.P)){
						this.pauseGame();
					}
					for (int i = 0; i < 5; i++) {
						if(Gdx.input.isTouched(i)) {
							touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
							camera.unproject(touchPos);
							if(touchPos.x > 363 && touchPos.x < 437 && touchPos.y < 100) {
								this.pauseGame();
							}
						}
					}
				}

				//the countdown
				if (gameStartAndUnpauseTimer > -2) gameStartAndUnpauseTimer -= Gdx.graphics.getDeltaTime();

				//the game when you are not paused or counting down
				if (gameStartAndUnpauseTimer <= 0) {
					//updates the score modifiers and removes the ones that are finished
					for(int ii = 0; ii < scoreModifiers.size(); ii++){
						scoreModifiers.get(ii).update(Gdx.graphics.getDeltaTime());
						if (scoreModifiers.get(ii).isDone()) {
							scoreModifiers.remove(ii);
							ii--;
						}
					}
					//checks if the score is being modified
					if (scoreModifiers.isEmpty()) isScoreBeingModified = false;
					//adds up all the score multipliers
					scoreModifier = 1;
					if (isScoreBeingModified) {
						for(int ii = 0; ii < scoreModifiers.size(); ii++){
							scoreModifier *= scoreModifiers.get(ii).getModify();
						}
					}

					//updates the player, bottles, and power ups
					player1.update(Gdx.graphics.getDeltaTime());
					for (int ii = 0; ii < bottleArray.size(); ii++) {
						bottleArray.get(ii).update(Gdx.graphics.getDeltaTime());
					}			
					extraLife1.update(Gdx.graphics.getDeltaTime());
					jumpBoost5.update(Gdx.graphics.getDeltaTime());
					scoreModifier2.update(Gdx.graphics.getDeltaTime());

					//player hits a bottle, both reset; life drops by one, the game resets if lives == 0
					for (int ii = 0; ii < bottleArray.size(); ii++) {
						if (player1.hitBox.overlaps(bottleArray.get(ii).hitBox)) {
							player1.setPosition(0, 120);
							bottlesToBeDeleted.add(bottleArray.get(ii));
							if (isImpossibleCase() == false) {
								if (bottleArray.get(ii).speed < 450) {
									this.newBottle(bottleArray.get(ii).speed + 10);
								} else {
									this.newBottle(bottleArray.get(ii).speed);
								}
							}
							lives--;
							if (lives == 0) {
								myGame.setScreen(new GameOverScreen(myGame, score));
							}
						}
					}
					//player gets an extra life
					if(player1.hitBox.overlaps(extraLife1.hitBox)){
						if (lives < 99) lives++;
						extraLife1.reset(0, 700); //the x doesn't matter, as it will be re-randomized
					}
					//player gets a jump boost
					if(player1.hitBox.overlaps(jumpBoost5.hitBox)){
						player1.jumpPower += 100;
						player1.boost += 5;
						jumpBoost5.reset(0, 700); //the x doesn't matter, as it will be re-randomized
					}
					//player gets a score modifier
					if(player1.hitBox.overlaps(scoreModifier2.hitBox)) {
						isScoreBeingModified = true;
						this.scoreModifiers.add(new Modifier(2.0f, 21));
						scoreModifier2.reset(0, 700); //the x doesn't matter, as it will be re-randomized
					}

					//bottle reset (hitting the left wall)
					for (int ii = 0; ii < bottleArray.size(); ii++) {
						if (bottleArray.get(ii).hits(bottleResetPoint) == 1) {
							//creates a new bottle if the last bottle in the array reaches a speed of 450
							if (bottleArray.get(ii).speed >= 450 && ii == bottleArray.size()-1) {
								if (isImpossibleCase() == false) {
									this.newBottle(300);
								}
							}
							//resets the bottle
							bottlesToBeDeleted.add(bottleArray.get(ii));
							if (isImpossibleCase() == false) {
								if (bottleArray.get(ii).speed < 500) {
									this.newBottle(bottleArray.get(ii).speed + 10);
								} else {
									this.newBottle(bottleArray.get(ii).speed);
								}
							}
							//gives to x points, where x is 1 * how much your score is being modified by
							score += 1 * scoreModifier;
						}
					}
					//jump boost power up hitting the floor
					if(jumpBoost5.hits(floor) == 1){
						jumpBoost5.reset(0, 700); //the x doesn't matter, as it will be re-randomized
					}
					//extra life power up hitting the floor
					if(extraLife1.hits(floor) == 1){
						extraLife1.reset(0, 700); //the x doesn't matter, as it will be re-randomized
					}
					//score multiplier power up hitting the floor
					if(scoreModifier2.hits(floor) == 1){
						scoreModifier2.reset(0, 700); //the x doesn't matter, as it will be re-randomized
					}

					//landing, un-crouching
					if (player1.hits(floor) == 1) {
						player1.landOnGround(0, 120);
						player1.inAir = false;
					}
					//can't pass the left wall
					if(player1.hits(leftWall) == 1){
						player1.setPosition(1, player1.hitBox.y);
					}
					//can't pass the right wall
					if(player1.hits(rightWall) == 1) {
						if (myGame.user.currentSkin.idNumber == myGame.defaultSkin.idNumber) {
							player1.setPosition(758, player1.hitBox.y);
						} else if (myGame.user.currentSkin.idNumber == myGame.sheepSkin.idNumber) {
							player1.setPosition(720, player1.hitBox.y);
						}
					}

					//moves left (with keyboard)
					if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
						if (player1.dominantDirection == 0 || player1.dominantDirection == 1) {
							player1.moveLeft(Gdx.graphics.getDeltaTime());
						}
					}
					//moves right (with keyboard)
					if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)){
						if (player1.dominantDirection == 0 || player1.dominantDirection == 2) {
							player1.moveRight(Gdx.graphics.getDeltaTime());
						}
					}
					//jumps (with keyboard)
					if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)){
						player1.Jump();
					}
					//crouches (with keyboard)
					if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
						if (!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT) && 
								!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) {
							player1.Crouch();
						}
					}

					//movement with the touch screen
					for (int i = 0; i < 5; i++) {
						if (Gdx.input.isTouched(i)) {
							touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
							camera.unproject(touchPos);
							//move left
							if (touchPos.x < 100 && touchPos.y < 100){
								if (player1.dominantDirection == 0 || player1.dominantDirection == 1) {
									player1.moveLeft(Gdx.graphics.getDeltaTime());
								}
							}
							//move right
							if (touchPos.x > 100 && touchPos.x < 200 && touchPos.y < 100) {
								if (player1.dominantDirection == 0 || player1.dominantDirection == 2) {
									player1.moveRight(Gdx.graphics.getDeltaTime());
								}
							}
							//jump
							if(touchPos.x > 700 && touchPos.y < 100){
								player1.Jump();
							}
							//crouch
							if(touchPos.x > 600 && touchPos.x < 700 && touchPos.y < 100) {
								player1.Crouch();
							}
						}
					}	

					while (bottlesToBeDeleted.size() > 0) {
						bottleArray.remove(bottlesToBeDeleted.get(0));
						bottlePool.free(bottlesToBeDeleted.get(0));
						bottlesToBeDeleted.remove(0);
					}
				}
			}
		} else if (canNowActOnThisScreenTimer <= 0.15) {
			canNowActOnThisScreenTimer += Gdx.graphics.getDeltaTime();
		}
	}

	public void pauseGame() {
		gameStartAndUnpauseTimer = 0;
		isPaused = true;
		pauseClickTimer = 0;
	}
	public void unPauseGame() {
		gameStartAndUnpauseTimer = 4; //the onscreen timer
		isPaused = false;
		pauseClickTimer = 0; //allows you to re-pause
	}

	public void newBottle(int speed) {
		Bottle b = bottlePool.obtain();
		b.reconstructor(900, randNumGenerator.nextInt(70) + 135, speed);
		this.bottleArray.add(b); //ranges from 135-205
	}
	public boolean isImpossibleCase() {

		return false;
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
		TextureManager.disposeAllGameTextures();
	}
}

//the pool of bottles
class BottlePool extends Pool<Bottle> {
	protected Bottle newObject() {
		//all zeroes because bottle.reconstructor() will re-do the values anyway
		return new Bottle(0, 0);
	}
}