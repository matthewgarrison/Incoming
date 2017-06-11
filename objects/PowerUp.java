package net.ocps.tchs.MDGame.objects;

import java.util.Random;

import net.ocps.tchs.MDGame.tools.TextureManager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PowerUp {
	public Rectangle hitBox;
	public Sprite sprite;
	public int speed;

	Random randNumGenerator = new Random(); //randomizes the x-coor and spawn chance
	public int randomSpawn; //the randomly generated number
	public static int spawnChance; //used to change spawn chance in different difficulties

	//initializes
	public PowerUp(int x, int y) {
		hitBox = new Rectangle(x, y, 50, 50); //the hitbox
		sprite = new Sprite(TextureManager.powerUp_extraLife_texture); //the image
		setPosition(x, y);
		speed = 0;
		spawnChance = 1; //how often it will spawn
	}

	public void setPosition(float x, float y) {
		hitBox.x = x;
		hitBox.y = y;
		sprite.setPosition(x,  y);
	}

	public int hits(Rectangle r){
		if(hitBox.overlaps(r)){
			return 1;
		}
		return -1;
	}

	public void reset(float x, float y){
			speed = 0;
			setPosition(randNumGenerator.nextInt(700) + 50, y); //the x coordinate ranges from 50-750
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void update(float delta){
		randomSpawn = randNumGenerator.nextInt(7500 / spawnChance); //can increase spawnChance to increase the spawn chance
		if (randomSpawn == 100) {
			speed = 140; //the power up starts dropping
		}
		hitBox.y -= delta * speed; //gravity
		sprite.setPosition(hitBox.x, hitBox.y);
	}

	//draws the image
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
}
