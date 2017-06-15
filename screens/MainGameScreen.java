package com.matthewgarrison.screens;

import java.util.ArrayList;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.tools.TextureManager;
import com.matthewgarrison.objects.Projectile;
import com.matthewgarrison.objects.MainGuy;
import com.matthewgarrison.objects.Modifier;
import com.matthewgarrison.objects.PowerUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class MainGameScreen implements Screen {
	private GameHandler myGame;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private float canActOnThisScreenTimer, onscreenTimer, pauseClickTimer;
	private boolean isPaused;

	private Rectangle floor, projectileResetPoint, leftWall, rightWall;

	private int score, lives;

	private MainGuy player;

	private ArrayList<Projectile> projectiles;
	private ArrayList<Projectile> projectilesToBeDeleted;
	private ProjectilePool projectilePool;

	private PowerUp extraLife, jumpBoost, scoreModifier;

	private int currentScoreModifier;
	private boolean isScoreBeingModified;
	private ArrayList<Modifier> scoreModifiers;

	public MainGameScreen(GameHandler g) {
		this.myGame = g;
	}

	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameHandler.getScreenWidth(), GameHandler.getScreenHeight());
		batch = new SpriteBatch();

		isPaused = false;
		pauseClickTimer = 0;
		onscreenTimer = 4;

		player = new MainGuy(1, 120);
		this.projectiles = new ArrayList<Projectile>();
		this.projectilesToBeDeleted = new ArrayList<Projectile>();
		this.projectilePool = new ProjectilePool();
		this.addNewProjectile();

		extraLife = new PowerUp(TextureManager.powerUpExtraLife);
		jumpBoost = new PowerUp(TextureManager.powerUpScoreMod);
		scoreModifier = new PowerUp(TextureManager.powerUpJumpBoost);

		score = 0;
		currentScoreModifier = 1;
		isScoreBeingModified = false;
		scoreModifiers = new ArrayList<Modifier>();

		switch (myGame.getUser().getCurrentDifficulty()) {
			case 1:
				Projectile.setSpeedIncrease(10);
				PowerUp.setSpawnChance(3);
				lives = 5;
				break;
			case 2:
				Projectile.setSpeedIncrease(20);
				PowerUp.setSpawnChance(2);
				lives = 3;
				break;
			case 3:
				Projectile.setSpeedIncrease(30);
				PowerUp.setSpawnChance(1);
				lives = 1;
				break;
		}

		canActOnThisScreenTimer = 0;

		floor = new Rectangle(-200, 0, 1200, 120);
		projectileResetPoint = new Rectangle(-76, 0, 1, 600);
		leftWall = new Rectangle(-1, 0, 1, 10000);
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
		batch.draw(TextureManager.textures[TextureManager.mainGameScreen], 0, 0);

		myGame.getTextNormal().draw(batch, "Score: " + score,  20, 460);
		myGame.getTextNormal().draw(batch, "Lives: " + lives, 655, 460);
		myGame.getTextNormalSelected().draw(batch, "jump Power : " + player.getJumpPower() + "%", 275, 460);
		switch (currentScoreModifier) {
			case 1:
				break;
			case 2:
				myGame.getTextNormal().draw(batch, "Double Points! (" +
						(int)scoreModifiers.get(0).getTimeLeft() + " seconds remaining)", 150, 390);
				break;
			case 4:
				myGame.getTextNormal().draw(batch, "Quadruple Points! (" +
						(int)scoreModifiers.get(0).getTimeLeft() + " seconds remaining)", 160, 390);
				break;
			case 8:
				myGame.getTextNormal().draw(batch, "Octuple Points! (" +
						(int)scoreModifiers.get(0).getTimeLeft() + " seconds remaining)", 155, 390);
				break;
			default:
				myGame.getTextNormal().draw(batch, "Score is being multiplied by " +
						currentScoreModifier + " (" + (int)scoreModifiers.get(0).getTimeLeft()
						+ ")", 100, 390);
				break;
		}

		player.draw(batch);
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(batch);
		}
		extraLife.draw(batch);
		jumpBoost.draw(batch);
		scoreModifier.draw(batch);

		batch.draw(TextureManager.textures[TextureManager.onScreenLeftButton], 0, 0);
		batch.draw(TextureManager.textures[TextureManager.onScreenRightButton], 100, 0);
		batch.draw(TextureManager.textures[TextureManager.onScreenUpButton], 700, 0);
		batch.draw(TextureManager.textures[TextureManager.onScreenDownButton], 600, 0);
		if (isPaused) {
			batch.draw(TextureManager.textures[TextureManager.onScreenPlayButton], 363, 0);
			batch.draw(TextureManager.textures[TextureManager.pauseMenu], 130, 110);
			switch (myGame.getUser().getCurrentDifficulty()) {
				case 1:
					batch.draw(TextureManager.textures[TextureManager.easy], 300, 230);
					break;
				case 2:
					batch.draw(TextureManager.textures[TextureManager.medium], 309, 230);
					break;
				case 3:
					batch.draw(TextureManager.textures[TextureManager.hard], 300, 230);
					break;
			}
		} else {
			batch.draw(TextureManager.textures[TextureManager.onScreenPauseButton], 363, 0);
		}

		// The count-down shown when the game starts and un-pauses.
		if (onscreenTimer >= 0 && !isPaused){
			switch ((int) onscreenTimer) {
				case 3:
					batch.draw(TextureManager.textures[TextureManager.gameStartUnPause3], 363, 203);
					break;
				case 2:
					batch.draw(TextureManager.textures[TextureManager.gameStartUnPause2], 363, 203);
					break;
				case 1:
					batch.draw(TextureManager.textures[TextureManager.gameStartUnPause1], 363, 203);
					break;
				case 0:
					batch.draw(TextureManager.textures[TextureManager.gameStartUnPauseGo], 343, 203);
					break;
			}
		}

		batch.end();

		if (canActOnThisScreenTimer <= 0.15) {
			canActOnThisScreenTimer += delta;
		} else {
			if (isPaused) {
				if (pauseClickTimer <= 0.15) {
					pauseClickTimer += delta;
				} else {
					if(Gdx.input.isKeyJustPressed(Keys.P)){
						this.unPauseGame();
					}
					for (int i = 0; i < 5; i++) {
						if (Gdx.input.isTouched(i)) {
							Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
							camera.unproject(touchPos);
							if(touchPos.x > 176 && touchPos.x < 373 && touchPos. y > 151 && touchPos.y < 201){
								myGame.setScreen(new MainMenuScreen(myGame));
							}
							if(touchPos.x > 433 && touchPos.x < 633 && touchPos. y > 151 && touchPos.y < 201){
								this.unPauseGame();
							}
							if(touchPos.x > 363 && touchPos.x < 437 && touchPos.y < 100) {
								this.unPauseGame();
							}
						}
					}
				}
			} else {
				if (pauseClickTimer <= 0.15) {
					pauseClickTimer += delta;
				} else {
					if(Gdx.input.isKeyJustPressed(Keys.P)){
						this.pauseGame();
					}
					for (int i = 0; i < 5; i++) {
						if(Gdx.input.isTouched(i)) {
							Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
							camera.unproject(touchPos);
							if(touchPos.x > 363 && touchPos.x < 437 && touchPos.y < 100) {
								this.pauseGame();
							}
						}
					}
				}

				if (onscreenTimer >= 0) {
					onscreenTimer -= delta;
				} else {
					// Updates the score modifiers and removes the ones that are finished.
					for (int ii = 0; ii < scoreModifiers.size(); ii++) {
						scoreModifiers.get(ii).update(delta);
						if (scoreModifiers.get(ii).isDone()) {
							scoreModifiers.remove(ii);
							ii--;
						}
					}

					if (scoreModifiers.isEmpty()) isScoreBeingModified = false;
					currentScoreModifier = 1;
					if (isScoreBeingModified) {
						for(int ii = 0; ii < scoreModifiers.size(); ii++){
							currentScoreModifier *= scoreModifiers.get(ii).getModifyingValue();
						}
					}

					// Updates the player, Projectiles, and power ups.
					player.update(delta);
					for (int ii = 0; ii < projectiles.size(); ii++) {
						projectiles.get(ii).update(delta);
					}
					extraLife.update(delta);
					jumpBoost.update(delta);
					scoreModifier.update(delta);

					// Player hits a projectile.
					for (int ii = 0; ii < projectiles.size(); ii++) {
						if (player.getHitBox().overlaps(projectiles.get(ii).getHitBox())) {
							player.setPosition(0, 120);
							projectilesToBeDeleted.add(projectiles.get(ii));
							this.addNewProjectile();
							lives--;
							if (lives == 0) {
								myGame.setScreen(new GameOverScreen(myGame, score));
							}
						}
					}

					// Player gets an extra life.
					if(player.getHitBox().overlaps(extraLife.getHitBox())){
						if (lives < 99) lives++;
						extraLife.reset();
					}
					// Player gets a jump boost.
					if(player.getHitBox().overlaps(jumpBoost.getHitBox())){
						player.setJumpPower(player.getJumpPower()+100);
						player.setBoost(player.getBoost()+5);
						jumpBoost.reset();
					}
					// Player gets a score modifier.
					if(player.getHitBox().overlaps(scoreModifier.getHitBox())) {
						isScoreBeingModified = true;
						this.scoreModifiers.add(new Modifier(2.0f, 21));
						scoreModifier.reset();
					}

					// Projectile reset (hitting the left wall).
					for (int ii = 0; ii < projectiles.size(); ii++) {
						if (projectiles.get(ii).getHitBox().overlaps(projectileResetPoint)) {
							projectilesToBeDeleted.add(projectiles.get(ii));
							this.addNewProjectile();
							score += currentScoreModifier;
						}
					}
					// Creates a new projectile if the last projectile in the array reaches the speed cap.
					if (projectiles.get(projectiles.size()).getSpeed() >= Projectile.getSpeedCap()) {
						this.addNewProjectile();
					}

					if(jumpBoost.getHitBox().overlaps(floor)) jumpBoost.reset();
					if(extraLife.getHitBox().overlaps(floor)) extraLife.reset();
					if(scoreModifier.getHitBox().overlaps(floor)) scoreModifier.reset();

					if (player.getHitBox().overlaps(floor)) {
						player.landOnGround(floor.getY() + floor.getHeight());
					}
					if(player.getHitBox().overlaps(leftWall)){
						player.setPosition(leftWall.getX() + leftWall.getWidth(), player.getHitBox().y);
					}
					if(player.getHitBox().overlaps(rightWall)) {
						if (myGame.getUser().getCurrentSkin().getIdNumber() ==
								myGame.getDefaultSkin().getIdNumber()) {
							player.setPosition(758, player.getHitBox().y);
						} else if (myGame.getUser().getCurrentSkin().getIdNumber() ==
								myGame.getSheepSkin().getIdNumber()) {
							player.setPosition(720, player.getHitBox().y);
						}
					}

					if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
						if (player.getDominantDirection() == 0 || player.getDominantDirection() == 1) {
							player.moveLeft(delta);
						}
					}
					if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)){
						if (player.getDominantDirection() == 0 || player.getDominantDirection() == 1) {
							player.moveRight(delta);
						}
					}
					if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) ||
							Gdx.input.isKeyPressed(Keys.W)){
						player.jump();
					}
					if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
						if (!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT) &&
								!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) {
							player.crouch();
						}
					}

					for (int i = 0; i < 5; i++) {
						if (Gdx.input.isTouched(i)) {
							Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
							camera.unproject(touchPos);
							if (touchPos.x < 100 && touchPos.y < 100){
								if (player.getDominantDirection() == 0 || player.getDominantDirection() == 1) {
									player.moveLeft(Gdx.graphics.getDeltaTime());
								}
							}
							if (touchPos.x > 100 && touchPos.x < 200 && touchPos.y < 100) {
								if (player.getDominantDirection() == 0 || player.getDominantDirection() == 2) {
									player.moveRight(Gdx.graphics.getDeltaTime());
								}
							}
							if(touchPos.x > 700 && touchPos.y < 100){
								player.jump();
							}
							if(touchPos.x > 600 && touchPos.x < 700 && touchPos.y < 100) {
								player.crouch();
							}
						}
					}

					while (projectilesToBeDeleted.size() > 0) {
						projectiles.remove(projectilesToBeDeleted.get(0));
						projectilePool.free(projectilesToBeDeleted.get(0));
						projectilesToBeDeleted.remove(0);
					}
				}
			}
		}
	}

	private void pauseGame() {
		onscreenTimer = 0;
		isPaused = true;
		pauseClickTimer = 0;
	}
	private void unPauseGame() {
		onscreenTimer = 4;
		isPaused = false;
		pauseClickTimer = 0;
	}

	private void addNewProjectile() {
		Projectile p = projectilePool.obtain();
		p.reconstructor();
		this.projectiles.add(p);
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
		TextureManager.disposeAllGameTextures();
	}
}

class ProjectilePool extends Pool<Projectile> {
	protected Projectile newObject() {
		return new Projectile();
	}
}