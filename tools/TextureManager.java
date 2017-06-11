package net.ocps.tchs.MDGame.tools;

import net.ocps.tchs.MDGame.GameHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

//for the images
public class TextureManager {
	//the screens
	public static Texture splashScreen_texture; //the splash
	public static final int splashScreen_value = 1;
	public static Texture mainMenuScreen_texture; //main menu
	public static final int mainMenuScreen_value = 2;
	public static Texture mainGameScreen_texture; //game background
	public static final int mainGameScreen_value = 3;
	public static Texture gameOverScreen_texture; //the game over screen
	public static final int gameOverScreen_value = 5;
	public static Texture optionsScreenEasy_texture; //the options screen, when in easy mode
	public static Texture optionsScreenMedium_texture; //options screen, medium mode
	public static Texture optionsScreenHard_texture; //options screen, hard mode
	public static final int optionsScreens_value = 6;
	public static Texture leadersboardsScreen_texture; //leadersboard (local, not thru google)
	public static final int leadersboardsScreen_value = 7;
	
	//pause menu
	public static Texture pauseMenu_texture;
	public static final int pauseMenu_value = 4;

	//mainguy
	public static Texture mainGuy_texture1Right; //right-facing image, #1
	public static Texture mainGuy_texture2Right; //#2
	public static Texture mainGuy_texture3Right; //#3
	public static Texture mainGuy_texture1Left; //left-facing image, #1
	public static Texture mainGuy_texture2Left; //#2
	public static Texture mainGuy_texture3Left; //#3
	public static Texture mainGuy_textureCrouchRight; //crouching, facing right
	public static Texture mainGuy_textureCrouchLeft; //crouching, facing left
	public static Texture mainGuy_textureJumpRight; //jumping, facing right
	public static Texture mainGuy_textureJumpLeft; //jumping, facing left
	public static final int mainGuy_value = 8;

	//bottle
	public static Texture bottle_Texture; //the bottle (also, pitchfork)
	public static final int bottle_value = 9;

	//power ups
	public static Texture powerUp_extraLife_texture; //extra life
	public static Texture powerUp_scoreModTwo_texture; //2x score modifier
	public static Texture powerUp_jumpBoost_texture; //jump boost
	public static final int powerUps_value = 10;

	//buttons
	public static Texture onScreenLeftButton_texture; //left input button
	public static Texture onScreenRightButton_texture; //right button
	public static Texture onScreenUpButton_texture; //up
	public static Texture onScreenDownButton_texture; //down
	public static Texture onScreenPauseButton_texture; //pause button
	public static Texture onScreenPlayButton_texture; //play button
	public static final int buttons_value = 11;

	public static Texture Easy; //easy is not selected
	public static Texture DarkEasy; //easy is selected
	public static Texture Medium;
	public static Texture DarkMedium;
	public static Texture Hard; 
	public static Texture DarkHard;
	public static Texture Normal;
	public static Texture DarkNormal;
	public static Texture Sheep;
	public static Texture DarkSheep;
	public static final int labels_value = 12;

	//on-screen count down for game start and unpause
	public static Texture gameStartUnPause3; //a 3, displayed when the game is starting or unpausing
	public static Texture gameStartUnPause2; //a 2
	public static Texture gameStartUnPause1; //a 1
	public static Texture gameStartUnPauseGo; //a "GO"
	public static final int countDown_value = 13;

	public static Texture cheatIcon; //displayed on the game over screen when you used the "cheat inapp purchases" method
	public static final int cheatIcon_value = 14;

	public static void loadAllGameTextures(GameHandler myGame) {
		load(mainGameScreen_value, myGame);
		load(pauseMenu_value, myGame);
		load(mainGuy_value, myGame);
		load(bottle_value, myGame);
		load(powerUps_value, myGame);
		load(buttons_value, myGame);
		load(countDown_value, myGame);
		load(labels_value, myGame);
	}
	public static void disposeAllGameTextures() {
		dispose(mainGameScreen_value);
		dispose(pauseMenu_value);
		dispose(mainGuy_value);
		dispose(bottle_value);
		dispose(powerUps_value);
		dispose(buttons_value);
		dispose(countDown_value);
		dispose(labels_value);
	}

