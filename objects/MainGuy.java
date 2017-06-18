package com.matthewgarrison.objects;

import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainGuy {
	private Rectangle hitBox;
	private Sprite sprite;

	private int speed, gravitySpeed;
	private float velocityY;
	private boolean inAir;

	private boolean facingLeft;
	private float timeSinceMovedLeft, timeSinceMovedRight;
	private int animationCounter, dominantDirection;
	private final int DD_NOTSET = 0, DD_LEFT = 1, DD_RIGHT = 2;

	private int jumpPower;
	public final static int MAX_JUMP = 20, MIN_JUMP = 10;

	private static int standingHitboxHeight, crouchingHitboxHeight, width;

	public MainGuy(int x, int y) {
		hitBox = new Rectangle(x, y, width, 77);
		sprite = new Sprite(TextureManager.textures[TextureManager.mainGuy1Right]);
		setPosition(x, y);

		speed = 200;
		gravitySpeed = 25;
		velocityY = 0;
		inAir = false;
		facingLeft = false;
		animationCounter = 0;
		jumpPower = 10;

		timeSinceMovedLeft = 0;
		timeSinceMovedRight = 0;
		dominantDirection = DD_NOTSET;
	}

	public void landOnGround(float y){
		hitBox.height = standingHitboxHeight;
		setPosition(hitBox.x, y);
		velocityY = 0;
		inAir = false;

		if (facingLeft) {
			sprite.setTexture(TextureManager.textures[TextureManager.mainGuy3Left]);
		} else {
			sprite.setTexture(TextureManager.textures[TextureManager.mainGuy3Right]);
		}
	}

	public void moveLeft(float delta) {
		timeSinceMovedLeft = 0;
		setPosition(hitBox.x - speed*delta, hitBox.y);

		// Cycles through 3 animations; if MainGuy's in air, it will turn the sprite the correct way.
		facingLeft = true;
		if (inAir) {
			sprite.setTexture(TextureManager.textures[TextureManager.mainGuyJumpLeft]);
		} else {
			animationCounter++;
			if (animationCounter < 10) {
				sprite.setTexture(TextureManager.textures[TextureManager.mainGuy1Left]);
			} else if (animationCounter < 20) {
				sprite.setTexture(TextureManager.textures[TextureManager.mainGuy2Left]);
			} else if (animationCounter < 30) {
				sprite.setTexture(TextureManager.textures[TextureManager.mainGuy3Left]);
			} else if (animationCounter < 40){
				animationCounter = 0;
			}
		}
	}

	public void moveRight(float delta) {
		timeSinceMovedRight = 0;
		setPosition(hitBox.x + speed*delta, hitBox.y);

		// Cycles through 3 animations; if MainGuy's in air, it will turn the sprite the correct way.
		facingLeft = false;
		if (inAir) {
			sprite.setTexture(TextureManager.textures[TextureManager.mainGuyJumpRight]);
		} else {
			animationCounter++;
			if (animationCounter < 7) {
				sprite.setTexture(TextureManager.textures[TextureManager.mainGuy1Right]);
			} else if (animationCounter < 14) {
				sprite.setTexture(TextureManager.textures[TextureManager.mainGuy2Right]);
			} else if (animationCounter < 21) {
				sprite.setTexture(TextureManager.textures[TextureManager.mainGuy3Right]);
			} else if (animationCounter < 28) {
				animationCounter = 0;
			}
		}
	}

	public void jump() {
		if (!inAir) {
			inAir = true;

			// The jump boost.
			if (jumpPower > 10) {
				jumpPower--;
			}

			velocityY = jumpPower;

			if (facingLeft) {
				sprite.setTexture(TextureManager.textures[TextureManager.mainGuyJumpLeft]);
			} else {
				sprite.setTexture(TextureManager.textures[TextureManager.mainGuyJumpRight]);
			}
		}
	}

	public void crouch() {
		if (!inAir) {
			if (facingLeft){
				sprite.setTexture(TextureManager.textures[TextureManager.mainGuyCrouchLeft]);
			} else {
				sprite.setTexture(TextureManager.textures[TextureManager.mainGuyCrouchRight]);
			}
			hitBox.height = crouchingHitboxHeight;
		}
	}

	public void setPosition(float x, float y){
		hitBox.setPosition(x, y);
		sprite.setPosition(x, y);
	}

	public void update(float delta){
		velocityY -= (gravitySpeed * delta);
		hitBox.y += velocityY;
		sprite.setPosition(hitBox.x, hitBox.y);

		timeSinceMovedLeft += delta;
		timeSinceMovedRight += delta;

		if ((Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) &&
				(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))) {
			if (dominantDirection == DD_NOTSET) {
				if (this.timeSinceMovedLeft > this.timeSinceMovedRight) {
					dominantDirection = DD_LEFT;
				} else if (this.timeSinceMovedRight > this.timeSinceMovedLeft) {
					dominantDirection = DD_RIGHT;
				}
			}
		} else {
			dominantDirection = DD_NOTSET;
		}
	}

	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}

	public static void switchToSheepSkin() {
		MainGuy.standingHitboxHeight = 56;
		MainGuy.crouchingHitboxHeight = 44;
		MainGuy.width = 80;
	}

	public static void switchToDefaultSkin() {
		MainGuy.standingHitboxHeight = 77;
		MainGuy.crouchingHitboxHeight = 65;
		MainGuy.width = 42;
	}

	public void addJumpBoost(int addition) {
		if (jumpPower + addition <= MAX_JUMP) jumpPower += addition;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public int getJumpPower() {
		return this.jumpPower;
	}

	public void setJumpPower(int jumpPower) {
		this.jumpPower = jumpPower;
	}

	public int getDominantDirection() {
		return dominantDirection;
	}
}