package net.ocps.tchs.MDGame.objects;

import java.util.Random;

import net.ocps.tchs.MDGame.tools.TextureManager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Bottle implements Poolable {
	public Rectangle hitBox;
	public Sprite sprite;
	public int speed; //how fast it moves, obviously
	public static int speedIncrease; //how much its speed increases by (this increases as difficulty does)
	Random randNumGenerator = new Random();

	//initializes
	public Bottle(int x, int y) {
		this.hitBox = new Rectangle(x, y, 75, 25); //the hitbox
		this.sprite = new Sprite(TextureManager.bottle_Texture); //the image
		this.setPosition(x, y);
		this.speed = 300; //base speed
	}
	public void reconstructor(int x, int y, int speed) {
		this.setPosition(x, y);
		this.speed = speed;
		System.out.println(speed + " " + this.speed);
	}

	public void setPosition(float x, float y) {
		hitBox.x = x;
		hitBox.y = y;
		sprite.setPosition(x, y);
	}

	public int hits(Rectangle r){
		if (this.hitBox.overlaps(r)) {
			return 1;
		}
		return -1;
	}
	
	public Rectangle getHitBox() {
		return this.hitBox;
	}

	public void reset(float x, float y){
		this.setPosition(900, randNumGenerator.nextInt(70) + 135);
		if (this.speed < 460) { //caps at a speed of 450-460, when MyGdxGame spawns a new one
			this.speed += speedIncrease;
		}
	}

	public void update(float delta){
		hitBox.x -= delta * speed; //movement
		sprite.setPosition(hitBox.x, hitBox.y);	 
	}

	//draws the image
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}

	//called when the pool reclaims the bullet
	public void reset() {
	}
}