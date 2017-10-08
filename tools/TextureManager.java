package com.matthewgarrison.tools;

import com.matthewgarrison.GameHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.matthewgarrison.enums.Skin;

public class TextureManager {
	public static Texture[] textures;

	public static final int splashScreen = 0, mainMenuScreen = 1, mainGameScreen = 2,
		gameOverScreen = 3, optionsScreenEasy = 4, optionsScreenMedium = 5,
			optionsScreenHard = 6, leaderboardsScreen = 7;
	public static final int pauseMenu = 8;
	public static final int mainGuy1Right = 9, mainGuy2Right = 10, mainGuy3Right = 11,
			mainGuy1Left = 12, mainGuy2Left = 13, mainGuy3Left = 14, mainGuyCrouchRight = 15,
			mainGuyCrouchLeft = 16, mainGuyJumpRight = 17, mainGuyJumpLeft = 18;
	public static final int projectile = 19;
	public static final int powerUpExtraLife = 20, powerUpScoreMod = 21, powerUpJumpBoost = 22;
	public static final int onScreenLeftButton = 23, onScreenRightButton = 24, onScreenUpButton = 25,
			onScreenDownButton = 26, onScreenPauseButton = 27, onScreenPlayButton = 28;
	public static final int easy = 29, darkEasy = 30, medium = 31, darkMedium = 32, hard = 33,
			darkHard = 34, normal = 35, darkNormal = 36, sheep = 37, darkSheep = 38;
	public static final int gameStartUnPause3 = 39, gameStartUnPause2 = 40, gameStartUnPause1 = 41,
			gameStartUnPauseGo = 42;
	public static final int numTextures = 43;

	public static void loadAllGameTextures(GameHandler game) {
		textures = new Texture[numTextures];
		loadSkinTextures(game);
		textures[splashScreen] = new Texture(Gdx.files.internal("SplashScreen.png"));
		textures[pauseMenu] = new Texture(Gdx.files.internal("PauseMenu.png"));
		textures[powerUpExtraLife] = new Texture(Gdx.files.internal("PowerUps/extraLifeIcon.png"));
		textures[powerUpScoreMod] = new Texture(Gdx.files.internal("PowerUps/scoreModifierIcon.png"));
		textures[powerUpJumpBoost] = new Texture(Gdx.files.internal("PowerUps/jumpBoostIcon.png"));
		textures[onScreenLeftButton] = new Texture(Gdx.files.internal("Buttons/OnScreenLeft.png"));
		textures[onScreenRightButton] = new Texture(Gdx.files.internal("Buttons/OnScreenRight.png"));
		textures[onScreenUpButton] = new Texture(Gdx.files.internal("Buttons/OnScreenUp.png"));
		textures[onScreenDownButton] = new Texture(Gdx.files.internal("Buttons/OnScreenDown.png"));
		textures[onScreenPauseButton] = new Texture(Gdx.files.internal("Buttons/OnScreenPause.png"));
		textures[onScreenPlayButton] = new Texture(Gdx.files.internal("Buttons/OnScreenPlay.png"));
		textures[easy] = new Texture(Gdx.files.internal("Labels/Easy.png"));
		textures[darkEasy] = new Texture(Gdx.files.internal("Labels/EasyDarkened.png"));
		textures[medium] = new Texture(Gdx.files.internal("Labels/Medium.png"));
		textures[darkMedium] = new Texture(Gdx.files.internal("Labels/MediumDarkened.png"));
		textures[hard] = new Texture(Gdx.files.internal("Labels/Hard.png"));
		textures[darkHard] = new Texture(Gdx.files.internal("Labels/HardDarkened.png"));
		textures[normal] = new Texture(Gdx.files.internal("Labels/Normal.png"));
		textures[darkNormal] = new Texture(Gdx.files.internal("Labels/NormalDarkened.png"));
		textures[sheep] = new Texture(Gdx.files.internal("Labels/Sheep.png"));
		textures[darkSheep] = new Texture(Gdx.files.internal("Labels/SheepDarkened.png"));
		textures[gameStartUnPause3] = new Texture(Gdx.files.internal("Labels/GSUP_3.png"));
		textures[gameStartUnPause2] = new Texture(Gdx.files.internal("Labels/GSUP_2.png"));
		textures[gameStartUnPause1] = new Texture(Gdx.files.internal("Labels/GSUP_1.png"));
		textures[gameStartUnPauseGo] = new Texture(Gdx.files.internal("Labels/GSUP_Go.png"));
	}

	public static void disposeAllGameTextures() {
		for (Texture t : textures) t.dispose();
	}

	public static void loadSkinTextures(GameHandler game) {
		String skin = Skin.getSkinName(game.getUser().getCurrentSkin());

		textures[mainMenuScreen] = new Texture(Gdx.files.internal(skin + "/Screens/MainMenu.png"));
		textures[mainGameScreen] = new Texture(Gdx.files.internal(skin + "/Screens/MainGame.png"));
		textures[gameOverScreen] = new Texture(Gdx.files.internal(skin + "/Screens/GameOver.png"));
		textures[optionsScreenEasy] = new Texture(Gdx.files.internal(skin + "/Screens/OptionsEasy.png"));
		textures[optionsScreenMedium] = new Texture(Gdx.files.internal(skin + "/Screens/OptionsMedium.png"));
		textures[optionsScreenHard] = new Texture(Gdx.files.internal(skin + "/Screens/OptionsHard.png"));
		textures[leaderboardsScreen] = new Texture(Gdx.files.internal(skin + "/Screens/Leaderboards.png"));
		textures[mainGuy1Left] = new Texture(Gdx.files.internal(skin + "/MainGuy/1Left.png"));
		textures[mainGuy2Left] = new Texture(Gdx.files.internal(skin + "/MainGuy/2Left.png"));
		textures[mainGuy3Left] = new Texture(Gdx.files.internal(skin + "/MainGuy/3Left.png"));
		textures[mainGuy1Right] = new Texture(Gdx.files.internal(skin + "/MainGuy/1Right.png"));
		textures[mainGuy2Right] = new Texture(Gdx.files.internal(skin + "/MainGuy/2Right.png"));
		textures[mainGuy3Right] = new Texture(Gdx.files.internal(skin + "/MainGuy/3Right.png"));
		textures[mainGuyCrouchLeft] = new Texture(Gdx.files.internal(skin + "/MainGuy/CrouchLeft.png"));
		textures[mainGuyCrouchRight] = new Texture(Gdx.files.internal(skin + "/MainGuy/CrouchRight.png"));
		textures[mainGuyJumpLeft] = new Texture(Gdx.files.internal(skin + "/MainGuy/JumpLeft.png"));
		textures[mainGuyJumpRight] = new Texture(Gdx.files.internal(skin + "/MainGuy/JumpRight.png"));
		textures[projectile] = new Texture(Gdx.files.internal(skin + "/Projectile.png"));
	}


}