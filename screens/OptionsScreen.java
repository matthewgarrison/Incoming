package net.ocps.tchs.MDGame.screens;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import net.ocps.tchs.MDGame.GameHandler;
import net.ocps.tchs.MDGame.tools.TextureManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class OptionsScreen implements Screen {

	GameHandler myGame;
	OrthographicCamera camera;
	Vector3 touchPos;
	SpriteBatch batch;
	public float canNowActOnThisScreenTimer;

	//NOTE: things in the constructor we want created once, like objects. Things in show() we want recreated or reset
	//every time the screen is viewed
	//constructor (show is then called after this, by setScreen())
	public OptionsScreen (GameHandler g) {
		this.myGame = g;
	}

	//called by the setScreen() method
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		TextureManager.load(TextureManager.optionsScreens_value, myGame);
		canNowActOnThisScreenTimer = 0;
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		switch (myGame.user.currentDifficulty) {
		case 1:
			batch.draw(TextureManager.optionsScreenEasy_texture, 0, 0);
			break;
		case 2:
			batch.draw(TextureManager.optionsScreenMedium_texture, 0, 0);
			break;
		case 3:
			batch.draw(TextureManager.optionsScreenHard_texture, 0, 0);
			break;
		}

		switch (myGame.user.currentSkin.idNumber) {
		case 1:
			batch.draw(TextureManager.Sheep, 450, 210);
			batch.draw(TextureManager.DarkNormal, 186, 210);
			break;
		case 2:
			batch.draw(TextureManager.DarkSheep, 450, 210);
			batch.draw(TextureManager.Normal, 186, 210);
			break;
		}
		batch.end();

		//displays ads
		if (myGame.user.adsShown) {
			myGame.showAds();
		}
		if (canNowActOnThisScreenTimer > 0.15) {
			for (int i = 0; i < 5; i++) {
				if(Gdx.input.isTouched(i)){
					touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
					camera.unproject(touchPos);

					//returns to the menu
					if(touchPos.x < 136 && touchPos.x > 26 && touchPos.y > 144 && touchPos.y < 190){
						myGame.setScreen(new MainMenuScreen(myGame));
					}

					//used to change difficulty and skin
					BufferedWriter out = null;	
					if(touchPos.x < 291 && touchPos.x > 181 && touchPos.y > 276 && touchPos.y < 330) {
						try {
							out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external("MDGames/Incoming/difficulty.in").write(false)));
							out.write("1");
							out.close();
						} catch (GdxRuntimeException ex) {
							Gdx.app.log("ERROR", ex.getMessage());
						} catch (IOException e) {
							Gdx.app.log("ERROR", e.getMessage());
						} finally {
							if (out != null) {
								try {
									out.close();
								} catch (IOException e) {
								}
							}
						}
						myGame.user.currentDifficulty = 1;
					}
					if(touchPos.x < 490 && touchPos.x > 321 && touchPos.y > 276 && touchPos.y < 330) {
						try {
							out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external("MDGames/Incoming/difficulty.in").write(false)));
							out.write("2");
							out.close();
						} catch (GdxRuntimeException ex) {
							Gdx.app.log("ERROR", ex.getMessage());
						} catch (IOException e) {
							Gdx.app.log("ERROR", e.getMessage());
						} finally {
							if (out != null) {
								try {
									out.close();
								} catch (IOException e) {
								}
							}
						}
						myGame.user.currentDifficulty = 2;
					}
					if(touchPos.x < 629 && touchPos.x > 515 && touchPos.y > 276 && touchPos.y < 330) {
						try {
							out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external("MDGames/Incoming/difficulty.in").write(false)));
							out.write("3");
							out.close();
						} catch (GdxRuntimeException ex) {
							Gdx.app.log("ERROR", ex.getMessage());
						} catch (IOException e) {
							Gdx.app.log("ERROR", e.getMessage());
						} finally {
							if (out != null) {
								try {
									out.close();
								} catch (IOException e) {
								}
							}
						}
						myGame.user.currentDifficulty = 3;
					}

					if (touchPos.x < 350 && touchPos.x > 186 && touchPos.y < 270 && touchPos.y > 210) {
						try {
							out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local("MDGames/Incoming/whichSkin.in").write(false)));
							out.write("1");
							out.close();
						} catch (GdxRuntimeException ex) {
							Gdx.app.log("ERROR", ex.getMessage());
						} catch (IOException e) {
							Gdx.app.log("ERROR", e.getMessage());
						} finally {
							if (out != null) {
								try {
									out.close();
								} catch (IOException e) {
								}
							}
						}
						myGame.user.currentSkin = myGame.defaultSkin;
						myGame.SwitchToDefaultSkin();
					}
					if (touchPos.x < 614 && touchPos.x > 450 && touchPos.y < 270 && touchPos.y > 210) {
						try {
							out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local("MDGames/Incoming/whichSkin.in").write(false)));
							out.write("2");
							out.close();
						} catch (GdxRuntimeException ex) {
							Gdx.app.log("ERROR", ex.getMessage());
						} catch (IOException e) {
							Gdx.app.log("ERROR", e.getMessage());
						} finally {
							if (out != null) {
								try {
									out.close();
								} catch (IOException e) {
								}
							}
						}
						myGame.user.currentSkin = myGame.sheepSkin;
						myGame.SwitchToSheepSkin();
					}
				}
			}
		} else {
			canNowActOnThisScreenTimer += Gdx.graphics.getDeltaTime();
		}
	}

	public void resize(int width, int height) {

	}

	//called when you switch to another screen
	public void hide() {
		this.dispose();
	}

	//called when you open another app or go to the homescreen
	public void pause() {

	}

	//return from the app from pause
	public void resume() {

	}

	//IS NOT automatically called when the screen switches
	public void dispose() {
		batch.dispose();
		TextureManager.dispose(TextureManager.optionsScreens_value);
	}

}
