package com.matthewgarrison.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.matthewgarrison.tools.TextureManager;

public class Button {
	// In progress.
	private Rectangle hitBox;
	private Sprite sprite;

	public Button(float x, float y, float width, float height, int textureID) {
		hitBox = new Rectangle(x, y, width, height);
		sprite = new Sprite(TextureManager.textures[textureID]);
		sprite.setPosition(x, y);
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setSprite(int textureID) {
		sprite.setTexture(TextureManager.textures[textureID]);
	}
}
