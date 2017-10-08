package com.matthewgarrison.tools;

import com.matthewgarrison.GameHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.matthewgarrison.enums.*;

public class TextureManager {
	public static Texture[] textures;

	public static void loadAllGameTextures(GameHandler game) {
		textures = new Texture[TextureEnum.values().length];
		loadSkinTextures(game);
		textures[TextureEnum.SPLASH_SCREEN.ordinal()] = new Texture(Gdx.files.internal("SplashScreen.png"));
		textures[TextureEnum.PAUSE_MENU.ordinal()] = new Texture(Gdx.files.internal("PauseMenu.png"));
		textures[TextureEnum.POWERUP_EXTRA_LIFE.ordinal()] = new Texture(Gdx.files.internal("PowerUps/extraLifeIcon.png"));
		textures[TextureEnum.POWERUP_SCORE_MOD.ordinal()] = new Texture(Gdx.files.internal("PowerUps/scoreModifierIcon.png"));
		textures[TextureEnum.POWERUP_JUMP_BOOST.ordinal()] = new Texture(Gdx.files.internal("PowerUps/jumpBoostIcon.png"));
		textures[TextureEnum.ONSCREEN_LEFT_BUTTON.ordinal()] = new Texture(Gdx.files.internal("Buttons/OnScreenLeft.png"));
		textures[TextureEnum.ONSCREEN_RIGHT_BUTTON.ordinal()] = new Texture(Gdx.files.internal("Buttons/OnScreenRight.png"));
		textures[TextureEnum.ONSCREEN_UP_BUTTON.ordinal()] = new Texture(Gdx.files.internal("Buttons/OnScreenUp.png"));
		textures[TextureEnum.ONSCREEN_DOWN_BUTTON.ordinal()] = new Texture(Gdx.files.internal("Buttons/OnScreenDown.png"));
		textures[TextureEnum.ONSCREEN_PAUSE_BUTTON.ordinal()] = new Texture(Gdx.files.internal("Buttons/OnScreenPause.png"));
		textures[TextureEnum.ONSCREEN_PLAY_BUTTON.ordinal()] = new Texture(Gdx.files.internal("Buttons/OnScreenPlay.png"));
		textures[TextureEnum.EASY.ordinal()] = new Texture(Gdx.files.internal("Labels/Easy.png"));
		textures[TextureEnum.EASY_SELECTED.ordinal()] = new Texture(Gdx.files.internal("Labels/EasyDarkened.png"));
		textures[TextureEnum.MEDIUM.ordinal()] = new Texture(Gdx.files.internal("Labels/Medium.png"));
		textures[TextureEnum.MEDIUM_SELECTED.ordinal()] = new Texture(Gdx.files.internal("Labels/MediumDarkened.png"));
		textures[TextureEnum.HARD.ordinal()] = new Texture(Gdx.files.internal("Labels/Hard.png"));
		textures[TextureEnum.HARD_SELECTED.ordinal()] = new Texture(Gdx.files.internal("Labels/HardDarkened.png"));
		textures[TextureEnum.NORMAL.ordinal()] = new Texture(Gdx.files.internal("Labels/Normal.png"));
		textures[TextureEnum.NORMAL_SELECTED.ordinal()] = new Texture(Gdx.files.internal("Labels/NormalDarkened.png"));
		textures[TextureEnum.SHEEP.ordinal()] = new Texture(Gdx.files.internal("Labels/Sheep.png"));
		textures[TextureEnum.SHEEP_SELECTED.ordinal()] = new Texture(Gdx.files.internal("Labels/SheepDarkened.png"));
		textures[TextureEnum.GAME_START_UNPAUSE_3.ordinal()] = new Texture(Gdx.files.internal("Labels/GSUP_3.png"));
		textures[TextureEnum.GAME_START_UNPAUSE_2.ordinal()] = new Texture(Gdx.files.internal("Labels/GSUP_2.png"));
		textures[TextureEnum.GAME_START_UNPAUSE_1.ordinal()] = new Texture(Gdx.files.internal("Labels/GSUP_1.png"));
		textures[TextureEnum.GAME_START_UNPAUSE_GO.ordinal()] = new Texture(Gdx.files.internal("Labels/GSUP_Go.png"));
	}

	public static void disposeAllGameTextures() {
		for (Texture t : textures) t.dispose();
	}

	public static void loadSkinTextures(GameHandler game) {
		String skin = game.getUser().getCurrentSkin().name();

		textures[TextureEnum.MAIN_MENU_SCREEN.ordinal()] = new Texture(Gdx.files.internal(skin + "/Screens/MainMenu.png"));
		textures[TextureEnum.MAIN_GAME_SCREEN.ordinal()] = new Texture(Gdx.files.internal(skin + "/Screens/MainGame.png"));
		textures[TextureEnum.GAME_OVER_SCREEN.ordinal()] = new Texture(Gdx.files.internal(skin + "/Screens/GameOver.png"));
		textures[TextureEnum.OPTIONS_SCREEN_EASY.ordinal()] = new Texture(Gdx.files.internal(skin + "/Screens/OptionsEasy.png"));
		textures[TextureEnum.OPTIONS_SCREEN_MEDIUM.ordinal()] = new Texture(Gdx.files.internal(skin + "/Screens/OptionsMedium.png"));
		textures[TextureEnum.OPTIONS_SCREEN_HARD.ordinal()] = new Texture(Gdx.files.internal(skin + "/Screens/OptionsHard.png"));
		textures[TextureEnum.LEADERBOARDS_SCREEN.ordinal()] = new Texture(Gdx.files.internal(skin + "/Screens/Leaderboards.png"));
		textures[TextureEnum.MAINGUY_LEFT_1.ordinal()] = new Texture(Gdx.files.internal(skin + "/MainGuy/1Left.png"));
		textures[TextureEnum.MAINGUY_LEFT_2.ordinal()] = new Texture(Gdx.files.internal(skin + "/MainGuy/2Left.png"));
		textures[TextureEnum.MAINGUY_LEFT_3.ordinal()] = new Texture(Gdx.files.internal(skin + "/MainGuy/3Left.png"));
		textures[TextureEnum.MAINGUY_RIGHT_1.ordinal()] = new Texture(Gdx.files.internal(skin + "/MainGuy/1Right.png"));
		textures[TextureEnum.MAINGUY_RIGHT_2.ordinal()] = new Texture(Gdx.files.internal(skin + "/MainGuy/2Right.png"));
		textures[TextureEnum.MAINGUY_RIGHT_3.ordinal()] = new Texture(Gdx.files.internal(skin + "/MainGuy/3Right.png"));
		textures[TextureEnum.MAINGUY_LEFT_CROUCH.ordinal()] = new Texture(Gdx.files.internal(skin + "/MainGuy/CrouchLeft.png"));
		textures[TextureEnum.MAINGUY_RIGHT_CROUCH.ordinal()] = new Texture(Gdx.files.internal(skin + "/MainGuy/CrouchRight.png"));
		textures[TextureEnum.MAINGUY_LEFT_JUMP.ordinal()] = new Texture(Gdx.files.internal(skin + "/MainGuy/JumpLeft.png"));
		textures[TextureEnum.MAINGUY_RIGHT_JUMP.ordinal()] = new Texture(Gdx.files.internal(skin + "/MainGuy/JumpRight.png"));
		textures[TextureEnum.PROJECTILE.ordinal()] = new Texture(Gdx.files.internal(skin + "/Projectile.png"));
	}


}