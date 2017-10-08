package com.matthewgarrison.objects;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.matthewgarrison.enums.TextureEnum;

public class Projectile {
	private Rectangle hitBox;
	private Sprite sprite;
	private int speed;
	private boolean reachedSpeedCap;
	private static int speedIncrease;
	private final static int speedCap = 460;

	public Projectile() {
		this.hitBox = new Rectangle(0, 0, 75, 25);
		this.sprite = new Sprite(TextureManager.textures[TextureEnum.PROJECTILE.ordinal()]);
		this.setPosition(0, 0);
		this.speed = 300;
		this.reachedSpeedCap = false;
	}

	// Returns true if the projectile reached the speed cap.
	public boolean init(int maxHeight) {
		this.setPosition(900, GameHandler.rand.nextInt(maxHeight) + 150);
		if (this.speed + speedIncrease <= speedCap) {
			this.speed += speedIncrease;
			return false;
		} else return true;
	}

	private void setPosition(float x, float y) {
		hitBox.x = x;
		hitBox.y = y;
		sprite.setPosition(x, y);
	}

	public Rectangle getHitBox() {
		return this.hitBox;
	}

	public void update(float delta){
		hitBox.x -= delta * speed;
		sprite.setPosition(hitBox.x, hitBox.y);
	}

	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}

	// Called when the pool reclaims the bullet.
	public void reset() {
	}

	public static void setSpeedIncrease(int n) {
		speedIncrease = n;
	}

	public static int getSpeedIncrease() {
		return speedIncrease;
	}

	public int getSpeed() {
		return speed;
	}

	public boolean getReachedSpeedCap() {
		return reachedSpeedCap;
	}

	public void setReachedSpeedCap(boolean b) {
		reachedSpeedCap = b;
	}
}