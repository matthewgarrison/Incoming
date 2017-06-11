package net.ocps.tchs.MDGame.objects;

import net.ocps.tchs.MDGame.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainGuy {
	public Rectangle hitBox;
	public Sprite sprite;

	public int speed; //how fast he moves left-right
	public int gravitySpeed; //how strong gravity is

	public float VelocityY; //veritcal velocity
	public boolean inAir; //is he in the air?

	public boolean facingLeft;
	public int animation; //used to create the walking animation
	public float timeSinceMovedLeft;
	public float timeSinceMovedRight;
	public int dominantDirection; //0 = not set, 1 = left, 2 = right

	public int jumpPower; //the displayed value
	public int boost; //how much jump is boosted by

	public static int standingHitbox; //how tall he is while standing
	public static int crouchingHitbox; //while crouching
	public static int width; //how wide he is

	//initializes
	public MainGuy(int x, int y) {
		hitBox = new Rectangle(x, y, width, 77); //the hitbox
		sprite = new Sprite(TextureManager.mainGuy_texture1Right); //the image
		setPosition(x, y);

		speed = 200;
		gravitySpeed = 25;
		VelocityY = 0;
		inAir = false;
		facingLeft = false;
		animation = 0;
		jumpPower = 100;
		boost = 0;

		timeSinceMovedLeft = 0;
		timeSinceMovedRight = 0;
		dominantDirection = 0;
	}

	//collisions
	public int hits(Rectangle r) {
		if(hitBox.overlaps(r)) {
			return 1;
		}
		return -1;
	}

	//used to perform several different actions
	public void landOnGround(float x, float y){
		hitBox.height = standingHitbox; //how tall he normally is
		setPosition(hitBox.x, y);
		VelocityY = 0; //zeroes the y velocity
		inAir = false; //allows you to jump again

		//turns the sprite the right way
		if(facingLeft == true){
			sprite.setTexture(TextureManager.mainGuy_texture3Left);
		}
		else if(facingLeft == false){
			sprite.setTexture(TextureManager.mainGuy_texture3Right);
		}
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	//moves mainguy left
	public void moveLeft(float delta) {
		timeSinceMovedLeft = 0;
		setPosition(hitBox.x - speed*delta, hitBox.y); //x = x - (speed * time elapsed)

		//animation
		facingLeft = true;
		//cycles through 3 animations; if mainGuy's in air, it will turn the sprite the correct way
		if (inAir == false) {
			animation++;
			if (animation < 5) {
				sprite.setTexture(TextureManager.mainGuy_texture1Left);
			}
			if (animation >= 5 && animation < 10) {
				sprite.setTexture(TextureManager.mainGuy_texture2Left);
			}
			if (animation >= 10) {
				sprite.setTexture(TextureManager.mainGuy_texture3Left);
				if (animation == 15) {
					animation = 0;
				}
			}
		} else {
			sprite.setTexture(TextureManager.mainGuy_textureJumpLeft);
		}
	}

	//moves him right
	public void moveRight(float delta) {
		timeSinceMovedRight = 0;
		setPosition(hitBox.x + speed*delta, hitBox.y); //x = x + (speed * time elapsed)

		//animation
		facingLeft = false;
		if (inAir == false) {
			animation += 1;
			if(animation < 5) {
				sprite.setTexture(TextureManager.mainGuy_texture1Right);
			}
			if(animation >= 5 && animation < 10) {
				sprite.setTexture(TextureManager.mainGuy_texture2Right);
			}
			if(animation >= 10) {
				sprite.setTexture(TextureManager.mainGuy_texture3Right);
				if(animation == 15){
					animation = 0;
				}
			}
		} else {
			sprite.setTexture(TextureManager.mainGuy_textureJumpRight);
		}
	}

	//jumps
	public void Jump() {
		if (inAir == false) {
			inAir = true;
			//the jump boost
			if (jumpPower > 100) {
				jumpPower -= 20;
			}
			VelocityY = 10 + boost; //adds speed to your y-velocity
			//subtracts from the boost amount
			if (boost > 0) {
				boost--;
			}
			//which jump picture to use
			if(facingLeft == true){
				sprite.setTexture(TextureManager.mainGuy_textureJumpLeft);
			}
			else if(facingLeft == false){
				sprite.setTexture(TextureManager.mainGuy_textureJumpRight);
			}
		}
	}

	public void Crouch() {
		if (inAir == false) {
			//checks for and uses the correctly facing image
			if(facingLeft == true){
				sprite.setTexture(TextureManager.mainGuy_textureCrouchLeft);
			}
			else if(facingLeft == false){
				sprite.setTexture(TextureManager.mainGuy_textureCrouchRight);
			}
			hitBox.height = crouchingHitbox; //sets the height to the correct, smaller number
		}
	}

	//sets the new position
	public void setPosition(float x, float y){
		hitBox.x = x;
		hitBox.y = y;
		sprite.setPosition(x, y);
	}

	public void update(float delta){
		VelocityY -= (gravitySpeed * delta); //drops by (gravitySpeed * time elapsed)
		hitBox.y += VelocityY;
		sprite.setPosition(hitBox.x, hitBox.y);

		timeSinceMovedLeft += delta;
		timeSinceMovedRight += delta;
		
		if ((Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) && 
				(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) && dominantDirection == 0) {
			if (this.timeSinceMovedLeft > this.timeSinceMovedRight) {
				dominantDirection = 1;
			}
			if (this.timeSinceMovedRight > this.timeSinceMovedLeft) {
				dominantDirection = 2;
			}
		} else if ((Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) && 
				(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) && dominantDirection != 0) {
			//empty, because there's still two inputs, but dominant direction has been set
		} else {
			//0 or 1 directions is pressed
			dominantDirection = 0;
		}
	}

	//draws the image
	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
}