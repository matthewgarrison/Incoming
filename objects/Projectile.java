package com.matthewgarrison.objects;

import com.matthewgarrison.GameHandler;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Projectile implements Poolable {
	private Rectangle hitBox;
	private Sprite sprite;
	private int speed;
	private static int speedIncrease;
	private final static int speedCap = 460;

	public Projectile() {
		this.hitBox = new Rectangle(0, 0, 75, 25);
		this.sprite = new Sprite(TextureManager.textures[TextureManager.projectile]);
		this.setPosition(0, 0);
		this.speed = 300;
		speedIncrease = 5;
	}

	public void reconstructor() {
		this.setPosition(900, GameHandler.rand.nextInt(70) + 150);
		if (this.speed + speedIncrease <= speedCap) {
			this.speed += speedIncrease;
		}
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

	public static int getSpeedCap() {
		return speedCap;
	}

	public int getSpeed() {
		return speed;
	}
}