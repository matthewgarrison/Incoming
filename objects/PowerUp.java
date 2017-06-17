package com.matthewgarrison.objects;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.matthewgarrison.GameHandler;
import com.matthewgarrison.tools.TextureManager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PowerUp {
	private Rectangle hitBox;
	private Sprite sprite;
	private int speed;
	private static int spawnChance = 1;

	public PowerUp(int textureID) {
		hitBox = new Rectangle(0, 0, 50, 50);
		sprite = new Sprite(TextureManager.textures[textureID]);
		reset();
	}

	private void setPosition(float x, float y) {
		hitBox.setPosition(x, y);
		sprite.setPosition(x,  y);
	}

	public void reset() {
		speed = 0;
		setPosition(GameHandler.rand.nextInt(700) + 50, GameHandler.SCREEN_HEIGHT);
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void update(float delta){
		int randomSpawn = GameHandler.rand.nextInt(7500 / spawnChance);
		// The powerup starts dropping.
		if (randomSpawn == 0) speed = 140;
		hitBox.y -= delta * speed;
		sprite.setPosition(hitBox.x, hitBox.y);
	}

	public static void setSpawnChance(int n) {
		spawnChance = n;
	}

	//draws the image
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
}
