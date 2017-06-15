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
	private int boost;

	private static int standingHitboxHeight, crouchingHitboxHeight, width;

	public MainGuy(int x, int y) {
		hitBox = new Rectangle(x, y, width, 77);
		sprite = new Sprite(TextureManager.mainGuy_texture1Right);
		setPosition(x, y);

		speed = 200;
		gravitySpeed = 25;
		velocityY = 0;
		inAir = false;
		facingLeft = false;
		animationCounter = 0;
		jumpPower = 100;
		boost = 0;

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
			sprite.setTexture(TextureManager.mainGuy_texture3Left);
		} else {
			sprite.setTexture(TextureManager.mainGuy_texture3Right);
		}
	}

	public void moveLeft(float delta) {
		timeSinceMovedLeft = 0;
		setPosition(hitBox.x - speed*delta, hitBox.y);

		// Cycles through 3 animations; if MainGuy's in air, it will turn the sprite the correct way.
		facingLeft = true;
		if (inAir) {
			sprite.setTexture(TextureManager.mainGuy_textureJumpLeft);
		} else {
			animationCounter++;
			if (animationCounter == 0) {
				sprite.setTexture(TextureManager.mainGuy_texture1Left);
			} else if (animationCounter == 5) {
				sprite.setTexture(TextureManager.mainGuy_texture2Left);
			} else if (animationCounter == 10) {
				sprite.setTexture(TextureManager.mainGuy_texture3Left);
			} else if (animationCounter == 15){
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
			sprite.setTexture(TextureManager.mainGuy_textureJumpRight);
		} else {
			animationCounter++;
			if (animationCounter == 0) {
				sprite.setTexture(TextureManager.mainGuy_texture1Right);
			} else if (animationCounter == 5) {
				sprite.setTexture(TextureManager.mainGuy_texture2Right);
			} else if (animationCounter == 10) {
				sprite.setTexture(TextureManager.mainGuy_texture3Right);
			} else if (animationCounter == 15){
				animationCounter = 0;
			}
		}
	}

	public void jump() {
		if (!inAir) {
			inAir = true;

			// The jump boost.
			if (jumpPower > 100) {
				jumpPower -= 20;
			}
			if (boost > 0) {
				boost--;
			}

			velocityY = 10 + boost;

			if (facingLeft) {
				sprite.setTexture(TextureManager.mainGuy_textureJumpLeft);
			} else {
				sprite.setTexture(TextureManager.mainGuy_textureJumpRight);
			}
		}
	}

	public void crouch() {
		if (!inAir) {
			if (facingLeft){
				sprite.setTexture(TextureManager.mainGuy_textureCrouchLeft);
			} else {
				sprite.setTexture(TextureManager.mainGuy_textureCrouchRight);
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

	public Rectangle getHitBox() {
		return hitBox;
	}

	public int getJumpPower() {
		return this.jumpPower;
	}

	public void setJumpPower(int jumpPower) {
		this.jumpPower = jumpPower;
	}

	public int getBoost() {
		return boost;
	}

	public void setBoost(int boost) {
		this.boost = boost;
	}

	public int getDominantDirection() {
		return dominantDirection;
	}
}