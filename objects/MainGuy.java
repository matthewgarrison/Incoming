package com.matthewgarrison.objects;

import com.matthewgarrison.enums.DirectionEnum;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.matthewgarrison.enums.TextureEnum;

public class MainGuy {
	private Rectangle hitBox;
	private Sprite sprite;

	private int speed, gravitySpeed;
	private float velocityY;
	private boolean inAir;

	private boolean facingLeft;
	private float timeSinceMovedLeft, timeSinceMovedRight;
	private int animationCounter;
	private DirectionEnum dominantDirection;

	private int jumpPower;
	public final static int MAX_JUMP = 20, MIN_JUMP = 10;

	private static int standingHitboxHeight, crouchingHitboxHeight, width;

	public MainGuy(int x, int y) {
		hitBox = new Rectangle(x, y, width, 77);
		sprite = new Sprite(TextureManager.textures[TextureEnum.MAINGUY_RIGHT_1.ordinal()]);
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
		dominantDirection = DirectionEnum.NOT_SET;
	}

	public void landOnGround(float y){
		hitBox.height = standingHitboxHeight;
		setPosition(hitBox.x, y);
		velocityY = 0;
		inAir = false;

		if (facingLeft) {
			sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_LEFT_3.ordinal()]);
		} else {
			sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_RIGHT_3.ordinal()]);
		}
	}

	public void moveLeft(float delta) {
		timeSinceMovedLeft = 0;
		setPosition(hitBox.x - speed*delta, hitBox.y);

		// Cycles through 3 animations; if MainGuy's in air, it will turn the sprite the correct way.
		facingLeft = true;
		if (inAir) {
			sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_LEFT_JUMP.ordinal()]);
		} else {
			animationCounter++;
			if (animationCounter < 7) {
				sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_LEFT_1.ordinal()]);
			} else if (animationCounter < 14) {
				sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_LEFT_2.ordinal()]);
			} else if (animationCounter < 21) {
				sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_LEFT_3.ordinal()]);
			} else if (animationCounter < 28){
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
			sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_RIGHT_JUMP.ordinal()]);
		} else {
			animationCounter++;
			if (animationCounter < 7) {
				sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_RIGHT_1.ordinal()]);
			} else if (animationCounter < 14) {
				sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_RIGHT_2.ordinal()]);
			} else if (animationCounter < 21) {
				sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_RIGHT_3.ordinal()]);
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
				sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_LEFT_JUMP.ordinal()]);
			} else {
				sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_RIGHT_JUMP.ordinal()]);
			}
		}
	}

	public void crouch() {
		if (!inAir) {
			if (facingLeft){
				sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_LEFT_CROUCH.ordinal()]);
			} else {
				sprite.setTexture(TextureManager.textures[TextureEnum.MAINGUY_RIGHT_CROUCH.ordinal()]);
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
			if (dominantDirection == DirectionEnum.NOT_SET) {
				if (this.timeSinceMovedLeft > this.timeSinceMovedRight) {
					dominantDirection = DirectionEnum.LEFT;
				} else if (this.timeSinceMovedRight > this.timeSinceMovedLeft) {
					dominantDirection = DirectionEnum.RIGHT;
				}
			}
		} else {
			dominantDirection = DirectionEnum.NOT_SET;
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

	public DirectionEnum getDominantDirection() {
		return dominantDirection;
	}

	public int getStandingHitboxHeight() {
		return standingHitboxHeight;
	}
}