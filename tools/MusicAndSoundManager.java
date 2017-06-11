package net.ocps.tchs.MDGame.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicAndSoundManager {
	
	public MusicAndSoundManager() {
		Music music = Gdx.audio.newMusic(Gdx.files.internal("soundsAndMusic/iWantToBeAHero.ogg"));
		music.setLooping(true);
		music.play();

	}
	
	//model it after the texture manager
}