	public static void load(int i, GameHandler myGame) {
		switch (i) {
		case splashScreen_value: 
			splashScreen_texture = new Texture(Gdx.files.internal("SplashScreen.png"));
			break;
		case pauseMenu_value:
			pauseMenu_texture = new Texture(Gdx.files.internal("PauseMenu.png"));
			break;
		case powerUps_value:
			powerUp_extraLife_texture = new Texture(Gdx.files.internal("PowerUps/extraLifeIcon.png")); 
			powerUp_scoreModTwo_texture = new Texture(Gdx.files.internal("PowerUps/scoreModifierIcon.png")); 
			powerUp_jumpBoost_texture = new Texture(Gdx.files.internal("PowerUps/jumpBoostIcon.png")); 
			break;
		case buttons_value:
			onScreenLeftButton_texture = new Texture(Gdx.files.internal("Buttons/OnScreenLeft.png"));
			onScreenRightButton_texture = new Texture(Gdx.files.internal("Buttons/OnScreenRight.png"));
			onScreenUpButton_texture = new Texture(Gdx.files.internal("Buttons/OnScreenUp.png"));
			onScreenDownButton_texture = new Texture(Gdx.files.internal("Buttons/OnScreenDown.png"));
			onScreenPauseButton_texture = new Texture(Gdx.files.internal("Buttons/OnScreenPause.png"));
			onScreenPlayButton_texture = new Texture(Gdx.files.internal("Buttons/OnScreenPlay.png"));
			break;
		case labels_value:
			Easy = new Texture(Gdx.files.internal("Labels/Easy.png"));
			DarkEasy = new Texture(Gdx.files.internal("Labels/EasyDarkened.png"));
			Medium = new Texture(Gdx.files.internal("Labels/Medium.png"));
			DarkMedium = new Texture(Gdx.files.internal("Labels/MediumDarkened.png"));
			Hard = new Texture(Gdx.files.internal("Labels/Hard.png"));
			DarkHard = new Texture(Gdx.files.internal("Labels/HardDarkened.png"));
			Normal = new Texture(Gdx.files.internal("Labels/Normal.png"));
			DarkNormal = new Texture(Gdx.files.internal("Labels/NormalDarkened.png"));
			Sheep = new Texture(Gdx.files.internal("Labels/Sheep.png"));
			DarkSheep = new Texture(Gdx.files.internal("Labels/SheepDarkened.png"));
			break;
		case countDown_value:
			gameStartUnPause3 = new Texture(Gdx.files.internal("Labels/GSUP_3.png"));
			gameStartUnPause2 = new Texture(Gdx.files.internal("Labels/GSUP_2.png"));
			gameStartUnPause1 = new Texture(Gdx.files.internal("Labels/GSUP_1.png"));
			gameStartUnPauseGo = new Texture(Gdx.files.internal("Labels/GSUP_Go.png"));
			break;
		case cheatIcon_value:
			cheatIcon = new Texture(Gdx.files.internal("cheat.png"));
			break;
		}
		
		if (myGame.user.currentSkin.idNumber == myGame.defaultSkin.idNumber) {
			switch(i) {
			case mainMenuScreen_value:
				mainMenuScreen_texture = new Texture(Gdx.files.internal("NormalMode/Screens/MainMenu.png"));
				break;
			case mainGameScreen_value:
				mainGameScreen_texture = new Texture(Gdx.files.internal("NormalMode/Screens/MainGame.png"));
				break;
			case gameOverScreen_value:
				gameOverScreen_texture = new Texture(Gdx.files.internal("NormalMode/Screens/GameOver.png"));
				break;
			case optionsScreens_value:
				optionsScreenEasy_texture = new Texture(Gdx.files.internal("NormalMode/Screens/OptionsEasy.png"));
				optionsScreenMedium_texture = new Texture(Gdx.files.internal("NormalMode/Screens/OptionsMedium.png"));
				optionsScreenHard_texture = new Texture(Gdx.files.internal("NormalMode/Screens/OptionsHard.png"));
				break;
			case leadersboardsScreen_value:
				leadersboardsScreen_texture = new Texture(Gdx.files.internal("NormalMode/Screens/Leaderboards.png"));
				break;
			case mainGuy_value:
				mainGuy_texture1Left = new Texture(Gdx.files.internal("NormalMode/MainGuy/Guy1Left.png"));
				mainGuy_texture2Left = new Texture(Gdx.files.internal("NormalMode/MainGuy/Guy2Left.png"));
				mainGuy_texture3Left = new Texture(Gdx.files.internal("NormalMode/MainGuy/Guy3Left.png"));
				mainGuy_texture1Right = new Texture(Gdx.files.internal("NormalMode/MainGuy/Guy1Right.png"));
				mainGuy_texture2Right = new Texture(Gdx.files.internal("NormalMode/MainGuy/Guy2Right.png"));
				mainGuy_texture3Right = new Texture(Gdx.files.internal("NormalMode/MainGuy/Guy3Right.png"));
				mainGuy_textureCrouchLeft = new Texture(Gdx.files.internal("NormalMode/MainGuy/GuyCrouchLeft.png"));
				mainGuy_textureCrouchRight = new Texture(Gdx.files.internal("NormalMode/MainGuy/GuyCrouchRight.png"));
				mainGuy_textureJumpLeft = new Texture(Gdx.files.internal("NormalMode/MainGuy/GuyJumpLeft.png"));
				mainGuy_textureJumpRight = new Texture(Gdx.files.internal("NormalMode/MainGuy/GuyJumpRight.png"));
				break;
			case bottle_value:
				bottle_Texture = new Texture(Gdx.files.internal("NormalMode/Bottle.png"));
				break;
			}
		} else if (myGame.user.currentSkin.idNumber == myGame.sheepSkin.idNumber){
			switch(i) {
			case mainMenuScreen_value:
				mainMenuScreen_texture = new Texture(Gdx.files.internal("SheepMode/Screens/MainMenu.png"));
				break;
			case mainGameScreen_value:
				mainGameScreen_texture = new Texture(Gdx.files.internal("SheepMode/Screens/MainGame.png"));
				break;
			case gameOverScreen_value:
				gameOverScreen_texture = new Texture(Gdx.files.internal("SheepMode/Screens/GameOver.png"));
				break;
			case optionsScreens_value:
				optionsScreenEasy_texture = new Texture(Gdx.files.internal("SheepMode/Screens/OptionsEasy.png"));
				optionsScreenMedium_texture = new Texture(Gdx.files.internal("SheepMode/Screens/OptionsMedium.png"));
				optionsScreenHard_texture = new Texture(Gdx.files.internal("SheepMode/Screens/OptionsHard.png"));
				break;
			case leadersboardsScreen_value:
				leadersboardsScreen_texture = new Texture(Gdx.files.internal("SheepMode/Screens/Leaderboards.png"));
				break;
			case mainGuy_value:
				mainGuy_texture1Left = new Texture(Gdx.files.internal("SheepMode/MainGuy/Guy1Left.png"));
				mainGuy_texture2Left = new Texture(Gdx.files.internal("SheepMode/MainGuy/Guy2Left.png"));
				mainGuy_texture3Left = new Texture(Gdx.files.internal("SheepMode/MainGuy/Guy3Left.png"));
				mainGuy_texture1Right = new Texture(Gdx.files.internal("SheepMode/MainGuy/Guy1Right.png"));
				mainGuy_texture2Right = new Texture(Gdx.files.internal("SheepMode/MainGuy/Guy2Right.png"));
				mainGuy_texture3Right = new Texture(Gdx.files.internal("SheepMode/MainGuy/Guy3Right.png"));
				mainGuy_textureCrouchLeft = new Texture(Gdx.files.internal("SheepMode/MainGuy/GuyCrouchLeft.png"));
				mainGuy_textureCrouchRight = new Texture(Gdx.files.internal("SheepMode/MainGuy/GuyCrouchRight.png"));
				mainGuy_textureJumpLeft = new Texture(Gdx.files.internal("SheepMode/MainGuy/GuyJumpLeft.png"));
				mainGuy_textureJumpRight = new Texture(Gdx.files.internal("SheepMode/MainGuy/GuyJumpRight.png"));
				break;
			case bottle_value:
				bottle_Texture = new Texture(Gdx.files.internal("SheepMode/Pitchfork.png"));
				break;
			}
		}
	}
	public static void dispose(int i) {
		switch(i) {
		case splashScreen_value:
			splashScreen_texture.dispose();
			break;
		case mainMenuScreen_value: 
			mainMenuScreen_texture.dispose();
			break;
		case mainGameScreen_value:
			mainGameScreen_texture.dispose();
			break;
		case pauseMenu_value:
			pauseMenu_texture.dispose();
			break;
		case gameOverScreen_value:
			gameOverScreen_texture.dispose();
			break;
		case optionsScreens_value:
			optionsScreenEasy_texture.dispose();
			optionsScreenMedium_texture.dispose();
			optionsScreenHard_texture.dispose();
			break;
		case leadersboardsScreen_value:
			leadersboardsScreen_texture.dispose();
			break;
		case mainGuy_value:
			mainGuy_texture1Right.dispose();
			mainGuy_texture2Right.dispose();
			mainGuy_texture3Right.dispose();
			mainGuy_texture1Left.dispose();
			mainGuy_texture2Left.dispose();
			mainGuy_texture3Left.dispose();
			mainGuy_textureCrouchLeft.dispose();
			mainGuy_textureCrouchRight.dispose();
			mainGuy_textureJumpLeft.dispose();
			mainGuy_textureJumpRight.dispose();
			break;
		case bottle_value:
			bottle_Texture.dispose();
			break;
		case powerUps_value:
			powerUp_extraLife_texture.dispose();
			powerUp_scoreModTwo_texture.dispose();
			powerUp_jumpBoost_texture.dispose();
			break;
		case buttons_value:
			onScreenLeftButton_texture.dispose();
			onScreenRightButton_texture.dispose();
			onScreenUpButton_texture.dispose();
			onScreenDownButton_texture.dispose();
			onScreenPauseButton_texture.dispose();
			onScreenPlayButton_texture.dispose();
			break;
		case labels_value:
			Easy.dispose();
			DarkEasy.dispose();
			Medium.dispose();
			DarkMedium.dispose();
			Hard.dispose(); 
			DarkHard.dispose();
			Normal.dispose();
			DarkNormal.dispose();
			Sheep.dispose();
			DarkSheep.dispose();
			break;
		case countDown_value:
			gameStartUnPause3.dispose();
			gameStartUnPause2.dispose();
			gameStartUnPause1.dispose();
			gameStartUnPauseGo.dispose();
			break;
		case cheatIcon_value:
			cheatIcon.dispose();
			break;
		}
	}
}